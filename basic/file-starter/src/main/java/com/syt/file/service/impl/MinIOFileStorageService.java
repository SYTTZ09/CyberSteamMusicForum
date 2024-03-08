package com.syt.file.service.impl;


import com.syt.file.config.MinIOConfig;
import com.syt.file.config.MinIOConfigProperties;
import com.syt.file.service.FileStorageService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@EnableConfigurationProperties(MinIOConfigProperties.class)
@Import(MinIOConfig.class)
public class MinIOFileStorageService implements FileStorageService {

    private static final Tika tika = new Tika();

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinIOConfigProperties minIOConfigProperties;

    private final static String separator = "/";

    /**
     * @param dirPath
     * @param filename  yyyy/mm/dd/file.jpg
     * @return
     */
    public String builderFilePath(String dirPath,String filename) {
        StringBuilder stringBuilder = new StringBuilder(50);
        if(!StringUtils.isEmpty(dirPath)){
            stringBuilder.append(dirPath).append(separator);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String todayStr = sdf.format(new Date());
        stringBuilder.append(todayStr).append(separator);
        stringBuilder.append(filename);
        return stringBuilder.toString();
    }

    /**
     *  上传图片文件
     * @param prefix  文件前缀
     * @param filename  文件名
     * @param fileBytes 文件
     * @return  文件全路径
     */
    @Override
    public String uploadImgFile(String prefix, String filename, byte[] fileBytes) {
        try {
            // 校验类型
            File file = File.createTempFile("tmp","txt");
            FileOutputStream outputStream = null;
            outputStream = new FileOutputStream(file);
            outputStream.write(fileBytes);
            outputStream.close();
            String fileType = tika.detect(file);
            Set<String> fileTypeSet = new HashSet<String>() {{
                add("image/jpeg");
                add("image/webp");
                add("image/png");
                add("image/bmp");
                add("image/gif");
            }};
            if (!fileTypeSet.contains(fileType)) {
                throw new RuntimeException("文件格式错误");
            }
            FileInputStream inputStream = new FileInputStream(file);
            String filePath = builderFilePath(prefix, filename);
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType(fileType)
                    .bucket(minIOConfigProperties.getBucket()).stream(inputStream,inputStream.available(),-1)
                    .build();
            minioClient.putObject(putObjectArgs);
            StringBuilder urlPath = new StringBuilder(minIOConfigProperties.getReadPath());
            urlPath.append(separator+minIOConfigProperties.getBucket());
            urlPath.append(separator);
            urlPath.append(filePath);
            return urlPath.toString();
        } catch (Exception ex){
            log.error("minio put file error.",ex);
            throw new RuntimeException("上传文件失败");
        }
    }


    /**
     *  上传音乐文件
     * @param prefix  文件前缀
     * @param filename  文件名
     * @param fileBytes 文件
     * @return  文件全路径
     */
    @Override
    public String uploadMusicFile(String prefix, String filename, byte[] fileBytes) {
        try {
            // 校验类型
            File file = File.createTempFile("tmp","txt");
            FileOutputStream outputStream = null;
            outputStream = new FileOutputStream(file);
            outputStream.write(fileBytes);
            outputStream.close();
            String fileType = tika.detect(file);
            Set<String> fileTypeSet = new HashSet<String>() {{
                add("audio/mpeg");
            }};
            if (!fileTypeSet.contains(fileType)) {
                throw new RuntimeException("文件格式错误");
            }
            FileInputStream inputStream = new FileInputStream(file);
            String filePath = builderFilePath(prefix, filename);
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType(fileType)
                    .bucket(minIOConfigProperties.getBucket()).stream(inputStream,inputStream.available(),-1)
                    .build();
            minioClient.putObject(putObjectArgs);
            StringBuilder urlPath = new StringBuilder(minIOConfigProperties.getReadPath());
            urlPath.append(separator+minIOConfigProperties.getBucket());
            urlPath.append(separator);
            urlPath.append(filePath);
            return urlPath.toString();
        } catch (Exception ex){
            log.error("minio put file error.",ex);
            throw new RuntimeException("上传文件失败");
        }
    }

    /**
     *  上传html文件
     * @param prefix  文件前缀
     * @param filename   文件名
     * @param fileBytes  文件
     * @return  文件全路径
     */
    @Override
    public String uploadHtmlFile(String prefix, String filename,byte[] fileBytes) {
        try {
            // 校验类型
            File file = File.createTempFile("tmp","txt");
            FileOutputStream outputStream = null;
            outputStream = new FileOutputStream(file);
            outputStream.write(fileBytes);
            outputStream.close();
            String fileType = tika.detect(file);
            Set<String> fileTypeSet = new HashSet<String>() {{
                add("text/html");
            }};
            if (!fileTypeSet.contains(fileType)) {
                throw new RuntimeException("文件格式错误");
            }
            FileInputStream inputStream = new FileInputStream(file);
            String filePath = builderFilePath(prefix, filename);
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType(fileType)
                    .bucket(minIOConfigProperties.getBucket()).stream(inputStream,inputStream.available(),-1)
                    .build();
            minioClient.putObject(putObjectArgs);
            StringBuilder urlPath = new StringBuilder(minIOConfigProperties.getReadPath());
            urlPath.append(separator+minIOConfigProperties.getBucket());
            urlPath.append(separator);
            urlPath.append(filePath);
            return urlPath.toString();
        } catch (Exception ex){
            log.error("minio put file error.",ex);
            throw new RuntimeException("上传文件失败");
        }
    }

    /**
     * 删除文件
     * @param pathUrl  文件全路径
     */
    @Override
    public void delete(String pathUrl) {
        String key = pathUrl.replace(minIOConfigProperties.getEndpoint()+"/","");
        int index = key.indexOf(separator);
        String bucket = key.substring(0,index);
        String filePath = key.substring(index+1);
        // 删除Objects
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucket).object(filePath).build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            log.error("minio remove file error.  pathUrl:{}",pathUrl);
        }
    }


    /**
     * 下载文件
     * @param pathUrl  文件全路径
     * @return  文件流
     *
     */
    @Override
    public byte[] downLoadFile(String pathUrl)  {
        String key = pathUrl.replace(minIOConfigProperties.getEndpoint()+"/","");
        int index = key.indexOf(separator);
        String bucket = key.substring(0,index);
        String filePath = key.substring(index+1);
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(minIOConfigProperties.getBucket()).object(filePath).build());
        } catch (Exception e) {
            log.error("minio down file error.  pathUrl:{}",pathUrl);

        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while (true) {
            try {
                if (!((rc = inputStream.read(buff, 0, 100)) > 0)) break;
            } catch (IOException e) {
                log.error(e.getClass().getName(), e);
            }
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
