package personal.wxh.aopdemo;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author 王鑫华(鑫华)
 */
@Aspect
@Component
@RequiredArgsConstructor
public class ServiceAop {

    private final TranslateRepository translateRepository;

    /**
     * demo拦截了service，也可以修改成其他的
     * 参考 <a href="https://www.cnblogs.com/sgh1023/p/13363679.html">aop</a>
     */
    @Pointcut("execution (* personal.wxh.aopdemo.service.*.*(..))")
    private void autoTranslatePointcut() {
        // AOP
    }

    /**
     * aop拦截response，并进行翻译，设置到map上
     */
    @Around("autoTranslatePointcut()")
    public Object afterAdvice(ProceedingJoinPoint pjp) {
        Object proceed;
        try {
             proceed = pjp.proceed();
        } catch (Throwable e) {
            // 处理异常, 直接把异常转成bizException抛出去
            throw new RuntimeException();
        }

        if (!(proceed instanceof ApiResponse)) {
            return proceed;
        }

        ApiResponse<?> response = (ApiResponse<?>) proceed;
        Object result = response.getResult();
        if (result == null) {
            return response;
        }

        // 对支持的集中类型进行翻译
        // 1. 基础模型
        if (result instanceof BaseModel) {
            translate((BaseModel) result);
        }
        // 2. 带基础模型的基础分页模型
        if (result instanceof PageQueryResult) {
            translate((PageQueryResult<?>) result);
        }
        return response;
    }

    @SneakyThrows
    private void translate(BaseModel baseModel) {
        Set<Field> fields = obtainAnnotatedFields(baseModel.getClass(), Translate.class);
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }

        Map<String, String> translateMap = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(baseModel);
            if (value != null) {
                // 作为key去查询
                // 举例，这个值叫idCard, 具体要翻译的字段，业务上的名称
                String lookUp = routeLookUpKey(field);

                // 这个值是0, 也就是具体要翻译的值
                String fieldValue = value.toString();

                String translated = translateRepository.translate(lookUp, fieldValue);
                if (translated != null) {
                    // 找到了翻译字段，加到翻译map中
                    // 注意这里的key不是lookUp，而是字段名，应为这个是给前端查找的
                    translateMap.put(field.getName(), translated);
                }
            }
        }
        // 设置好翻译map，给前端使用
        baseModel.setTranslation(translateMap);
    }

    private void translate(PageQueryResult<?> query) {
        List<?> data = query.getData();
        if (!CollectionUtils.isEmpty(data)) {
            for (Object model : data) {
                // 只有继承了baseMode的才需要翻译
                if (model instanceof BaseModel) {
                    translate((BaseModel) model);
                }
            }
        }
    }

    /**
     * 从属性上获取出要查询的key
     * 默认规则是从注解上获取，如果注解上没有声明，则尝试通过属性名获取
     * @param field 属性
     * @return 查询用的key
     */
    private String routeLookUpKey(Field field) {
        Translate annotation = field.getAnnotation(Translate.class);
        if (!StringUtils.isEmpty(annotation.lookUp())) {
            return annotation.lookUp();
        } else {
            return field.getName();
        }
    }

    /**
     * 反射获取一组被某个注解标注的属性
     * @return null safe set
     */
    public Set<Field> obtainAnnotatedFields(Class<?> classs, Class<? extends Annotation> ann) {
        Set<Field> set = new HashSet<>();
        Class<?> c = classs;
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(ann)) {
                    set.add(field);
                }
            }
            c = c.getSuperclass();
        }
        return set;
    }
}
