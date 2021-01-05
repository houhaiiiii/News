package com.light.admin.test;

import com.light.admin.AdminApplication;
import com.light.common.aliyun.GreeTextScan;
import com.light.common.aliyun.GreenImageScan;
import com.light.common.fastdfs.FastDFSClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 阿里云内容服务测试
 */
@SpringBootTest(classes = AdminApplication.class)
@RunWith(SpringRunner.class)
public class AliyunTest {

    @Autowired
    private GreeTextScan greeTextScan;

    @Autowired
    private GreenImageScan greenImageScan;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Test
    public void testImage() throws Exception {
        //group1/M00/00/00/wKjIgl9W6iOAD2doAAFY4E1K7-g384.png
        byte[] image1 = fastDFSClient.download("group1", "M00/00/00/wKhWgl_xuLuAMEAwAAkPTtRYVIs452.png");
        List<byte[]> images = new ArrayList<>();
        images.add(image1);
        Map map = greenImageScan.imageScan(images);
        System.out.println(map);
    }

    @Test
    public void testText() throws Exception {
        String text = "习近平";
        Map map = greeTextScan.greeTextScan(text);
        System.out.println(map);

    }

}
