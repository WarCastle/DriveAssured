package com.castle.drive.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.drive.system.entity.SysDictType;
import com.castle.drive.system.query.SysDictTypeQuery;
import com.castle.drive.system.vo.SysDictTypeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/4 9:58
 * @Classname SysDictTypeMapper
 * @Description 字典类型Mapper接口
 */
@Mapper
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {

    /**
     * 字典类型详情
     *
     * @param id
     * @return
     */
    SysDictTypeVo getSysDictTypeById(Long id);

    /**
     * 字典类型列表
     *
     * @param query
     * @return
     */
    List<SysDictTypeVo> getSysDictTypeList(SysDictTypeQuery query);

}
