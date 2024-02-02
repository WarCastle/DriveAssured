package com.castle.drive.util;

import com.castle.drive.framework.bean.IpRegion;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/2 14:03
 * @Classname IpRegionUtil
 * @Description IP归属地信息工具类
 */
@Slf4j
@Component
public class IpRegionUtil {

    private static final String CHINA = "中国";

    private static final String PROVINCE = "省";

    private static final String CITY = "市";

    private static final String INTRANET = "内网IP";

    private static Searcher searcher;

    /**
     * 程序启动时，将ip2region.xdb一次性加载到内存中
     * 并发场景下可安全使用
     */
    @PostConstruct
    private static void init() {
        InputStream inputStream = null;
        try {
            inputStream = new ClassPathResource("/ip2region.xdb").getInputStream();
            byte[] buff = FileCopyUtils.copyToByteArray(inputStream);
            searcher = Searcher.newWithBuffer(buff);
            log.info("加载ip2region.xdb成功");
        } catch (IOException e) {
            log.error("加载ip2region.xdb异常：{}", e.getMessage());
        } finally {
            try {
                if (Objects.nonNull(inputStream)) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.info(String.valueOf(e));
            }
        }
    }

    public static IpRegion getIpRegion(String ip) {
        try {
            String region = searcher.search(ip);
            if (StringUtils.isBlank(region)) {
                return null;
            }
            String[] array = region.split("\\|");
            String country = array[0];
            String province = null;
            String city = null;
            String isp = null;
            String areaDesc;
            if (Objects.equals(CHINA, country)) {
                province = array[2];
                city = array[3];
                isp = array[4];
                if (province.endsWith(PROVINCE)) {
                    province = province.substring(0, province.length() - 1);
                }
                if (city.endsWith(CITY)) {
                    city = city.substring(0, city.length() - 1);
                }
                if (Objects.equals(province, city)) {
                    areaDesc = province;
                } else {
                    areaDesc = province + " " + city;
                }
            } else if (region.contains(INTRANET)) {
                areaDesc = INTRANET;
            } else {
                province = array[2];
                isp = array[4];
                if (StringUtils.isBlank(province)) {
                    areaDesc = country;
                } else {
                    areaDesc = country + " " + province;
                }
            }
            IpRegion ipRegion = new IpRegion();
            ipRegion.setCountry(country);
            ipRegion.setProvince(province);
            ipRegion.setCity(city);
            ipRegion.setAreaDesc(areaDesc);
            ipRegion.setIsp(isp);
            return ipRegion;
        } catch (Exception e) {
            log.error("解析IP归属地信息异常：{}，{}", ip, e.getMessage());
        }
        return null;
    }

    /**
     * 通过IP获取区域描述
     * @param ip
     * @return
     */
    public static String getIpAreaDesc(String ip) {
        IpRegion ipRegion = getIpRegion(ip);
        if (Objects.nonNull(ipRegion)) {
            return ipRegion.getAreaDesc();
        }
        return null;
    }

    @PreDestroy
    public static void destroy() {
        try {
            if (Objects.nonNull(searcher)) {
                searcher.close();
            }
        } catch (IOException e) {
            log.info(String.valueOf(e));
        }
    }
}