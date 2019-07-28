package ch.obermuhlner.javafx.gridform;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.text.Format;
import java.time.LocalDate;
import java.util.*;

public class GridForm {
    public static final DecimalFormat INTEGER_FORMAT = new DecimalFormat("#0");
    public static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("#0.########");

    private final GridPane gridPane;

    private int rowIndex = 0;

    public GridForm(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public RowBuilder row() {
        return new RowBuilder(gridPane, rowIndex++, 0);
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
        if (selectedElementProperty.getValue() == null && !listProperty.isEmpty()) {
            selectedElementProperty.setValue(listProperty.get(0));
        }
        rowIndex++;
        return control;
    }

    public <T> ComboBox<T> addComboBox(String label, List<T> elementList, Property<T> selectedElementProperty) {
        ListProperty<T> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList(elementList));

        return addComboBox(label, listProperty, selectedElementProperty);
    }

    public <T> ChoiceBox<T> addChoiceBox(String label, ListProperty<T> listProperty, Property<T> selectedElementProperty) {
        addLeftLabel(label);

        ChoiceBox<T> control = new ChoiceBox<>();
        gridPane.add(control, 1, rowIndex);
        Bindings.bindBidirectional(control.itemsProperty(), listProperty);
        control.valueProperty().bindBidirectional(selectedElementProperty);
        if (selectedElementProperty.getValue() == null && !listProperty.isEmpty()) {
            selectedElementProperty.setValue(listProperty.get(0));
        }
        rowIndex++;
        return control;
    }

    public <T> ChoiceBox<T> addChoiceBox(String label, List<T> elementList, Property<T> selectedElementProperty) {
        ListProperty<T> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList(elementList));

