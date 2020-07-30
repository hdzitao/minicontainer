package study.factory.auto.value;


/**
 * 把值转换成java对象
 * Created by taojinhou on 2020/3/19.
 */
public interface ValueConvertor {
    Object convert(Class<?> type, String value);
}
