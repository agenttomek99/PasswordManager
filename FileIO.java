package passwordManager;

import java.math.BigInteger;

import static passwordManager.Encryption.*;

public class FileIO {

    BigInteger phi;
    BigInteger result;
    BigInteger e;

    /**
     * Function handles logging in and calculating all the necessary values
     *
     * @param pswd  user's password
     * @param login user's login
     * @return n - part of the RSA
     */
    public String addUser(String pswd, String login) {
        int add1 = Math.abs(pswd.hashCode());
        int add2 = Math.abs(login.hashCode());
        BigInteger hpswd = primeLargerThan(BigInteger.valueOf(Math.abs(add1)));
        BigInteger hlogin = primeLargerThan(BigInteger.valueOf(Math.abs(add2)));
        phi = getPhi(hpswd, hlogin);
        e = getE(phi);
        hpswd = hpswd.abs();
        hlogin = hlogin.abs();
        //System.out.println(hpswd);
        //System.out.println(hlogin);
        result = hpswd.multiply(hlogin);
        //System.out.println("e = "+e);
        //System.out.println("phi = "+phi);
        //System.out.println("n = "+result);
        return result.toString();

    }
}
