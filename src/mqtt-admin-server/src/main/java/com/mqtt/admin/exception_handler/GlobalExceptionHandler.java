package com.mqtt.admin.exception_handler;

import com.mqtt.admin.entity.ResultBox;
import com.mqtt.admin.exception_handler.exception.ConnectionFoundException;
import com.mqtt.admin.exception_handler.exception.ConnectionNotFoundException;
import com.mqtt.admin.exception_handler.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

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

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public ResultBox sqlExceptionHandler(SQLException e) {
        glog(e);
        return ResultBox.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ClassNotFoundException.class)
    @ResponseBody
    public ResultBox classNotFoundExceptionHandler(ClassNotFoundException e) {
        glog(e);
        return ResultBox.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConnectionNotFoundException.class)
    @ResponseBody
    public ResultBox connectionNotFoundExceptionHandler(ConnectionNotFoundException e) {
        glog(e);
        return ResultBox.error(ExceptionEnum.CONNECTION_NOT_FOUND);
    }

    @ExceptionHandler(ConnectionFoundException.class)
    @ResponseBody
    public ResultBox connectionFoundExceptionHandler(ConnectionFoundException e) {
        glog(e);
        return ResultBox.error(ExceptionEnum.CONNECTION_FOUND);
    }

    @ExceptionHandler(MqttException.class)
    @ResponseBody
    public ResultBox mqttExceptionHandler(MqttException e) {
        glog(e);
        return ResultBox.error(ExceptionEnum.FAILED);
    }
}
