package ch.obermuhlner.javafx.gridform.example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BugsApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root);

        String text = "Fish";

        /*
        GridPane gridPane = new GridPane();
        root.getChildren().add(gridPane);

        int rowIndex = 0;
        gridPane.add(new CheckBox(text), 0, rowIndex++);
        gridPane.add(new CheckBox(text), 0, rowIndex++);
        gridPane.add(new RadioButton(text), 0, rowIndex++);
        gridPane.add(new RadioButton(text), 0, rowIndex++);
        gridPane.add(new Button("Quite a long text"), 0, rowIndex++);
        */

        VBox vBox = new VBox();
        //vBox.setFillWidth(true);
        root.getChildren().add(vBox);

        vBox.getChildren().add(new CheckBox(text));
        vBox.getChildren().add(new CheckBox(text));
        vBox.getChildren().add(new RadioButton(text));
        vBox.getChildren().add(new RadioButton(text));
        vBox.getChildren().add(new Button("Quite a long text"));

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
