package ch.obermuhlner.javafx.gridform.example;

import ch.obermuhlner.javafx.gridform.GridForm;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
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

        ListProperty<String> stringListProperty = new SimpleListProperty<>(FXCollections.observableArrayList(
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
        ListProperty<Animal> animalListProperty = new SimpleListProperty<>(FXCollections.observableArrayList(
                Animal.values()));

        StringProperty stringProperty = new SimpleStringProperty("StringProperty");
        IntegerProperty integerProperty = new SimpleIntegerProperty(1234);
        DoubleProperty doubleProperty = new SimpleDoubleProperty(Math.PI);
        BooleanProperty booleanProperty = new SimpleBooleanProperty();
        ObjectProperty<Animal> animalProperty = new SimpleObjectProperty<>();
        ListProperty<String> stringListProperty2 = new SimpleListProperty<>(FXCollections.observableArrayList("a", "b", "Unknown"));

        {
            GridPane gridPane = new GridPane();
            GridForm gridForm = new GridForm(gridPane);

            gridForm.addLabel("Label");
            gridForm.addLabel("Label", stringProperty);
            gridForm.addLabel("Label", integerProperty, GridForm.INTEGER_FORMAT);
            gridForm.addEmpty();

            gridForm.addNode("Node", new Rectangle(20, 20));

            gridForm.addButton("Button", "OK");
            gridForm.addButton("Button", "OK", new Rectangle(20, 20));

            gridForm.addTextField("TextField", stringProperty);
            gridForm.addTextField("TextField", doubleProperty, GridForm.DOUBLE_FORMAT);

            gridForm.addTextArea("TextArea", stringProperty);

            gridForm.addCheckBox("CheckBox", booleanProperty);
            gridForm.addCheckBox("CheckBox", "Property", booleanProperty);

            gridForm.addSlider("Slider", doubleProperty, 0, 10);
            Slider slider = gridForm.addSlider("Slider", doubleProperty, 0, 10);
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);
            slider.setMajorTickUnit(1.0);
            slider.setMinorTickCount(10);

            mainTabPane.getTabs().add(new Tab("Simple", gridPane));
        }

        {
            GridPane gridPane = new GridPane();
            GridForm gridForm = new GridForm(gridPane);

            gridForm.addLabel("Label", stringProperty);

            gridForm.addComboBox("ComboBox", stringListProperty, stringProperty);
            gridForm.addComboBox("ComboBox", animalListProperty, animalProperty);
            gridForm.addComboBox("ComboBox", Arrays.asList(Animal.values()), animalProperty);
            gridForm.addComboBox("ComboBox", Arrays.asList(1, 2, 3), integerProperty);

            gridForm.addListView("ListView", stringListProperty, stringProperty);
            ListView<Animal> listView1 = gridForm.addListView("ListView", animalListProperty, animalProperty);
            listView1.setPrefHeight(24 * 4);
            ListView<Animal> listView2 = gridForm.addListView("ListView", Arrays.asList(Animal.values()), animalProperty);
            listView2.setPrefHeight(24 * 4);

            mainTabPane.getTabs().add(new Tab("Lists (Single Selection)", gridPane));
        }

        {
            GridPane gridPane = new GridPane();
            GridForm gridForm = new GridForm(gridPane);

            gridForm.addListView("ListView", stringListProperty, stringListProperty2);
            gridForm.addListView("Selected ListView", stringListProperty2, stringProperty);

            gridForm.addButton("Select", "Add x,y,z").setOnAction(event -> {
                stringListProperty2.add("x");
                stringListProperty2.add("y");
                stringListProperty2.add("z");
            });

            mainTabPane.getTabs().add(new Tab("Lists (Multi Selection)", gridPane));
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
