package study.factory.auto.value;

import study.factory.BeanFactory;

import java.lang.reflect.Type;

/**
 * java值分析器
 * Created by taojinhou on 2020/1/10.
 */
public class JavaValueParser implements ValueParser {
    private final String value;
    private final Type type;

    public JavaValueParser(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public Object getValue(BeanFactory factory) {
        return factory.getBean(JSONValueConverter.class).convert(this.type, value);
    }
}
