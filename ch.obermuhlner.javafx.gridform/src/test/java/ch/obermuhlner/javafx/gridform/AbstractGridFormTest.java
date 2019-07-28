package ch.obermuhlner.javafx.gridform;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.testfx.framework.junit.ApplicationTest;

public abstract class AbstractGridFormTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        GridPane gridPane = new GridPane();
        GridForm gridForm = new GridForm(gridPane);

        setup(gridForm);

        stage.setScene(new Scene(gridPane));
        stage.show();
    }

    abstract protected void setup(GridForm gridForm);
}
