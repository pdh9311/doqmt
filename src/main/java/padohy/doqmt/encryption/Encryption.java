package padohy.doqmt.encryption;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
  public static String sha512(String str) {
    String password;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      md.update(str.getBytes());
      byte[] digest = md.digest();
      password = new BigInteger(1, digest).toString(16).toUpperCase();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return password;
  }
}
