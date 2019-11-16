package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.KeywordDO;
import com.wangdao.mall.bean.KeywordDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface KeywordDOMapper {
    long countByExample(KeywordDOExample example);

    int deleteByExample(KeywordDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(KeywordDO record);

    int insertSelective(KeywordDO record);

    List<KeywordDO> selectByExample(KeywordDOExample example);

    KeywordDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") KeywordDO record, @Param("example") KeywordDOExample example);

    int updateByExample(@Param("record") KeywordDO record, @Param("example") KeywordDOExample example);

    int updateByPrimaryKeySelective(KeywordDO record);

    int updateByPrimaryKey(KeywordDO record);

    @Select("select DISTINCT LAST_INSERT_ID() FROM cskaoyan_mall_keyword")
    int selectLastInsertId();
}