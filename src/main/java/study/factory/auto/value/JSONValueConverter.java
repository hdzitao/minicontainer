package study.factory.auto.value;


import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import study.factory.auto.MiniComponent;

import java.lang.reflect.Type;

/**
 * Created by taojinhou on 2020/3/20.
 */
@MiniComponent
public class JSONValueConverter {
    @SneakyThrows
    public Object convert(Type type, String value) {
        Object convert = JSON.parse(value);
        if (convert instanceof JSON) {
            convert = ((JSON) convert).toJavaObject(type);
        }

        return convert;
    }
}
