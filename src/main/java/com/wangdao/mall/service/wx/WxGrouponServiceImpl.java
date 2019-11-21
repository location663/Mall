package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageHelper;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.GrouponDOMapper;
import org.apache.shiro.SecurityUtils;
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

    /**
     * 展示用户的团购
     * @param showType 0表示该用户发起的团购，1表示该用户参加的团购
     * @return
     */
    @Override
    public Map listByUseridAndShowtype(Integer showType) {
        UserDO userDO = (UserDO) SecurityUtils.getSubject().getPrincipal();

        GrouponDOExample grouponDOExample = new GrouponDOExample();
        GrouponDOExample.Criteria criteria = grouponDOExample.createCriteria()
                .andDeletedEqualTo(false).andUserIdEqualTo(userDO.getId());

        if (showType == 0){
            criteria.andCreatorUserIdEqualTo(userDO.getId());
        } else {
            criteria.andCreatorUserIdNotEqualTo(userDO.getId());
        }

        return null;
    }
}
