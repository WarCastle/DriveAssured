package com.castle.drive.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.drive.framework.exception.BusinessException;
import com.castle.drive.framework.page.OrderByItem;
import com.castle.drive.framework.page.OrderMapping;
import com.castle.drive.framework.page.Paging;
import com.castle.drive.system.dto.SysFileDto;
import com.castle.drive.system.entity.SysFile;
import com.castle.drive.system.mapper.SysFileMapper;
import com.castle.drive.system.query.SysFileQuery;
import com.castle.drive.system.service.SysFileService;
import com.castle.drive.system.vo.SysFileVo;
import com.castle.drive.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/5 17:05
 * @Classname SysFileServiceImpl
 * @Description 系统文件服务接口实现类
 */
@Slf4j
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    @Resource
    private SysFileMapper sysFileMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysFile(SysFileDto dto) {
        Long id = dto.getId();
        SysFile sysFile = getById(id);
        if (Objects.isNull(sysFile)) {
            throw new BusinessException("系统文件不存在");
        }
        BeanUtils.copyProperties(dto, sysFile);
        return updateById(sysFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysFile(Long id) {
        return removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysFileVo getSysFileById(Long id) {
        return sysFileMapper.getSysFileById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Paging<SysFileVo> getSysFilePage(SysFileQuery query) {
        OrderMapping orderMapping = new OrderMapping();
        orderMapping.put("createTime", "create_time");
        PagingUtil.handlePage(query, orderMapping, OrderByItem.desc("id"));
        List<SysFileVo> list = sysFileMapper.getSysFilePage(query);
        return new Paging<>(list);
    }
}
