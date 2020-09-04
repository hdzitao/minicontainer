package study.factory.configure;

import study.factory.BeanFactory;

public interface BeanConstructor {
    Object getBeanByConstructor(BeanFactory factory);
}
