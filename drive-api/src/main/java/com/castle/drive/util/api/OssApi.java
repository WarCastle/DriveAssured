package com.castle.drive.util.api;

import com.alibaba.fastjson2.JSON;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.castle.drive.config.properties.OssProperties;
import com.castle.drive.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/1/31 17:15
 * @Classname OssApi
 * @Description OSS接口调用工具类
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "file.file-server-type", havingValue = "OSS", matchIfMissing = true)
public class OssApi {

    private static OssProperties ossProperties;

    private static OSS ossClient;

    public OssApi(OssProperties ossProperties) {
        OssApi.ossProperties = ossProperties;
    }

    @PostConstruct
    public void initOssClient() {
        try {
            DefaultCredentialProvider credentialsProvider = CredentialsProviderFactory.newDefaultCredentialProvider(
                    ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
            ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), credentialsProvider);
            log.info("OSS实例初始化成功：" + JSON.toJSONString(ossProperties));
        } catch (Exception e) {
            log.error("OSS实例初始化异常：" + JSON.toJSONString(ossProperties) + "，message = {}", e.getMessage());
        }
    }

    public static String upload(InputStream inputStream, String dirName, String fileName) {
        try {
            String rootDir = ossProperties.getRootDir();
            String fileKey = "";
            if (StringUtils.isNotBlank(rootDir)) {
                fileKey = rootDir + "/";
            }
            fileKey = fileKey + dirName + "/" +fileName;
            log.info("OSS上传文件fileKey：" + fileKey);
            PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucketName(), fileKey, inputStream);
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            log.info("OSS上传文件结果：" + JSON.toJSONString(result));
            String accessUrl = ossProperties.getAccessDomain();
            String url = accessUrl + "/" + fileKey;
            // 返回访问路径
            log.info("OSS上传文件成功，fileKey：{}，url：{}", fileKey, url);
            return url;
        } catch (OSSException oe) {
            log.error("OSS上传文件异常，错误消息：{}，错误码：{}，请求ID：{}，主机ID：{}",
                    oe.getErrorMessage(), oe.getErrorCode(), oe.getRequestId(), oe.getHostId());
            throw new BusinessException("OSS上传文件异常");
        } catch (ClientException ce) {
            log.error("OSS客户端异常：" + ce.getMessage());
            throw new BusinessException("OSS连接异常");
        } catch (Exception e) {
            throw e;
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            if (Objects.nonNull(ossClient)) {
                ossClient.shutdown();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
