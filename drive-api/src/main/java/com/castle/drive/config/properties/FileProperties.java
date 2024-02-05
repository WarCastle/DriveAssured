package com.castle.drive.config.properties;

import com.castle.drive.common.enums.FileServerType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YuLong
 * @Date 2024/2/5 11:18
 * @Classname FileProperties
 * @Description 上传文件属性配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    /**
     * 文件访问类型
     */
    private FileServerType fileServerType;

}
