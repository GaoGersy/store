//package com.piesat.project.common.exception;
//
//
//import com.piesat.project.common.Result;
//
//import org.apache.shiro.authz.AuthorizationException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//import java.io.IOException;
//
//import javax.annotation.Resource;
//
//
///**
// * 异常处理器
// */
//@ControllerAdvice
//public class BDExceptionHandler {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Resource
//    ExceptionService mExceptionService;
//
//    /**
//     * 自定义异常
//     */
//    @ExceptionHandler(BDException.class)
//    public Result handleBDException(BDException e) {
//        return handleError(e, e.getCode(), e.getMsg());
//    }
//
//    @ExceptionHandler(DuplicateKeyException.class)
//    public Result handleDuplicateKeyException(DuplicateKeyException e) {
//        return handleError(e, "数据库中已存在该记录");
//    }
//
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public Result noHandlerFoundException(NoHandlerFoundException e) {
//        return handleError(e, "没找到页面");
//    }
//
//    @ExceptionHandler(AuthorizationException.class)
//    public Result handleAuthorizationException(AuthorizationException e) {
//        return handleError(e, "未授权");
//    }
//
//    @ExceptionHandler(NullPointerException.class)
//    public Result handleNullPointerException(NullPointerException e) {
//        return handleError(e, "空指针异常");
//    }
//
////	@ExceptionHandler(JsonSyntaxException.class)
////	public R handleAuthorizationException(JsonSyntaxException e) {
////		logger.error(e.getMessage(), e);
////		return R.error("Json语法错误");
////	}
//
//    @ExceptionHandler(IllegalStateException.class)
//    public Result handleIllegalStateException(IllegalStateException e) {
//        return handleError(e, "服务器非法状态错误");
//    }
//
//    @ExceptionHandler(IOException.class)
//    public Result handleIOException(IOException e) {
//        return handleError(e, "服务器IO异常");
//    }
//
//    @ExceptionHandler(Exception.class)
//    public Result handleException(Exception e) {
//        return handleError(e, "服务器未知错误，请联系管理员");
//    }
//
//    private Result handleError(Throwable throwable, String msg) {
//        return handleError(throwable, ResultCode.INTERNAL_SERVER_ERROR.code(), msg);
//    }
//
//    private Result handleError(Throwable throwable, int error, String msg) {
//        logger.error(throwable.getMessage(),throwable);
//        ErrorBean errorBean = StackTraceHandler.getStackTrace(throwable, 5);
//        mExceptionService.save(errorBean);
//        return ResultGenerator.genFailResult(msg);
//    }
//}
