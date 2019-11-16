package com.wangdao.mall.service.util;

import com.wangdao.mall.bean.StorageDO;
import com.wangdao.mall.mapper.StorageDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

@Component
public class StorageUtils {
    @Autowired
    StorageDOMapper storageDOMapper;

    /**
     * 上传文件或图片到 cskaoyan_mall_storage 工具方法
     * 文件存放路径为 classpath: static/wx/
     * 或者其他相对于静态访问路径 /wx/
     * @param file 上传的文件
     * @param realPath 文件存储的绝对路径
     * @return
     */
    public StorageDO insertStorage(MultipartFile file, String realPath){
        StorageDO storageDO = new StorageDO();
        String originalFilename = file.getOriginalFilename();
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
        String substring1 = realPath.substring(realPath.indexOf("wx/"));
        String url = "http://localhost:8080/" + substring1;
        storageDO.setUrl(url + s + substring);
        storageDO.setKey(s+substring);

        int i = storageDOMapper.insertSelective(storageDO);
        int id = storageDOMapper.selectLastInsertStoragre();
        StorageDO storageDO1 = storageDOMapper.selectByPrimaryKey(id);
        return storageDO1;
    }
}
