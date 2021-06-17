package org.fleet.modules.oss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.fleet.modules.oss.entity.OSSFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IOSSFileService extends IService<OSSFile> {

    void upload(MultipartFile multipartFile) throws IOException;

    boolean delete(OSSFile ossFile);

}
