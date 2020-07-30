package study.factory.auto;

import study.factory.BeanFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

/**
 * 类型注入工具
 * Created by taojinhou on 2019/4/16.
 */
public class AutoInfo implements InjectInfo {
    // 注入类型
    private final Class<?> clazz;

    private AutoInfo(Autowired autowired, Class<?> clazz) {
        // 不指定注入类型就自定注入,也可以自己指定
        this.clazz = autowired != null && autowired.value() != Autowired.AUTOWIRED_DEFAULT_CLASS ? autowired.value() : clazz;
    }

    public static AutoInfo forField(Field field) {
        return new AutoInfo(field.getAnnotation(Autowired.class), field.getType());
    }

    public static AutoInfo forParameter(Parameter parameter) {
        return new AutoInfo(parameter.getAnnotation(Autowired.class), parameter.getType());
    }

    @Override
    public Object getBean(BeanFactory factory) {
        return factory.getBean(this.clazz);
    }
}
