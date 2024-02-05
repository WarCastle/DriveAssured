package com.castle.drive.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.drive.framework.page.Paging;
import com.castle.drive.system.dto.SysFileDto;
import com.castle.drive.system.entity.SysFile;
import com.castle.drive.system.query.SysFileQuery;
import com.castle.drive.system.vo.SysFileVo;

/**
 * @author YuLong
 * @Date 2024/2/5 12:00
 * @Classname SysFileService
 * @Description 系统文件服务接口
 */
public interface SysFileService extends IService<SysFile> {

    /**
     * 修改系统文件
     * @param dto
     * @return
     */
    boolean updateSysFile(SysFileDto dto);

    /**
     * 删除系统文件
     * @param id
     * @return
     */
    boolean deleteSysFile(Long id);

    /**
     * 系统文件详情
     * @param id
     * @return
     */
    SysFileVo getSysFileById(Long id);

    /**
     * 系统文件分页列表
     * @param query
     * @return
     */
    Paging<SysFileVo> getSysFilePage(SysFileQuery query);
}
