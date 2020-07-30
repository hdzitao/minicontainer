package study.factory.auto;

import lombok.SneakyThrows;
import study.factory.BeanConfigure;
import study.factory.BeanCreatingException;
import study.factory.BeanFactory;
import study.factory.processor.BeanAfterProcessor;
import study.reflect.ClassResolver;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Created by taojinhou on 2018/11/29.
 */
@MiniComponent
@AutowiredRegister
public class MethodAutowiredProcessor implements BeanAfterProcessor {
    @Override
    public Object after(BeanFactory factory, BeanConfigure beanConfigure, Object bean) throws BeanCreatingException {
        Arrays.stream(ClassResolver.getMethods(beanConfigure.getBeanClass()))
                .filter(method -> method.isAnnotationPresent(Autowired.class))
                .forEach(new Consumer<Method>() {
                    @Override
                    @SneakyThrows
                    public void accept(Method method) {
                        Object[] args = InjectInfo.getParameterBeans(method, factory);
                        method.setAccessible(true);
                        method.invoke(bean, args);
                    }
                });

        return bean;
    }
}
