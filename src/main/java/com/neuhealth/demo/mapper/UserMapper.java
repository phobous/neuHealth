package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM users WHERE username = #{username} AND password = #{password} AND is_deleted = false AND status = true")
    User login(@Param("username") String username, @Param("password") String password);
    // 模糊查询用户列表（按用户名或真实姓名）
    List<User> queryUsers(@Param("keyword") String keyword);

    // 逻辑删除用户
    void logicalDelete(@Param("userId") int userId);

    // 查询所有角色ID及名称
    List<Long> getAllRoleIds();
}
