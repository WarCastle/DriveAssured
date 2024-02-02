package com.castle.drive.framework.bean;

import lombok.Data;

/**
 * @author YuLong
 * @Date 2024/2/2 14:23
 * @Classname IpRegion
 * @Description IP区域
 */
@Data
public class IpRegion {

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区域描述
     */
    private String areaDesc;

    /**
     * 运营商
     */
    private String isp;

}
