package com.tang.leyou.user.service;

import com.tang.leyou.common.CodecUtils;
import com.tang.leyou.user.domain.TbUser;
import com.tang.leyou.user.mapper.TbUserMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Classname UserServiceImpl
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/27 19:38
 * @Created by ASUS
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser addUser(TbUser tbUser) {

        tbUser.setCreated(new Date());

        String salt = CodecUtils.generateSalt();

        tbUser.setSalt(salt);
        // 加盐  加密
        tbUser.setPassword(CodecUtils.md5Hex(tbUser.getPassword(), salt));

        int flage = tbUserMapper.insert(tbUser);

        if (flage == 0) {
            return null;
        }

        return tbUser;
    }

    @Override
    public TbUser checkLogin(String username, String password) {

        Example example = new Example(TbUser.class);

        example.createCriteria()
                .andEqualTo("username", username);

        TbUser tbUser = tbUserMapper.selectOneByExample(example);

        if (tbUser != null) {
            // 对比密码
            if (tbUser.getPassword().equals( CodecUtils.md5Hex(password,tbUser.getSalt()) )) {

                tbUser.setPassword("");

                return tbUser;

            }

        }

        return tbUser = null;
    }
}