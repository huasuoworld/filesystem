package org.hua.filesystem;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;


/**
 * mongodb存储附件，最大16M，超过16M请拆分存储
 */
@Repository("hdfsFileDAO")
public class HdfsFileDAO implements FileCommDAO {

  private final static Logger log = LoggerFactory.getLogger(HdfsFileDAO.class);

  /**
   * 上传附件 参数:MultipartFile uploadfile
   */
  @Override
  public FileParam upload(FileParam upload) {
    log.info("FileDAO>>upload>>" + upload.toString());
    String filename = UUID.randomUUID().toString().replaceAll("-", "");
    try {
      if (StringUtils.isEmpty(upload.getBase64file())) {
        log.error("文件base64不能为空！");
        return null;
      }
      if (StringUtils.isEmpty(upload.getFullname())) {
        log.error("文件全名不能为空！");
        return null;
      }
      String extension = FilenameUtils.getExtension(upload.getFullname());
      filename = filename + "." + extension;
      //创建临时文件
      byte[] imageByteArray = null;
      if (StringUtils.isEmpty(upload.getEncodetype())) {
        imageByteArray = Base64Util.decodeImage(upload.getBase64file());
      } else if ("1".equals(upload.getEncodetype())) {
        imageByteArray = Base64Util.decodeUrlImage(upload.getBase64file());
      } else if ("2".equals(upload.getEncodetype())) {
        String base64file = URLDecoder.decode(upload.getBase64file(), "UTF-8");
        imageByteArray = Base64Util.decodeUrlImage(base64file);
      }

      FileCopyUtils.copy(imageByteArray, new File(
          "/Users/huacailiang/Downloads/zhimaide/" + UUID.randomUUID().toString() + ".png"));
      FileParam outfile = new FileParam();
      outfile.setFullname(filename);
      return outfile;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  /**
   * 下载附件 参数：filename
   */
  @Override
  public FileParam download(FileParam download) {
    log.info("FileDAO>>download>>" + download.toString());
    try {
      File file = new File("/Users/huacailiang/Downloads/zhimaide/"+UUID.randomUUID().toString()+".png");
      FileUtils.copyURLToFile(new URL(
              "http://192.168.1.210:50075/webhdfs/v1/eis/files/test/"+download.getFullname()+"?op=OPEN&namenoderpcaddress=0.0.0.0:9000&offset=0"),
          file);
      byte[] imageData = FileCopyUtils.copyToByteArray(file);
      FileParam outfile = new FileParam();
      outfile.setFileByte(imageData);
      return outfile;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }
}
