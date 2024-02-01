package com.castle.drive.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.drive.framework.exception.BusinessException;
import com.castle.drive.framework.page.OrderByItem;
import com.castle.drive.framework.page.OrderMapping;
import com.castle.drive.framework.page.Paging;
import com.castle.drive.user.dto.UserRoleDto;
import com.castle.drive.user.entity.UserRole;
import com.castle.drive.user.mapper.UserRoleMapper;
import com.castle.drive.user.query.UserRoleQuery;
import com.castle.drive.user.service.UserRoleService;
import com.castle.drive.user.vo.UserRoleVo;
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
 * @Date 2024/2/1 17:01
 * @Classname UserRoleServiceImpl
 * @Description 用户角色 服务实现类
 */
@Slf4j
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addUserRole(UserRoleDto dto) {
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(dto, userRole);
        return save(userRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUserRole(UserRoleDto dto) {
        Long id = dto.getId();
        UserRole userRole = getById(id);
        if (Objects.isNull(userRole)) {
            throw new BusinessException("用户角色不存在");
        }
        BeanUtils.copyProperties(dto, userRole);
        return updateById(userRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteUserRole(Long id) {
        return removeById(id);
    }

    @Override
    public UserRoleVo getUserRoleById(Long id) {
        return userRoleMapper.getUserRoleById(id);
    }

    @Override
    public Paging<UserRoleVo> getUserRolePage(UserRoleQuery query) {
        OrderMapping orderMapping = new OrderMapping();
        orderMapping.put("createTime", "create_time");
        PagingUtil.handlePage(query, orderMapping, OrderByItem.desc("id"));
        List<UserRoleVo> list = userRoleMapper.getUserRolePage(query);
        Paging<UserRoleVo> paging = new Paging<>(list);
        return paging;
    }

}