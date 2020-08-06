package passwordManager;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Tomasz Kulnianin
 * before use please read readme file!
 */

public class Test {

    public static void main(String[] args) {

        try {
            BigInteger phi;
            BigInteger d;
            BufferedWriter fileKey = new BufferedWriter(new FileWriter("publickey.txt", true));
            BufferedWriter filePswd = new BufferedWriter(new FileWriter("passwords.txt", true));
            BufferedReader readKey = new BufferedReader(new FileReader("publickey.txt"));
            BufferedReader readPswd = new BufferedReader(new FileReader("passwords.txt"));

            Scanner keyboard = new Scanner(System.in);
            passwordManager.FileIO user = new passwordManager.FileIO();
            passwordManager.Encryption bob = new passwordManager.Encryption();
            String password = "";
            String login = "";
            String name = "";
            String help0 = "";
            String help = "";
            String help1 = "";
            String decrypted = "";
            BigInteger help2;
            BigInteger help3;
            ArrayList<BigInteger> decryption = new ArrayList<BigInteger>();
            BigInteger[] encryption;
            int decision = -1;
            //PrintWriter write = new PrintWriter(file);
            for (; ; ) {
                System.out.println("What'd you like to do?" + "\n" + "");
                System.out.println("1. Add user");
                System.out.println("2. Add password to user's valut");
                System.out.println("3. View user's vault");
                System.out.println("4. To exit");
                decision = keyboard.nextInt();
                keyboard.nextLine();
                if (decision == 1) {
                    System.out.println("Enter your login");
                    login = keyboard.nextLine();
                    System.out.println("Enter your password");
                    password = keyboard.nextLine();
                    //write.write(user.addUser(password, login) + "\n");
                    user.addUser(password, login);
                    //fileKey.newLine();
                    fileKey.write(String.valueOf(user.e));
                    login = "";
                    password = "";
                    System.out.println("Your d: " + "\n" + user.e.modInverse(user.phi));
                    System.out.println("KEEP IT SOMEWHERE SAFE");
                    fileKey.close();
                }

                if (decision == 2) {
                    System.out.println("Enter your login");
                    login = keyboard.nextLine();
                    System.out.println("Enter your password");
                    password = keyboard.nextLine();
                    user.addUser(password, login);
                    System.out.println("How many accounts'd you like to add?");
                    int n = keyboard.nextInt();
                    keyboard.nextLine();
                    help = readKey.readLine();
                    help2 = new BigInteger(help);
                    for (; n > 0; n--) {
                        System.out.println("Name of the webpage");
                        name = keyboard.nextLine();
                        name += "\n";
                        System.out.println("Login");
                        name += keyboard.nextLine();
                        name += "\n";
                        System.out.println("Password");
                        name += keyboard.nextLine();
                        name += "\n";
                        help3 = help2.modInverse(user.result);
                        encryption = bob.encrypt(name, help2, user.result);
                        //filePswd.newLine();
                        for (int i = 0; i < encryption.length; i++)
                            filePswd.write(encryption[i] + "\t" + "");
                        //filePswd.newLine();
                    }
                    filePswd.close();


                }
                if (decision == 3) {
                    System.out.println("Enter your login");
                    login = keyboard.nextLine();
                    System.out.println("Enter your password");
                    password = keyboard.nextLine();
                    user.addUser(password, login);
                    System.out.println("Enter your d:");
                    d = keyboard.nextBigInteger();
                    help0 = readKey.readLine();
                    while (true) {
                        help = readPswd.readLine();
                        if (help == null)
                            break;
                        int pom1 = 0; //starts right here
                        for (; ; ) {
                            pom1 = help.indexOf("\t");
                            //System.out.println(pom1+" "+help.length());
                            if (pom1 == -1)
                                break;
                            help1 = help.substring(0, pom1);
                            help2 = new BigInteger(help1);
                            decryption.add(help2);
                            help = help.substring(pom1 + 1);
                            //System.out.println(decryption);
                        }
                        for (int i = 0; i < decryption.size(); i++) {
                            if (bob.decrypt(decryption.get(i), d, user.result) == "\n") {
                                decrypted = "\n";
                            }
                            decrypted += (bob.decrypt(decryption.get(i), d, user.result));
                            //System.out.println("Decryption:"+"\n"+bob.decrypt(decryption.get(i),d,user.result));
                        }
                        System.out.println(decrypted);

                    }

                }
                if (decision == 4)
                    break;

            }

        } catch (FileNotFoundException e) {
            System.out.println("Holy moly sth wrong with the files. Maybe you forgot to create them?");
        } catch (IOException e) {
            System.out.println("Holy moly IOException buddy");
        }


    }
}
