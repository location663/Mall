package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.CartDO;
import com.wangdao.mall.bean.CartDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CartDOMapper {
    long countByExample(CartDOExample example);

    int deleteByExample(CartDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CartDO record);

    int insertSelective(CartDO record);

    List<CartDO> selectByExample(CartDOExample example);

    CartDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CartDO record, @Param("example") CartDOExample example);

    int updateByExample(@Param("record") CartDO record, @Param("example") CartDOExample example);

    int updateByPrimaryKeySelective(CartDO record);

    int updateByPrimaryKey(CartDO record);
}