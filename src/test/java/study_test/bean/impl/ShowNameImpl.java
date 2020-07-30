package study_test.bean.impl;

import study.factory.auto.MiniComponent;
import study_test.bean.ShowName;

@MiniComponent
public class ShowNameImpl implements ShowName {
    @Override
    public String name() {
        return "hdzi";
    }
}
