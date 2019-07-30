package ch.obermuhlner.javafx.gridform;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.testfx.framework.junit.ApplicationTest;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public abstract class AbstractGridFormTest extends ApplicationTest {

    private Scene scene;

    @Override
    public void start(Stage stage) {
        GridPane gridPane = new GridPane();
        GridForm gridForm = new GridForm(gridPane);

        scene = new Scene(gridPane);

        setup(gridForm);

        stage.setScene(scene);
        stage.show();
    }

    abstract protected void setup(GridForm gridForm);

    protected void snapshot(String name) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                WritableImage image = scene.snapshot(null);
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File(name + ".png"));
                countDownLatch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
