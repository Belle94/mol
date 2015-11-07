import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        stage.centerOnScreen();
        stage.show();
    }
    //TODO gli archi non devano avere pesi uguale a 0 e possono avere valori decimali
}