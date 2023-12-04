package com.mqtt.admin.exception_handler;

import com.mqtt.admin.entity.ResultBox;
import com.mqtt.admin.exception_handler.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private void glog(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResultBox notFoundExceptionHandler(NotFoundException e) {
        glog(e);
        return ResultBox.error(ExceptionEnum.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultBox exceptionHandler(Exception e) {
        glog(e);
        return ResultBox.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
    }


}
