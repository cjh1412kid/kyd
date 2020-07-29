package com.nuite.manager.mapper;

import com.nuite.manager.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    List<User> listAllUser();

    User getUserById(Integer userId);

    void insertUser(User user);

    void updateUser(User user);

    User getUserInfo(User user);//

    User getOneUserInfo(Map<String, Object> map);

    void updateUserBaseInfo(User user);

    void updateUserRights(User user);

    int getCountByName(String loginname);

    void deleteUser(int userId);

    int getCount(User user);

    List<User> listPageUser(User user);

    User getUserAndRoleById(Integer userId);

    void updateLastLogin(User user);
}
