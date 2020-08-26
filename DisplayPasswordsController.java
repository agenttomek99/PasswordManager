package PasswordManagerGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

import static PasswordManagerGUI.LoginScreenController.getLogin;
import static PasswordManagerGUI.LoginScreenController.getPassword;

public class DisplayPasswordsController {
    String name = "";
    String help0 = "";
    String help = "";
    String help1 = "";
    String decrypted = "";
    BigInteger help2;
    BigInteger help3;
    BigInteger[] encryption;
    ArrayList<BigInteger> decryption = new ArrayList();
    String login = getLogin();
    String password = getPassword();
    BigInteger d;
    Encryption bob = new Encryption();
    @FXML
    private ImageView loginNew;

    @FXML
    private MenuItem author;

    @FXML
    private Button newAccountBtn;

    @FXML
    private Button displayBtn;

    @FXML
    private Text text;

    @FXML
    private TextField newWebpage;

    @FXML
    private TextField newLogin;

    @FXML
    private PasswordField passwordNew;

    @FXML
    private Text authorMark;

    @FXML
    void addNewAccount(ActionEvent event) {
        //System.out.println("Name of the webpage");
        name = newWebpage.getText();
        name += "\n";

        name += newLogin.getText();
        name += "\n";

        name += passwordNew.getText();
        name += "\n";
        try {
            BufferedWriter fileKey = new BufferedWriter(new FileWriter("publickey.txt", true));
            BufferedWriter filePswd = new BufferedWriter(new FileWriter("passwords.txt", true));
            BufferedReader readKey = new BufferedReader(new FileReader("publickey.txt"));
            BufferedReader readPswd = new BufferedReader(new FileReader("passwords.txt"));
            help = readKey.readLine();
            help2 = new BigInteger(help);
            help3 = help2.modInverse(FileIO.result);
            encryption = bob.encrypt(name, help2, FileIO.result);
            for (int i = 0; i < encryption.length; i++)
                filePswd.write(encryption[i] + "\t" + "");
            filePswd.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void display(ActionEvent event) {
        try {
            text.setText("");
            BufferedWriter fileKey = new BufferedWriter(new FileWriter("publickey.txt", true));
            BufferedWriter filePswd = new BufferedWriter(new FileWriter("passwords.txt", true));
            BufferedReader readKey = new BufferedReader(new FileReader("publickey.txt"));
            BufferedReader readPswd = new BufferedReader(new FileReader("passwords.txt"));
            d = LoginScreenController.getD();
            help0 = readKey.readLine();
            while (true) {
                help = readPswd.readLine();
                if (help == null)
                    break;
                int pom1 = 0; //poczatek liczby
                for (; ; ) {
                    pom1 = help.indexOf("\t");
                    //System.out.println(pom1+" "+help.length());
                    if (pom1 == -1)
                        break;
                    help1 = help.substring(0, pom1);
                    help2 = new BigInteger(help1);
                    decryption.add(help2);
                    help = help.substring(pom1 + 1, help.length());
                    //System.out.println(decryption);
                }
                for (int i = 0; i < decryption.size(); i++) {
                    if (bob.decrypt(decryption.get(i), d, FileIO.result) == "\n") {
                        decrypted = "\n";
                    }
                    decrypted += (bob.decrypt(decryption.get(i), d, FileIO.result));
                    //System.out.println("Decryption:"+"\n"+bob.decrypt(decryption.get(i),d,user.result));
                }
                text.setText(text.getText() + "" + decrypted);
                filePswd.close();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void displayAuthor(ActionEvent event) {
        authorMark.setText("Copyright by" + "\n" + "Tomasz Kulnianin");
    }
}


