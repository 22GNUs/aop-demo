package personal.wxh.aopdemo;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来查询翻译的服务，真实的实现可以是redis
 *
 * @author 王鑫华(鑫华)
 */
@Repository
public class TranslateRepository {

    private static final Map<String, String> STORAGE = new HashMap<String, String>() {{
        put("idCard:0", "身份证");
    }};

    public String translate(String lookUp, String field) {
        String key = lookUp + ":" + field;
        return STORAGE.get(key);
    }
}
