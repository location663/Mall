package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.UserDO;
import com.wangdao.mall.bean.UserDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserDOMapper {
    long countByExample(UserDOExample example);

    int deleteByExample(UserDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    List<UserDO> selectByExample(UserDOExample example);

    UserDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserDO record, @Param("example") UserDOExample example);

    int updateByExample(@Param("record") UserDO record, @Param("example") UserDOExample example);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);

    List<String> selectPermissionByUsername(@Param("username") String username);

    @Select("select count(mobile) from cskaoyan_mall_user where mobile = #{mobile}")
    int checkAccountExistByMobile(String mobile);

    @Update("update cskaoyan_mall_user set password = #{param2} where mobile = #{param1}")
    int updatePasswordByMobile(String mobile, String md5Password);
}
