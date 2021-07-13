package com.example.demo.Controllor;

import com.example.demo.Entity.CustomResponse;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import com.example.demo.Service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@Controller
//不会返回网页 传回JSON
@ResponseBody
public class UserController
{
    @Autowired
    private UserService userService;
    @Autowired
    private VerifyService verifyService;

    //注册
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("verifyCode") String verifyCode) throws NoSuchAlgorithmException
    {

        if (!verifyService.codeExist())
        {
            return "验证码不存在";
        }
        else if (!verifyService.ifValid())
        {
            return "验证码已失效";
        }
        else
        {
            if (verifyService.ifEqual(verifyCode))
            {
                return userService.register(email, password);
            }
            else
            {
                return "验证码错误";
            }
        }
    }

    //登录
    @PostMapping(value = "/login")
    public CustomResponse loginCheck(@RequestParam("email") String email,
                                     @RequestParam("password") String password)
    {
        User user = userService.loginCheck(email, password);
        if (user == null)
        {
            return new CustomResponse("用户名或密码错误");
        }
        else
        {
            return new CustomResponse("登陆成功", email, user.getCode(), user.getUserid());
        }
    }

    @PostMapping("/getVerifyCode")
    public Boolean getAuthCode(@RequestParam("email") String email)
    {
        return verifyService.SendAuthCode(email);
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("verifyCode") String verifyCode) throws NoSuchAlgorithmException, SQLException
    {

        if (!verifyService.codeExist())
        {
            return "验证码不存在";
        }
        else if (!verifyService.ifValid())
        {
            return "验证码已失效";
        }
        else
        {
            if (verifyService.ifEqual(verifyCode))
            {
                return userService.resetPassword(email, password);
            }
            else
            {
                return "验证码错误";
            }
        }
    }
}
