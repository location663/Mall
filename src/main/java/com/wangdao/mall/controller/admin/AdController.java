package com.wangdao.mall.controller.admin;
import com.wangdao.mall.bean.AdDO;
import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.admin.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-15 17:13
 **/
@RestController
@RequestMapping("admin/ad")
public class AdController {
    @Autowired
    AdService adDOService;

    /**
     * 获取根据条件查询广告，默认不输入条件时该条件的值是null
     * @param page
     * @param limit
     * @param name
     * @param content
     * @return
     */
    @RequestMapping("list")
    public BaseReqVo getAdList(Integer page, Integer limit, String name, String content){
        Map<String, Object> map = adDOService.queryAdDOs(page, limit, name, content);
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo();
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**
     * 编辑广告
     * @param adDO
     * @return
     */
    @RequestMapping("update")
    public BaseReqVo getAdList(@RequestBody AdDO adDO){
        BaseReqVo<Object> baseReqVo = new BaseReqVo();
        //这是import java.sql.Date;不是java.util22222222.Date!!!
//        adDO.setUpdateTime(new Date(System.currentTimeMillis()));
        //发现用java.util22222222.Date也没什么区别，是能正常修改数据库，但是请求报文和响应报文里updateTime的格式不对，
        //"2018-01-31T16:00:00.000+0000"
        //"2019-11-15 08:00:19"
        adDO.setUpdateTime(new java.util.Date());

        int update = adDOService.updateAdDO(adDO);
        if(update == 1){
            AdDO adDO1 = adDOService.queryAdDO(adDO.getId());//这个地方应不应该去查一遍数据库？？？
            baseReqVo.setData(adDO1);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
        }
        return baseReqVo;
    }

//    private String convert(){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
//        String format = simpleDateFormat.format(new Date());
//        return format;
//    }返回值是string，不行，因为AdDO类里Date updateTime

    /**
     * 删除广告
     * @param adDO
     * @return
     */
    @RequestMapping("delete")
    public BaseReqVo deleteAdDO(@RequestBody AdDO adDO){
        int delete = adDOService.deleteAdDO(adDO);
        BaseReqVo<Object> baseReqVo = new BaseReqVo();
        if(delete == 1){
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
        }
        return baseReqVo;
    }

    /**
     * 新建广告，图片上传还未实现
     * @param adDO
     * @return
     */
    @RequestMapping("create")
    public AdDO createAdDO(@RequestBody AdDO adDO){
        BaseReqVo<Object> baseReqVo = new BaseReqVo();
        Date date = new Date();
        adDO.setAddTime(date);
        adDO.setUpdateTime(date);
        int id = adDOService.createAdDO(adDO);
        if(id > 0){
            adDO.setId(id);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            baseReqVo.setData(adDO);
        }
//        AdDO adDO1 = adDOService.queryAdDO(adDO.getId());这样不能完成功能，因为现在adDO里还没有id属性。
//        解决办法1：应该在插入新的一条数据时，一并返回select last_insert_id(),再去查询出来该条数据的信息。
        //解决办法2：插入该条新数据时，返回值int，返回的是插入的id, 再在service层或者controller层adDO.setId()。
        //由于响应报文里有几个比请求报文里的bean多了几条额外的id    ！！！,addTime, updateTime属性，所以必须要查询数据库
        return adDO;
    }
}
