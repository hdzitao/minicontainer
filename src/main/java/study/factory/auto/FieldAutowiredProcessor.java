package study.factory.auto;

import lombok.SneakyThrows;
import study.factory.BeanConfigure;
import study.factory.BeanCreatingException;
import study.factory.BeanFactory;
import study.factory.processor.BeanAfterProcessor;
import study.reflect.ClassResolver;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Created by taojinhou on 2018/11/29.
 */
@MiniComponent
@AutowiredRegister
public class FieldAutowiredProcessor implements BeanAfterProcessor {
    @Override
    public Object after(BeanFactory factory, BeanConfigure beanConfigure, Object bean) throws BeanCreatingException {
        Arrays.stream(ClassResolver.getFields(beanConfigure.getBeanClass()))
                .forEach(new Consumer<Field>() {
                    @Override
                    @SneakyThrows
                    public void accept(Field field) {
                        if (field.isAnnotationPresent(Autowired.class)) {
                            field.setAccessible(true);
                            field.set(bean, AutoInfo.forField(field).getBean(factory));
                        } else if (field.isAnnotationPresent(Value.class)) {
                            field.setAccessible(true);
                            field.set(bean, ValueInfo.forField(field).getBean(factory));
                        }
                    }
                });

        return bean;
    }
}
