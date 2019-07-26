package ch.obermuhlner.javafx.gridform.example;

import ch.obermuhlner.javafx.gridform.GridForm;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Arrays;

public class ExampleApp extends Application {

    public enum Animal {
        Dog,
        Cat,
        Cow,
        Fish
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root);

        TabPane mainTabPane = new TabPane();
        root.getChildren().add(mainTabPane);
        mainTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        {
            GridPane gridPane = new GridPane();
            GridForm gridForm = new GridForm(gridPane);

            ListProperty<String> stringListProperty = new SimpleListProperty<>(FXCollections.observableArrayList(
                    "alpha", "beta", "gamma"));
            ListProperty<Animal> animalListProperty = new SimpleListProperty<>(FXCollections.observableArrayList(
                    Animal.values()));

            StringProperty stringProperty = new SimpleStringProperty("StringProperty");
            IntegerProperty integerProperty = new SimpleIntegerProperty(123);
            ObjectProperty<Animal> animalProperty = new SimpleObjectProperty<>();

            gridForm.addLabel("Label");
            gridForm.addLabel("Label", stringProperty);
            gridForm.addLabel("Label", integerProperty, GridForm.INTEGER_FORMAT);
            gridForm.addEmpty();

            gridForm.addTextField("TextField", stringProperty);
            gridForm.addEmpty();

            gridForm.addTextArea("TextArea", stringProperty);
            gridForm.addEmpty();

            gridForm.addComboBox("ComboBox", stringListProperty, stringProperty);
            gridForm.addComboBox("ComboBox", animalListProperty, animalProperty);
            gridForm.addComboBox("ComboBox", Arrays.asList(Animal.values()), animalProperty);
            gridForm.addEmpty();

            mainTabPane.getTabs().add(new Tab("Simple", gridPane));
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
