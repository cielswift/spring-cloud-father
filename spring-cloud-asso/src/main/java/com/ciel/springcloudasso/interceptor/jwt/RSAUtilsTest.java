package com.ciel.springcloudasso.interceptor.jwt;

import org.springframework.util.Base64Utils;

class RSAUtilsTest {

    //private static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAHzDQssuIce0x5thZdTbS1N/iJtkpluE5X3sU0nvRIUAmkqVi9zCuJrRizpOCSGatkX21gQjwJRb7Fz/H38HuyeSv6myLHBbQU866eU3nJPpuQcYiWQMbcWv+y2Y9y0oOqjEZwv+ERj0mLYGO+SHftYwJ3T6yyeHXd3VcMBKkiQIDAQAB";
    //private static String  PRIVATE_KEY= "MIICWwIBAAKBgQCAHzDQssuIce0x5thZdTbS1N/iJtkpluE5X3sU0nvRIUAmkqVi9zCuJrRizpOCSGatkX21gQjwJRb7Fz/H38HuyeSv6myLHBbQU866eU3nJPpuQcYiWQMbcWv+y2Y9y0oOqjEZwv+ERj0mLYGO+SHftYwJ3T6yyeHXd3VcMBKkiQIDAQABAoGAGk/Ng56kY/IZpzzkQ4Lp7mRfUh9uS7c7q7+rFDyhxvIQLZvCMU/YhHtYYk5QRto9k/mkGmlUf5TuYkSFCbQvLGGpc7WKOvEuoHzXcHAc1peQa5+7WKDjhs8R9W6T5g4cYnqknTWNh1nfVbM9GOBdDLFxBJgJVh/r2fvhpB1WL4ECQQCQf2yrKiiHJsEIuO67iVRy54HcEnZvV0nYVnhFNWfDLtMIm/6I7G6n0MZHGWCIs77EPAhiMtg9IioE3I0Q/neZAkEA4vzQE6VcG12MoYkmudgmpnO3YjPdST9Ui1sEhrxvqXNru+ykfwx3kWZt42yv5471FytVSPJwXwX63fnfNp7qcQJAbRaWLr68LBR45SHgsdpi1ACtTDzwBuzKRjY5xF4mQPenwLcsOajtfWojVuf0th+lJLcByUkDcVvKhzMOmMbT+QJAPc9GAfOHb1Q8FUi5qOW5MJ5WE4G4Algid4gjZWUuqt/pOFUqPgZxEMDpr4JLLCz7hIGiSajq/JUuSP/fzonboQJAbWcNpngj5xddtGn/PVE1Zv9iwJmfD+/9Y9nQ+0AO/FFK6ruFEjikefkrB8byBcGy6fU7ax7AjqsF5HdsE1BfaA==";

    private static String PUBLIC_KEY ="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJmx3bmIH/tnrh/ZCdnJLu46j64HFaAkfOD2ws6tIveUYoc0pefkXg3/mF3QaNGWXaYpm5p5YyvcyR3EHtB18E0CAwEAAQ==";
    private static String  PRIVATE_KEY= "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAmbHduYgf+2euH9kJ2cku7jqPrgcVoCR84PbCzq0i95RihzSl5+ReDf+YXdBo0ZZdpimbmnljK9zJHcQe0HXwTQIDAQABAkAvzlx6VjYke/Z0X40p2eMoJL0GU5vNaSwSyY6EvOPwgBLNta5BOGHzbXFuNL0iBLg6me3DNhfOjMxqnTiXHFudAiEAyjbe5NkO3vD3k+qaAAcqvYz4BO736UxrYbUJQgPfBh8CIQDCkzbS13gceIAtMYSn0tlOWkswtS1sZv06W4K5ezAEEwIgBuCo4hWz2MdPVFuVU0zrGjlSMjDCg8/SkycqInGpQzcCIQC3SYPAUzaS4zQi3l2O1YDcXRQtOs4iKlXqLh/TZ2aQvwIhAL2A1NqFoO3Kx/IMYp6/rrvALeS0YwnGDyQ/8cJsNa+G";

    public static String rsaPublicEncryptAndPrivateKeyDecrypt(String rawText) {

        byte[] privateKey = Base64Utils.decodeFromString(PRIVATE_KEY);
        byte[] publicKey = Base64Utils.decodeFromString(PUBLIC_KEY);
        try {
            System.out.println("原数据为:"+rawText);
            byte[] encryptByPublicKey = RSAUtils.encryptByPublicKey(rawText.getBytes(),
                    publicKey);

            String encryptByPublicKeyString = Base64Utils.encodeToString(encryptByPublicKey);
            System.out.println("公钥加密后的数据为:"+encryptByPublicKeyString);

            byte[] decryptByPrivateKey = RSAUtils.decryptByPrivateKey(
                    Base64Utils.decodeFromString(encryptByPublicKeyString),
                    privateKey);

            String result=new String(decryptByPrivateKey);
            System.out.println("私钥解密后的数据为:"+result);
            return result;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }



    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        System.out.println(rsaPublicEncryptAndPrivateKeyDecrypt("Hello world!"));
        System.out.println(System.currentTimeMillis()-l);

    }
}