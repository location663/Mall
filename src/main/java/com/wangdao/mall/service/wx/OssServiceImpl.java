package com.wangdao.mall.service.wx;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.wangdao.mall.bean.StorageDO;
import com.wangdao.mall.component.AliyunComponent;
import com.wangdao.mall.mapper.StorageDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-20 19:36
 **/
@Service
@Transactional
public class OssServiceImpl implements OssService{
    @Autowired
    AliyunComponent aliyunComponent;

    @Autowired
    StorageDOMapper storageDOMapper;

    public StorageDO uploadStorage(MultipartFile file) {
        OSSClient ossClient = aliyunComponent.getOssClient();
        String bucket = aliyunComponent.getOss().getBucket();
        String endPoint = aliyunComponent.getOss().getEndPoint();

        String originalFilename = file.getOriginalFilename();
        //存到服务器上的文件名
        String key = UUID.randomUUID().toString().replaceAll("-", "") + originalFilename;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PutObjectResult putObjectResult = ossClient.putObject(bucket, key, inputStream);

        StorageDO storageDO = null;
        if(putObjectResult != null){
            storageDO = new StorageDO();
//            storageDO.setId(0);
            storageDO.setKey(key);
            storageDO.setName(key);
            storageDO.setType("image/jpeg");
            storageDO.setSize((int)file.getSize());
            storageDO.setUrl("https://" + bucket + "." + endPoint + "/" + key);
            storageDO.setAddTime(new Date());
            storageDO.setUpdateTime(new Date());
            storageDO.setDeleted(false);
            int insert = storageDOMapper.insert(storageDO);
            if(insert == 1){
                int id = storageDOMapper.selectLastInsertStoragre();
                storageDO.setId(id);
            }
        }
        return storageDO;
    }
}
