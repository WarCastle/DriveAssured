package com.castle.drive.config.properties;

import com.castle.drive.util.YamlUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/6 14:26
 * @Classname MerchantLineProperties
 * @Description 多商户行级数据权限属性配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "merchant-line")
public class MerchantLineProperties {

    /**
     * 商户ID列名称
     */
    private String merchantIdColumn;

    /**
     * 包含的表名称
     */
    private List<String> includeTables;

    public void setIncludeTables(List<String> includeTables) {
        this.includeTables = YamlUtil.parseListArray(includeTables);
    }
}
