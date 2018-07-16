package cn.xuzilin.common.service;

import cn.xuzilin.common.dao.UserEntityMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import cn.xuzilin.common.po.UserEntity;

import cn.xuzilin.common.utils.PasswordUtil;
import cn.xuzilin.core.shiro.token.TokenManager;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 96428 on 2017/9/7.
 * This in weixin-edu, com.outstudio.weixin.common.service
 */
@Service
public class UserService {

    @Resource
    private UserEntityMapper userMapper;


    public UserEntity getByAccount(String account) {
        return userMapper.getByAccount(account);
    }

//    public String changePassword(String newPassword, String oldPassword) {
//        UserEntity user = TokenManager.getUserToken();
//        boolean flag = PasswordUtil.validatePassword(oldPassword, user.getPass());
//        if (!flag) {
//            return "old password is not right";
//        } else {
//            int resultCode = setPassword(user.getAccount(), newPassword);
//            if (resultCode != 0) {
//                return "success";
//            } else {
//                return "fail";
//            }
//        }
//    }
//    public  int setPassword(String account, String password) {
//        String encodedPass = PasswordUtil.createHash(password);
//        UserEntity userEntity = new UserEntity();
//        userEntity.setPass(encodedPass);
//        userEntity.setAccount(account);
//        return userMapper.insert(userEntity);
//    }
}
