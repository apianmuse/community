package muse.community.service;

import muse.community.mapper.UserMapper;
import muse.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired(required = false) 
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbuser = userMapper.findByAccountId(user.getAccountId()); //查看此github用户是否登录过
        if(dbuser == null){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);////////////有问题
        }else{
            //更新
            dbuser.setGmtModified(System.currentTimeMillis());
            dbuser.setToken(user.getToken());
            dbuser.setName(user.getName());
            dbuser.setAvatarUrl(user.getAvatarUrl());
            userMapper.update(dbuser);
        }
    }
}
