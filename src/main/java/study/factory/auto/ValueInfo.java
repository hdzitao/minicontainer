package study.factory.auto;


import study.factory.BeanFactory;
import study.factory.auto.value.ConfigValueParser;
import study.factory.auto.value.JavaValueParser;
import study.factory.auto.value.ValueParser;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.regex.Matcher;

/**
 * Created by taojinhou on 2019/4/16.
 */
public class ValueInfo implements InjectInfo {
    private final ValueParser parser;

    private ValueInfo(Value value, Type type) {
        Matcher matcher;
        if ((matcher = ConfigValueParser.CONFIG_PATTERN.matcher(value.value())).find()) {
            this.parser = new ConfigValueParser(matcher.group(1), type);
        } else {
            this.parser = new JavaValueParser(value.value(), type);
        }
    }

    public static ValueInfo forField(Field field) {
        return new ValueInfo(field.getAnnotation(Value.class), field.getGenericType());
    }

    public static ValueInfo forParameter(Parameter parameter) {
        return new ValueInfo(parameter.getAnnotation(Value.class), parameter.getParameterizedType());
    }

    @Override
    public Object getBean(BeanFactory factory) {
        return this.parser.getValue(factory);
    }
}
