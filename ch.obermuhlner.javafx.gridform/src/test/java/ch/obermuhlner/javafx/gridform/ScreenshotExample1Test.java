package ch.obermuhlner.javafx.gridform;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class ScreenshotExample1Test extends AbstractGridFormTest {

    public enum Animal {
        Dog,
        Cat,
        Cow,
        Fish
    }

    @Override
    protected void setup(GridForm gridForm) {
        StringProperty stringProperty = new SimpleStringProperty("StringProperty");
        IntegerProperty integerProperty = new SimpleIntegerProperty(1234);
        IntegerProperty clickCountProperty = new SimpleIntegerProperty(0);
        DoubleProperty doubleProperty = new SimpleDoubleProperty(Math.PI);
        ObjectProperty<Animal> animalProperty = new SimpleObjectProperty<>();
        ListProperty<Animal> selectedAnimalListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
        ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>(LocalDate.now());
        ObjectProperty<Color> colorProperty = new SimpleObjectProperty<>(Color.PEACHPUFF);

        gridForm.row()
                .label("Label")
                .label(stringProperty)
                .label(integerProperty, GridForm.INTEGER_FORMAT);
        gridForm.row()
                .label("Label")
                .empty()
                .label("Cells can be empty");
        gridForm.row()
                .label("Button")
                .button("Click me")
                .setOnAction(button -> {
                    clickCountProperty.set(clickCountProperty.get() + 1);
                })
                .label(clickCountProperty, GridForm.INTEGER_FORMAT);
        gridForm.row()
                .label("TextField")
                .textField(stringProperty);
        gridForm.row()
                .label("TextField")
                .textField(doubleProperty, GridForm.DOUBLE_FORMAT);
        gridForm.row()
                .label("PasswordField")
                .passwordField(stringProperty);
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
                .comboBox(animalProperty, Animal.values());
        gridForm.row()
                .label("ChoiceBox")
                .choiceBox(animalProperty, Animal.values());
        gridForm.row()
                .label("RadioButton")
                .radioButtons(animalProperty, Animal.values());
        gridForm.row()
                .label("CheckBox")
                .checkBoxes(selectedAnimalListProperty, Animal.values());
        gridForm.row()
                .label("ListView")
                .listView(animalProperty, Animal.values())
                .with(listView -> {
                    listView.setPrefHeight(24 * 4);
                });
        gridForm.row()
                .label("DatePicker")
                .datePicker(dateProperty);
        gridForm.row()
                .label("ColorPicker")
                .colorPicker(colorProperty);
        gridForm.row()
                .label("Node")
                .node(new Rectangle(20, 20));

    }

    @Test
    public void testScreenshot() {
        snapshot("Example1");
    }
}
