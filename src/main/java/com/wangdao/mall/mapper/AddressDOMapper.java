package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.AddressDO;
import com.wangdao.mall.bean.AddressDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Select("select DISTINCT LAST_INSERT_ID() from cskaoyan_mall_address")
    int selectLastInsertId();

    @Update("update cskaoyan_mall_address set is_default = 0 where user_id = #{userId} and id <> #{id} ")
    int updateDefaultByUidAndId(@Param("userId") Integer userid, @Param("id") Integer id);

    @Update("update cskaoyan_mall_address set deleted = 1 where user_id = #{userId} and id = #{id} ")
    int updateDeletedById(@Param("userId") Integer userid, @Param("id") Integer id);
}