package ch.obermuhlner.javafx.gridform;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.text.Format;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class RowBuilder {

    private final GridPane gridPane;
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

    public NodeRowBuilder<Label> label(String text) {
        Label control = new Label(text);

        return node(control);
    }

    public NodeRowBuilder<Label> label(String text, Node graphic) {
        Label control = new Label(text, graphic);

        return node(control);
    }

    public NodeRowBuilder<Label> label(StringProperty text) {
        Label control = new Label();
        control.textProperty().bindBidirectional(text);

        return node(control);
    }

    public <T> NodeRowBuilder<Label> label(Property<T> text, Format format) {
        Label control = new Label();
        control.textProperty().bindBidirectional(text, format);

        return node(control);
    }

    public NodeRowBuilder<TextField> textField(StringProperty text) {
        TextField control = new TextField();
        control.textProperty().bindBidirectional(text);

        return node(control);
    }

    public <T> NodeRowBuilder<TextField> textField(Property<T> text, Format format) {
        TextField control = new TextField();
        control.textProperty().bindBidirectional(text, format);

        return node(control);
    }

    public NodeRowBuilder<Slider> slider(Property<Number> value, double min, double max) {
        Slider control = new Slider(min, max, value.getValue().doubleValue());
        control.valueProperty().bindBidirectional(value);

        return node(control);
    }

    public ButtonRowBuilder button(String text) {
        Button control = new Button(text);

        return node(control, new ButtonRowBuilder(this, control));
    }

    public NodeRowBuilder<Button> button(String text, Node graphic) {
        Button control = new Button(text, graphic);

        return node(control);
    }

    public <T> NodeRowBuilder<ComboBox<T>> comboBox(T[] elementArray, Property<T> selectedElementProperty) {
        return comboBox(Arrays.asList(elementArray), selectedElementProperty);
    }

    public <T> NodeRowBuilder<ComboBox<T>> comboBox(List<T> elementList, Property<T> selectedElementProperty) {
        ListProperty<T> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList(elementList));

        return comboBox(listProperty, selectedElementProperty);
    }

    public <T> NodeRowBuilder<ComboBox<T>> comboBox(ListProperty<T> listProperty, Property<T> selectedElementProperty) {
        ComboBox<T> control = new ComboBox<T>();
        Bindings.bindBidirectional(control.itemsProperty(), listProperty);
        control.valueProperty().bindBidirectional(selectedElementProperty);
        if (selectedElementProperty.getValue() == null && !listProperty.isEmpty()) {
            selectedElementProperty.setValue(listProperty.get(0));
        }

        gridPane.add(control, colIndex, rowIndex);
        return new NodeRowBuilder(this, control);
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

    public static class ButtonRowBuilder extends NodeRowBuilder<Button> {
        ButtonRowBuilder(RowBuilder rowBuilder, Button button) {
            super(rowBuilder, button);
        }

        public final void setOnAction(EventHandler<ActionEvent> value) {
            node.onActionProperty().set(value);
        }
    }
}
