package study.factory.auto.value;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import study.factory.BeanCreatingException;
import study.factory.auto.MiniComponent;

import java.io.InputStream;

/**
 * todo 随便写写不知道好不好用
 * Created by taojinhou on 2020/3/20.
 */
@MiniComponent
public class JSONConfigureContext {
    private final JSONObject configure;

    @SneakyThrows
    public JSONConfigureContext() {
        InputStream resource = getClass().getClassLoader().getResourceAsStream("minicontainer.json");
        if (resource == null) {
            throw new BeanCreatingException("Can't found minicontainer.json");
        }
        this.configure = JSON.parseObject(resource, JSONObject.class);
    }

    public String get(String path) {

        Object current = this.configure;
        for (String split : path.split("\\.")) {
            if (current instanceof JSONObject) {
                current = ((JSONObject) current).get(split);
            } else if (current instanceof JSONArray) {
                current = ((JSONArray) current).get(Integer.parseInt(split));
            } else {
                throw new BeanCreatingException("can't find value, key:" + path);
            }
        }

        return JSONObject.toJSONString(current);
    }
}
