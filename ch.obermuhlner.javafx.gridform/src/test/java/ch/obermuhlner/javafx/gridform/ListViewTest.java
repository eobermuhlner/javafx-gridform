package ch.obermuhlner.javafx.gridform;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ListViewTest extends AbstractGridFormTest {

    private final ListProperty<String> stringListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final StringProperty stringProperty = new SimpleStringProperty();

    @Override
    protected void setup(GridForm gridForm) {
        gridForm.row()
                .label("ListView")
                .listView(stringProperty, "Alpha", "Beta");
    }

    @Test
    public void testComboBox() {
        assertEquals("Alpha", stringProperty.get());
        snapshot("ListView1");
    }
}
