package ch.obermuhlner.javafx.gridform;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.text.DecimalFormat;

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
