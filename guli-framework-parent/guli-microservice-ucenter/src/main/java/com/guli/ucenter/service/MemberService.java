package com.guli.ucenter.service;

import com.guli.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author guoch
 * @since 2021-01-30
 */
public interface MemberService extends IService<Member> {

    Integer countRegisterByDay(String day);

    Member getByOpenid(String openid);
}
