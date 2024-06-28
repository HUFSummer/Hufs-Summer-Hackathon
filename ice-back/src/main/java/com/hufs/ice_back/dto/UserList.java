package com.hufs.ice_back.dto;

import java.util.ArrayList;


import java.util.List;

import com.hufs.ice_back.entity.UserEntity;



public class UserList {

    private String email;
    private String name;
    private String age;

    public UserList(UserEntity userEntity) {
        this.email = userEntity.getEmail();
        this.name = userEntity.getName();
        this.age = userEntity.getAge();
    }


    public static List<UserList> getList(List<UserEntity> userEntitylist) {
        List<UserList> list = new ArrayList<>();
        for (UserEntity userEntity: userEntitylist){
            UserList userListItem = new UserList(userEntity);
            list.add(userListItem);
        }
        return list;
    }
}
