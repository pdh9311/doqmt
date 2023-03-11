package padohy.doqmt.utils;

import lombok.Getter;
import lombok.ToString;
import org.apache.tika.Tika;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Getter
@ToString
public class ImageConverter {

  private String filename;
  private String mimeType;
  private String base64;
  private String dataUrl;

  // File -> Binary Data -> Base64 encode -> String 반환.
  public ImageConverter(File file) {
    filename = file.getName();
    mimeType = new Tika().detect(filename);
    base64 = imageToBase64(file);
    dataUrl = createDataUrl();
  }

  private String imageToBase64(File file) {
    String base64String;
    FileInputStream fis = null;
    ByteArrayOutputStream baos = null;
    try {
      fis = new FileInputStream(file);
      baos = new ByteArrayOutputStream();
      int len = 0;
      byte[] buf = new byte[1024];
      while ((len = fis.read(buf)) != -1) {
        baos.write(buf, 0, len);
      }
      byte[] fileArray = baos.toByteArray();
      base64String = new String(Base64.encodeBase64String(fileArray));
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        baos.close();
        fis.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    return base64String;
  }

  private String createDataUrl() {
    return new StringBuilder()
        .append("data:")
        .append(mimeType)
        .append(";base64,")
        .append(base64)
        .toString();
  }


}
