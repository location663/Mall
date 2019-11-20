package com.wangdao.mall.service.wx;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.component.AliyunComponent;
import com.wangdao.mall.exception.WxException;
import com.wangdao.mall.mapper.CommentDOMapper;
import com.wangdao.mall.mapper.OrderDOMapper;
import com.wangdao.mall.mapper.OrderGoodsDOMapper;
import com.wangdao.mall.mapper.UserDOMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    UserDOMapper userDOMapper;

    @Autowired
    OrderDOMapper orderDOMapper;

    @Autowired
    CommentDOMapper commentDOMapper;

    @Autowired
    OrderGoodsDOMapper orderGoodsDOMapper;

    @Autowired
    AliyunComponent aliyunComponent;

    private Map<String, String> regMap = new HashMap<>();

    /**
     * 微信用户登录
     * @return
     */
    @Override
    public Map login() {
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();
        HashMap<Object, Object> map2 = new HashMap<>();
        map2.put("avatarUrl", userDO.getAvatar());
        map2.put("nickName", userDO.getNickname());
        HashMap<Object, Object> map1 = new HashMap<>();
        map1.put("userInfo", map2);
        map1.put("tokenExpire", new Date(System.currentTimeMillis() + 1000*60*60*24));
        return map1;
    }

    /**
     * 用户个人页面
     * @return
     */
    @Override
    public Map userIndex() {
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();
        OrderDOExample orderDOExample = new OrderDOExample();
        orderDOExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userDO.getId());
        List<OrderDO> orderDOList = orderDOMapper.selectByExample(orderDOExample);
        HashMap<Object, Object> map2 = new HashMap<>();
        int unpaid = 0;
        int unrecv = 0;
        int unship = 0;
        int uncomment = 0;
        for (OrderDO orderDO : orderDOList) {
            if (orderDO.getOrderStatus() == 101){
                unpaid++;
            }
            if (orderDO.getOrderStatus() == 301){
                unrecv++;
            }
            if (orderDO.getOrderStatus() == 201){
                unship++;
            }
            if (orderDO.getOrderStatus() == 401) {
                /*uncomment++;
                OrderGoodsDOExample orderGoodsDOExample = new OrderGoodsDOExample();
                orderGoodsDOExample.createCriteria().andDeletedEqualTo(false).andOrderIdEqualTo(orderDO.getId());
                List<OrderGoodsDO> orderGoodsDOS = orderGoodsDOMapper.selectByExample(orderGoodsDOExample);
                CommentDOExample commentDOExample = new CommentDOExample();
                if (orderGoodsDOS.size() != 0) {
                    commentDOExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userDO.getId()).andValueIdEqualTo(orderGoodsDOS.get(0).getGoodsId());
                    List<CommentDO> commentDOS = commentDOMapper.selectByExample(commentDOExample);
                    if (commentDOS.size() != 0) {
                        uncomment--;
                    }
                }*/
                uncomment += orderDO.getComments();
            }
        }
        map2.put("uncomment", uncomment);
        map2.put("unpaid", unpaid);
        map2.put("unrecv", unrecv);
        map2.put("unship", unship);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("order", map2);
        return map;
    }

    /**
     * 获取验证码
     * @param mobile
     */
    @Override
    public void getRegCaptcha(String mobile){
        IAcsClient iAcsClient = aliyunComponent.getIacsClient();
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "王道训练营");
        request.putQueryParameter("TemplateCode", "SMS_173765187");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int v = (int) (Math.random() * 10);
            sb.append(v);
        }
//        request.putQueryParameter("TemplateParam", "{\"code\": \"65536\"}");
        request.putQueryParameter("TemplateParam", "{\"code\": \"" + sb.toString() +"\"}");
        try {
            CommonResponse commonResponse = iAcsClient.getCommonResponse(request);
            System.out.println(commonResponse.getData());
            regMap.put(mobile, sb.toString());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户注册
     * @param userDO
     * @return
     * @throws WxException
     */
    @Override
    public Map userRegister(UserDO userDO) throws WxException {
        UserDO userDO1 = userDOMapper.selectByPrimaryKey(userDO.getId());
        if (userDO1 != null){
            throw new WxException("用户名已存在");
        }
        if (regMap.get(userDO.getMobile()) == null || !regMap.get(userDO.getMobile()).equals(userDO.getCode())){
            throw new WxException("您输入的验证码错误");
        }
        userDO.setWeixinOpenid(userDO.getWxCode());
        userDO.setAddTime(new Date());
        userDO.setUpdateTime(new Date());
        userDOMapper.insertSelective(userDO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("userInfo", userDO);
        map.put("token", userDO.getUsername());
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("data", map);
        return map1;
    }
}
