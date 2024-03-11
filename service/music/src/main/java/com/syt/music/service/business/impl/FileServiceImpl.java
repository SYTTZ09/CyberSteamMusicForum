package com.syt.music.service.business.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.syt.file.service.FileStorageService;
import com.syt.model.common.dtos.res.Response;
import com.syt.model.common.enums.ResponseCode;
import com.syt.model.music.dos.MusicInfo;
import com.syt.model.music.dos.MusicOwner;
import com.syt.model.music.dos.MusicState;
import com.syt.model.music.dtos.req.UploadRequest;
import com.syt.music.service.business.FileService;
import com.syt.music.service.data.MusicInfoService;
import com.syt.music.service.data.MusicOwnerService;
import com.syt.music.service.data.MusicStateService;
import com.syt.util.thread.UserIdThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.Date;

@Service
@Transactional
@Slf4j
public class FileServiceImpl implements FileService {

    @Resource
    private FileStorageService fileStorageService;
    @Resource
    private MusicInfoService musicInfoService;
    @Resource
    private MusicStateService musicStateService;
    @Resource
    private MusicOwnerService musicOwnerService;

    /**
     * 上传音乐
     *
     * @param request
     * @return
     */
    @Override
    public Response<String> upload(UploadRequest request) {
        // 1 获取参数
        String name = request.getName();
        String singer = request.getSinger();
        String musicBase64String = request.getMusic();
        String imageBase64String = request.getImage();
        imageBase64String = imageBase64String != null ? imageBase64String : "";
        String introduction = request.getIntroduction();
        introduction = introduction != null ? introduction : "";
        Boolean isPublic = request.getIsPublic();

        // 2 校验参数
        if (StringUtils.isBlank(name) ||
                StringUtils.isBlank(singer) ||
                StringUtils.isBlank(musicBase64String) ||
                isPublic == null
        ) {
            return new Response<>(ResponseCode.PARAM_REQUIRE.getCode(),
                    "歌名、歌手、音乐都不能为空",
                    "fail"
            );
        }
        if (name.length() > 100 || singer.length() > 100 || introduction.length() > 1000) {
            return new Response<>(ResponseCode.Fail.getCode(),
                    "字数超出限制",
                    "fail"
            );
        }

        // 3 解析文件并上传
        Date currentTime = new Date();
        String imageFileId = null;
        String musicFileId = null;
        // 3.1 image
        if (StringUtils.isNotBlank(imageBase64String)) {
            // 解析 base64
            byte[] imageBytes = Base64.getMimeDecoder().decode(imageBase64String);
            try {
                // 保存
                String imageFileName = name + currentTime.getTime();
                imageFileId = fileStorageService.uploadImgFile("image",
                        imageFileName,
                        imageBytes
                );
            } catch (Exception e) {
                return new Response<>(ResponseCode.Fail.getCode(),
                        "图片保存失败!",
                        "fail"
                );
            }
        }
        // 3.2 music
        // 解析 base64
        byte[] musicBytes = Base64.getMimeDecoder().decode(musicBase64String);
        try {
            // 保存
            String musicFileName = name + currentTime.getTime();
            musicFileId = fileStorageService.uploadMusicFile("music",
                    musicFileName,
                    musicBytes
            );
        } catch (Exception e) {
            return new Response<>(ResponseCode.Fail.getCode(),
                    "音乐保存失败!",
                    "fail"
            );
        }

        // 6 构造实体
        // 6.1  MusicInfo
        MusicInfo newMusicInfo = new MusicInfo();
        newMusicInfo.setName(name);
        newMusicInfo.setSinger(singer);
        newMusicInfo.setMusicSrc(musicFileId);
        newMusicInfo.setImageSrc(imageFileId);
        newMusicInfo.setIntroduction(introduction);
        newMusicInfo.setCreateTime(currentTime);
        newMusicInfo.setUpdateTime(currentTime);
        // 6.1.1 保存
        musicInfoService.save(newMusicInfo);
        // 6.1.2 获取 musicId
        Integer musicId = musicInfoService.getOne(Wrappers.<MusicInfo>lambdaQuery()
                .eq(MusicInfo::getMusicSrc, newMusicInfo.getMusicSrc())
        ).getId();
        // 6.2 MusicState
        MusicState newMusicState = new MusicState();
        newMusicState.setMusicId(musicId);
        newMusicState.setIsPublic(isPublic);
        newMusicState.setCreateTime(currentTime);
        newMusicState.setUpdateTime(currentTime);
        musicStateService.save(newMusicState);
        // 6.3 MusicOwner
        Integer userId = UserIdThreadLocalUtil.getUserId();
        MusicOwner newMusicOwner = new MusicOwner();
        newMusicOwner.setMusicId(musicId);
        newMusicOwner.setUserId(userId);
        newMusicOwner.setCreateTime(currentTime);
        newMusicOwner.setUpdateTime(currentTime);
        musicOwnerService.save(newMusicOwner);

        // 7 返回响应
        return new Response<>(ResponseCode.SUCCESS.getCode(),
                "上传成功",
                "success"
        );
    }
}
