package study.factory.configure;

/**
 * Created by taojinhou on 2020/8/4.
 */
public interface BeanConfigureReader<Definition> {
    BeanConfigure read(Definition definition);
}
