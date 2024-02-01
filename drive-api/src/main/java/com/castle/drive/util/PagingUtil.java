package com.castle.drive.util;

import com.castle.drive.common.constant.CommonConstant;
import com.castle.drive.framework.page.BasePageQuery;
import com.castle.drive.framework.page.OrderByItem;
import com.castle.drive.framework.page.OrderMapping;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/1 16:37
 * @Classname PagingUtil
 * @Description 分页工具类
 */
public class PagingUtil {


    public static void handlePage(BasePageQuery basePageQuery, OrderMapping orderMapping, String defaultOrderBy) {
        String orderBy = defaultOrderBy;
        Integer pageIndex = CommonConstant.DEFAULT_PAGE_INDEX;
        Integer pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
        if (Objects.nonNull(basePageQuery)) {
            pageIndex = basePageQuery.getPageIndex();
            pageSize = basePageQuery.getPageSize();
            // 判断参数中是否有排序
            String orderByColumn = basePageQuery.getOrderByColumn();
            if (StringUtils.isNotBlank(orderByColumn)) {
                // 获取列映射
                if (Objects.nonNull(orderMapping) && !orderMapping.isEmpty()) {
                    String dbColumn = orderMapping.get(orderByColumn);
                    // 如果没找到对应的数据库映射列，则使用当前参数列名称
                    if (StringUtils.isNotBlank(dbColumn)) {
                        orderByColumn = dbColumn;
                    }
                }
                Boolean orderByAsc = basePageQuery.getOrderByAsc();
                String paramOrderBy = OrderByItem.orderBy(orderByColumn, orderByAsc);
                if (StringUtils.isNotBlank(paramOrderBy)) {
                    orderBy = paramOrderBy;
                }
            }
        }
        // 分页
        PageHelper.startPage(pageIndex, pageSize, orderBy);
    }
}
