import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

/**
 * Main class
 */
public class Main extends Application {
    /**
     * Main function
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /** The standard method called by main in JavaFX applications
     * @param stage of application
     */
    @Override
    public void start(Stage stage) throws Exception {
        Gui gui = new Gui();
        stage.setTitle("Progetto MOL");
        Scene scene = new Scene(gui.getRootElement());
        stage.setScene(scene);
        stage.show();
    }
}