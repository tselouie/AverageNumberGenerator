package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;




public class Main extends Application {
    GridPane gridPane;
    Button loginButton,clearButton,testButton;
    Text  userLabel,passLabel, title;
    Scene scene1;
    TextField userName;
    PasswordField password;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Average Lottery Number Generator");
        primaryStage.sizeToScene();




        gridPane = new GridPane();
        gridPane.setMinSize(400, 150);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        title = new Text("LOTTO GENERATOR");
        title.setFont(new Font("Oswald",24));
        userName = new TextField();
        userName.setPromptText("Enter e-mail");
        userName.setMaxWidth(200);
        password = new PasswordField();
        password.setPromptText("Enter Password");
        password.setMaxWidth(200);
        userLabel = new Text("Username:");

        passLabel = new Text("Password:");

        loginButton = new Button("Login");
        clearButton = new Button("Clear");
        testButton = new Button("Test");
        gridPane.add(title,0,0, 2,2);
        gridPane.add(userLabel, 0, 3);
        gridPane.add(userName, 1, 3);
        gridPane.add(passLabel, 0, 4);
        gridPane.add(password, 1, 4);
        gridPane.add(loginButton, 0, 5);
        gridPane.add(clearButton, 1, 5);
        gridPane.add(testButton, 0, 6);


        scene1 = new Scene(gridPane, 250, 200);
        password.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                System.out.println("Logged in as:");
                System.out.println("username: " + userName.getText());
                restApi.username = userName.getText();
                restApi.password = password.getText();
                System.out.println("password: " + password.getText());
                restApi.getToken();

                if(restApi.access_token != "") {

                    getAvgNumbers.display(primaryStage);
                }else{
                    Alert.display("Warning","Incorrect Username or Password.");
                }
            }
            });
        clearButton.setOnAction(e -> {
             userName.clear();
             password.clear();

            });

       loginButton.setOnAction(e -> {

                System.out.println("Logged in as:");
           System.out.println("username: " + userName.getText());
           restApi.username = userName.getText();

           restApi.password = password.getText();
           System.out.println("password: " + password.getText());
           restApi.getToken();

if(restApi.access_token != "") {
    System.out.println("Second screen showing..");
    getAvgNumbers.display(primaryStage);

}else{
    Alert.display("Warning","Incorrect Username or Password.");
}
        });

        testButton.setOnAction(e -> {

            primaryStage.setScene(scene1);
            primaryStage.show();
        });


        primaryStage.setScene(scene1);
        primaryStage.show();

    }



    public static void main(String[] args) {
        launch(args);

    }

}

