package com.castle.drive.common.controller;

import com.castle.drive.auth.annotation.Login;
import com.castle.drive.auth.annotation.Permission;
import com.castle.drive.common.service.UploadService;
import com.castle.drive.common.vo.UploadVo;
import com.castle.drive.framework.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/5 11:44
 * @Classname UploadController
 * @Description 文件上传
 */
@Slf4j
@Login
@RestController
@RequestMapping("/common/upload")
@Tag(name = "文件上传")
public class UploadController {

    @Resource
    private UploadService uploadService;

    /**
     * 单个文件上传
     * @param type
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public ApiResult<UploadVo> upload(@RequestParam(required = false) String type,
                                      @RequestPart("file") MultipartFile multipartFile) throws Exception {
        UploadVo uploadVo = uploadService.upload(type, multipartFile);
        return ApiResult.success(uploadVo);
    }

    /**
     * 多个文件上传
     * 请使用swaggerUI测试多文件上传
     * @param type
     * @param multipartFiles
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/batch", consumes = "multipart/form-data")
    @Operation(summary = "多个文件上传")
    @Permission("sys:file:upload-batch")
    public ApiResult<List<UploadVo>> uploadBatch(@RequestParam(required = false) String type,
                                                 @RequestPart("files") List<MultipartFile> multipartFiles) throws Exception {
        List<UploadVo> uploadVoList = uploadService.uploadBatch(type, multipartFiles);
        return ApiResult.success(uploadVoList);
    }

}
