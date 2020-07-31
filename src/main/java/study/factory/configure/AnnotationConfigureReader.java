package study.factory.configure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.factory.auto.MiniComponent;
import study.reflect.PackageResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by taojinhou on 2020/7/31.
 */
public class AnnotationConfigureReader implements BeanConfigureReader {
    @AllArgsConstructor
    private static class Component {
        @Getter
        private final String pkg;
        @Getter
        private final ClassLoader classLoader;
    }

    private final List<Component> components = new ArrayList<>();

    public void addComponent(String pkg, ClassLoader classLoader) {
        this.components.add(new Component(pkg, classLoader));
    }

    @Override
    public void register(ConfigurableBeanFactory factory) {
        // 扫描class文件
        Set<Class<?>> classes = this.components.stream()
                .flatMap(entry -> PackageResolver.scanClass(entry.getPkg(), entry.getClassLoader(),
                        clazz -> clazz.isAnnotationPresent(MiniComponent.class)).stream())
                .collect(Collectors.toSet());
        // 注册
        classes.forEach(clazz -> factory.addBeanConfigure(
                BeanConfigure.forClass(clazz, clazz.getAnnotation(MiniComponent.class).singleton())));
    }
}
