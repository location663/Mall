package com.wangdao.mall.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.wangdao.mall.bean.BaseReqVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
public class MallExceptionHandler {

    @ExceptionHandler(NoSuchGoodsException.class)
    public BaseReqVo noSuchGoodsHandler(NoSuchGoodsException e){
        return new BaseReqVo(null , null, 702);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public BaseReqVo InvalidFormat(InvalidFormatException e){
        return new BaseReqVo(null , null, 601);
    }

    @ExceptionHandler(AuthenticationException.class)
    public BaseReqVo Authhentication(AuthenticationException e){
        return new BaseReqVo(null, null, 901);
    }
}
