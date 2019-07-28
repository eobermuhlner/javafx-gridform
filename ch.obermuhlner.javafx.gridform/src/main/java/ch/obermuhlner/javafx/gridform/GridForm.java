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

    public void emptyRow() {
        row().label("");
    }

    private void addLeftLabel(String label) {
        if (label != null) {
            gridPane.add(new Label(label), 0, rowIndex);
        }
    }

}
