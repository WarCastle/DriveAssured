package com.castle.drive.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.drive.user.entity.User;
import com.castle.drive.user.query.AppUserQuery;
import com.castle.drive.user.query.UserQuery;
import com.castle.drive.user.vo.AppUserVo;
import com.castle.drive.user.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/1 16:20
 * @Classname UserMapper
 * @Description 用户信息 Mapper 接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 用户信息详情
     *
     * @param id
     * @return
     */
    UserVo getUserById(Long id);

    /**
     * 用户信息分页列表
     *
     * @param query
     * @return
     */
    List<UserVo> getUserPage(UserQuery query);

    /**
     * App用户信息详情
     *
     * @param id
     * @return
     */
    AppUserVo getAppUserById(Long id);

    /**
     * App用户信息分页列表
     *
     * @param query
     * @return
     */
    List<AppUserVo> getAppUserPage(AppUserQuery query);
}
