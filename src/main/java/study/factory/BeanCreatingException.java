package study.factory;

/**
 * 创建bean过程中出现的异常
 * Created by taojinhou on 2020/3/16.
 */
public class BeanCreatingException extends RuntimeException {
    public BeanCreatingException(Class<?> requiredClass, String reason) {
        super("Failed to create " + requiredClass.getName() + ":\n" + reason);
    }

    public BeanCreatingException(String reason) {
        super("Failed to create bean:" + reason);
    }
}
