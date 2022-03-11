package personal.wxh.aopdemo;

import lombok.Getter;

/**
 * @author 王鑫华(鑫华)
 */
public enum TestCode {

    ID_CARD("身份证", 0),

    WHATEVER_CODE("不知道什么证", 1);

    @Getter
    private final String desc;

    @Getter
    private final int code;

    TestCode(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }
}
