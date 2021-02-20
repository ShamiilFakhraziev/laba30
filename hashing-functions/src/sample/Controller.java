package sample;

import javax.security.auth.login.LoginException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnEnter;

    @FXML
    private Label labelAnswer;

    @FXML
    private Button btnLook;

    @FXML
    private TextField textField;
    String line = "";


    @FXML
    void initialize() {
        try {
            File file = new File("Файл.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            line = reader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnLook.setOnMouseClicked(event -> {

            MessageDigest sha1 = null;
            try {
                sha1 = MessageDigest.getInstance("SHA-1");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] bytes = sha1.digest(line.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                builder.append(String.format("%02X ", b));
            }
            textField.setText(line);
            labelAnswer.setText(builder.toString());
            try (FileWriter writer = new FileWriter("Файл.txt", false)) {
                writer.write(builder.toString());
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        btnEnter.setOnMouseClicked(event -> {
            String finalResult = textField.getText();
            MessageDigest sha1 = null;
            try {
                sha1 = MessageDigest.getInstance("SHA-1");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] bytes = sha1.digest(finalResult.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                builder.append(String.format("%02X ", b));
            }
            textField.setText(finalResult);
            labelAnswer.setText(builder.toString());
            try (FileWriter writer = new FileWriter("Файл.txt", false)) {
                writer.write( builder.toString());
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });

    }
}
