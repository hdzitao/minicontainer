package study;

import lombok.SneakyThrows;
import study.factory.*;
import study.factory.auto.MiniComponent;
import study.reflect.PackageResolver;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by taojinhou on 2019/4/16.
 */
public class MiniContainer implements BeanFactory {

    private final MiniBeanFactory factory;

    private MiniContainer(MiniBeanFactory factory) {
        this.factory = factory;
    }

    @SneakyThrows
    public static MiniContainer app(Class<?> appClass) {
        MiniBeanFactory factory = createFactory(appClass);
        factory.addBeanConfigure(BeanConfigure.forBean(MiniContainer.class, new MiniContainer(factory))); // 注册自己
        return factory.getBean(MiniContainer.class);
    }

    private static MiniBeanFactory createFactory(Class<?> app) {
        LinkedHashMap<String, ClassLoader> components = new LinkedHashMap<>();
        // 默认组件
        components.put(MiniContainer.class.getPackage().getName(), MiniContainer.class.getClassLoader());
        // 用户组件
        components.put(app.getPackage().getName(), app.getClassLoader());
        // 用户附加的组件
        MiniScanBase scanBase = app.getAnnotation(MiniScanBase.class);
        if (scanBase != null) {
            for (String comp : scanBase.value()) {
                components.put(comp, app.getClassLoader());
            }
        }
        // 扫描class文件
        Set<Class<?>> classes = components.entrySet().stream()
                .flatMap(entry -> PackageResolver.scanClass(entry.getKey(), entry.getValue(),
                        clazz -> clazz.isAnnotationPresent(MiniComponent.class)).stream())
                .collect(Collectors.toSet());
        // 新建BeanFactory
        MiniBeanFactory factory = new MiniBeanFactory();
        classes.forEach(clazz -> factory.addBeanConfigure(
                BeanConfigure.forClass(clazz, clazz.getAnnotation(MiniComponent.class).singleton())));
        factory.finish();

        return factory;
    }

    @Override
    public <T> T getBean(Class<T> clazz) throws BeanCreatingException {
        return this.factory.getBean(clazz);
    }
}