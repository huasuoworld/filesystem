package org.hua.filesystem;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;


/**
 * mongodb存储附件，最大16M，超过16M请拆分存储
 *
 */
@Repository("fileCommDAO")
public class FileDAO implements FileCommDAO {
	
	private final static Logger log = LoggerFactory.getLogger(FileDAO.class);
	
	@Autowired
	private GridFsTemplate gridFsTemplate;
	@Autowired
	private MongoDbFactory mongoDbFactory;
	
	private GridFSBucket gridFSBucket() {
		return GridFSBuckets.create(mongoDbFactory.getDb());
	}
	/**
	 * 上传附件
	 * 参数:MultipartFile uploadfile
	 * */
	@Override
	public FileParam upload(FileParam upload) {
		log.info("FileDAO>>upload");
		String filename = UUID.randomUUID().toString().replaceAll("-", "");
		InputStream is = null;
		try {
			if(StringUtils.isEmpty(upload.getBase64file())) {
				log.error("文件base64不能为空！");
				return null;
			} 
			if(StringUtils.isEmpty(upload.getFullname())) {
				log.error("文件全名不能为空！");
				return null;
			} 
			String extension = FilenameUtils.getExtension(upload.getFullname());
			filename = filename + "." + extension;
			//创建临时文件
			byte[] imageByteArray = null;
			if(StringUtils.isEmpty(upload.getEncodetype())) {
				imageByteArray = Base64Util.decodeImage(upload.getBase64file());
			} 
			else if("1".equals(upload.getEncodetype())) {
				imageByteArray = Base64Util.decodeUrlImage(upload.getBase64file());
			}
			else if("2".equals(upload.getEncodetype())) {
				String base64file = URLDecoder.decode(upload.getBase64file(), "UTF-8");
				imageByteArray = Base64Util.decodeUrlImage(base64file);
			}
			is = new ByteArrayInputStream(imageByteArray);
	    	gridFsTemplate.store(is, filename, extension);
	    	FileParam outfile = new FileParam();
	    	outfile.setFullname(filename);
	    	return outfile;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		} finally {
			if( is != null ) {
				try {
					is.close();
				} catch (Exception e2) {
					log.error(e2.getMessage(), e2);
				}
			}
		}
	}

	/**
	 * 下载附件
	 * 参数：filename
	 * */
	@Override
	public FileParam download(FileParam download) {
		log.info("FileDAO>>download");
		try {
			Query query = new Query();
	        query.addCriteria(Criteria.where("filename").is(download.getFullname()));
			GridFSFile gridFSFile =  gridFsTemplate.findOne(query);
			if(gridFSFile == null) {
	        	log.error("没有找到该文件！");
	        	return null;
	        }
			GridFSDownloadStream stream = gridFSBucket().openDownloadStream(gridFSFile.getObjectId());
			byte imageData[] = new byte[Long.valueOf(gridFSFile.getLength()).intValue()];
			try {
				stream.read(imageData);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			stream.close();
			
        	FileParam outfile = new FileParam();
			String base64file = Base64Util.encodeImage(imageData);
			log.info("FileDAO>>download>>base64file>>" + base64file);
			outfile.setBase64file(base64file);
        	return outfile;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
