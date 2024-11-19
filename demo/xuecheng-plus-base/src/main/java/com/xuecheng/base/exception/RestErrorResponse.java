package com.xuecheng.base.exception;

import java.io.Serializable;

/**
 * @author chenyingtao
 * @version 1.0
 * @projectName demo
 * @package com.xuecheng.base.exception
 * @className com.xuecheng.base.exception.RestErroResponse
 * @date 2024/11/19 20:07
 * @description 和前端约定返回的异常信息模型
 */

public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
