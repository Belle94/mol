import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main class
 */
public class Main extends Application {
    /** the standard method called by main in javafx applications
     * @param stage of application
     */
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Main function
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}