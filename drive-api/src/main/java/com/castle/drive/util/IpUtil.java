package com.castle.drive.util;

import com.castle.drive.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author YuLong
 * @Date 2024/2/2 9:52
 * @Classname IpUtil
 * @Description IP地址工具类
 */
@Slf4j
@Component
public class IpUtil {

    private static final String UNKNOWN = "unknown";
    private static final String IPV6_LOCAL = "0:0:0:0:0:0:0:1";
    private static final String FE = "fe";
    private static final String[] IP_HEADS = new String[] {"x-forwarded-for", "X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP"};

    /**
     * 获取请求用户的IP地址
     * @return
     */
    public static String getRequestIp() {
        HttpServletRequest request = HttpServletRequestUtil.getRequest();
        String ip = getRequestIp(request);
        if (ip.contains(CommonConstant.COMMA)) {
            String[] str = ip.split(CommonConstant.COMMA);
            ip = str[0];
        }
        return ip;
    }

    /**
     * 获取请求用户的IP地址
     * @param request
     * @return
     */
    public static String getRequestIp(HttpServletRequest request) {
        String ip = null;
        for (String ipHead : IP_HEADS) {
            ip = request.getHeader(ipHead);
            if (StringUtils.isBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
                break;
            }
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (Objects.equals(IPV6_LOCAL, ip)) {
            ip = getLocalHostIp();
        }
        return ip;
    }

    public static String getLocalHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.info("未知主机异常：{}", e.getMessage());
        }
        return null;
    }

    public static List<String> getLocalhostIpList() {
        List<String> list = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (ObjectUtils.isEmpty(networkInterfaces)) {
                return null;
            }
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (!isValidateNetworkInterface(networkInterface)) {
                    continue;
                }
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!isValidateAddress(inetAddress)) {
                        continue;
                    }
                    list.add(inetAddress.getHostAddress());
                }
            }
        } catch (SocketException e) {
            log.info("SocketException：{}", e.getMessage());
        }
        return list;
    }

    /**
     * 是否是有效的网络
     * @param networkInterface
     * @return
     */
    private static boolean isValidateNetworkInterface(NetworkInterface networkInterface) {
        try {
            return !networkInterface.isLoopback() && !networkInterface.isPointToPoint()
                    && networkInterface.isUp() && !networkInterface.isVirtual();
        } catch (SocketException e) {
            log.info("SocketException：{}", e.getMessage());
        }
        return false;
    }

    /**
     * 是否是有效的IP
     * @param inetAddress
     * @return
     */
    private static boolean isValidateAddress(InetAddress inetAddress) {
        try {
            String hostAddress = inetAddress.getHostAddress();
            // 排除IPV6地址
            if (hostAddress.startsWith(FE)) {
                return false;
            }
            if (!isValidIpv4(hostAddress)) {
                return false;
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        return true;
    }

    /**
     * 校验是否是有效的IPV4
     * @param ipAddress
     * @return
     */
    public static boolean isValidIpv4(String ipAddress) {
        return Pattern.matches("^([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}$", ipAddress);
    }

}
