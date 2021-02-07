package com.guli.statistics.client;

import com.guli.common.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-31 9:32
 */
@FeignClient("GULI-UCENTER")
@Component
public interface UcenterClient {
    @GetMapping(value = "/admin/ucenter/member/count-register/{day}")
    public R registerCount(@PathVariable("day") String day);
}
