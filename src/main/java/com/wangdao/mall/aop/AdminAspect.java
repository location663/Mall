/**
 * Created by IntelliJ IDEA
 * User: DB
 * Date:
 * Time: 17:29
 **/
package com.wangdao.mall.aop;


import com.wangdao.mall.bean.LogDO;
import com.wangdao.mall.service.admin.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@Component
@Aspect
public class AdminAspect {

    LogDO logDO;

    @Autowired
    LogService logService;

    @Autowired
    HttpServletRequest request;

    @Pointcut("execution(* com.wangdao.mall.controller.admin..*(..)))")
    public void AdminControllerMypointCut(){}


    @Before("AdminControllerMypointCut()")
    public void mybefore(JoinPoint joinPoint) throws Throwable {
        logDO = new LogDO();
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String admin = (String)request.getSession().getAttribute("admin");
        logDO.setAdmin(admin);
        logDO.setIp(getRemoteIp(request));
        logDO.setAction(getAction(request));
        logDO.setAddTime(new Date(System.currentTimeMillis()));
        logDO.setUpdateTime(new Date(System.currentTimeMillis()));
        logDO.setStatus(true);
        logDO.setDeleted(false);
        logDO.setType(getActionType(request));
        logDO.setResult("成功");
        logDO.setComment("");
    }

