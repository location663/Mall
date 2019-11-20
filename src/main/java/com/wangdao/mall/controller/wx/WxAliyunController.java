package com.wangdao.mall.controller.wx;

import com.aliyun.oss.model.PutObjectResult;
import com.wangdao.mall.bean.StorageDO;
import com.wangdao.mall.service.util.wx.BaseRespVo;
import com.wangdao.mall.service.wx.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-20 15:37
 **/
@RestController
@RequestMapping("wx")
public class WxAliyunController {

    @Autowired
    OssService OssService;

    /**上传文件
     * @param file
     * @return
     */
    @RequestMapping("storage/upload")
    public BaseRespVo uploadStorage(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        StorageDO storageDO = OssService.uploadStorage(file);
        if(storageDO == null){
            return BaseRespVo.fail();
        }
        return BaseRespVo.ok(storageDO);
    }
}
