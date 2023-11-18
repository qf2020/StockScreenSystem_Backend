package com.cjk.bakend.demo.controller;

import com.cjk.bakend.demo.pojo.Result;
import com.cjk.bakend.demo.utils.RedisUtils;
import com.google.code.kaptcha.Producer;

import lombok.extern.slf4j.Slf4j;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 在前端渲染登录页面时，就会向后端请求获取验证码，该接口需将验证码图片用base64编码后传给前端，并将验证码对应的随机码也传给前端
 *
 * 验证码获取控制器
 * @author Administrator
 */
@Slf4j
@Controller
public class KaptchaController {


    @Autowired
    private Producer producer;
    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/captcha")
    public ResponseEntity<Result> captcha() throws IOException {
        String key = UUID.randomUUID().toString();
        String code = producer.createText();
    
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        byte[] bytes = outputStream.toByteArray();
        String base64Image = Base64.encodeBase64String(bytes);
        String str = "data:image/jpeg;base64,";
        String base64Img = str + base64Image;

        redisUtils.hset("CaptchaCode", key, code, 120);
        Map<String, Object> map = new HashMap<>();
        map.put("codeKey", key);
        map.put("codeImage", base64Img);
        log.info("Log:{/captcha:"+" key: "+key+" code: "+code+"}");
        return ResponseEntity.status(HttpStatus.OK).body(Result.succ(map));
    }

}
