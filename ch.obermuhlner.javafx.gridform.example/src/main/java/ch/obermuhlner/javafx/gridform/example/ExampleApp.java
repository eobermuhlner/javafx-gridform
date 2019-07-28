package ch.obermuhlner.javafx.gridform.example;

import ch.obermuhlner.javafx.gridform.GridForm;
import ch.obermuhlner.javafx.gridform.RowBuilder;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.time.LocalDate;
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
                "a", "b", "c", "x", "y", "z"));
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
                    .with(b -> b.setOnAction(event -> b.setText("Clicked")));
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
                    .comboBox(animalListProperty, animalProperty);
            gridForm.row()
                    .label("ComboBox")
                    .comboBox(Animal.values(), animalProperty);

            mainTabPane.getTabs().add(new Tab("Fluent", gridPane));
        }

        {
            GridPane gridPane = new GridPane();
            GridForm gridForm = new GridForm(gridPane);

            gridForm.addLabel("Label");
            gridForm.addLabel("Label", stringProperty);
            gridForm.addLabel("Label", integerProperty, GridForm.INTEGER_FORMAT);
            gridForm.addLabel("Label", doubleProperty, GridForm.DOUBLE_FORMAT);
            gridForm.addEmpty();

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

            gridForm.addChoiceBox("ChoiceBox", stringListProperty, stringProperty);
            gridForm.addChoiceBox("ChoiceBox", Arrays.asList(Animal.values()), animalProperty);

            gridForm.addRadioButton("RadioButton", Arrays.asList(Animal.values()), animalProperty);

            mainTabPane.getTabs().add(new Tab("Choices (Multi Selection)", gridPane));
        }

        {
            GridPane gridPane = new GridPane();
            GridForm gridForm = new GridForm(gridPane);

            gridForm.addLabel("Label", stringProperty);

            ListView<String> listView1 = gridForm.addListView("ListView", stringListProperty, stringProperty);
            listView1.setPrefHeight(24 * 4);
            ListView<Animal> listView2 = gridForm.addListView("ListView", animalListProperty, animalProperty);
            listView2.setPrefHeight(24 * 4);
            ListView<Animal> listView3 = gridForm.addListView("ListView", Arrays.asList(Animal.values()), animalProperty);
            listView3.setPrefHeight(24 * 4);

            gridForm.addButton("Action", "Animal=null").setOnAction(event -> {
                animalProperty.set(null);
            });

            mainTabPane.getTabs().add(new Tab("Lists (Single Selection)", gridPane));
        }

        {
            GridPane gridPane = new GridPane();
            GridForm gridForm = new GridForm(gridPane);

            ListView<String> listView = gridForm.addListView("ListView", stringListProperty, stringListProperty2);
            listView.setPrefHeight(24 * 8);
            ListView<String> selectedListView = gridForm.addListView("Selected ListView", stringListProperty2, stringProperty);
            selectedListView.setPrefHeight(24 * 4);
            gridForm.addCheckBox("CheckBox", stringListProperty, stringListProperty2);

            gridForm.addButton("Action", "Add x,y,z").setOnAction(event -> {
                stringListProperty2.add("x");
                stringListProperty2.add("y");
                stringListProperty2.add("z");
            });

            mainTabPane.getTabs().add(new Tab("Lists (Multi Selection)", gridPane));
        }

        {
            GridPane gridPane = new GridPane();
            GridForm gridForm = new GridForm(gridPane);

            gridForm.addDatePicker("DatePicker", dateProperty);
            gridForm.addColorPicker("ColorPicker", colorProperty);
            gridForm.addNode("Node", new Rectangle(20, 20));

            mainTabPane.getTabs().add(new Tab("Misc", gridPane));
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
