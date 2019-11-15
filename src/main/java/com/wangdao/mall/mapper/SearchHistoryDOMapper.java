package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.SearchHistoryDO;
import com.wangdao.mall.bean.SearchHistoryDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SearchHistoryDOMapper {
    long countByExample(SearchHistoryDOExample example);

    int deleteByExample(SearchHistoryDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SearchHistoryDO record);

    int insertSelective(SearchHistoryDO record);

    List<SearchHistoryDO> selectByExample(SearchHistoryDOExample example);

    SearchHistoryDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SearchHistoryDO record, @Param("example") SearchHistoryDOExample example);

    int updateByExample(@Param("record") SearchHistoryDO record, @Param("example") SearchHistoryDOExample example);

    int updateByPrimaryKeySelective(SearchHistoryDO record);

    int updateByPrimaryKey(SearchHistoryDO record);
}