package jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

public class TestJasypt {
  public static void main(String[] args) {
    StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
    standardPBEStringEncryptor.setPassword("Demo_Pwd!2020");
    standardPBEStringEncryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
    standardPBEStringEncryptor.setIvGenerator(new RandomIvGenerator());
    String results = standardPBEStringEncryptor.encrypt("springCloud_Pwd!");
    System.out.println(results);
    System.out.println(standardPBEStringEncryptor.decrypt(results));

    String results2 = standardPBEStringEncryptor.encrypt("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");
    System.out.println(results2);
    System.out.println(standardPBEStringEncryptor.decrypt(results2));
  }
}
