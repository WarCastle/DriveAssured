package com.castle.drive.framework.mybatis.plugins.handler;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.castle.drive.auth.annotation.DataScope;
import com.castle.drive.auth.util.AppLoginUtil;
import com.castle.drive.auth.util.LoginUtil;
import com.castle.drive.common.enums.SystemType;
import com.castle.drive.util.SystemTypeUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/6 15:53
 * @Classname DataScopeHandler
 * @Description 数据作用域处理器
 */
@Slf4j
public class DataScopeHandler implements DataPermissionHandler {

    private static final String _COUNT = "_COUNT";

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        try {
            Class<?> clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(StringPool.DOT)));
            String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(StringPool.DOT) + 1);
            if (methodName.endsWith(_COUNT)) {
                // 如果是分页获取数量方法，则截取原方法名称
                methodName = methodName.substring(0, methodName.lastIndexOf("_"));
            }
            // 获取目标方法
            Method method = getTargetMethod(clazz, methodName);
            // 不是自定义方法，跳过
            if (Objects.isNull(method)) {
                return where;
            }
            // 获取方法数据范围注解
            DataScope dataScope = method.getAnnotation(DataScope.class);
            // 如果没有指定数据范围注解，则跳过
            if (Objects.isNull(dataScope)) {
                return where;
            }
            // 用户表别名
            String userAlias = dataScope.userAlias();
            // 用户列名称
            String userIdColumn = dataScope.userIdColumn();
            // 用户列完整名称
            String userColumnName = userIdColumn;
            if (StringUtils.isNotBlank(userAlias)) {
                userColumnName = userAlias + "." +userIdColumn;
            }
            String dataScopeSql = null;
            // 获取系统类型
            SystemType systemType = SystemTypeUtil.getSystemTypeByToken();
            if (Objects.equals(SystemType.ADMIN, systemType)) {
                // 管理后台数据权限处理
                // 管理员跳过
                if (LoginUtil.isAdmin()) {
                    return where;
                }
                // 获取当前登录用户ID
                Long userId = LoginUtil.getUserId();
                // 增加数据范围sql
                dataScopeSql = userColumnName + " = " + userId;
                // TODO 如果是商城，则判断商家表别名和列名称，如果有部门数据权限，则判断部门表和列别名
                // ...
            } else if (Objects.equals(SystemType.APP, systemType)) {
                // APP用户端数据权限处理
                // 获取当前登录用户ID
                Long userId = AppLoginUtil.getUserId();
                // 增加数据范围sql
                dataScopeSql = userColumnName + " = " + userId;
            }
            if (StringUtils.isBlank(dataScopeSql)) {
                return where;
            }
            Expression appendExpression = CCJSqlParserUtil.parseCondExpression(dataScopeSql);
            if (Objects.isNull(where)) {
                return appendExpression;
            }
            return new AndExpression(where, appendExpression);
        } catch (Exception e) {
            log.error("Exception: {}", e.getMessage());
            throw new RuntimeException("");
        }
    }

    /**
     * 获取目标方法
     * @param clazz
     * @param methodName
     * @return
     * @throws Exception
     */
    private Method getTargetMethod(Class<?> clazz, String methodName) throws Exception {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (Objects.equals(method.getName(), methodName)) {
                return method;
            }
        }
        return null;
    }
}
