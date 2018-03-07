package com.navinfo.opentsp.dongfeng.common.result;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * General Result
 *
 * @author zhangy
 * @date 2016-03-02
 * @modify
 * @copyright Navi Tsp
 */
@JsonRootName("result")
public class HttpCommandResultWithData<T> extends CommonResult {

    private T data;

    public HttpCommandResultWithData() {
    }

    public HttpCommandResultWithData(Integer code, String message) {
        super(code, message);
    }

    public HttpCommandResultWithData(Integer code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public HttpCommandResultWithData setData(T data) {
        this.data = data;
        return this;
    }
}
