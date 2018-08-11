package org.hua.filesystem;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(allowCredentials="true", origins="*")
@RestController
public class FileController {
	
	private final static Logger LOG = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileService fileService;
	
	@GetMapping("api/file/hello")
	public FileParam hello(@RequestBody FileParam fileParam) {
		try {
			return fileParam;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
	}

	@PostMapping("api/file/upload")
	public String upload(@RequestBody FileParam upload) {
		FileParam outFile = null;
		try {
			outFile = fileService.upload(upload);
			return outFile.getFullname();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
	}
	
	@GetMapping("api/file/download")
	public HttpEntity<byte[]> download(String fullname) {
		InputStream is = null;
		try {
			FileParam file = new FileParam();
			file.setFullname(fullname);
			FileParam download = fileService.download(file);
			
			//创建临时文件
			byte[] imageByteArray = Base64Util.decodeImage(download.getBase64file());
			/** assume that below line gives you file content in byte array **/
		    // prepare response
		    HttpHeaders header = new HttpHeaders();
		    header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fullname);
		    return new HttpEntity<byte[]>(imageByteArray, header);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		} finally {
			try {
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
				LOG.error(e2.getMessage(), e2);
			}
		}
	}
	
	@GetMapping("api/file/lookup")
	public HttpEntity<byte[]> lookup(String fullname) {
		InputStream is = null;
		try {
			FileParam file = new FileParam();
			file.setFullname(fullname);
			FileParam download = fileService.download(file);
			
			//创建临时文件
			byte[] imageByteArray = Base64Util.decodeImage(download.getBase64file());
			/** assume that below line gives you file content in byte array **/
		    HttpHeaders header = new HttpHeaders();
		    header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fullname);
		    header.setContentType(MediaTypeFactory.getMediaType(fullname).get());
		    return new HttpEntity<byte[]>(imageByteArray, header);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		} finally {
			try {
				if(is != null) {
					is.close();
				}
			} catch (Exception e2) {
				LOG.error(e2.getMessage(), e2);
			}
		}
	}
	public static void main(String[] args) {
		try {
			File file = new File("D:/20180630160454.jpg");
			byte[] imageData = FileUtils.readFileToByteArray(file);
			String base64 = Base64Util.encodeImage(imageData);
			System.out.println(base64);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
