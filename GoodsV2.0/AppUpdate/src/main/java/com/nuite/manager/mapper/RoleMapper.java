package com.nuite.manager.mapper;

import com.nuite.manager.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> listAllRoles();

    Role getRoleById(int roleId);

    void insertRole(Role role);

    void updateRoleBaseInfo(Role role);

    void deleteRoleById(int roleId);

    int getCountByName(Role role);

    void updateRoleRights(Role role);
}
