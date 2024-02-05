package com.castle.drive.common.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.castle.drive.auth.util.CommonLoginUtil;
import com.castle.drive.common.constant.UploadConstant;
import com.castle.drive.common.enums.FileServerType;
import com.castle.drive.common.enums.SysFileType;
import com.castle.drive.common.enums.SystemType;
import com.castle.drive.common.enums.UploadType;
import com.castle.drive.common.service.UploadService;
import com.castle.drive.common.vo.UploadVo;
import com.castle.drive.config.properties.FileProperties;
import com.castle.drive.config.properties.LocalFileProperties;
import com.castle.drive.framework.exception.UploadException;
import com.castle.drive.system.entity.SysFile;
import com.castle.drive.system.service.SysFileService;
import com.castle.drive.util.IpRegionUtil;
import com.castle.drive.util.IpUtil;
import com.castle.drive.util.SystemTypeUtil;
import com.castle.drive.util.TraceIdUtil;
import com.castle.drive.util.api.OssApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/5 11:53
 * @Classname UploadServiceImpl
 * @Description 文件上传服务接口实现类
 */
@Slf4j
@Service
public class UploadServiceImpl implements UploadService {

    @Resource
    private FileProperties fileProperties;

    @Resource
    private LocalFileProperties localFileProperties;

    @Resource
    private SysFileService sysFileService;

    @Override
    public UploadVo upload(String type, MultipartFile multipartFile) throws Exception {
        log.info("文件上传开始");
        if (StringUtils.isBlank(type)) {
            type = UploadType.ANY.getType();
        }
        String originalFilename = multipartFile.getOriginalFilename();
        log.info("originalFilename: {}", originalFilename);
        String contentType = multipartFile.getContentType();
        log.info("contentType: {}", contentType);
        long size = multipartFile.getSize();
        log.info("size: {}", size);
        double mb = size * 1.00 / 1024 / 1024;
        BigDecimal sizeMb = NumberUtil.round(mb, 2);
        log.info("sizeMb: {}", sizeMb);
        // 获取文件后缀
        String extension = FilenameUtils.getExtension(originalFilename);
        // 校验业务类型
        checkUploadType(type, sizeMb, extension);
        // 上传目录名称：年月
        String formatDate = DateUtil.format(new Date(), DatePattern.SIMPLE_MONTH_PATTERN);
        // 文件名称：年月日时分秒+15位唯一ID
        String uploadDirName = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
        String newFileName = formatDate + IdWorker.getId() + "." + extension;
        log.info("newFileName: " + newFileName);
        String url;
        File uploadFile = null;
        FileServerType fileServerType = fileProperties.getFileServerType();
        if (Objects.equals(FileServerType.OSS, fileServerType)) {
            // 阿里云OSS文件上传
            InputStream inputStream = multipartFile.getInputStream();
            url = OssApi.upload(inputStream, uploadDirName, newFileName);
        } else {
            // 本地文件上传路径
            String uploadPath = localFileProperties.getUploadPath();
            File uploadRootDir = new File(uploadPath);
            // 上传目录不存在，则直接创建
            if (!uploadRootDir.exists()) {
                uploadRootDir.mkdirs();
            }
            File uploadDir = new File(uploadRootDir, uploadDirName);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            // 上传文件到本地目录
            uploadFile = new File(uploadDir, newFileName);
            log.info("uploadFile: {}", uploadFile);
            multipartFile.transferTo(uploadFile);
            // 返回本地文件访问路径
            url = localFileProperties.getAccessUrl() + "/" + uploadDirName + "/" + newFileName;
            log.info("url: {}", url);
        }
        // 保存文件记录
        SysFile sysFile = new SysFile();
        sysFile.setTraceId(TraceIdUtil.getTraceId());
        sysFile.setServerType(fileServerType.getCode());
        sysFile.setUploadType(type);
        sysFile.setDirName(uploadDirName);
        sysFile.setOriginalFileName(originalFilename);
        sysFile.setFileName(newFileName);
        sysFile.setContentType(contentType);
        sysFile.setExtension(extension);
        sysFile.setSize(size);
        sysFile.setSizeMb(sizeMb);
        sysFile.setUrl(url);
        // 系统类型
        SystemType systemType = SystemTypeUtil.getSystemTypeByToken();
        if (Objects.nonNull(systemType)) {
            sysFile.setSystemType(systemType.getCode());
            sysFile.setUserId(CommonLoginUtil.getUserId(systemType));
        }
        // 文件物理路径，本地文件服务时才有
        if (Objects.nonNull(uploadFile)) {
            sysFile.setFilePath(uploadFile.getPath());
        }
        // 文件类型
        Integer fileType = getFileTypeByContentType(contentType);
        sysFile.setFileType(fileType);
        // 请求IP和区域信息
        String requestIp = IpUtil.getRequestIp();
        String ipAreaDesc = IpRegionUtil.getIpAreaDesc(requestIp);
        sysFile.setIp(requestIp);
        sysFile.setIpArea(ipAreaDesc);
        // 保存文件记录
        sysFileService.save(sysFile);
        UploadVo uploadVo = new UploadVo();
        uploadVo.setId(sysFile.getId());
        uploadVo.setOriginalFilename(originalFilename);
        uploadVo.setFilename(newFileName);
        uploadVo.setUrl(url);
        uploadVo.setSize(size);
        uploadVo.setSizeMb(sizeMb);
        log.info("文件上传结束");
        return uploadVo;
    }

