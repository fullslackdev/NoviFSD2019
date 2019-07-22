package dev.fullslack;

import dev.fullslack.security.EncryptSecret;
import dev.fullslack.security.TimeBasedOneTimePasswordUtil;

import java.util.Scanner;

public class TwoFactorAuthExample {

    public static void main(String[] args) throws Exception {
        EncryptSecret secret = new EncryptSecret();
        //KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        //keyGen.init(256, SecureRandom.getInstanceStrong());
        //String base32Secret = "LBD4ESAG6RLQLKGMQ2TXELVZ6ACZ6BEX";
        String base32Secret = "";
        /*for (int i = 0; i < 10; i++) {
            base32Secret = TimeBasedOneTimePasswordUtil.generateBase32Secret();
            System.out.println("Secret = " + base32Secret);
        }*/

        //for (int i = 0; i < 10; i++) {
        base32Secret = TimeBasedOneTimePasswordUtil.generateBase32Secret(32);
        //System.out.println("Secret = " + base32Secret);

        //SecretKey secretKey = keyGen.generateKey();
        //SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        String secretKeySpec = secret.createSecretKeySpec();
        secretKeySpec = "dK09XhczjxVvFBkBuXrF2oLdoBLQrm8y7WnAckj6VC8=";
        //System.out.println("Secret key = " + secretKeySpec);

        String encryptedBase32Secret = secret.encrypt(base32Secret, secretKeySpec);
        encryptedBase32Secret = "oEiOgaldsR/Tuor6Mfzkh7fHIfntefDVZ8s5sMHiQARS+5bSdBrbLNPxKFu7ZjAN+WMqJSRXPCQ4AIl9";
        //System.out.println("Encrypted secret = " + encryptedBase32Secret);

        String decryptedBase32Secret = secret.decrypt(encryptedBase32Secret, secretKeySpec);
        System.out.println("Decrypted secret = " + decryptedBase32Secret);

        //System.out.println();
        //}

        base32Secret = decryptedBase32Secret;

        String keyId = "admin@fullslack.dev";
        System.out.println("Image URL = " + TimeBasedOneTimePasswordUtil.qrImageUrl(keyId, base32Secret));

        String code = TimeBasedOneTimePasswordUtil.generateCurrentNumberString(base32Secret);
        System.out.println("Code = " + code);

        /*while (true) {
            long diff = 30 - ((System.currentTimeMillis() / 1000) % 30);
            code = TimeBasedOneTimePasswordUtil.generateCurrentNumberString(base32Secret);
            System.out.println("Secret code = " + code + ", change in " + diff + " seconds");
            Thread.sleep(2000);
        }*/

        Scanner input = new Scanner(System.in);

        System.out.print("Enter auth code: ");
        int authNumber = input.nextInt();
        System.out.println();

        boolean test = TimeBasedOneTimePasswordUtil.validateCurrentNumber(base32Secret, authNumber, 10000);
        System.out.println("Code was " + test);
    }
}
