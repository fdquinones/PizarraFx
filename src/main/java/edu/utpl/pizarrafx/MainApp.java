package edu.utpl.pizarrafx;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.utpl.pizarrafx.constant.FxmlEnum;
import edu.utpl.pizarrafx.guice.FxmlLoaderService;
import edu.utpl.pizarrafx.guice.GuiceModule;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainApp extends Application {
    
    private static final Logger LOG = LogManager.getLogger(MainApp.class);
    
    @Override
    public void start(Stage stage) throws Exception {
        System.setProperty("java.net.preferIPv4Stack", "true"); // osx prefers ipv6
        
        LOG.debug("This will be printed on debug");
        LOG.info("This will be printed on info");
        LOG.warn("This will be printed on warn");
        LOG.error("This will be printed on error");
        LOG.fatal("This will be printed on fatal");

        LOG.info("Appending string: {}.", "Hello, World");
        
        final Injector injector = Guice.createInjector(new GuiceModule());
        final FxmlLoaderService fxmlLoaderService = injector.getInstance(FxmlLoaderService.class);

        final Parent root = fxmlLoaderService.load(FxmlEnum.MAIN);

        Scene scene = new Scene(root, 725, 600);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Pizarra distribuida - Sistemas distribuidos UTPL");
        stage.getIcons().add(new Image("/icons/pencil-and-brush-icon.png"));
        stage.setScene(scene);
        
        // Center it on my primary monitor.
	Rectangle2D screen = Screen.getScreens().get(Screen.getScreens().size() - 1).getBounds();
	stage.setX(screen.getMaxX() / 2.0 - stage.getScene().getWidth() / 2.0);
	stage.setY(screen.getHeight() / 2.0 - stage.getScene().getHeight() / 2.0);
	stage.centerOnScreen();
        
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
        stage.sizeToScene();

        stage.setOnCloseRequest((WindowEvent e) -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
