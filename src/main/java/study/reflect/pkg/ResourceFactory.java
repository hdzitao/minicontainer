package study.reflect.pkg;

/**
 * Created by taojinhou on 2019/11/8.
 */
final class ResourceFactory {
    private ResourceFactory() {
    }

    static ResourceInfo getResource(String name, ClassLoader classLoader) {
        if (ClassInfo.isMatch(name)) {
            return new ClassInfo(name, classLoader);
        } else {
            return new ResourceInfo(name, classLoader);
        }
    }
}
