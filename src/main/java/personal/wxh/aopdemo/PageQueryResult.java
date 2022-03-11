package personal.wxh.aopdemo;

import lombok.Data;

import java.util.List;

/**
 * @author 王鑫华(鑫华)
 */
@Data
public class PageQueryResult<T> {

    public PageQueryResult(List<T> data) {
        this.data = data;
    }

    private List<T> data;
}
