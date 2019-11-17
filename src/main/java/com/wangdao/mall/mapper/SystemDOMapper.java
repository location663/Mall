package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.SystemDO;
import com.wangdao.mall.bean.SystemDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SystemDOMapper {
    long countByExample(SystemDOExample example);

    int deleteByExample(SystemDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SystemDO record);

    int insertSelective(SystemDO record);

    List<SystemDO> selectByExample(SystemDOExample example);

    SystemDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SystemDO record, @Param("example") SystemDOExample example);

    int updateByExample(@Param("record") SystemDO record, @Param("example") SystemDOExample example);

    int updateByPrimaryKeySelective(SystemDO record);

    int updateByPrimaryKey(SystemDO record);

}
