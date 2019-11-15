package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.StorageDO;
import com.wangdao.mall.bean.StorageDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface StorageDOMapper {
    long countByExample(StorageDOExample example);

    int deleteByExample(StorageDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StorageDO record);

    int insertSelective(StorageDO record);

    List<StorageDO> selectByExample(StorageDOExample example);

    StorageDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StorageDO record, @Param("example") StorageDOExample example);

    int updateByExample(@Param("record") StorageDO record, @Param("example") StorageDOExample example);

    int updateByPrimaryKeySelective(StorageDO record);

    int updateByPrimaryKey(StorageDO record);

    @Select("select LAST_INSERT_ID() from cskaoyan_mall_storage")
    int selectLastInsertStoragr();
}