package com.castle.drive.framework.mybatis;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.github.yitter.idgen.YitIdHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author YuLong
 * @Date 2024/2/19 10:51
 * @Classname YitIdGenerator
 * @Description Yit Id生成器
 */
@Slf4j
@Component
public class YitIdGenerator implements IdentifierGenerator {
    @Override
    public Number nextId(Object entity) {
        return YitIdHelper.nextId();
    }
}
