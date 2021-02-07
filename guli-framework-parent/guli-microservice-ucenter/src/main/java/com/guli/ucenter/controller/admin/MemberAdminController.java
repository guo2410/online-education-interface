package com.guli.ucenter.controller.admin;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-30 23:02
 */

import com.guli.common.vo.R;
import com.guli.ucenter.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/admin/ucenter/member")
public class MemberAdminController {

    @Autowired
    MemberService memberService;

    @ApiOperation(value = "今日注册数")
    @GetMapping(value = "count-register/{day}")
    public R registerCount(
            @ApiParam(name = "day", value = "统计日期")
            @PathVariable("day") String day) {
        Integer count = memberService.countRegisterByDay(day);
        return R.ok().data("countRegister", count);
    }
}
