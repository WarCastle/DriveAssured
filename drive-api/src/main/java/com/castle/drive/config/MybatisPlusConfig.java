package com.castle.drive.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.castle.drive.config.properties.MerchantLineProperties;
import com.castle.drive.framework.mybatis.plugins.handler.DataScopeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author YuLong
 * @Date 2024/2/6 15:49
 * @Classname MybatisPlusConfig
 * @Description MybatisPlus配置
 */
@Slf4j
@Configuration
public class MybatisPlusConfig {

    @Resource
    private MerchantLineProperties merchantLineProperties;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 数据范围权限
        interceptor.addInnerInterceptor(new DataPermissionInterceptor(new DataScopeHandler()));
        // 攻击SQL阻断解析器，防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

}
