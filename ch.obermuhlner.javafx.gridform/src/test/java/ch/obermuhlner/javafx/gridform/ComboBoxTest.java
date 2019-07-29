package ch.obermuhlner.javafx.gridform;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ComboBoxTest extends AbstractGridFormTest {

    private final ListProperty<String> stringListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final StringProperty stringProperty = new SimpleStringProperty();

    private ComboBox<String> comboxBox;

    @Override
    protected void setup(GridForm gridForm) {
        gridForm.row()
                .comboBox(stringProperty, stringListProperty)
                .with(comboxBox -> this.comboxBox = comboxBox);
    }

    @Test
    public void testComboBox_empty() {
        assertEquals(new ArrayList<String>(), stringListProperty.get());
        assertEquals(null, stringProperty.get());

        stringListProperty.add("Alpha");
        stringListProperty.add("Beta");

        clickOn(comboxBox).clickOn("Beta");
        assertEquals("Beta", stringProperty.get());
    }
}
