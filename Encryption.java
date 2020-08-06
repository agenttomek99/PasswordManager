package passwordManager;

import java.math.BigInteger;
import java.util.Random;

public class Encryption {
    /**
     * generates prime larger than given number. It's capable of creating huge numbers due to raising parameter to the power of 15. Thanks to the Big Integer implementation it handels that well.
     *
     * @param number
     * @return
     */

    static public BigInteger primeLargerThan(BigInteger number) //returns prime number larger than given number
    {
        for (; ; ) {
            number = number.pow(15);
            number = number.nextProbablePrime();
            if (number.isProbablePrime(1))
                return number;
        }

    }

    /**
     * @param phi
     * @return random e coprime to phi
     */
    static public BigInteger getE(BigInteger phi) //returns random e, 1<e<phi(n)
    {
        for (; ; ) {
            BigInteger e = new BigInteger(phi.bitLength(), new Random());
            if (e.gcd(phi).intValue() == 1 && phi.compareTo(e) == 1 && phi.compareTo(BigInteger.valueOf(0)) == 1)
                return e;
        }

    }

    /**
     * returns Euler's totient function by calculating (p-1)(q-1)
     *
     * @param p
     * @param q
     * @return Euler's totient
     */
    static public BigInteger getPhi(BigInteger p, BigInteger q) {
        return (p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)));
    }

    /**
     * Decrypts a signle sign
     *
     * @param encryptedpswd encrypted password
     * @param d             part of the RSA
     * @param n             part of the RSA
     * @return returns a decrypted single sign due to some length issues
     */

    public String decrypt(BigInteger encryptedpswd, BigInteger d, BigInteger n) {
        BigInteger additional;
        BigInteger result;
        String decryptedpsgn = ""; //decrytping sign-by-sign

        //for (int i = 0; i<encryptedpswd.length; i++)
        //{
        additional = encryptedpswd;
        result = additional.modPow(d, n);
        //System.out.println(result);
        decryptedpsgn += Character.toString(result.intValue());
        //}


        return decryptedpsgn;
    }

    /**
     * encrypts given string
     *
     * @param pswd string to encrpyt
     * @param e    part of the RSA
     * @param n    part of the RSA
     * @return encrypted password as a Big Integer array
     */
    public BigInteger[] encrypt(String pswd, BigInteger e, BigInteger n) {
        BigInteger[] encryptedpswd = new BigInteger[pswd.length()];
        BigInteger additional;
        BigInteger result;
        for (int i = 0; i < pswd.length(); i++) {
            encryptedpswd[i] = BigInteger.valueOf(pswd.charAt(i));
            additional = encryptedpswd[i];
            result = additional.modPow(e, n);
            encryptedpswd[i] = result;
        }
        return encryptedpswd;
    }

    /**
     * @param d   part of the RSA
     * @param phi Euler's totient function
     * @return modular multiplicative inverse
     */
    public int modularInverse(int d, int phi) // returns modular inverse
    {
        for (int i = 1; i < phi; i++) {
            if ((d * i) % phi == 1)
                return i;
        }
        return -1;
    }
}


