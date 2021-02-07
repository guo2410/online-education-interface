package com.guli.statistics.controller.admin;

import com.guli.common.vo.R;
import com.guli.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-31 9:36
 */
@CrossOrigin
@RequestMapping("/admin/statistics/daily")
@RestController
public class DailyAdminController {

    @Autowired
    DailyService dailyService;

    @GetMapping("show-chart//{begin}/{end}/{type}")
    public R showChart(
            @PathVariable("begin") String begin,
            @PathVariable("end") String end,
            @PathVariable("type") String type
    ){
        Map<String,Object> map = dailyService.getChartData(begin, end, type);
        return R.ok().data(map);
    }

    @PostMapping("{day}")
    public R createStatisticsByDate(@PathVariable String day) {
        dailyService.createStatisticsByDay(day);
        return R.ok();
    }
}
