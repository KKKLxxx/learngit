package com.example.demo.Entity;

import com.example.demo.Utils.SendCreateTime;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userid;
    String email;
    String password;
    String salt;
    String code;
    String createtime;

    public void setUserid(Long UserId)
    {
        this.userid = UserId;
    }

    public Long getUserid()
    {
        return userid;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    public String getSalt()
    {
        return salt;
    }

    public void setCode(String hashCode)
    {
        this.code = hashCode;
    }

    public String getCode()
    {
        return code;
    }

    public void setCreatetime(String createtime)
    {
        this.createtime = createtime;
    }

    public String getCreatetime()
    {
        return createtime;
    }

    public String addSalt() throws NoSuchAlgorithmException
    {
        // SHA1PRNG算法是基于SHA-1实现且保密性较强的随机数生成器
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        // get a random salt
        secureRandom.nextBytes(salt);
        StringBuilder builder = new StringBuilder();
        for (byte num : salt)
        {
            // 将十进制数转换成十六进制(使用&运算，正数部分没变，负数部分二进制从右往左第9位及以上都为0
            builder.append(Integer.toString((num & 0xff) + 0x100, 16).substring(1));
        }
        return builder.toString();
    }

    public String setSHACode(String password, String salt) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        password = salt + password + salt;
        byte[] bytes = md.digest(password.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte num : bytes)
        {
            builder.append(Integer.toString((num & 0xff) + 0x100, 16).substring(1));
        }
        return builder.toString();
    }
}
