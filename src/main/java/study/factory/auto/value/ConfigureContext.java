package study.factory.auto.value;

/**
 * 怎么取配置文件
 */
public interface ConfigureContext {
    // 根据配置取值
    String get(String path);
}
