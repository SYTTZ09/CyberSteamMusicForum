package com.syt.music.controller.v1;


import com.syt.model.common.dtos.req.LoadMoreRequest;
import com.syt.model.common.dtos.res.Response;
import com.syt.model.music.vos.MusicVO;
import com.syt.music.service.business.IndexListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/v1/indexList")
@Api(value = "主页列表服务", tags = "主页列表服务")
public class IndexListController {

    @Resource
    private IndexListService indexListService;

    @PostMapping("/latest")
    @ApiOperation("最近播放")
    public Response<List<MusicVO>> latestList(@RequestBody LoadMoreRequest request) {
        return indexListService.latestList(request);
    }
    @PostMapping("/day")
    @ApiOperation("日榜")
    public Response<List<MusicVO>> dayList(@RequestBody LoadMoreRequest request) {
        return indexListService.dayList(request);
    }
    @PostMapping("/month")
    @ApiOperation("月榜")
    public Response<List<MusicVO>> monthList(@RequestBody LoadMoreRequest request) {
        return indexListService.monthList(request);
    }
    @PostMapping("/year")
    @ApiOperation("年榜")
    public Response<List<MusicVO>> yearList(@RequestBody LoadMoreRequest request) {
        return indexListService.yearList(request);
    }
    @PostMapping("/total")
    @ApiOperation("总榜")
    public Response<List<MusicVO>> totalList(@RequestBody LoadMoreRequest request) {
        return indexListService.totalList(request);
    }
}
