package com.atguigu.ggkt.vod.exception;
import com.atguigu.ggkt.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice //aop
public class GlobalTwoExceptionHandler {
    @ExceptionHandler(Exception.class) //指定异常的处理类
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail(null).message("执行了全局异常处理,芜湖");
    }
    //自定义异常处理
    @ExceptionHandler(GgktException.class) //指定异常的处理类
    @ResponseBody
    public Result error2(GgktException e){
        e.printStackTrace();
        return Result.fail(null).message(e.getMsg()).code(e.getCode());
    }
}
