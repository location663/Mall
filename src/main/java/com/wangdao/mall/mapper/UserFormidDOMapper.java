package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.UserFormidDO;
import com.wangdao.mall.bean.UserFormidDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserFormidDOMapper {
    long countByExample(UserFormidDOExample example);

    int deleteByExample(UserFormidDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserFormidDO record);

    int insertSelective(UserFormidDO record);

    List<UserFormidDO> selectByExample(UserFormidDOExample example);

    UserFormidDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserFormidDO record, @Param("example") UserFormidDOExample example);

    int updateByExample(@Param("record") UserFormidDO record, @Param("example") UserFormidDOExample example);

    int updateByPrimaryKeySelective(UserFormidDO record);

    int updateByPrimaryKey(UserFormidDO record);
}