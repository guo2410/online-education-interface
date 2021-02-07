package com.guli.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-21 11:59
 */
public interface FileService {
    String upload(MultipartFile file);
}
