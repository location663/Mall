package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageHelper;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.GrouponDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxGrouponServiceImpl implements WxGrouponService {

    @Autowired
    GrouponDOMapper grouponDOMapper;

    @Autowired
    HomeCatalogServiceImpl homeCatalogServiceImpl;

    /**
     * 团购列表
     * @param pageDTO
     * @return
     */
    @Override
    public Map<String, Object> listGroupon(RequestPageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getSize());

        GrouponDOExample grouponDOExample = new GrouponDOExample();
        grouponDOExample.createCriteria().andDeletedEqualTo(false);
        List<GrouponDO> grouponDOS = grouponDOMapper.selectByExample(grouponDOExample);
        List<GrouponRecordVO> grouponRecordVOList = homeCatalogServiceImpl.getGrouponRecordVOList(grouponDOS);
//        PageInfo<GrouponDO> grouponDOPageInfo = new PageInfo<>(grouponDOS);
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", grouponRecordVOList.size());
        map.put("data", grouponRecordVOList);
        return map;
    }

    /**
     * 团购详情
     * @param grouponId
     * @return
     */
    @Override
    public GrouponDetailVO selectById(Integer grouponId) {
        GrouponDO grouponDO = grouponDOMapper.selectByPrimaryKey(grouponId);
        return null;
    }
}
