package ch.obermuhlner.javafx.gridform;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GridFormTest extends ApplicationTest {

    private StringProperty stringProperty = new SimpleStringProperty();
    private IntegerProperty integerProperty = new SimpleIntegerProperty();

    private Button button;
    private TextField stringTextField;
    private TextField integerTextField;

    @Override
    public void start(Stage stage) {
        GridPane gridPane = new GridPane();
        GridForm gridForm = new GridForm(gridPane);

        button = gridForm.addButton("button", "Button");
        button.setOnAction(event -> button.setText("clicked!"));

        stringTextField = gridForm.addTextField("stringTextField", stringProperty);
        integerTextField = gridForm.addTextField("integerTextField", integerProperty, GridForm.INTEGER_FORMAT);

        stage.setScene(new Scene(gridPane));
        stage.show();
    }

    @Test
    public void testButton() {
        assertEquals("Button", button.getText());
        clickOn(button);
        assertEquals("clicked!", button.getText());
    }

    @Test
    public void testStringTextField() {
        assertEquals(null, stringProperty.get());
        assertEquals(null, stringTextField.getText());

        stringProperty.set("Alpha");
        assertEquals("Alpha", stringProperty.get());
        assertEquals("Alpha", stringTextField.getText());

        stringTextField.setText("Beta");
        assertEquals("Beta", stringProperty.get());
        assertEquals("Beta", stringTextField.getText());

        clickOn(stringTextField);
        type(KeyCode.BACK_SPACE, KeyCode.BACK_SPACE, KeyCode.BACK_SPACE, KeyCode.BACK_SPACE);
        write("Gamma");
        assertEquals("Gamma", stringProperty.get());
        assertEquals("Gamma", stringTextField.getText());
    }

    @Test
    public void testIntegerTextField() {
        assertEquals(0, integerProperty.get());
        assertEquals("0", integerTextField.getText());

        integerProperty.set(111);
        assertEquals(111, integerProperty.get());
        assertEquals("111", integerTextField.getText());

        integerProperty.set(222);
        assertEquals(222, integerProperty.get());
        assertEquals("222", integerTextField.getText());

        clickOn(integerTextField);
        type(KeyCode.BACK_SPACE, KeyCode.BACK_SPACE, KeyCode.BACK_SPACE);
        write("333");
        assertEquals(333, integerProperty.get());
        assertEquals("333", integerTextField.getText());
    }
}
