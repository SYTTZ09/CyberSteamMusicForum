package com.syt.music.controller.v1;

import com.syt.model.common.dtos.res.Response;
import com.syt.music.service.business.BehaviorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/v1/behavior")
@Api(value = "行为服务", tags = "行为服务")
public class BehaviorController {
    @Resource
    private BehaviorService behaviorService;


    @GetMapping("/changeLikeState/{id}")
    @ApiOperation("音乐喜欢")
    public Response<String> changeLikeState(@PathVariable Integer id) {
        return behaviorService.changeLikeState(id);
    }

}
