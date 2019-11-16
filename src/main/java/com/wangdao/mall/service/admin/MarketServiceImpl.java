package com.wangdao.mall.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.BrandDOMapper;
import com.wangdao.mall.mapper.RegionDOMapper;

import com.wangdao.mall.service.util.StorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.sql.Date;

@Service
@Transactional
public class MarketServiceImpl implements MarketService{

    @Autowired
    RegionDOMapper regionDOMapper;

    @Autowired
    BrandDOMapper brandDOMapper;

    /*@Autowired
    StorageDOMapper storageDOMapper;*/

    @Autowired
    StorageUtils storageUtils;

    /**
     * 商场管理，获得全部行政区域
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

    /**
     * 商品品牌显示页及模糊查询
     * @param pageDTO
     * @return
     */
    @Override
    public Map<String, Object> selectBrand(RequestPageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getLimit());

        BrandDOExample brandDOExample = new BrandDOExample();
        BrandDOExample.Criteria criteria = brandDOExample.createCriteria();
        if (null != pageDTO.getId()) {
            criteria.andIdEqualTo(pageDTO.getId());
        }
        if (null != pageDTO.getName()){
            criteria.andNameLike("%" + pageDTO.getName() + "%");
        }
        brandDOExample.setOrderByClause(pageDTO.getSort() + " " + pageDTO.getOrder());
        List<BrandDO> brandDOS = brandDOMapper.selectByExample(brandDOExample);

        PageInfo<BrandDO> brandDOPageInfo = new PageInfo<>(brandDOS);
        long total = brandDOPageInfo.getTotal();

        HashMap<String, Object> map = new HashMap<>();

        map.put("items", brandDOS);
        map.put("total", total);

        return map;
    }

    /**
     * 更新商品品牌信息
     * @param brandDO
     * @return
     */
    @Override
    public BrandDO updateBrandById(BrandDO brandDO) {

        BrandDOExample brandDOExample = new BrandDOExample();
        brandDOExample.createCriteria().andIdEqualTo(brandDO.getId());
        brandDOMapper.updateByExample(brandDO, brandDOExample);

        BrandDOExample brandDOExample1 = new BrandDOExample();
        brandDOExample1.createCriteria().andIdEqualTo(brandDO.getId());
        List<BrandDO> brandDOS = brandDOMapper.selectByExample(brandDOExample1);

        return brandDOS.get(0);
    }

    /**
     * 加入新的商品品牌
     * @param brandDO
     * @return
     */
    @Override
    public BrandDO insertBrand(BrandDO brandDO) {

        brandDO.setAddTime(new Date(System.currentTimeMillis()));
        brandDO.setUpdateTime(new Date(System.currentTimeMillis()));
        brandDOMapper.insertSelective(brandDO);

        BrandDO brandDO1 = brandDOMapper.selectByPrimaryKey(brandDOMapper.selectLastInsertId());

        return brandDO1;
    }

    /**
     * 上传商品图片
     * @param file
     * @return
     */
    @Override
    public StorageDO insertStorage(MultipartFile file) {
        StorageDO storageDO = new StorageDO();
        String realPath = "E:/develop/wangdao_codes/springboot/mall/target/classes/static/wx/storage/fetch/";

        StorageDO storageDO1 = storageUtils.insertStorage(file, realPath);
        /*String originalFilename = file.getOriginalFilename();
        String s1 = UUID.randomUUID().toString() + originalFilename;
        String s = Integer.toHexString(s1.hashCode());
        String substring = originalFilename.substring(originalFilename.indexOf("."));
        File file1 = new File(realPath, s + substring);
        try {
            file.transferTo(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        storageDO.setSize((int) file1.length());
        storageDO.setAddTime(new Date(System.currentTimeMillis()));
        storageDO.setUpdateTime(new Date(System.currentTimeMillis()));
        storageDO.setType(file.getContentType());
        storageDO.setName(file.getOriginalFilename());
        storageDO.setUrl(realPath + s + substring);
        storageDO.setKey(s+substring);

        int i = storageDOMapper.insertSelective(storageDO);
        int id = storageDOMapper.selectLastInsertStoragr();
        StorageDO storageDO1 = storageDOMapper.selectByPrimaryKey(id);*/

        return storageDO1;
    }

    /**
     * 行政区域中间方法
     * @param id
     * @param type
     * @return
     */
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
