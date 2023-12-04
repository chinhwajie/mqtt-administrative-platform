package com.mqtt.admin.exception_handler;

public interface BaseErrorInfoInterface {

    /**
     * 错误码
     *
     * @return error code
     */
    String getResultCode();

    /**
     * 错误描述
     *
     * @return error message
     */
    String getResultMsg();
}
