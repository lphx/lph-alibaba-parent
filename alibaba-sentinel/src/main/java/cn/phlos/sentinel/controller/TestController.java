package cn.phlos.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TestController
 * @Author: lph
 * @Description:
 * @Date: 2022/5/24 22:26
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/hot_test")
    @SentinelResource(value = "hotTest",blockHandler = "hotTestBlock")
    public String hotTest(@RequestParam(value = "id") String id) throws InterruptedException {
        Thread.sleep(Long.valueOf(id));
        return "正常";
    }

    public String hotTestBlock(String id, BlockException blockException){
        System.out.println(blockException);
        return "熔断";
    }


    @GetMapping("/testE")
    @SentinelResource(value = "hotTest",fallback = "testEBlock")
    public String testE() {
        int i = 1/0;
        return "正常";
    }

    public String testEBlock(Throwable throwable){
        return "异常";
    }

}
