package study;

import lombok.SneakyThrows;
import study.factory.BeanCreatingException;
import study.factory.BeanFactory;
import study.factory.MiniBeanFactory;
import study.factory.auto.MiniConfigureReader;
import study.factory.configure.AnnotationConfigureRegister;
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
        AnnotationConfigureRegister configureRegister = new AnnotationConfigureRegister(new MiniConfigureReader());
        // 系统组件
        configureRegister.addComponent(MiniContainer.class.getPackage().getName(), MiniContainer.class.getClassLoader());
        // 用户组件
        configureRegister.addComponent(app.getPackage().getName(), app.getClassLoader());
        // 用户附加的组件
        MiniScanBase scanBase = app.getAnnotation(MiniScanBase.class);
        if (scanBase != null) {
            for (String comp : scanBase.value()) {
                configureRegister.addComponent(comp, app.getClassLoader());
            }
        }
        // 新建BeanFactory
        return new MiniBeanFactory(configureRegister).finish();
    }

    @Override
    public <T> T getBean(Class<T> clazz) throws BeanCreatingException {
        return this.factory.getBean(clazz);
    }
}