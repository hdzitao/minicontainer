package study_test.aop;


import study.aop.After;
import study.aop.Aop;
import study.aop.CutPoint;
import study.factory.auto.MiniComponent;

@MiniComponent
@Aop
public class ChangeName {
    @After(path = "study_test/bean/impl/ShowNameImpl", method = "name")
    public void change(CutPoint point) {
        point.setResult("aop: " + point.getResult());
    }
}
