package com.castle.drive.common.service;

import com.castle.drive.common.vo.UploadVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/5 11:45
 * @Classname UploadService
 * @Description 文件上传服务接口
 */
public interface UploadService {

    /**
     * 上传单个文件
     * @param type
     * @param multipartFile
     * @return
     * @throws Exception
     */
    UploadVo upload(String type, MultipartFile multipartFile) throws Exception;

    /**
     * 批量上传文件
     * @param type
     * @param multipartFiles
     * @return
     * @throws Exception
     */
    List<UploadVo> uploadBatch(String type, List<MultipartFile> multipartFiles) throws Exception;
}
