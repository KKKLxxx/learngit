package com.example.demo.Service;

import com.example.demo.Entity.CustomResponse;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Utils.SendCreateTime;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Service
@Transactional
public class UserService
{
    @Autowired
    UserRepository userRepository;
    @Autowired
    HikariDataSource dataSource;

    public String register(String email, String password) throws NoSuchAlgorithmException
    {
        if (userRepository.findUserByEmail(email) != null)
        {
            return "该用户已存在";
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        String salt = user.addSalt();
        user.setSalt(salt);
        String SHACode = user.setSHACode(password, salt);
        user.setCode(SHACode);
        user.setCreatetime(SendCreateTime.sendTime());
        userRepository.save(user);
        return "注册成功";
    }

    public User loginCheck(String email, String password)
    {
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    public String infoShow(String code)
    {
        return userRepository.findUserByCode(code).getEmail();
    }

    public String resetPassword(String email, String password) throws NoSuchAlgorithmException, SQLException
    {
        User tUser = userRepository.findUserByEmail(email);
        if (tUser == null)
            return "该用户不存在";
        String userid = String.valueOf(tUser.getUserid());
        String salt = tUser.addSalt();
        String SHACode = tUser.setSHACode(password, salt);

        Connection connection = dataSource.getConnection();
        String sql = "UPDATE users SET password = ?, salt = ?, code = ? WHERE userid = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1, password);
        ps.setObject(2, salt);
        ps.setObject(3, SHACode);
        ps.setObject(4, userid);
        ps.executeUpdate();
        connection.close();
        return "密码重置成功";
    }
}
