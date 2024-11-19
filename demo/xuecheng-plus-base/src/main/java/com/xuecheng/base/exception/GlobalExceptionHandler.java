package com.xuecheng.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2023/2/12 17:01
 */

/**
 * ### 代码解释
 * <p>
 * 这段Java代码定义了一个全局异常处理器 `GlobalExceptionHandler`，用于捕获并处理项目中的异常。具体功能如下：
 * <p>
 * 1. **自定义异常处理**：
 * - 使用 `@ExceptionHandler(XueChengPlusException.class)` 注解捕获 `XueChengPlusException` 异常。
 * - 记录异常信息并返回包含错误信息的 `RestErrorResponse` 对象。
 * <p>
 * 2. **通用异常处理**：
 * - 使用 `@ExceptionHandler(Exception.class)` 注解捕获所有未处理的异常。
 * - 记录异常信息并返回包含默认错误信息的 `RestErrorResponse` 对象。
 * <p>
 * ### 控制流图
 * <p>
 * ```mermaid
 * flowchart TD
 * subgraph 自定义异常处理
 * Start1[捕获 XueChengPlusException] --> Record1{记录异常信息}
 * Record1 --> Parse1[解析异常信息]
 * Parse1 --> Return1[返回 RestErrorResponse]
 * end
 * <p>
 * subgraph 通用异常处理
 * Start2[捕获 Exception] --> Record2{记录异常信息}
 * Record2 --> Parse2[解析默认错误信息]
 * Parse2 --> Return2[返回 RestErrorResponse]
 * end
 * ```
 * <p>
 * <p>
 * ### 详细解释
 * <p>
 * 1. **自定义异常处理**：
 * - **Start1**：捕获 `XueChengPlusException` 异常。
 * - **Record1**：记录异常信息，包括错误消息和堆栈跟踪。
 * - **Parse1**：从异常对象中解析出错误信息。
 * - **Return1**：创建并返回包含错误信息的 `RestErrorResponse` 对象。
 * <p>
 * 2. **通用异常处理**：
 * - **Start2**：捕获所有未处理的异常。
 * - **Record2**：记录异常信息，包括错误消息和堆栈跟踪。
 * - **Parse2**：解析默认的错误信息。
 * - **Return2**：创建并返回包含默认错误信息的 `RestErrorResponse` 对象。
 */
@Slf4j // 日志注解
//@ControllerAdvice //
@RestControllerAdvice
public class GlobalExceptionHandler {

    //对项目的自定义异常类型进行处理
    //    @ResponseBody // 返回json数据
    @ExceptionHandler(XueChengPlusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 返回状态码，服务器错误
    public RestErrorResponse customException(XueChengPlusException e) {
        // 日志系统，记录异常
        log.error("系统异常{}", e.getErrMessage(), e);
        // .. 其他方法
        // 给用户发消息


        //解析出异常信息
        String errMessage = e.getErrMessage();
        return new RestErrorResponse(errMessage);
    }


    //    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse exception(Exception e) {

        //记录异常
        log.error("系统异常{}", e.getMessage(), e);

        // 系统自定义异常
        //解析出异常信息
        return new RestErrorResponse(CommonError.UNKOWN_ERROR.getErrMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        List<String> errors = new ArrayList<>();

        bindingResult.getFieldErrors().stream().forEach(item -> {
            errors.add(item.getDefaultMessage());
        });

//        errors.stream().forEach(System.out::println);
        String message = String.join(",", errors);
        log.error("参数校验失败{}", message);
        return new RestErrorResponse(CommonError.REQUEST_NULL.getErrMessage());
    }
}
