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
                .label("ComboBox")
                .comboBox(stringProperty, "Alpha", "Beta")
                .with(comboxBox -> this.comboxBox = comboxBox);
    }

    @Test
    public void testComboBox() {
        assertEquals("Alpha", stringProperty.get());
        snapshot("ComboBox1");

        clickOn(comboxBox).clickOn("Beta");
        assertEquals("Beta", stringProperty.get());
    }
}