        return addChoiceBox(label, listProperty, selectedElementProperty);
    }

    public <T> List<RadioButton> addRadioButton(String label, List<T> elementList, Property<T> selectedElementProperty) {
        addLeftLabel(label);

        List<RadioButton> radioButtons = new ArrayList<>();

        ToggleGroup toggleGroup = new ToggleGroup();
        for (T element : elementList) {
            RadioButton radioButton = new RadioButton(String.valueOf(element));
            radioButton.setToggleGroup(toggleGroup);
            radioButton.setUserData(element);
            gridPane.add(radioButton, 1, rowIndex++);
            if (element != null && element.equals(selectedElementProperty.getValue())) {
                radioButton.setSelected(true);
            }

            radioButtons.add(radioButton);
        }

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedElementProperty.setValue((T) newValue.getUserData());
            }
        });
        selectedElementProperty.addListener((observable, oldValue, newValue) -> {
            for (RadioButton radioButton : radioButtons) {
                radioButton.setSelected(Objects.equals(newValue, radioButton.getUserData()));
            }
        });

        return radioButtons;
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

        if (selectedElementProperty.getValue() == null && !listProperty.isEmpty()) {
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

        control.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<T>() {
            @Override
            public void onChanged(Change<? extends T> c) {
                ObservableList<T> selectedItems = control.getSelectionModel().getSelectedItems();

                Set<T> elementsRemaining = new HashSet<>(selectedElementsProperty);
                Set<T> elementsToAdd = new HashSet<>();
                for (T selectedItem : selectedItems) {
                    if (elementsRemaining.contains(selectedItem)) {
                        elementsRemaining.remove(selectedItem);
                    } else {
                        elementsToAdd.add(selectedItem);
                    }
                }
                selectedElementsProperty.addAll(elementsToAdd);
                selectedElementsProperty.removeAll(elementsRemaining);
            }
        });

        selectedElementsProperty.addListener((observable, oldValue, newValue) -> {
            MultipleSelectionModel<T> selectionModel = control.getSelectionModel();

            Set<T> selectionRemaining = new HashSet<>(selectionModel.getSelectedItems());
            Set<T> selectionToAdd = new HashSet<>();
            for (T element : newValue) {
                if (selectionRemaining.contains(element)) {
                    selectionRemaining.remove(element);
                } else {
                    selectionToAdd.add(element);
                }
            }

            for (T toAdd : selectionToAdd) {
                selectionModel.select(toAdd);
            }
            for (T toRemove : selectionRemaining) {
                selectionModel.clearSelection(listProperty.indexOf(toRemove));
            }
        });

        rowIndex++;
        return control;
    }

    public <T> ListView<T> addListView(String label, List<T> elementList, ListProperty<T> selectedElementsProperty) {
        ListProperty<T> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList(elementList));

        return addListView(label, listProperty, selectedElementsProperty);
    }

    public Slider addSlider(String label, DoubleProperty doubleProperty, double min, double max, double value) {
        addLeftLabel(label);

        Slider control = new Slider(min, max, value);

        // TODO binding is not stable - for some reason 2 manual listeners seems to be more stable
        //Bindings.bindBidirectional(control.valueProperty(), doubleProperty);

        doubleProperty.addListener((observable, oldValue, newValue) -> {
            control.valueProperty().set(newValue.doubleValue());
        });
        control.valueProperty().addListener((observable, oldValue, newValue) -> {
            doubleProperty.set(newValue.doubleValue());
        });

        gridPane.add(control, 1, rowIndex);
        rowIndex++;
        return control;
    }

    public Slider addSlider(String label, DoubleProperty doubleProperty, double min, double max) {
        return addSlider(label, doubleProperty, min, max, doubleProperty.get());
    }

    public CheckBox addCheckBox(String label, BooleanProperty booleanProperty) {
        return addCheckBox(label, null, booleanProperty);
    }

    public CheckBox addCheckBox(String label, String text, BooleanProperty booleanProperty) {
        addLeftLabel(label);

        CheckBox control = new CheckBox(text);
        Bindings.bindBidirectional(booleanProperty, control.selectedProperty());
        gridPane.add(control, 1, rowIndex);
        rowIndex++;
        return control;
    }

    public <T> List<CheckBox> addCheckBox(String label, List<T> elementList, ListProperty<T> selectedElementsProperty) {
        gridPane.add(new Text(label), 0, rowIndex);

        List<CheckBox> checkBoxes = new ArrayList<>();
        for (T element : elementList) {
            CheckBox checkBox = new CheckBox(String.valueOf(element));
            checkBox.setSelected(selectedElementsProperty.contains(element));
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    if (!selectedElementsProperty.contains(element)) {
                        selectedElementsProperty.add(element);
                    }
                } else {
                    selectedElementsProperty.remove(element);
                }
            });
            gridPane.add(checkBox, 1, rowIndex++);

            checkBoxes.add(checkBox);
        }

        selectedElementsProperty.addListener(new ListChangeListener<T>() {
            @Override
            public void onChanged(Change<? extends T> c) {
                Set<T> selectedElements = new HashSet<>(selectedElementsProperty);
                for (int i = 0; i < elementList.size(); i++) {
                    T element = elementList.get(i);
                    CheckBox checkBox = checkBoxes.get(i);
                    checkBox.setSelected(selectedElements.contains(element));
                }
            }
        });


        return checkBoxes;
    }

    public DatePicker addDatePicker(String label, ObjectProperty<LocalDate> dateProperty) {
        addLeftLabel(label);

        DatePicker control = new DatePicker(dateProperty.get());
        Bindings.bindBidirectional(dateProperty, control.valueProperty());
        gridPane.add(control, 1, rowIndex);
        rowIndex++;
        return control;
    }

    public ColorPicker addColorPicker(String label, ObjectProperty<Color> colorProperty) {
        addLeftLabel(label);

        ColorPicker control = new ColorPicker(colorProperty.get());
        Bindings.bindBidirectional(colorProperty, control.valueProperty());
        gridPane.add(control, 1, rowIndex);
        rowIndex++;
        return control;
    }

    private void addLeftLabel(String label) {
        if (label != null) {
            gridPane.add(new Label(label), 0, rowIndex);
        }
    }

}
