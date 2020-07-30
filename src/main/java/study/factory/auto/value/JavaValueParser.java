package study.factory.auto.value;

import study.factory.BeanFactory;

/**
 * java值分析器
 * Created by taojinhou on 2020/1/10.
 */
public class JavaValueParser implements ValueParser {
    private final String value;
    private final Class<?> clazz;

    public JavaValueParser(String value, Class<?> clazz) {
        this.value = value;
        this.clazz = clazz;
    }

    @Override
    public Object getValue(BeanFactory factory) {
        return factory.getBean(ValueConvertor.class).convert(this.clazz, value);
    }
}
