package com.syt.music.controller.v1;

import com.syt.model.common.dtos.req.LoadMoreRequest;
import com.syt.model.common.dtos.res.Response;
import com.syt.model.music.dos.MusicInfo;
import com.syt.music.service.business.UserListService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/v1/userList")
public class UserListController {

    @Resource
    private UserListService userListService;

    @PostMapping("/all/{id}")
    public Response<List<MusicInfo>> allList(@PathVariable Integer id, @RequestBody LoadMoreRequest request) {
        return userListService.allList(id, request);
    }

    @PostMapping("/like/{id}")
    public Response<List<MusicInfo>> likeList(@PathVariable Integer id, @RequestBody LoadMoreRequest request) {
        return userListService.likeList(id, request);
    }

    @PostMapping("/public/{id}")
    public Response<List<MusicInfo>> publicList(@PathVariable Integer id, @RequestBody LoadMoreRequest request) {
        return userListService.publicList(id, request);
    }

    @PostMapping("/private/{id}")
    public Response<List<MusicInfo>> privateList(@PathVariable Integer id, @RequestBody LoadMoreRequest request) {
        return userListService.privateList(id, request);
    }
}
