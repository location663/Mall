/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/15
 * Time:19:29
 **/
package com.wangdao.mall.controller.admin;


import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.admin.UserService;
import com.wangdao.mall.service.util.UserRegExutils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("admin")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 根据条件展示user
     *
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions(value = {"admin:user:list"})
    @RequestMapping("/user/list")
    public BaseReqVo userList(int page, int limit, String sort, String order, String username, String mobile) {
        if(UserRegExutils.userReg(mobile)==602){
            BaseReqVo baseReqVo=new BaseReqVo(null,null,601);
            return baseReqVo;
        }
        else {
            Map map = userService.listByUserCondition(page, limit, sort, order, username, mobile);
            BaseReqVo baseReqVo = new BaseReqVo(map, "成功", 0);
            return baseReqVo;
        }
    }

    /**
     * 根据条件展示地址
     *
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param name
     * @param
     * @return
     */
    @RequiresPermissions(value = {"admin:address:list"})
    @RequestMapping("/address/list")
    public BaseReqVo addressList(int page, int limit, String sort, String order, String name, String userId) {
        if (UserRegExutils.userReg(name) == 601 || UserRegExutils.userReg(userId) == 602) {
            BaseReqVo baseReqVo = new BaseReqVo(null, null, 601);
            return baseReqVo;
        } else {
            Map map = userService.listByAddressCondition(page, limit, sort, order, name, userId);
            BaseReqVo baseReqVo = new BaseReqVo(map, "成功", 0);
            return baseReqVo;
        }
    }

    /**
     * 根据条件展示收藏
     *
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param valueId
     * @param userId
     * @return
     */
    @RequiresPermissions(value = {"admin:collect:list"})
    @RequestMapping("/collect/list")
    public BaseReqVo collectList(int page, int limit, String sort, String order, String valueId, String userId) {
        if(UserRegExutils.userReg(valueId)==602||UserRegExutils.userReg(userId)==602){
            BaseReqVo baseReqVo=new BaseReqVo(null,null,601);
            return baseReqVo;
        }
       else{ Map map = userService.listByCollectCondition(page, limit, sort, order, valueId, userId);
        BaseReqVo baseReqVo = new BaseReqVo(map, "成功", 0);
        return baseReqVo;
        }
    }

    /**
     * 展示用户足迹
     *
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param goodsId
     * @param userId
     * @return
     */
    @RequiresPermissions(value = {"admin:footprint:list"})
    @RequestMapping("/footprint/list")
    public BaseReqVo footprintList(int page, int limit, String sort, String order, String goodsId, String userId) {
        if (UserRegExutils.userReg(goodsId) == 602 || UserRegExutils.userReg(userId) == 602) {
            BaseReqVo baseReqVo = new BaseReqVo(null, null, 601);
            return baseReqVo;
        } else {
            Map map = userService.listByFootprintCondition(page, limit, sort, order, goodsId, userId);
            BaseReqVo baseReqVo = new BaseReqVo(map, "成功", 0);
            return baseReqVo;
        }
    }

    /**
     * 展示用户搜索历史
     *
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param keyword
     * @param userId
     * @return
     */
    @RequiresPermissions(value = {"admin:history:list"})
    @RequestMapping("/history/list")
    public BaseReqVo historyList(int page, int limit, String sort, String order, String keyword, String userId) {
        if (UserRegExutils.userReg(keyword) == 601 || UserRegExutils.userReg(userId) == 602) {
            BaseReqVo baseReqVo = new BaseReqVo(null, null, 601);
            return baseReqVo;
        } else {
            Map map = userService.listByHistoryCondition(page, limit, sort, order, keyword, userId);
            BaseReqVo baseReqVo = new BaseReqVo(map, "成功", 0);
            return baseReqVo;
        }
    }

    /**
     * 展示反馈表
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param username
     * @param id
     * @return
     */
    @RequiresPermissions(value = {"admin:feedback:list"})
    @RequestMapping("/feedback/list")
    public BaseReqVo feedbackList(int page, int limit, String sort, String order, String username, String id) {
        if(UserRegExutils.userReg(username)==601||UserRegExutils.userReg(id)==602){
            BaseReqVo baseReqVo=new BaseReqVo(null,null,601);
            return baseReqVo;
        }
        else {
        Map map = userService.listByFeedbackCondition(page, limit, sort, order, username, id);
        BaseReqVo baseReqVo = new BaseReqVo(map, "成功", 0);
        return baseReqVo;
        }
    }
}
