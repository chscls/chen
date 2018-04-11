package com.zhitail.frame.common.upload;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;




@Component
public class NewFileRepository {

	public String storeByExt(String basePath,String keyword, String ext, MultipartFile file)
			throws IOException {
			String filename = UploadUtils.generateFilename(keyword, ext);
			File root=new File(basePath);
			UploadUtils.checkDirAndCreate(root);
			String destPath=root.getAbsolutePath()+filename;
			File dest = new File(destPath);
			dest = UploadUtils.getUniqueFile(dest);
			store(file, dest);
			return filename;
	}
	private void store(MultipartFile file, File dest) throws IOException {
		try {
			UploadUtils.checkDirAndCreate(dest.getParentFile());
			file.transferTo(dest);
		} catch (IOException e) {
		
			throw e;
		}
	}

	private String getKeyWord(String path){
		return path.split("/")[0];
		
	}
}

