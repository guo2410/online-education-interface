package com.guli.ucenter.controller;


import com.guli.common.vo.R;
import com.guli.ucenter.Vo.LoginInfoVo;
import com.guli.ucenter.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author guoch
 * @since 2021-01-30
 */
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {

    @PostMapping("login")
    public R login(HttpServletRequest request) {
        String token = request.getHeader("token");
        String cookie = request.getHeader("cookie");

        System.out.println("token====" + token);
        System.out.println("cookie====" + cookie);

        return R.ok();
    }

    @PostMapping("info/{token}")
    public R getInfoByToken(@PathVariable String token) {
        Claims claims = JwtUtils.checkJWT(token);
        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("avatar");
        String id = (String) claims.get("id");
        LoginInfoVo loginInfoVo = new LoginInfoVo();
        loginInfoVo.setId(id);
        loginInfoVo.setAvatar(avatar);
        loginInfoVo.setNickname(nickname);
        return R.ok().data("loginInfo", loginInfoVo);
    }

}

