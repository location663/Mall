package com.wangdao.mall.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.wangdao.mall.bean.BaseReqVo;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
public class MallExceptionHandler {

    @ExceptionHandler(NoSuchGoodsException.class)
    public BaseReqVo noSuchGoodsHandler(){
        return new BaseReqVo(null , null, 702);
    }


    @ExceptionHandler(InvalidFormatException.class)
    public BaseReqVo invalidFormat(){
        return new BaseReqVo(null , "您输入有误，请重新输入", 601);
    }

    @ExceptionHandler(WxException.class)
    public BaseReqVo wxHandler(WxException e){
        return new BaseReqVo(null , e.getMessage(), 702);
    }

    @ExceptionHandler(AuthorizationException.class)
    public BaseReqVo authors(AuthorizationException e){
        return new BaseReqVo(null, "你没有经过认证", 506);
    }

    @ExceptionHandler(IllegalStateException.class)
    public BaseReqVo IllegalState(IllegalStateException e){
        return new BaseReqVo(null, e.getMessage(), 703);
    }

    @ExceptionHandler(SystemBusyBxception.class)
    public BaseReqVo SystemBusy(SystemBusyBxception e){
        return new BaseReqVo(null, e.getMessage(), 704);
    }
}
