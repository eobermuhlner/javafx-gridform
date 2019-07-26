package ch.obermuhlner.javafx.gridform;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

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

    public Node addNode(String label, Node node) {
        addLeftLabel(label);

        gridPane.add(node, 1, rowIndex);
        rowIndex++;
        return node;
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

    public Button addButton(String label, String text) {
        addLeftLabel(label);

        Button control = new Button(text);
        gridPane.add(control, 1, rowIndex);
        rowIndex++;
        return control;
    }

    public Button addButton(String label, String text, Node graphic) {
        addLeftLabel(label);

        Button control = new Button(text, graphic);
        gridPane.add(control, 1, rowIndex);
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

    public <T> ComboBox<T> addComboBox(String label, ListProperty<T> listProperty, Property<T> selectedElementProperty) {
        addLeftLabel(label);

        ComboBox<T> control = new ComboBox<>();
        gridPane.add(control, 1, rowIndex);
        Bindings.bindBidirectional(control.itemsProperty(), listProperty);
        control.valueProperty().bindBidirectional(selectedElementProperty);
        if (selectedElementProperty.getValue() == null) {
            selectedElementProperty.setValue(listProperty.get(0));
        }
        rowIndex++;
        return control;
    }

    public <T> ComboBox<T> addComboBox(String label, List<T> elementList, Property<T> selectedElementProperty) {
        ListProperty<T> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList(elementList));

        return addComboBox(label, listProperty, selectedElementProperty);
    }

    public <T> ListView<T> addListView(String label, ListProperty<T> listProperty, Property<T> selectedElementProperty) {
        addLeftLabel(label);

        ListView<T> control = new ListView<>();
        gridPane.add(control, 1, rowIndex);
        Bindings.bindBidirectional(control.itemsProperty(), listProperty);

        control.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedElementProperty.setValue(newValue);
        });
        selectedElementProperty.addListener((observable, oldValue, newValue) -> {
            control.getSelectionModel().select(newValue);
        });

        if (selectedElementProperty.getValue() == null) {
            selectedElementProperty.setValue(listProperty.get(0));
        } else {
            control.getSelectionModel().select(selectedElementProperty.getValue());
        }
        rowIndex++;
        return control;
    }

    public <T> ListView<T> addListView(String label, List<T> elementList, Property<T> selectedElementProperty) {
        ListProperty<T> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList(elementList));

        return addListView(label, listProperty, selectedElementProperty);
    }

    public <T> ListView<T> addListView(String label, ListProperty<T> listProperty, ListProperty<T> selectedElementsProperty) {
        addLeftLabel(label);

        ListView<T> control = new ListView<>();
        gridPane.add(control, 1, rowIndex);
        Bindings.bindBidirectional(control.itemsProperty(), listProperty);

        control.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        control.getSelectionModel().clearSelection();
        for (T selectedElement : selectedElementsProperty) {
            control.getSelectionModel().select(selectedElement);
        }

        control.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<T> selectedItems = control.getSelectionModel().getSelectedItems();
            selectedElementsProperty.clear();
            selectedElementsProperty.addAll(selectedItems);
        });
        /*
        selectedElementsProperty.addListener((observable, oldValue, newValue) -> {
            control.getSelectionModel().clearSelection();
            for (T selectedElement : newValue) {
                control.getSelectionModel().select(selectedElement);
            }
        });
        */

        rowIndex++;
        return control;
    }

    public Slider addSlider(String label, DoubleProperty doubleProperty, double min, double max, double value) {
        gridPane.add(new Text(label), 0, rowIndex);

        Slider valueSlider = new Slider(min, max, value);
        Bindings.bindBidirectional(doubleProperty, valueSlider.valueProperty());
        gridPane.add(valueSlider, 1, rowIndex);
        rowIndex++;
        return valueSlider;
    }

    public Slider addSlider(String label, DoubleProperty doubleProperty, double min, double max) {
        return addSlider(label, doubleProperty, min, max, doubleProperty.get());
    }

    public CheckBox addCheckBox(String label, BooleanProperty booleanProperty) {
        return addCheckBox(label, null, booleanProperty);
    }

    public CheckBox addCheckBox(String label, String text, BooleanProperty booleanProperty) {
        gridPane.add(new Text(label), 0, rowIndex);

        CheckBox valueCheckBox = new CheckBox(text);
        Bindings.bindBidirectional(booleanProperty, valueCheckBox.selectedProperty());
        gridPane.add(valueCheckBox, 1, rowIndex);
        rowIndex++;
        return valueCheckBox;
    }

    private void addLeftLabel(String label) {
        if (label != null) {
            gridPane.add(new Label(label), 0, rowIndex);
        }
    }

}
