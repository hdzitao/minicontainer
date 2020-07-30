package study.factory.auto;

import lombok.SneakyThrows;
import study.factory.BeanConfigure;
import study.factory.BeanCreatingException;
import study.factory.BeanFactory;
import study.factory.processor.BeanAfterProcessor;
import study.reflect.ClassResolver;

import java.lang.reflect.Field;

/**
 * Created by taojinhou on 2018/11/29.
 */
@MiniComponent
@AutowiredRegister
public class FieldAutowiredProcessor implements BeanAfterProcessor {
    @Override
    @SneakyThrows
    public Object after(BeanFactory factory, BeanConfigure beanConfigure, Object bean) throws BeanCreatingException {
        for (Field field : ClassResolver.getFields(beanConfigure.getBeanClass())) {
            if (field.isAnnotationPresent(Autowired.class)) {
                ClassResolver.set(field, bean, AutoInfo.forField(field).getBean(factory));
            } else if (field.isAnnotationPresent(Value.class)) {
                ClassResolver.set(field, bean, ValueInfo.forField(field).getBean(factory));
            }
        }

        return bean;
    }
}
