package com.wangdao.mall.service;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.SystemDO;
import com.wangdao.mall.bean.SystemDOExample;
import com.wangdao.mall.mapper.SystemDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    SystemDOMapper systemDOMapper;

    /**
     * 获取运费配置
     * get方法
     */
    @Override
    public BaseReqVo<Map<String, Object>> getExpressConfig() {
        SystemDOExample systemDOExample = new SystemDOExample();
        List<String> getExpressConfigList = new ArrayList<>();
        getExpressConfigList.add("cskaoyan_mall_express_freight_min");
        getExpressConfigList.add("cskaoyan_mall_express_freight_value");
        systemDOExample.createCriteria().andKeyNameIn(getExpressConfigList);

        Map<String,Object> map = new HashMap<>();
        List<SystemDO> systemDOS = systemDOMapper.selectByExample(systemDOExample);
        for (SystemDO systemDO : systemDOS) {
            if ("cskaoyan_mall_express_freight_min".equals(systemDO.getKeyName())){
                map.put("litemall_express_freight_min", systemDO.getKeyValue());
            }
            if ("cskaoyan_mall_express_freight_value".equals(systemDO.getKeyName())){
                map.put("litemall_express_freight_value", systemDO.getKeyValue());
            }
        }
        return new BaseReqVo<>(map,"成功",0);
    }

    /**
     * 修改运费配置
     * post方法
     */
    @Override
    public BaseReqVo setExpressConfig(Map<String, Object> map) {
        SystemDOExample systemDOExample = new SystemDOExample();
        List<String> setExpressConfigList = new ArrayList<>();
        setExpressConfigList.add("cskaoyan_mall_express_freight_min");
        setExpressConfigList.add("cskaoyan_mall_express_freight_value");
        systemDOExample.createCriteria().andKeyNameIn(setExpressConfigList);
        List<SystemDO> systemDOS = systemDOMapper.selectByExample(systemDOExample);
        for (SystemDO systemDO : systemDOS) {
            if ("cskaoyan_mall_express_freight_min".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_express_freight_min"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
            if ("cskaoyan_mall_express_freight_value".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_express_freight_value"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
        }
        return new BaseReqVo(null,"成功",0);
    }

    /**
     * 获取商场配置
     * get方法
     */
    @Override
    public BaseReqVo<Map<String, Object>> getMallConfig() {
        SystemDOExample systemDOExample = new SystemDOExample();
        List<String> getMallConfigList = new ArrayList<>();
        getMallConfigList.add("cskaoyan_mall_mall_phone");
        getMallConfigList.add("cskaoyan_mall_mall_address");
        getMallConfigList.add("cskaoyan_mall_mall_name");
        getMallConfigList.add("cskaoyan_mall_mall_qq");
        systemDOExample.createCriteria().andKeyNameIn(getMallConfigList);

        Map<String, Object> map = new HashMap<>();
        List<SystemDO> systemDOS = systemDOMapper.selectByExample(systemDOExample);
        for (SystemDO systemDO : systemDOS) {
            if ("cskaoyan_mall_mall_phone".equals(systemDO.getKeyName())){
                map.put("litemall_mall_phone", systemDO.getKeyValue());
            }
            if ("cskaoyan_mall_mall_address".equals(systemDO.getKeyName())){
                map.put("litemall_mall_address", systemDO.getKeyValue());
            }
            if ("cskaoyan_mall_mall_name".equals(systemDO.getKeyName())){
                map.put("litemall_mall_name", systemDO.getKeyValue());
            }
            if ("cskaoyan_mall_mall_qq".equals(systemDO.getKeyName())){
                map.put("litemall_mall_qq", systemDO.getKeyValue());
            }
        }
        return new BaseReqVo<>(map,"成功",0);
    }

    /**
     * 修改商场配置
     * post方法
     */
    @Override
    public BaseReqVo setMallConfig(Map<String, Object> map) {
        SystemDOExample systemDOExample = new SystemDOExample();
        List<String> setMallConfigList = new ArrayList<>();
        setMallConfigList.add("cskaoyan_mall_mall_phone");
        setMallConfigList.add("cskaoyan_mall_mall_address");
        setMallConfigList.add("cskaoyan_mall_mall_name");
        setMallConfigList.add("cskaoyan_mall_mall_qq");
        systemDOExample.createCriteria().andKeyNameIn(setMallConfigList);
        List<SystemDO> systemDOS = systemDOMapper.selectByExample(systemDOExample);
        for (SystemDO systemDO : systemDOS) {
            if ("cskaoyan_mall_mall_phone".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_mall_phone"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
            if ("cskaoyan_mall_mall_address".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_mall_address"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
            if ("cskaoyan_mall_mall_name".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_mall_name"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
            if ("cskaoyan_mall_mall_qq".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_mall_qq"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
        }
        return new BaseReqVo(null,"成功",0);
    }

    /**
     * 获取订单配置
     * get方法
     */
    @Override
    public BaseReqVo<Map<String, Object>> getOrderConfig() {
        SystemDOExample systemDOExample = new SystemDOExample();
        List<String> getOrderConfigList = new ArrayList<>();
        getOrderConfigList.add("cskaoyan_mall_order_comment");
        getOrderConfigList.add("cskaoyan_mall_order_unpaid");
        getOrderConfigList.add("cskaoyan_mall_order_unconfirm");
        systemDOExample.createCriteria().andKeyNameIn(getOrderConfigList);

        Map<String, Object> map = new HashMap<>();
        List<SystemDO> systemDOS = systemDOMapper.selectByExample(systemDOExample);
        for (SystemDO systemDO : systemDOS) {
            if ("cskaoyan_mall_order_comment".equals(systemDO.getKeyName())){
                map.put("litemall_order_comment", systemDO.getKeyValue());
            }
            if ("cskaoyan_mall_order_unpaid".equals(systemDO.getKeyName())){
                map.put("litemall_order_unpaid", systemDO.getKeyValue());
            }
            if ("cskaoyan_mall_order_unconfirm".equals(systemDO.getKeyName())){
                map.put("litemall_order_unconfirm", systemDO.getKeyValue());
            }
        }
        return new BaseReqVo<>(map,"成功",0);
    }

    /**
     * 修改订单配置
     * post方法
     */
    @Override
    public BaseReqVo setOrderConfig(Map<String, Object> map) {
        SystemDOExample systemDOExample = new SystemDOExample();
        List<String> setOrderConfigList = new ArrayList<>();
        setOrderConfigList.add("cskaoyan_mall_order_comment");
        setOrderConfigList.add("cskaoyan_mall_order_unpaid");
        setOrderConfigList.add("cskaoyan_mall_order_unconfirm");
        systemDOExample.createCriteria().andKeyNameIn(setOrderConfigList);
        List<SystemDO> systemDOS = systemDOMapper.selectByExample(systemDOExample);
        for (SystemDO systemDO : systemDOS) {
            if ("cskaoyan_mall_order_comment".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_order_comment"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
            if ("cskaoyan_mall_order_unpaid".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_order_unpaid"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
            if ("cskaoyan_mall_order_unconfirm".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_order_unconfirm"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
        }
        return new BaseReqVo(null,"成功",0);
    }

    /**
     * 获取小程序配置
     * get方法
     */
    @Override
    public BaseReqVo<Map<String, Object>> getWxConfig() {
        SystemDOExample systemDOExample = new SystemDOExample();
        List<String> getWxConfigList = new ArrayList<>();
        getWxConfigList.add("cskaoyan_mall_wx_index_new");
        getWxConfigList.add("cskaoyan_mall_wx_catlog_goods");
        getWxConfigList.add("cskaoyan_mall_wx_catlog_list");
        getWxConfigList.add("cskaoyan_mall_wx_share");
        getWxConfigList.add("cskaoyan_mall_wx_index_brand");
        getWxConfigList.add("cskaoyan_mall_wx_index_hot");
        getWxConfigList.add("cskaoyan_mall_wx_index_topic");
        systemDOExample.createCriteria().andKeyNameIn(getWxConfigList);

        Map<String, Object> map = new HashMap<>();
        List<SystemDO> systemDOS = systemDOMapper.selectByExample(systemDOExample);
        for (SystemDO systemDO : systemDOS) {
            if ("cskaoyan_mall_wx_index_new".equals(systemDO.getKeyName())){
                map.put("litemall_wx_index_new", systemDO.getKeyValue());
            }
            if ("cskaoyan_mall_wx_catlog_goods".equals(systemDO.getKeyName())){
                map.put("litemall_wx_catlog_goods", systemDO.getKeyValue());
            }
            if ("cskaoyan_mall_wx_catlog_list".equals(systemDO.getKeyName())){
                map.put("litemall_wx_catlog_list", systemDO.getKeyValue());
            }
            if ("cskaoyan_mall_wx_share".equals(systemDO.getKeyName())){
                map.put("litemall_wx_share", systemDO.getKeyValue());
            }
            if ("cskaoyan_mall_wx_index_brand".equals(systemDO.getKeyName())){
                map.put("litemall_wx_index_brand", systemDO.getKeyValue());
            }
            if ("cskaoyan_mall_wx_index_hot".equals(systemDO.getKeyName())){
                map.put("litemall_wx_index_hot", systemDO.getKeyValue());
            }
            if ("cskaoyan_mall_wx_index_topic".equals(systemDO.getKeyName())){
                map.put("litemall_wx_index_topic", systemDO.getKeyValue());
            }
        }
        return new BaseReqVo<>(map,"成功",0);
    }

    /**
     * 修改小程序配置
     * post
     */
    @Override
    public BaseReqVo setWxConfig(Map<String, Object> map) {
        SystemDOExample systemDOExample = new SystemDOExample();
        List<String> setWxConfigList = new ArrayList<>();
        setWxConfigList.add("cskaoyan_mall_wx_index_new");
        setWxConfigList.add("cskaoyan_mall_wx_catlog_goods");
        setWxConfigList.add("cskaoyan_mall_wx_catlog_list");
        setWxConfigList.add("cskaoyan_mall_wx_share");
        setWxConfigList.add("cskaoyan_mall_wx_index_brand");
        setWxConfigList.add("cskaoyan_mall_wx_index_hot");
        setWxConfigList.add("cskaoyan_mall_wx_index_topic");
        systemDOExample.createCriteria().andKeyNameIn(setWxConfigList);
        List<SystemDO> systemDOS = systemDOMapper.selectByExample(systemDOExample);
        for (SystemDO systemDO : systemDOS) {
            if ("cskaoyan_mall_wx_index_new".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_wx_index_new"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
            if ("cskaoyan_mall_wx_catlog_goods".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_wx_catlog_goods"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
            if ("cskaoyan_mall_wx_catlog_list".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_wx_catlog_list"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
            if ("cskaoyan_mall_wx_share".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_wx_share"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
            if ("cskaoyan_mall_wx_index_brand".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_wx_index_brand"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
            if ("cskaoyan_mall_wx_index_hot".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_wx_index_hot"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
            if ("cskaoyan_mall_wx_index_topic".equals(systemDO.getKeyName())){
                systemDO.setKeyValue((String) map.get("litemall_wx_index_topic"));
                systemDOMapper.updateByPrimaryKeySelective(systemDO);
            }
        }
        return new BaseReqVo(null,"成功",0);
    }


}
