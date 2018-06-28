package com.zhitail.app.soa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zhitail.frame.common.upload.FileRepository;
import com.zhitail.frame.util.service.Result;

@RequestMapping("/services/PublicSvc")
@RestController
public class PublicSvc {
	@Autowired
	private  FileRepository fileRepository;
	@RequestMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result upload(String name,@RequestParam("file") MultipartFile file) throws IOException{
      
        String address = null;
        String origName = (name!=null?name:file.getOriginalFilename());
        String ext = FilenameUtils.getExtension(origName).toLowerCase(
                Locale.ENGLISH);
    
        address = fileRepository.storeByExt(ext,file);
    
        return  new Result(address);
    }
	@RequestMapping(value = "/uploadMultiple", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result upload(String name,@RequestParam("files") MultipartFile[] files) throws IOException{
      
        String address = null;
        List<String> list = new ArrayList<String>();
        for(MultipartFile file:files) {
        String origName = (name!=null?name:file.getOriginalFilename());
        String ext = FilenameUtils.getExtension(origName).toLowerCase(
                Locale.ENGLISH);
    
        address = fileRepository.storeByExt(ext,file);
        list.add(address);
        }
        return  new Result(list);
    }

}
