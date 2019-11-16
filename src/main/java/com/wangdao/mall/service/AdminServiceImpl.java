/**
 * Created by IntelliJ IDEA
 * User: DB
 * Date:
 * Time: 17:11
 **/
package com.wangdao.mall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.AdminDO;
import com.wangdao.mall.bean.AdminDOExample;
import com.wangdao.mall.bean.RoleDO;
import com.wangdao.mall.bean.RoleDOExample;
import com.wangdao.mall.mapper.AdminDOMapper;
import com.wangdao.mall.mapper.RoleDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminDOMapper adminDOMapper;

    @Autowired
    RoleDOMapper roleDOMapper;

    @Override
    public Map selectAllAdmin(String username,Integer page,Integer limit,String sort,String order) {
        AdminDOExample adminDOExample = new AdminDOExample();
        PageHelper.startPage(page,limit);
        if (username != null){
            adminDOExample.createCriteria().andUsernameLike("%" + username + "%");
        }
        adminDOExample.setOrderByClause(sort + " " + order);
        List<AdminDO> adminDOList = adminDOMapper.selectByExample(adminDOExample);
        PageInfo<AdminDO> userPageInfo = new PageInfo<>(adminDOList);
        long total = userPageInfo.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", adminDOList);
        return map;
    }

    @Override
    public List<Map> selectAllAdminRole() {
        List<Map> list = new ArrayList<>();
        RoleDOExample roleDOExample = new RoleDOExample();
        List<RoleDO> roleDOS = roleDOMapper.selectByExample(roleDOExample);
        for (RoleDO roleDO : roleDOS) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("value",roleDO.getId());
            map.put("label", roleDO.getName());
            list.add(map);
        }
        return list;
    }

    /**
     * username校验  √
     * 数据库没有添加 add_time          √
     *              update_time 完成  √
     * @param adminDO
     * @return
     */
    @Override
    public List<AdminDO> createNewAdmin(AdminDO adminDO) {
        AdminDOExample adminDOExampleForCheckUsername = new AdminDOExample();  // 作username重名校验
        List<AdminDO> adminDOListForCheckUsername = adminDOMapper.selectByExample(adminDOExampleForCheckUsername);
        for (AdminDO aDo : adminDOListForCheckUsername) {
            if (adminDO.getUsername().equals(aDo.getUsername())){
                return null;
            }
        }

        adminDO.setAddTime(new Date(System.currentTimeMillis()));
        int i = adminDOMapper.insertSelective(adminDO);      // 做 “增” 的操作
        AdminDOExample adminDOExample = new AdminDOExample();
        adminDOExample.createCriteria().andUsernameEqualTo(adminDO.getUsername()).andPasswordEqualTo(adminDO.getPassword());
        if (i != 0){
            List<AdminDO> adminDOList = adminDOMapper.selectByExample(adminDOExample);
            return adminDOList;
        }
        return null;
    }

    /**
     * 如果使用 updateByPrimarykey() 会把所有的字段给赋默认 例如：把add_time给覆盖掉
     * 现在使用 updateByPrimaryKeySelective() 只会更新想更新的值     √
     *
     * 问题更新：这里也要有重名校验  但这里似乎有点不一样啊  这里需要添加两个条件 1、id相同 2、名字重名
     *
     * @param adminDO
     * @return
     */
    @Override
    public AdminDO updateAdmin(AdminDO adminDO) {
        AdminDOExample adminDOExampleForCheckUsername = new AdminDOExample();  // 作username重名校验
        List<AdminDO> adminDOListForCheckUsername = adminDOMapper.selectByExample(adminDOExampleForCheckUsername);
        for (AdminDO aDo : adminDOListForCheckUsername) {
            if (adminDO.getUsername().equals(aDo.getUsername()) && adminDO.getId() != aDo.getId()){
                return null;
            }
        }

        int update = adminDOMapper.updateByPrimaryKeySelective(adminDO);  // 选择更新 建议使用selective
        if (update != 0){
            AdminDO adminDOAfterUpdate = adminDOMapper.selectByPrimaryKey(adminDO.getId());
            return adminDOAfterUpdate;
        }
        return null;
    }

}