    private Integer getFileTypeByContentType(String contentType) {
        if (StringUtils.isBlank(contentType)) {
            return SysFileType.FILE.getCode();
        }
        if (contentType.startsWith(UploadConstant.IMAGE_TYPE)) {
            return SysFileType.IMAGE.getCode();
        } else if (contentType.startsWith(UploadConstant.AUDIO_TYPE) || contentType.startsWith(UploadConstant.VIDEO_TYPE)) {
            return SysFileType.VIDEO.getCode();
        } else if (contentType.startsWith(UploadConstant.XLS_TYPE) ||
                contentType.startsWith(UploadConstant.XLSX_TYPE) ||
                contentType.startsWith(UploadConstant.DOC_TYPE) ||
                contentType.startsWith(UploadConstant.PPT_TYPE) ||
                contentType.startsWith(UploadConstant.PDF_TYPE) ||
                contentType.startsWith(UploadConstant.OFFICE_TYPE)) {
            return SysFileType.OFFICE.getCode();
        } else {
            return SysFileType.FILE.getCode();
        }
    }

    @Override
    public List<UploadVo> uploadBatch(String type, List<MultipartFile> multipartFiles) throws Exception {
        log.info("批量文件上传开始");
        List<UploadVo> uploadVoList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            UploadVo uploadVo = upload(type, multipartFile);
            uploadVoList.add(uploadVo);
        }
        log.info("批量文件上传结束");
        return uploadVoList;
    }

    private void checkUploadType(String type, BigDecimal sizeMb, String extension) throws Exception {
        UploadType uploadType = UploadType.get(type);
        log.info("uploadType = {}", uploadType);
        if (Objects.equals(UploadType.ANY, uploadType)) {
            return;
        }
        if (Objects.isNull(uploadType)) {
            throw new UploadException("不存在该文件类型");
        }
        Integer maxSizeMb = uploadType.getMaxSizeMb();
        List<String> extensions = uploadType.getExtensions();
        if (sizeMb.doubleValue() > maxSizeMb) {
            throw new UploadException("超出文件大小限制，请上传" + maxSizeMb + "MB以内的文件");
        }
        extension = extension.toLowerCase();
        if (!extensions.contains(extension)) {
            throw new UploadException("文件格式错误，请上传" + extensions + "格式的文件");
        }
    }
}
