package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;

import static javafx.scene.input.KeyCode.*;

public class getAvgNumbers {
    static Scene scene;
    static BorderPane border = new BorderPane();
    static Button button1;
    static Label label2WeeksLabel;
    static Label label2NumOfWeeks;
    static Text label2user;
    static Text label2numbers;
    static TextField inputNumber;
    static HBox header;
    static VBox vb2 = new VBox(15);
    static int[] average = new int[8];

    static UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("-?([1-9][0-9]*)?") && newText.length() < 3) {
            return change;
        }
        return null;
    };
    public static void calcAndDisplay(){

        Calculate.numDraws = Integer.valueOf(inputNumber.getText());
        average = Calculate.getAvgOfRecent();
        String nums = "";


        for (int it = 0; it < average.length; it++) {
            if (it == average.length - 1) {
                nums += " + " + average[it];
            } else {
                nums += average[it] + " ";
            }
        }
        label2numbers.setText(nums);
        label2numbers.setFont(Font.font(null, FontWeight.BOLD, 24));
        label2NumOfWeeks.setText("Average Numbers for last " + Calculate.numDraws + " weeks");
        System.out.println("numDraws: " + Calculate.numDraws);


    }

    public static void display(Stage stage){
        Stage window = new Stage();

        inputNumber = new TextField();
        inputNumber.setTextFormatter( new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
        inputNumber.clear();
        //inputNumber.setPrefWidth(20);

        inputNumber.setMaxWidth(30);
        button1= new Button("Get Lotto Numbers");
        label2user = new Text("Current User: " + restApi.username);
        label2user.setFont(new Font("Arial", 14));
        label2user.minHeight(15);
        label2numbers = new Text();
        label2WeeksLabel = new Label("Enter Number of weeks to be calculated:");
        label2NumOfWeeks = new Label("Average Numbers for " + Calculate.numDraws + " weeks");
        header = new HBox();
        header.setStyle("-fx-background-color: #42c8f4");
        header.setMinHeight(30);

        header.getChildren().add(label2user);
        vb2.getChildren().addAll(label2NumOfWeeks,label2numbers, label2WeeksLabel, inputNumber, button1);
        border.setTop(header);
        border.setCenter(vb2);






        inputNumber.setOnKeyPressed(event -> {
            if(event.getCode().equals(ENTER)) {
               calcAndDisplay();

            } });


        button1.setOnAction(e ->    {
                   calcAndDisplay();


        });


        scene =  new Scene(border, 300, 275);
        stage.setScene(scene);
        stage.show();
     // window.setScene(scene);
      //window.show();
    }

}


