package study.factory.auto.value;


import study.factory.BeanFactory;

/**
 * 值分析器
 * Created by taojinhou on 2020/1/10.
 */
public interface ValueParser {
    Object getValue(BeanFactory factory);
}
