package personal.wxh.aopdemo;

import lombok.Data;

/**
 * @author 王鑫华(鑫华)
 */
@Data
public class ApiResponse<T> {

    public ApiResponse(T result) {
        this.result = result;
    }

    private T result;
}
