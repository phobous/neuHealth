package com.neuhealth.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuhealth.demo.domain.User;
import com.neuhealth.demo.domain.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM users WHERE username = #{username} AND password = #{password} AND is_deleted = false AND status = true")
    User login(@Param("username") String username, @Param("password") String password);
    // 模糊查询用户列表（用户名或真实姓名）+ 联查角色名称
    @Select("""
        SELECT 
            u.id AS id,
            u.real_name AS realName,
            u.phone AS phone,
            u.gender AS gender,
            u.username AS username,
            u.email AS email,
            r.role_name AS roleName
        FROM users u
        LEFT JOIN sys_role r ON u.role_id = r.role_id
        WHERE (u.username LIKE CONCAT('%', #{keyword}, '%')
               OR u.real_name LIKE CONCAT('%', #{keyword}, '%'))
          AND u.is_deleted = false
          AND r.del_flag = '0'
        ORDER BY u.created_at DESC
        """)
    List<UserInfoDTO> queryUsers(@Param("keyword") String keyword);


    // 逻辑删除用户（假设表中有 is_deleted 字段）
    @Update("UPDATE users SET is_deleted = true WHERE id = #{userId}")
    void logicalDelete(@Param("userId") int userId);

    // 查询所有角色 ID（如果你想显示角色列表，请用角色表，不在 User 表中）
    @Select("SELECT role_id FROM sys_role")
    List<Long> getAllRoleIds();
}
