package study.factory;

import lombok.SneakyThrows;
import study.factory.auto.InjectInfo;
import study.factory.configure.BeanConfigure;
import study.factory.configure.BeanConfigureRegister;
import study.factory.configure.ConfigurableBeanFactory;
import study.factory.processor.BeanAfterConfigurator;
import study.factory.processor.BeanAfterProcessor;
import study.factory.processor.FactoryAfterEntry;
import study.factory.processor.FactoryAfterRegister;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 默认 BeanFactory
 * Created by taojinhou on 2019/4/16.
 */
public class MiniBeanFactory implements ConfigurableBeanFactory, BeanAfterConfigurator {
    // bean配置
    private final Map<Class<?>, BeanConfigure> beanConfigureMap;
    // 单例
    private final Map<Class<?>, Object> singletonBeanMap;
    // 正在创建
    private final Set<Class<?>> inCreatingBeans;
    // bean后置处理器
    private final List<BeanAfterProcessor> beanAfterProcessors;
    // factory后置处理器
    private final PriorityQueue<FactoryAfterEntry> factoryAfterProcessorHolder;
    // bean配置获取器
    private final BeanConfigureRegister configureRegister;


    public MiniBeanFactory(BeanConfigureRegister configureRegister) {
        // 初始化配置获取器
        this.configureRegister = configureRegister;
        // 线程安全
        this.beanConfigureMap = new ConcurrentHashMap<>();
        this.singletonBeanMap = new ConcurrentHashMap<>();
        this.inCreatingBeans = Collections.newSetFromMap(new ConcurrentHashMap<>());
        // 线程不安全
        this.beanAfterProcessors = new LinkedList<>();
        this.factoryAfterProcessorHolder = new PriorityQueue<>(); // factory后置处理器有优先级
    }

    /**
     * 获取 bean
     *
     * @param requiredClass
     * @param <T>
     * @return
     * @throws BeanCreatingException
     */
    @Override
    public <T> T getBean(Class<T> requiredClass) throws BeanCreatingException {
        BeanConfigure configure = findBeanConfigure(requiredClass);
        if (configure == null) {
            throw new BeanCreatingException(requiredClass, "can't find bean's configure");
        }
        return resolveBean(configure);
    }

    /**
     * 根据 configure获取bean
     *
     * @param configure
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T resolveBean(BeanConfigure configure) {
        Object bean;
        if (configure.isSingleton()) {
            Class<?> beanClass = configure.getBeanClass();
            if ((bean = this.singletonBeanMap.get(beanClass)) == null) {
                synchronized (configure.getSingletonLock()) {
                    if ((bean = this.singletonBeanMap.get(beanClass)) == null) {
                        bean = createBean(configure);
                        this.singletonBeanMap.put(beanClass, bean);
                    }
                }
            }
        } else {
            bean = createBean(configure);
        }

        return (T) bean;
    }

    /**
     * 执行BeanFactory初始化
     * 1. 读取bean配置
     * 2. 执行factory后置处理器
     */
    public MiniBeanFactory finish() {
        // 读取bean配置
        this.configureRegister.register(this);
        // 执行factory后置处理器
        while (!this.factoryAfterProcessorHolder.isEmpty()) {
            FactoryAfterEntry entry = this.factoryAfterProcessorHolder.poll();
            getBean(entry.getProcessor()).after(this, entry.getConfigure());
        }

        return this;
    }

    /**
     * 添加bean配置
     *
     * @param configure
     */
    @Override
    public void addBeanConfigure(BeanConfigure configure) {
        this.beanConfigureMap.put(configure.getBeanClass(), configure);

        /*
         * 处理factory后置处理器
         * 1. bean对象有FactoryAfterRegister注解或者它的注解被FactoryAfterRegister注解:
         *      @FactoryAfterRegister
         *      class A
         *      或者
         *      @FactoryAfterRegister
         *      @interface Do
         *
         *      @Do
         *      class A
         * 2. 取出FactoryAfterRegister指定的FactoryAfterProcessor和bean的配置形成FactoryAfterEntry
         * 3. finish函数取出FactoryAfterEntry执行 FactoryAfterProcessor.after(beanfactory, bean配置)
         */
        Class<?> beanClass = configure.getBeanClass();
        for (Annotation annotation : beanClass.getAnnotations()) {
            FactoryAfterRegister after;
            if (annotation instanceof FactoryAfterRegister) {
                // 直接包含FactoryAfterRegister注解
                after = (FactoryAfterRegister) annotation;
            } else if ((after = annotation.annotationType().getAnnotation(FactoryAfterRegister.class)) == null) {
                // 有包含FactoryAfterRegister注解的注解
                continue;
            }

            this.factoryAfterProcessorHolder.add(new FactoryAfterEntry(configure, after.value(), after.priority()));
        }
    }

    /**
     * 添加bean后置处理器
     *
     * @param processor
     */
    @Override
    public void addBeanAfterProcessor(BeanAfterProcessor processor) {
        this.beanAfterProcessors.add(processor);
    }

    /**
     * 创建bean
     *
     * @param configure
     * @return
     * @throws BeanCreatingException
     */
    @SneakyThrows
    private Object createBean(BeanConfigure configure) throws BeanCreatingException {
        Class<?> beanClass = configure.getBeanClass();
        Object bean;
        if ((bean = configure.getBean()) == null) {
            // 不是直接包含bean的配置要自己new

            // 加入inCreatingBeans,如果加入失败则bean本来就在inCreatingBeans中,形成循环依赖
            if (!this.inCreatingBeans.add(beanClass)) {
                throw new BeanCreatingException(beanClass, "Circular dependence");
            }

            // 获取构造函数
            Constructor<?> constructor = configure.getConstructor();
            if (constructor != null) {
                // 处理参数注入
                Object[] args = InjectInfo.getParameterBeans(constructor, this);
                bean = constructor.newInstance(args);
            } else {
                bean = beanClass.newInstance();
            }
        }

        // 执行 bean 后置处理器
        for (BeanAfterProcessor processor : this.beanAfterProcessors) {
            bean = processor.after(this, configure, bean);
        }

        this.inCreatingBeans.remove(beanClass);

        return bean;
    }

    /**
     * 根据requiredClass找相应的配置
     *
     * @param requiredClass
     * @return
     */
    private BeanConfigure findBeanConfigure(Class<?> requiredClass) {
        // 如果是接口，搜索整个bean空间
        if (requiredClass.isInterface()) {
            List<BeanConfigure> configures = new ArrayList<>();

            for (BeanConfigure configure : this.beanConfigureMap.values()) {
                if (requiredClass.isAssignableFrom(configure.getBeanClass())) {
                    configures.add(configure);
                }
            }

            if (configures.isEmpty()) {
                return null;
            } else if (configures.size() > 1) {
                throw new BeanCreatingException(requiredClass, "There are multiple interface implementations:"
                        + configures.stream().map(configure -> configure.getBeanClass().getName()).collect(Collectors.joining(",")));
            } else {
                return configures.get(0);
            }
        } else {
            return this.beanConfigureMap.get(requiredClass);
        }
    }
}
