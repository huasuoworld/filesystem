package org.hua.filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author: huacailiang
 * @date: 2019/9/17
 * @description:
 **/
public class HdfsUtils {

  public static void upload() {

    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
      HttpPut httpPut = new HttpPut(
          "http://192.168.1.210:50075/webhdfs/v1/eis/files/test/test.png?op=CREATE&namenoderpcaddress=0.0.0.0:9000");
      File file = new File("/Users/huacailiang/Downloads/zhimaide/screenshots.png");
      InputStreamEntity reqEntity = new InputStreamEntity(
          new FileInputStream(file), -1, ContentType.APPLICATION_OCTET_STREAM);
      reqEntity.setChunked(true);
      httpPut.setEntity(reqEntity);
      System.out.println("Executing request: " + httpPut.getRequestLine());
      try (CloseableHttpResponse response = httpclient.execute(httpPut)) {
        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void downloadFromHdfs() {
    File file = new File("/Users/huacailiang/Downloads/zhimaide/123456.png");
    try {
      FileUtils.copyURLToFile(new URL("http://192.168.1.210:50075/webhdfs/v1/eis/files/test/screenshots.png?op=OPEN&namenoderpcaddress=0.0.0.0:9000&offset=0"), file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
