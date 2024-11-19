package com.xuecheng.base.exception;

/**
 * @author chenyingtao
 * @version 1.0
 * @projectName demo
 * @packageName com.xuecheng.base.exception
 * @className CommonError
 * @date 2024/11/19 20:07
 * @description 通用错误信息
 */

/**
 * 该Java枚举类`CommonError`定义了几个常见的错误类型及其对应的错误信息。每个枚举常量代表一种错误情况，并通过构造方法初始化其错误信息。
 * `getErrMessage`方法用于获取枚举常量对应的错误信息。
 * - `UNKOWN_ERROR`：未知错误。
 * - `PARAMS_ERROR`：参数错误。
 * - `OBJECT_NULL`：对象为空。
 * - `QUERY_NULL`：查询结果为空。
 * - `REQUEST_NULL`：请求参数为空。
 */
public enum CommonError {

    UNKOWN_ERROR("执行过程异常，请重试。"),
    PARAMS_ERROR("非法参数"),
    OBJECT_NULL("对象为空"),
    QUERY_NULL("查询结果为空"),
    REQUEST_NULL("请求参数为空");

    private String errMessage;

    public String getErrMessage() {

        return errMessage;
    }

    private CommonError(String errMessage) {

        this.errMessage = errMessage;
    }

}
