package test;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import study.MiniContainer;

/**
 * Created by taojinhou on 2020/7/30.
 */
public class MiniContainerRunner extends BlockJUnit4ClassRunner {
    public MiniContainerRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected Object createTest() {
        Class<?> javaClass = getTestClass().getJavaClass();
        return MiniContainer.app(javaClass).getBean(javaClass);
    }
}
