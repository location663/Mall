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
    public BaseReqVo invalidFormat(InvalidFormatException e){
        return new BaseReqVo(null , "您输入有误，请重新输入", 601);
    }

    @ExceptionHandler(WxException.class)
    public BaseReqVo wxHandler(NoSuchGoodsException e){
        return new BaseReqVo(null , e.getMessage(), 702);
    }
}
