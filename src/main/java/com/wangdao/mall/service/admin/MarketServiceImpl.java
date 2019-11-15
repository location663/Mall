package com.wangdao.mall.service.admin;

import com.github.pagehelper.PageHelper;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.RegionDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MarketServiceImpl implements MarketService{

    @Autowired
    RegionDOMapper regionDOMapper;

    /**
     * 商场管理，行政区域
     * @return
     */
    @Override
    public List<RegionVO> selectRegions() {

        List<RegionVO> regionByPidAndType1 = getRegionByPidAndType(0, (byte) 1);
        for (RegionVO regionVO : regionByPidAndType1) {
            List<RegionVO> regionByPidAndType2 = getRegionByPidAndType(regionVO.getId(), (byte) 2);
            for (RegionVO vo : regionByPidAndType2) {
                List<RegionVO> regionByPidAndType3 = getRegionByPidAndType(vo.getId(), (byte) 3);
                vo.setChildren(regionByPidAndType3);
            }
            regionVO.setChildren(regionByPidAndType2);
        }

        return regionByPidAndType1;
    }

    @Override
    public Map<String, Object> selectBrand(RequestPageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getLimit());

        BrandDOExample brandDOExample = new BrandDOExample();
        brandDOExample.createCriteria().andIdEqualTo(pageDTO.getId()).andNameLike("%"+pageDTO.getName()+"%");



        return null;
    }


    private List<RegionVO> getRegionByPidAndType(Integer id, Byte type){
        RegionDOExample regionDOExample = new RegionDOExample();
        regionDOExample.createCriteria().andPidEqualTo(id).andTypeEqualTo(type);
        List<RegionDO> regionDOS = regionDOMapper.selectByExample(regionDOExample);
        ArrayList<RegionVO> regionVOS = new ArrayList<>();
        for (RegionDO regionDO : regionDOS) {
            RegionVO regionVO = new RegionVO();
            regionVO.setId(regionDO.getId());
            regionVO.setName(regionDO.getName());
            regionVO.setType(regionDO.getType());
            regionVO.setCode(regionDO.getCode());
            regionVO.setPid(regionDO.getPid());
            regionVOS.add(regionVO);
        }

        return regionVOS;

    }


}
