package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.AddressDO;
import com.wangdao.mall.bean.AddressDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AddressDOMapper {
    long countByExample(AddressDOExample example);

    int deleteByExample(AddressDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AddressDO record);

    int insertSelective(AddressDO record);

    List<AddressDO> selectByExample(AddressDOExample example);

    AddressDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AddressDO record, @Param("example") AddressDOExample example);

    int updateByExample(@Param("record") AddressDO record, @Param("example") AddressDOExample example);

    int updateByPrimaryKeySelective(AddressDO record);

    int updateByPrimaryKey(AddressDO record);
}