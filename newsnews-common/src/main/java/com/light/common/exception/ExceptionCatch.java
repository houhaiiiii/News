package com.light.common.exception;

import com.light.model.common.dtos.ResponseResult;
import com.light.model.common.enums.AppHttpCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 通用异常类
 */
@ControllerAdvice //控制器增强
@Log4j2
public class ExceptionCatch{

    /**
     * 捕获Exception此类异常
     * @ExceptionHandler(Exception.class) 异常处理器 与控制器增强一起使用，可以拦截指定的异常信息
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){

        //输出异常信息
        exception.printStackTrace();
        //记录异常日志
        log.error("catch exception:{}", exception.getMessage());

        //返回通用异常
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);

    }

}
