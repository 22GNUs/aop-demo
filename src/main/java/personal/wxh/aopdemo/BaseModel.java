package personal.wxh.aopdemo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 基础父类，带一个翻译map
 * @author 王鑫华(鑫华)
 */
public class BaseModel {

    @Getter
    @Setter
    private Map<String, String> translation;
}