    /**
     * return 之后
     */
    @AfterReturning("AdminControllerMypointCut()")
    public void myAfterReturning(){
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request.getRequestURI().contains("login")){
            String admin = (String) request.getSession().getAttribute("admin");
            logDO.setAdmin(admin);
        }
        if (!request.getMethod().equals("OPTIONS")){
            logService.insertLog(logDO);
        }
    }


    /**
     * 异常日志
     */
    @AfterThrowing("AdminControllerMypointCut()")
    public void myafterThrowing() {
        logDO.setStatus(false);
        String requestURI = request.getRequestURI();
        String admin = (String)request.getSession().getAttribute("admin");
        logDO.setAdmin(admin);
        if (requestURI != null) {
            if (requestURI.contains("login")) {
                logDO.setComment("帐号或密码错误");
                logDO.setResult("登录失败");
            } else if (requestURI.contains("create")) {
                logDO.setComment("创建失败");
                logDO.setComment("参数错误");
            } else if (requestURI.contains("update")) {
                logDO.setComment("修改失败");
                logDO.setComment("参数错误");
            } else if (requestURI.contains("delete")) {
                logDO.setResult("删除失败");
                logDO.setComment("未知异常");
            } else if (requestURI.contains("list")){
                logDO.setResult("查询失败");
                logDO.setComment("查询异常");
            }
        }
        logService.insertLog(logDO);
    }



    /**
     * 获得ip
     * @param request
     * @return ip
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0) {
            ip = request.getRemoteAddr();
            return ip;
        } else {
            ip = ip.split(",")[0];
            return ip;
        }
    }

    /**
     * 获得action
     * @param request
     * @return
     */
    public static String getAction(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("login")) {
            return "登录";
        }else if (requestURI.contains("admin/create")) {
            return "创建管理员";
        }else if (requestURI.contains("admin/update")) {
            return "编辑管理员";
        }else if (requestURI.contains("admin/delete")) {
            return "删除管理员";
        }else if (requestURI.contains("role/create")){
            return "创建管理权限";
        }else if (requestURI.contains("role/update")){
            return "编辑管理权限";
        }else if (requestURI.contains("role/delete")){
            return "删除管理权限";
        }else if (requestURI.contains("config/order") && request.getMethod().equals("GET")){
            return "查询订单配置";
        }else if (requestURI.contains("config/order") && request.getMethod().equals("POST")){
            return "编辑订单配置";
        }else if (requestURI.contains("config/express") && request.getMethod().equals("GET")){
            return "查询运费配置";
        }else if (requestURI.contains("config/express") && request.getMethod().equals("POST")){
            return "编辑运费配置";
        }else if (requestURI.contains("config/mall") && request.getMethod().equals("GET")){
            return "查询商城配置" ;
        }else if (requestURI.contains("config/mall") && request.getMethod().equals("POST")){
            return "编辑商城配置";
        }else if (requestURI.contains("config/wx") && request.getMethod().equals("GET")){
            return "查询小程序配置";
        }else if (requestURI.contains("config/wx") && request.getMethod().equals("POST")){
            return "编辑小程序配置";
        }else if (requestURI.contains("create")) {
            return "创建";
        }else if (requestURI.contains("logout")) {
            return "退出";
        }else if (requestURI.contains("update")) {
            return "编辑";
        }else if (requestURI.contains("delete")) {
            return "删除";
        }else if (requestURI.contains("read")) {
            return "查看详情";
        }else if (requestURI.contains("/user/list")){
            return "浏览会员管理";
        }else if (requestURI.contains("/address/list")){
            return "浏览收货地址";
        }else if (requestURI.contains("/collect/list")){
            return "浏览会员收藏";
        }else if (requestURI.contains("/footprint/list")){
            return "浏览会员足迹";
        }else if (requestURI.contains("/history/list")){
            return "浏览搜索历史";
        }else if (requestURI.contains("/feedback/list")){
            return "浏览意见反馈";
        }else if (requestURI.contains("/region/list")){
            return "浏览行政区域";
        }else if (requestURI.contains("/brand/list")){
            return "浏览品牌制造商";
        }else if (requestURI.contains("/category/list")){
            return "浏览商品类名";
        }else if (requestURI.contains("/order/list")){
            return "浏览订单管理";
        }else if (requestURI.contains("/issue/list")){
            return "浏览通用问题";
        }else if (requestURI.contains("/keyword/list")){
            return "浏览关键词";
        }else if (requestURI.contains("/goods/list")){
            return "浏览商品列表";
        }else if (requestURI.contains("/goods/catAndBrand")){
            return "浏览商品上架";
        }else if (requestURI.contains("/comment/list")){
            return "浏览商品评论";
        }else if (requestURI.contains("/ad/list")){
            return "浏览广告管理";
        }else if (requestURI.contains("/coupon/list")){
            return "浏览优惠券管理";
        }else if (requestURI.contains("/topic/list")){
            return "浏览专题管理";
        }else if (requestURI.contains("/groupon/list")){
            return "浏览团购规则";
        }else if (requestURI.contains("/groupon/listRecord")){
            return "浏览团购活动";
        }else if (requestURI.contains("/admin/list")){
            return "浏览管理员";
        }else if (requestURI.contains("/log/list")){
            return "浏览操作日志";
        }else if (requestURI.contains("/role/list")){
            return "浏览角色管理";
        }else if (requestURI.contains("/storage/list")){
            return "浏览对象存储";
        }else if (requestURI.contains("/stat/user")){
            return "浏览用户统计";
        }else if (requestURI.contains("/stat/order")){
            return "浏览订单统计";
        }else if (requestURI.contains("/stat/goods")){
            return "浏览商品统计";
        }else {
            return "进入管理界面";
        }
    }

    /**
     * 获得Type类型
     * @param request
     * @return
     */
    public static int getActionType(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        if (requestURI.contains("login") ||
            requestURI.contains("admin/create") ||
            requestURI.contains("admin/update") ||
            requestURI.contains("admin/delete") ||
            requestURI.contains("role/create")  ||
            requestURI.contains("role/update")  ||
            requestURI.contains("role/delete")  ||
            requestURI.contains("logout")       ||
            requestURI.contains("config/mall")  ||
            requestURI.contains("confix/wx")){
            return 1;
        }else if (requestURI.contains("config/order") || requestURI.contains("config/express")){
            return 2;
        }
        return 0;
    }
}
