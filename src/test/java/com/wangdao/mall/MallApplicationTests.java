package com.wangdao.mall;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.admin.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class MallApplicationTests {

    @Autowired
    GoodsService goodsService;

    @Test
    void contextLoads() {
    }

    @Test
    public void test1(){
        Integer page=1;
        Integer limit=20;
        Integer goodsSn=110;
        String name="魔";
        String sort="add_time";
        String order="desc";
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();


        HashMap<String, Object> map = goodsService.queryGoodsList(page,limit,goodsSn,name,sort, order);


        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);

        //System.out.println(baseReqVo);
    }

}
