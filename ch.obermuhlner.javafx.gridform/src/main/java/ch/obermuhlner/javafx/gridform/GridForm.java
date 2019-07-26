package ch.obermuhlner.javafx.gridform;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.List;

public class GridForm {
    public static final DecimalFormat INTEGER_FORMAT = new DecimalFormat("#0");
    public static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("#0.########");

    private final GridPane gridPane;

    private int rowIndex = 0;

    public GridForm(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public void addEmpty() {
        addLabel("");
    }

    public void addLabel(String label) {
        addLeftLabel(label);

        rowIndex++;
    }

    public Label addLabel(String label, StringProperty property) {
        addLeftLabel(label);

        Label control = new Label();
        gridPane.add(control, 1, rowIndex);
        Bindings.bindBidirectional(control.textProperty(), property);
        rowIndex++;
        return control;
    }

    public Label addLabel(String label, Property property, Format format) {
        addLeftLabel(label);

        Label control = new Label();
        gridPane.add(control, 1, rowIndex);
        Bindings.bindBidirectional(control.textProperty(), property, format);
        rowIndex++;
        return control;
    }

    public TextField addTextField(String label, StringProperty property) {
        addLeftLabel(label);

        TextField control = new TextField();
        gridPane.add(control, 1, rowIndex);
        Bindings.bindBidirectional(control.textProperty(), property);
        rowIndex++;
        return control;
    }

    public TextField addTextField(String label, Property property, Format format) {
        addLeftLabel(label);

        TextField control = new TextField();
        gridPane.add(control, 1, rowIndex);
        Bindings.bindBidirectional(control.textProperty(), property, format);
        rowIndex++;
        return control;
    }

    public TextArea addTextArea(String label, StringProperty property) {
        addLeftLabel(label);

        TextArea control = new TextArea();
        gridPane.add(control, 1, rowIndex);
        Bindings.bindBidirectional(control.textProperty(), property);
        rowIndex++;
        return control;
    }

    public ComboBox<String> addComboBox(String label, ListProperty<String> listProperty, StringProperty elementProperty) {
        addLeftLabel(label);

        ComboBox<String> control = new ComboBox<>();
        gridPane.add(control, 1, rowIndex);
        Bindings.bindBidirectional(control.itemsProperty(), listProperty);
        control.valueProperty().bindBidirectional(elementProperty);
        if (elementProperty.get() == null) {
            elementProperty.setValue(listProperty.get(0));
        }
        rowIndex++;
        return control;
    }

    public <T> ComboBox<T> addComboBox(String label, ListProperty<T> listProperty, ObjectProperty<T> elementProperty) {
        addLeftLabel(label);

        ComboBox<T> control = new ComboBox<>();
        gridPane.add(control, 1, rowIndex);
        Bindings.bindBidirectional(control.itemsProperty(), listProperty);
        control.valueProperty().bindBidirectional(elementProperty);
        if (elementProperty.get() == null) {
            elementProperty.setValue(listProperty.get(0));
        }
        rowIndex++;
        return control;
    }

    public <T> ComboBox<T> addComboBox(String label, List<T> elementList, ObjectProperty<T> elementProperty) {
        ListProperty<T> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList(elementList));

        return addComboBox(label, listProperty, elementProperty);
    }

    private void addLeftLabel(String label) {
        if (label != null) {
            gridPane.add(new Label(label), 0, rowIndex);
        }
    }

}
