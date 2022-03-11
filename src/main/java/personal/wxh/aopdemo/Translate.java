package personal.wxh.aopdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用该注解标注需要翻译的字段
 * @author 王鑫华(鑫华)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Translate {

    /**
     * 如果设置了该值，则可以通过这个值作为翻译的key来查找
     * 举个例子，对象内的属性字段为idCard
     *
     * <pre>
     *     {
     *         "idCard": 0
     *     }
     * </pre>
     *
     * 但是数据库里存储的翻译code叫idCode，这个时候又不想改对象的属性定义，可以手动声明这个映射
     */
    String lookUp() default "";
}
