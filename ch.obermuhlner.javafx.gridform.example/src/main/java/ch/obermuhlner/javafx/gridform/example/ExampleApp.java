package ch.obermuhlner.javafx.gridform.example;

import ch.obermuhlner.javafx.gridform.GridForm;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.time.LocalDate;

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
                "a", "b", "c", "x", "y", "z", "Just a long string"));
        ListProperty<Animal> animalListProperty = new SimpleListProperty<>(FXCollections.observableArrayList(
                Animal.values()));

        StringProperty stringProperty = new SimpleStringProperty("StringProperty");
        IntegerProperty integerProperty = new SimpleIntegerProperty(1234);
        DoubleProperty doubleProperty = new SimpleDoubleProperty(Math.PI);
        BooleanProperty booleanProperty = new SimpleBooleanProperty();
        ObjectProperty<Animal> animalProperty = new SimpleObjectProperty<>();
        ListProperty<String> stringListProperty2 = new SimpleListProperty<>(FXCollections.observableArrayList("a", "b", "Unknown"));
        ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>(LocalDate.now());
        ObjectProperty<Color> colorProperty = new SimpleObjectProperty<>(Color.PEACHPUFF);

        {
            GridPane gridPane = new GridPane();
            GridForm gridForm = new GridForm(gridPane);

            gridForm.row()
                    .label("Label")
                    .label(stringProperty);
            gridForm.row()
                    .label("Label")
                    .empty()
                    .label("Cells can be empty");
            gridForm.row()
                    .label("Button")
                    .button("Click me")
                    .fill()
                    .with(button -> button.setOnAction(event -> button.setText("Clicked")));
            gridForm.row()
                    .label("TextField")
                    .textField(stringProperty);
            gridForm.row()
                    .label("TextField")
                    .textField(doubleProperty, GridForm.DOUBLE_FORMAT);
            gridForm.row()
                    .label("Slider")
                    .slider(doubleProperty, 0, 10)
                    .with(slider -> {
                        slider.setShowTickMarks(true);
                        slider.setShowTickLabels(true);
                        slider.setMajorTickUnit(1.0);
                        slider.setMinorTickCount(10);
                    })
                    .label(doubleProperty, GridForm.DOUBLE_FORMAT);
            gridForm.row()
                    .label("ComboBox")
                    .comboBox(animalProperty, animalListProperty);
            gridForm.row()
                    .label("ComboBox")
                    .comboBox(animalProperty, Animal.values());

            mainTabPane.getTabs().add(new Tab("Fluent", gridPane));
        }

        {
            GridPane gridPane = new GridPane();
            GridForm gridForm = new GridForm(gridPane);

            gridForm.row().label("Label");
            gridForm.row().label("Label").label(stringProperty);
            gridForm.row().label("Label").label(integerProperty, GridForm.INTEGER_FORMAT);
            gridForm.row().label("Label").label(doubleProperty, GridForm.DOUBLE_FORMAT);
            gridForm.emptyRow();

            gridForm.row().label("Button").button("OK");
            gridForm.row().label("Button").button("OK", new Rectangle(20, 20));

            gridForm.row().label("TextField").textField(stringProperty);
            gridForm.row().label("TextField").textField(doubleProperty, GridForm.DOUBLE_FORMAT);

            gridForm.row().label("TextArea").textField(stringProperty);

            gridForm.row().label("CheckBox").checkBox(booleanProperty);
            gridForm.row().label("CheckBox").checkBox("Property", booleanProperty);

            gridForm.row()
                    .label("Slider")
                    .slider(doubleProperty, 0, 10)
                    .with(slider -> {
                        slider.setShowTickMarks(true);
                        slider.setShowTickLabels(true);
                        slider.setMajorTickUnit(1.0);
                        slider.setMinorTickCount(10);
                    });

            mainTabPane.getTabs().add(new Tab("Simple", gridPane));
        }

        {
            GridPane gridPane = new GridPane();
            GridForm gridForm = new GridForm(gridPane);

            gridForm.row().label("Label").label(stringProperty);

            gridForm.row().label("ComboBox").comboBox(stringProperty, stringListProperty);
            gridForm.row().label("ComboBox").comboBox(animalProperty, animalListProperty);
            gridForm.row().label("ComboBox").comboBox(animalProperty, Animal.values());
            gridForm.row().label("ComboBox").comboBox(integerProperty, 1, 2, 3);

            gridForm.row().label("ChoiceBox").choiceBox(stringProperty, stringListProperty);
            gridForm.row().label("ChoiceBox").choiceBox(animalProperty, Animal.values());

            gridForm.row().label("RadioButton").radioButtons(animalProperty, Animal.values());
            gridForm.row().label("RadioButton").radioButtons(new HBox(), animalProperty, Animal.values());

            mainTabPane.getTabs().add(new Tab("Choices (Multi Selection)", gridPane));
        }

        {
            GridPane gridPane = new GridPane();
            GridForm gridForm = new GridForm(gridPane);

            gridForm.row().label("Label").label(stringProperty);

            gridForm.row().label("ListView")
                    .listView(stringProperty, stringListProperty)
                    .with(listView -> listView.setPrefHeight(24 * 4));
            gridForm.row().label("ListView")
                    .listView(animalProperty, animalListProperty)
                    .with(listView -> listView.setPrefHeight(24 * 4));
            gridForm.row().label("ListView")
                    .listView(animalProperty, Animal.values())
                    .with(listView -> listView.setPrefHeight(24 * 4));

            gridForm.row().label("Action")
                    .button("Animal=null")
                    .setOnAction(event -> {
                        animalProperty.set(null);
                    });

            mainTabPane.getTabs().add(new Tab("Lists (Single Selection)", gridPane));
        }

        {
            GridPane gridPane = new GridPane();
            GridForm gridForm = new GridForm(gridPane);

            gridForm.row().label("ListView")
                    .listView(stringListProperty2, stringListProperty)
                    .with(listView -> listView.setPrefHeight(24 * 8));
            gridForm.row().label("Selected ListView")
                    .listView(stringProperty, stringListProperty2)
                    .with(listView -> listView.setPrefHeight(24 * 8));
            gridForm.row().label("CheckBox").checkBoxes(stringListProperty2, stringListProperty);
            gridForm.row().label("CheckBox").checkBoxes(new HBox(), stringListProperty2, stringListProperty);

            gridForm.row().label("Action")
                    .button("Add x,y,z")
                    .setOnAction(event -> {
                        stringListProperty2.add("x");
                        stringListProperty2.add("y");
                        stringListProperty2.add("z");
                    });

            mainTabPane.getTabs().add(new Tab("Lists (Multi Selection)", gridPane));
        }

        {
            GridPane gridPane = new GridPane();
            GridForm gridForm = new GridForm(gridPane);

            gridForm.row().label("DatePicker").datePicker(dateProperty);
            gridForm.row().label("ColorPicker").colorPicker(colorProperty);
            gridForm.row().label("Node").node(new Rectangle(20, 20));

            mainTabPane.getTabs().add(new Tab("Misc", gridPane));
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
