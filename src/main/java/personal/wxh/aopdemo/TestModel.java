package personal.wxh.aopdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 王鑫华(鑫华)
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TestModel extends BaseModel {

    /**
     * 声明一个需要翻译的字段，并且翻译表中，他的key为idCard
     */
    @Translate(lookUp = "idCard")
    private Integer code;

    public TestModel(TestCode code) {
        this.code = code.getCode();
    }

    /**
     * 例子没有被注解标注的，不会被自动翻译
     */
    private TestCode nonTranslate = TestCode.WHATEVER_CODE;
}
