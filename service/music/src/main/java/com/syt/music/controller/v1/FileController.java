package com.syt.music.controller.v1;


import com.syt.model.common.dtos.res.Response;
import com.syt.model.music.dtos.req.UploadRequest;
import com.syt.music.service.business.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/file")
@Api(value = "音乐服务", tags = "音乐服务")
public class FileController {

    @Resource
    private FileService musicService;

    @PostMapping("upload")
    @ApiOperation("音乐上传")
    public Response<String> upload(@RequestBody UploadRequest request) {
        return musicService.upload(request);
    }
}
