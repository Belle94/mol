import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import java.util.Optional;


/**
 * Class of graphic interface
 * Created by bruce on 17/10/15.
 */
public class Gui {
    private BorderPane borderPane;
    private MenuBar menuBar;
    private VBox vBox;
    private HBox hBox;
    private TableView table;
    private TextField search;
    private Menu file, edit, view;
    private double prefWidth = 800.0;
    private double prefHeight = 600.0;

    /**
     * builder, call methods for configuring the interface
     */
    public Gui(){
        initTable();
        initSearch();
        initMenu();
        initRootElement();
    }
    /**
     * Set and initialize Menu Item
     */
    private void initMenu(){
        menuBar = new MenuBar();
        initMenuFile();
        initMenuEdit();
        initMenuView();
        menuBar.getMenus().addAll(file,edit,view);
    }
    /**
     * set and initialize file section Menu
     */
    private void initMenuFile(){
        file = new Menu("File");
        MenuItem load = new MenuItem("load");
        MenuItem save = new MenuItem("save");
        MenuItem save_as = new MenuItem("save_as");
        MenuItem quit = new MenuItem("quit");
        load.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        save_as.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));
        quit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));

        file.getItems().addAll(load, save, save_as, quit);
    }
    /**
     * set and initialize edit section Menu
     */
    private void initMenuEdit(){
        edit = new Menu("Edit");
        MenuItem add = new MenuItem("add");
        MenuItem modify = new MenuItem("modify");
        MenuItem find = new MenuItem("find");
        MenuItem delete = new MenuItem("delete");
        add.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        modify.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
        delete.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));
        find.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));

        edit.getItems().addAll(add,modify,delete);
    }
    /**
     * set and initialize View section Menu
     */
    private void initMenuView(){
        view = new Menu("View");
        MenuItem Orders = new MenuItem("Orders");
        MenuItem Clients = new MenuItem("Clients");
        MenuItem Goods = new MenuItem("Goods");
        MenuItem Itineraries = new MenuItem("Itineraries");
        MenuItem Vehicles = new MenuItem("Vehicles");
        Orders.setAccelerator(KeyCombination.keyCombination("Alt+O"));
        Clients.setAccelerator(KeyCombination.keyCombination("Alt+C"));
        Goods.setAccelerator(KeyCombination.keyCombination("Alt+G"));
        Itineraries.setAccelerator(KeyCombination.keyCombination("Alt+I"));
        Vehicles.setAccelerator(KeyCombination.keyCombination("Alt+V"));
        view.getItems().addAll(Orders,Clients,Goods,Vehicles,Itineraries);
    }
    /**
     * Set and initialize table
     */
    private void initTable(){
        table = new TableView();
    }
    /**
     * Set and initialize search textView item
     */
    private void initSearch(){
        search = new TextField();
        search.setPromptText("Search");
        search.setMinWidth(25.0);
        search.setAlignment(Pos.TOP_CENTER);
    }
    /**
     * Set and initialize Root Item
     */
    private void initRootElement(){
        borderPane = new BorderPane();
        vBox = new VBox();
        hBox = new HBox();
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(table);
        hBox.getChildren().add(search);
        hBox.setPrefHeight(search.getHeight());
        borderPane.setPrefSize(prefWidth,prefHeight);
        borderPane.setCenter(vBox);
        borderPane.setTop(menuBar);
    }
    /**
     * @return main panel
     */
    public Pane getRootElement(){
        return borderPane;
    }
    /**
     * Setup general alert window
     * @param alert alert to setup
     * @param title title of the alert
     * @param header header of the alert
     * @return alert's return value
     */
    private Optional<ButtonType> setupMessage(Alert alert, String title, String header) {
        alert.setTitle(title);
        alert.setHeaderText(header);
        return alert.showAndWait();
    }
    /**
     * Error alert with the given title and header
     * @param title title of the error alert
     * @param header header of the error alert
     */
    private void errorMessage(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        setupMessage(alert, title, header);
    }
    /**
     * Confirm message with the given title and header
     * @param title title of the confirm alert
     * @param header header of the confirm alert
     * @return alert's return value
     */
    private boolean confirmMessage(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        return setupMessage(alert, title, header).get() != ButtonType.CANCEL;
    }
    /**
     * Text input alert with the given title and header
     * @param title title of the given text input alert
     * @param header header of the given text input alert
     * @return alert's return value
     */
    private Optional<String> textInputMessage(String title, String header) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle(title);
        textInputDialog.setHeaderText(header);
        return textInputDialog.showAndWait();
    }
}
