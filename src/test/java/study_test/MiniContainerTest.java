package study_test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import study.MiniContainer;
import study.factory.BeanFactory;
import study.factory.auto.Autowired;
import study.factory.auto.MiniComponent;
import study_test.bean.AskName;
import study_test.bean.ShowName;
import study_test.bean.impl.ShowNameImpl;
import test.MiniContainerRunner;

import static org.junit.Assert.*;

@RunWith(MiniContainerRunner.class)
@MiniComponent
public class MiniContainerTest {
    @Autowired
    private BeanFactory factory;

    @Test
    public void app() {
        assertNotNull(factory);
        assertSame(factory, factory.getBean(BeanFactory.class));
        assertSame(factory, factory.getBean(MiniContainer.class));
    }

    @Test
    public void _interface() {
        ShowName showName = factory.getBean(ShowName.class);
        assertNotNull(showName);

        ShowNameImpl showNameImpl = factory.getBean(ShowNameImpl.class);
        assertSame(showName, showNameImpl);
    }

    @Test
    public void aopChangeResult() {
        ShowName bean = factory.getBean(ShowName.class);
        assertEquals("aop: hdzi", bean.name());
    }

    @Test
    public void autoContractor() {
        AskName askName = factory.getBean(AskName.class);
        assertNotNull(askName);
        assertSame(factory.getBean(ShowName.class), askName.getName());
        assertEquals(9, askName.getAskNum());
    }
}
