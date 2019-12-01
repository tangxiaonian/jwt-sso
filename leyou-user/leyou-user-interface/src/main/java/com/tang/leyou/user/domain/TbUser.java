package com.tang.leyou.user.domain;

import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@Table(name = "tb_user")
public class TbUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码，加密存储
     */
    @JsonIgnore
    @Column(name = "`password`")
    private String password;

    /**
     * 注册qq号
     */
    @Column(name = "qq")
    private String qq;

    /**
     * 创建时间
     */
    @Column(name = "created")
    private Date created;

    /**
     * 密码加密的salt值
     */
    @JsonIgnore
    @Column(name = "salt")
    private String salt;
}