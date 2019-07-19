package dev.fullslack;

import dev.fullslack.security.TimeBasedOneTimePasswordUtil;

import java.util.Scanner;

public class TwoFactorAuthExample {

    public static void main(String[] args) throws Exception {
        //String base32Secret = "LBD4ESAG6RLQLKGMQ2TXELVZ6ACZ6BEX";
        String base32Secret = TimeBasedOneTimePasswordUtil.generateBase32Secret();
        System.out.println("Secret = " + base32Secret);

        String keyId = "user@fullslack.dev";
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
