package PasswordManagerGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class LoginScreenController {

    Scanner keyboard = new Scanner(System.in);
    FileIO user = new FileIO();


    private static String password = "";
    private static String login = "";
    private static BigInteger dBIG;
    private static BigInteger e;


    private Controller controller;

    @FXML
    private PasswordField lgn;

    @FXML
    private MenuItem author;

    @FXML
    private PasswordField pswd;

    @FXML
    private PasswordField d;

    @FXML
    private Button lgnBtn;

    @FXML
    private Button createActBtn;

    @FXML
    private Text dIsSet;

    @FXML
    private Text authorMark;

    @FXML
    void createAccount(ActionEvent event) {
        try {
            BufferedWriter fileKey = new BufferedWriter(new FileWriter("publickey.txt", true));
            BufferedWriter filePswd = new BufferedWriter(new FileWriter("passwords.txt", true));
            BufferedReader readKey = new BufferedReader(new FileReader("publickey.txt"));
            BufferedReader readPswd = new BufferedReader(new FileReader("passwords.txt"));
            login = lgn.getText();
            password = pswd.getText();
            //System.out.println(login+password);
            user.addUser(password, login);
            fileKey.write(String.valueOf(user.e));
            d.setText(user.e.modInverse(user.phi).toString());
            copyToClipboard(d.getText());
            dIsSet.setText("  D has been copied to clipboard" + "\n" + "    KEEP IT SOMEWHERE SAFE");
            fileKey.close();

        } catch (Exception e) {
            System.out.println("An error occured my friend");
        }
    }

    @FXML
    void login(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("displayPasswords.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            System.out.println("An error occured my friend");
        }
        user.addUser(pswd.getText(), lgn.getText());
        dBIG = new BigInteger(d.getText());
        //System.out.println(pswd.getText()+" "+lgn.getText()+" "+dBIG);
        controller.setScreen(pane);
        //System.out.println(FileIO.result);
    }

    @FXML
    void displayAuthor(ActionEvent event) {
        authorMark.setText("Copyright by" + "\n" + "Tomasz Kulnianin");
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public static String getPassword() {
        return password;
    }

    public static String getLogin() {
        return login;
    }

    public static BigInteger getD() {
        return dBIG;
    }

    public static void copyToClipboard(String string) {

        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent copy = new ClipboardContent();

        copy.putString(string);
        clipboard.setContent(copy);

    }

}
