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
     * 上传文件或图片到 cskaoyan_mall_storage 方法
     * 文件存放路径为 target/classes/static ，便于以后打包
     * 对文件名进行增加UUID前缀，防止重名文件的覆盖
     * 文件访问的URL为 "http://localhost:8080/" + UUID前缀 + 文件名
     * @param file 上传的文件
     * @return
     */
    public StorageDO insertStorage(MultipartFile file){
        /**
         * 获取应用下的target/classes/static
         */
        File system = new File("target/classes/static");
        if (!system.exists()){
            system.mkdirs();
        }
        String absolutePath = system.getAbsolutePath();

        /**
         * 随机生成UUID，后面加上文件名，避免重名文件的覆盖
         */
        // 获取文件名
        String originalFilename = file.getOriginalFilename();
        String s1 = UUID.randomUUID().toString();
        // 去掉UUID中的-
        String s2 = s1.replaceAll("-", "");
        File file1 = new File(absolutePath, s2 + originalFilename);
        try {
            file.transferTo(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 构建StorageDo对象，存储图片信息进数据库storage
         */
        StorageDO storageDO = new StorageDO();
        storageDO.setSize((int) file1.length());
        storageDO.setAddTime(new Date(System.currentTimeMillis()));
        storageDO.setUpdateTime(new Date(System.currentTimeMillis()));
        storageDO.setType(file.getContentType());
        storageDO.setName(file.getOriginalFilename());
        String url = "http://localhost:8080/" + s2 + originalFilename;
        storageDO.setUrl(url);
        storageDO.setKey(s2 + originalFilename);

        int i = storageDOMapper.insertSelective(storageDO);
        int id = storageDOMapper.selectLastInsertStoragre();
        StorageDO storageDO1 = storageDOMapper.selectByPrimaryKey(id);
        return storageDO1;
    }
}
