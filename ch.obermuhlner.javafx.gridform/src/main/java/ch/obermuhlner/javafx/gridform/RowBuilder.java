package ch.obermuhlner.javafx.gridform;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.text.Format;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class RowBuilder {

    protected final GridPane gridPane;
    private final int rowIndex;
    private final int colIndex;

    RowBuilder(RowBuilder rowBuilder) {
        this(rowBuilder.gridPane, rowBuilder.rowIndex, rowBuilder.colIndex + 1);
    }

    RowBuilder(GridPane gridPane, int rowIndex, int colIndex) {
        this.gridPane = gridPane;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public RowBuilder empty() {
        return new RowBuilder(this);
    }

    private <T extends Node, B extends NodeRowBuilder<T>> B node(T node, B builder) {
        gridPane.add(node, colIndex, rowIndex);
        return builder;
    }

    public <T extends Node> NodeRowBuilder<T> node(T node) {
        return node(node, new NodeRowBuilder<>(this, node));
    }

    public <T extends Node> NodeRowBuilder<T> node(T node, int colSpan, int rowSpan) {
        gridPane.add(node, colIndex, rowIndex, colSpan, rowSpan);
        return new NodeRowBuilder<>(this, node);
    }

    public <T extends Region> RegionRowBuilder<T> region(T region) {
        return node(region, new RegionRowBuilder<>(this, region));
    }

    private <T extends Region, B extends RegionRowBuilder<T>> B region(T region, B builder) {
        return node(region, builder);
    }

    public <T extends Control> ControlRowBuilder<T> control(T control) {
        return region(control, new ControlRowBuilder<>(this, control));
    }

    public ControlRowBuilder<Label> label(String text) {
        Label control = new Label(text);

        return control(control);
    }

    public ControlRowBuilder<Label> label(String text, Node graphic) {
        Label control = new Label(text, graphic);

        return control(control);
    }

    public ControlRowBuilder<Label> label(StringProperty text) {
        Label control = new Label();
        control.textProperty().bindBidirectional(text);

        return control(control);
    }

    public <T> NodeRowBuilder<Label> label(Property<T> text, Format format) {
        Label control = new Label();
        control.textProperty().bindBidirectional(text, format);

        return control(control);
    }

    public ControlRowBuilder<TextField> textField(StringProperty text) {
        TextField control = new TextField();
        control.textProperty().bindBidirectional(text);

        return control(control);
    }

    public <T> ControlRowBuilder<TextField> textField(Property<T> text, Format format) {
        TextField control = new TextField();
        control.textProperty().bindBidirectional(text, format);

        return control(control);
    }

    public ControlRowBuilder<Slider> slider(Property<Number> value, double min, double max) {
        Slider control = new Slider(min, max, value.getValue().doubleValue());
        control.valueProperty().bindBidirectional(value);

        return control(control);
    }

    public ButtonRowBuilder button(String text) {
        Button control = new Button(text);

        return node(control, new ButtonRowBuilder(this, control));
    }

    public ControlRowBuilder<Button> button(String text, Node graphic) {
        Button control = new Button(text, graphic);

        return control(control);
    }

    public <T> ControlRowBuilder<ComboBox<T>> comboBox(T[] elementArray, Property<T> selectedElementProperty) {
        return comboBox(Arrays.asList(elementArray), selectedElementProperty);
    }

    public <T> ControlRowBuilder<ComboBox<T>> comboBox(List<T> elementList, Property<T> selectedElementProperty) {
        ListProperty<T> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList(elementList));

        return comboBox(listProperty, selectedElementProperty);
    }

    public <T> ControlRowBuilder<ComboBox<T>> comboBox(ListProperty<T> listProperty, Property<T> selectedElementProperty) {
        ComboBox<T> control = new ComboBox<T>();
        Bindings.bindBidirectional(control.itemsProperty(), listProperty);
        control.valueProperty().bindBidirectional(selectedElementProperty);
        if (selectedElementProperty.getValue() == null && !listProperty.isEmpty()) {
            selectedElementProperty.setValue(listProperty.get(0));
        }

        return control(control);
    }

    public <T> ControlRowBuilder<ChoiceBox<T>> choiceBox(ListProperty<T> listProperty, Property<T> selectedElementProperty) {
        ChoiceBox<T> control = new ChoiceBox<>();
        Bindings.bindBidirectional(control.itemsProperty(), listProperty);
        control.valueProperty().bindBidirectional(selectedElementProperty);
        if (selectedElementProperty.getValue() == null && !listProperty.isEmpty()) {
            selectedElementProperty.setValue(listProperty.get(0));
        }

        return control(control);
    }

    public <T> ControlRowBuilder<ChoiceBox<T>> choiceBox(List<T> elementList, Property<T> selectedElementProperty) {
        ListProperty<T> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList(elementList));

        return choiceBox(listProperty, selectedElementProperty);
    }

    public <T> ControlRowBuilder<ChoiceBox<T>> choiceBox(T[] elementArray, Property<T> selectedElementProperty) {
        return choiceBox(Arrays.asList(elementArray), selectedElementProperty);
    }

    public ControlRowBuilder<CheckBox> checkBox(BooleanProperty booleanProperty) {
        return checkBox(null, booleanProperty);
    }

    public ControlRowBuilder<CheckBox> checkBox(String text, BooleanProperty booleanProperty) {
        CheckBox control = new CheckBox(text);
        Bindings.bindBidirectional(booleanProperty, control.selectedProperty());

        return control(control);
    }

    public <T> ControlRowBuilder<ListView<T>> listView(ListProperty<T> listProperty, Property<T> selectedElementProperty) {
        ListView<T> control = new ListView<>();
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

        return control(control);
    }

    public <T> ControlRowBuilder<ListView<T>> listView(List<T> elementList, Property<T> selectedElementProperty) {
        ListProperty<T> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList(elementList));

        return listView(listProperty, selectedElementProperty);
    }

    public <T> ControlRowBuilder<ListView<T>> listView(T[] elementArray, Property<T> selectedElementProperty) {
        return listView(Arrays.asList(elementArray), selectedElementProperty);
    }

    public <T> ControlRowBuilder<ListView<T>> listView(ListProperty<T> listProperty, ListProperty<T> selectedElementsProperty) {
        ListView<T> control = new ListView<>();
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

        return control(control);
    }

    public <T> ControlRowBuilder<ListView<T>> listView(List<T> elementList, ListProperty<T> selectedElementsProperty) {
        ListProperty<T> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList(elementList));

        return listView(listProperty, selectedElementsProperty);
    }

    public <T> ControlRowBuilder<ListView<T>> listView(T[] elementList, ListProperty<T> selectedElementsProperty) {
        return listView(Arrays.asList(elementList), selectedElementsProperty);
    }

    public <T> RegionRowBuilder<VBox> radioButtons(T[] elementArray, Property<T> selectedElementProperty) {
        return radioButtons(Arrays.asList(elementArray), selectedElementProperty);
    }

    public <T> RegionRowBuilder<VBox> radioButtons(List<T> elementList, Property<T> selectedElementProperty) {
        return radioButtons(elementList, selectedElementProperty, new VBox());
    }

    public <T> RegionRowBuilder<VBox> radioButtons(List<T> elementList, Property<T> selectedElementProperty, VBox box) {
        return radioButtons(elementList, selectedElementProperty, box, (pane, radiobutton) -> {
            pane.getChildren().add(radiobutton);
        });
    }

    public <T> RegionRowBuilder<HBox> radioButtons(List<T> elementList, Property<T> selectedElementProperty, HBox box) {
        return radioButtons(elementList, selectedElementProperty, box, (pane, radiobutton) -> {
            pane.getChildren().add(radiobutton);
        });
    }

    public <T, P extends Region> RegionRowBuilder<P> radioButtons(List<T> elementList, Property<T> selectedElementProperty, P pane, BiConsumer<P, RadioButton> paneAdder) {
        List<RadioButton> radioButtons = new ArrayList<>();

        ToggleGroup toggleGroup = new ToggleGroup();
        for (T element : elementList) {
            RadioButton radioButton = new RadioButton(String.valueOf(element));
            radioButton.setToggleGroup(toggleGroup);
            radioButton.setUserData(element);
            paneAdder.accept(pane, radioButton);

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

        return region(pane);
    }

    public <T> RegionRowBuilder<VBox> checkBoxes(T[] elementArray, ListProperty<T> selectedElementsProperty) {
        return checkBoxes(Arrays.asList(elementArray), selectedElementsProperty);
    }

    public <T> RegionRowBuilder<VBox> checkBoxes(List<T> elementList, ListProperty<T> selectedElementsProperty) {
        return checkBoxes(elementList, selectedElementsProperty, new VBox());
    }

    public <T> RegionRowBuilder<VBox> checkBoxes(List<T> elementList, ListProperty<T> selectedElementsProperty, VBox box) {
        return checkBoxes(elementList, selectedElementsProperty, box, (pane, checkbox) -> {
            pane.getChildren().add(checkbox);
        });
    }

    public <T, P extends Region> RegionRowBuilder<P> checkBoxes(List<T> elementList, ListProperty<T> selectedElementsProperty, P pane, BiConsumer<P, CheckBox> paneAdder) {
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
            paneAdder.accept(pane, checkBox);

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

        return region(pane);
    }

    public ControlRowBuilder<DatePicker> datePicker(ObjectProperty<LocalDate> dateProperty) {
        DatePicker control = new DatePicker(dateProperty.get());
        Bindings.bindBidirectional(dateProperty, control.valueProperty());

        return control(control);
    }

    public ControlRowBuilder<ColorPicker> colorPicker(ObjectProperty<Color> colorProperty) {
        ColorPicker control = new ColorPicker(colorProperty.get());
        Bindings.bindBidirectional(colorProperty, control.valueProperty());

        return control(control);
    }

    public static class NodeRowBuilder<T extends Node> extends RowBuilder {
        public final T node;

        NodeRowBuilder(RowBuilder rowBuilder, T node) {
            super(rowBuilder);

            this.node = node;
        }

        public final NodeRowBuilder<T> with(Consumer<T> nodeFunction) {
            nodeFunction.accept(node);
            return this;
        }
    }

    public static class RegionRowBuilder<T extends Region> extends NodeRowBuilder<T> {
        public final T region;

        RegionRowBuilder(RowBuilder rowBuilder, T region) {
            super(rowBuilder, region);

            this.region = region;
        }

        public RegionRowBuilder<T> fillWidth() {
            GridPane.setFillWidth(region, true);
            region.setMaxWidth(Double.MAX_VALUE);

            return this;
        }

        public RegionRowBuilder<T> fillHeight() {
            GridPane.setFillHeight(region, true);
            region.setMaxHeight(Double.MAX_VALUE);

            return this;
        }

        public RegionRowBuilder<T> fill() {
            return fillWidth().fillHeight();
        }
    }

    public static class ControlRowBuilder<T extends Control> extends RegionRowBuilder<T> {
        public final T control;

        ControlRowBuilder(RowBuilder rowBuilder, T control) {
            super(rowBuilder, control);

            this.control = control;
        }
    }

    public static class ButtonRowBuilder extends ControlRowBuilder<Button> {
        ButtonRowBuilder(RowBuilder rowBuilder, Button button) {
            super(rowBuilder, button);
        }

        public final void setOnAction(EventHandler<ActionEvent> value) {
            node.onActionProperty().set(value);
        }
    }
}
