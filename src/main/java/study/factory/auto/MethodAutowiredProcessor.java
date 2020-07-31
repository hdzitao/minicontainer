package study.factory.auto;

import study.factory.BeanCreatingException;
import study.factory.BeanFactory;
import study.factory.configure.BeanConfigure;
import study.factory.processor.BeanAfterProcessor;
import study.reflect.ClassResolver;

import java.util.Arrays;

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
                .forEach(method -> {
                    Object[] args = InjectInfo.getParameterBeans(method, factory);
                    ClassResolver.send(method, bean, args);
                });

        return bean;
    }
}
