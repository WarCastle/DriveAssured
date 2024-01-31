package com.castle.drive.common.constant;

/**
 * @author YuLong
 * @Date 2024/1/29 17:07
 * @Classname CommonConstant
 * @Description 公共常量类
 */
public interface CommonConstant {

    /**
     * 项目包名称
     */
    String PACKAGE_NAME = "com.castle.drive";

    /**
     * 公共包名称
     */
    String COMMON_PACKAGE_NAME = PACKAGE_NAME + ".common";

    /**
     * 默认页码为1
     */
    Integer DEFAULT_PAGE_INDEX = 1;

    /**
     * 默认页大小为10
     */
    Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 逗号
     */
    String COMMA = ",";

    /**
     * 请求参数body字符串
     */
    String REQUEST_PARAM_BODY_STRING = "REQUEST_PARAM_BODY_STRING";

    /**
     * 请求参数排除路径
     */
    String REQUEST_PARAM_EXCLUDE_PATH = "REQUEST_PARAM_EXCLUDE_PATH";

    /**
     * 日志链路ID
     */
    String TRACE_ID = "traceId";

    /**
     * 请求IP
     */
    String IP = "ip";

    /**
     * 1000
     */
    int ONE_THOUSAND = 1000;

    /**
     * knife4j
     */
    String KNIFE4J = "knife4j";

    /**
     * swaggerUI访问路径
     */
    String SWAGGER_UI_PATH = "/swagger-ui/index.html";

    /**
     * xls后缀
     */
    String XLS = ".xls";

    /**
     * xlsx后缀
     */
    String XLSX = ".xlsx";
}
