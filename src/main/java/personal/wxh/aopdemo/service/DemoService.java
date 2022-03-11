package personal.wxh.aopdemo.service;

import org.springframework.stereotype.Service;
import personal.wxh.aopdemo.ApiResponse;
import personal.wxh.aopdemo.PageQueryResult;
import personal.wxh.aopdemo.TestCode;
import personal.wxh.aopdemo.TestModel;

import java.util.Arrays;

/**
 * @author 王鑫华(鑫华)
 */
@Service
public class DemoService {

    public ApiResponse<TestModel> helloWorld() {
        return new ApiResponse<>(new TestModel(TestCode.ID_CARD));
    }

    public ApiResponse<PageQueryResult<TestModel>> helloWorldList() {
        return new ApiResponse<>(new PageQueryResult<>(Arrays.asList(
                new TestModel(TestCode.ID_CARD), new TestModel(TestCode.WHATEVER_CODE))));
    }
}
