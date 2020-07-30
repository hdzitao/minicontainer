package study.factory.auto.value;


import lombok.SneakyThrows;
import study.factory.BeanFactory;

import java.util.regex.Pattern;

/**
 * 配置分析器
 * Created by taojinhou on 2020/1/10.
 */
public class ConfigValueParser implements ValueParser {
    public static final Pattern CONFIG_PATTERN = Pattern.compile("^\\$\\{(.+)}$");

    private String configKey;
    private Class<?> clazz;

    public ConfigValueParser(String configKey, Class<?> clazz) {
        this.configKey = configKey;
        this.clazz = clazz;
    }

    @Override
    @SneakyThrows
    public Object getValue(BeanFactory factory) {
        return new JavaValueParser(factory.getBean(ConfigureContext.class).get(this.configKey), this.clazz)
                .getValue(factory);
    }
}
