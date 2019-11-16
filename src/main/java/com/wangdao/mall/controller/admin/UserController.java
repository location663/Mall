/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/15
 * Time:19:29
 **/
package com.wangdao.mall.controller.admin;


import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.admin.UserService;
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
    @RequestMapping("/user/list")
    public BaseReqVo userList(int page, int limit, String sort, String order, String username, String mobile) {
        Map map = userService.listByUserCondition(page, limit, sort, order, username, mobile);
        BaseReqVo baseReqVo = new BaseReqVo(map, "成功", 0);
        return baseReqVo;
    }

    /**
     * 根据条件展示地址
     *
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param name
     * @param userid
     * @return
     */
    @RequestMapping("/address/list")
    public BaseReqVo addressList(int page, int limit, String sort, String order, String name, Integer userid) {
        Map map = userService.listByAddressCondition(page, limit, sort, order, name, userid);
        BaseReqVo baseReqVo = new BaseReqVo(map, "成功", 0);
        return baseReqVo;
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
    @RequestMapping("/collect/list")
    public BaseReqVo collectList(int page, int limit, String sort, String order, Integer valueId, Integer userId) {
        Map map = userService.listByCollectCondition(page, limit, sort, order, valueId, userId);
        BaseReqVo baseReqVo = new BaseReqVo(map, "成功", 0);
        return baseReqVo;
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
    @RequestMapping("/footprint/list")
    public BaseReqVo footprintList(int page, int limit, String sort, String order, Integer goodsId, Integer userId) {
        Map map = userService.listByFootprintCondition(page, limit, sort, order, goodsId, userId);
        BaseReqVo baseReqVo = new BaseReqVo(map, "成功", 0);
        return baseReqVo;
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
    @RequestMapping("/history/list")
    public BaseReqVo footprintList(int page, int limit, String sort, String order, String keyword, Integer userId) {
        Map map = userService.listByHistoryCondition(page, limit, sort, order, keyword, userId);
        BaseReqVo baseReqVo = new BaseReqVo(map, "成功", 0);
        return baseReqVo;
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
    @RequestMapping("/feedback/list")
    public BaseReqVo feedbackList(int page, int limit, String sort, String order, String username, Integer id) {
        Map map = userService.listByFeedbackCondition(page, limit, sort, order, username, id);
        BaseReqVo baseReqVo = new BaseReqVo(map, "成功", 0);
        return baseReqVo;
    }
}
