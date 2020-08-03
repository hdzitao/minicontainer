package study.factory.configure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.factory.auto.MiniComponent;
import study.reflect.pkg.ClassInfo;
import study.reflect.pkg.Scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        this.components.forEach(entry -> {
            new Scanner(entry.getPkg(), entry.getClassLoader()).scan().stream()
                    .filter(resourceInfo -> resourceInfo instanceof ClassInfo)
                    .map(classInfo -> ((ClassInfo) classInfo).load())
                    .filter(Objects::nonNull)
                    .forEach(clazz -> {
                        MiniComponent component = clazz.getAnnotation(MiniComponent.class);
                        if (component != null) {
                            factory.addBeanConfigure(BeanConfigure.forClass(clazz, component.singleton()));
                        }
                    });
        });
    }
}
