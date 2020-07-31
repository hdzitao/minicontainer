package study;

import lombok.SneakyThrows;
import study.factory.BeanCreatingException;
import study.factory.BeanFactory;
import study.factory.MiniBeanFactory;
import study.factory.configure.AnnotationConfigureReader;
import study.factory.configure.BeanConfigure;
import study.factory.configure.MiniScanBase;

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
        AnnotationConfigureReader configureReader = new AnnotationConfigureReader();
        // 默认组件
        configureReader.addComponent(MiniContainer.class.getPackage().getName(), MiniContainer.class.getClassLoader());
        // 用户组件
        configureReader.addComponent(app.getPackage().getName(), app.getClassLoader());
        // 用户附加的组件
        MiniScanBase scanBase = app.getAnnotation(MiniScanBase.class);
        if (scanBase != null) {
            for (String comp : scanBase.value()) {
                configureReader.addComponent(comp, app.getClassLoader());
            }
        }
        // 新建BeanFactory
        return new MiniBeanFactory(configureReader).finish();
    }

    @Override
    public <T> T getBean(Class<T> clazz) throws BeanCreatingException {
        return this.factory.getBean(clazz);
    }
}