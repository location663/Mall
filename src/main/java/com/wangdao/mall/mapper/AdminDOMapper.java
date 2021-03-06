package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.AdminDO;
import com.wangdao.mall.bean.AdminDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AdminDOMapper {
    long countByExample(AdminDOExample example);

    int deleteByExample(AdminDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdminDO record);

    int insertSelective(AdminDO record);

    List<AdminDO> selectByExample(AdminDOExample example);

    AdminDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdminDO record, @Param("example") AdminDOExample example);

    int updateByExample(@Param("record") AdminDO record, @Param("example") AdminDOExample example);

    int updateByPrimaryKeySelective(AdminDO record);

    int updateByPrimaryKey(AdminDO record);

    @Select("select DISTINCT LAST_INSERT_ID() from cskaoyan_mall_admin")
    int selectLastInsertAdminId();

    List<String> selectPermissionByUsername(@Param("username") String username);

    @Select("select " +
            "s.api " +
            "from cskaoyan_mall_system_permissions AS s " +
            "left JOIN cskaoyan_mall_permission AS p " +
            "on p.permission = s.id " +
            "where role_id = #{roleid}")
    List<String> selectPermsLeft(Integer roleId);

    @Select("select name from cskaoyan_mall_role where id = #{roleId}")
    String selectRoleName(Integer roleId);
}