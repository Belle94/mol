import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

/**
 * Class of graphic interface
 */
public class Gui {
    private BorderPane borderPane;
    private MenuBar menuBar;
    private HBox hBox;
    private StackPane stackPane;
    private TableView table;
    private TableView<Client> tClient;
    private TableView<Order> tOrder;
    private TableView<Good> tGood;
    private TableView<Vehicle> tVehicle;
    private TextField search;
    private Menu file, edit, view;
    private HashMap<KeyCombination, Boolean> keyPressed;
    private double prefWidth = 800.0;
    private double prefHeight = 600.0;
    private double prefMenuHeight=30;

    /**
     * builder that calls methods for configuring the interface
     */
    public Gui(){
        try {
            initKeyPressed();
            initMenu();
            initSearch();
            inithBox();
            initTable();
            initClientTable();
            initOrderTable();
            initGoodTable();
            initVehicleTable();
            initStackPane();
            initRootElement();
        }catch (Exception e){
            errorMessage("Something Wrong!","Error Message: "+e.getMessage());
        }
    }
    /**
     * init Keys structure, and set all keys to false.
     */
    private void initKeyPressed(){
        keyPressed = new HashMap<>();
        keyPressed.put(KeyCombination.keyCombination("Ctrl+F"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+C"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+O"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+G"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+V"), false);
    }
    /**
     * set and init the Horizontal Box used to contain search text filed
     */
    private void inithBox(){
        hBox = new HBox();
        hBox.getChildren().add(search);
        hBox.setMaxHeight(25);
        hBox.setStyle("-fx-padding: 0 0 0 2; -fx-background-color: transparent;");
    }
    /**
     * set and init the Vertical Box. VBox contain ScrollPane and consequently the Table.
     */
    private void initStackPane(){
        stackPane = new StackPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(table);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        stackPane.getChildren().add(scrollPane);
        stackPane.setAlignment(Pos.TOP_LEFT);
    }
    /**
     * Set and initialize Menu Item
     */
    private void initMenu(){
        menuBar = new MenuBar();
        initMenuFile();
        initMenuEdit();
        initMenuView();
        menuBar.getMenus().addAll(file, edit, view);
    }
    /**
     * set and initialize file section Menu
     */
    private void initMenuFile(){
        file = new Menu("File");
        MenuItem load = new MenuItem("Load");
        MenuItem save = new MenuItem("Save");
        MenuItem save_as = new MenuItem("Save as ");
        MenuItem quit = new MenuItem("Quit");
        load.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        save_as.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));
        quit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));

        quit.setOnAction(e -> {
            if (confirmMessage("Exit Confirmation", "Are u sure u want to exit?"))
                System.exit(0);
        });

        file.getItems().addAll(load, save, save_as, quit);
    }
    /**
     * set and initialize edit section Menu
     */
    private void initMenuEdit(){
        edit = new Menu("Edit");
        MenuItem add = new MenuItem("Add");
        MenuItem modify = new MenuItem("Modify");
        MenuItem find = new MenuItem("Find");
        MenuItem delete = new MenuItem("Delete");
        add.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        modify.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
        delete.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));

        find.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
        find.setOnAction(e -> {
            if (keyPressed.get(KeyCombination.keyCombination("Ctrl+F"))) {
                stackPane.getChildren().remove(hBox);
                keyPressed.put(KeyCombination.keyCombination("Ctrl+F"), false);
            } else {
                stackPane.getChildren().add(hBox);
                search.requestFocus();
                keyPressed.put(KeyCombination.keyCombination("Ctrl+F"), true);
            }
        });

        edit.getItems().addAll(add, modify, delete, find);
    }
    /**
     * set and initialize View section Menu
     */
    private void initMenuView(){
        view = new Menu("View");
        MenuItem Orders = new MenuItem("Orders");
        MenuItem Clients = new MenuItem("Clients");
        MenuItem Goods = new MenuItem("Goods");
        MenuItem Vehicles = new MenuItem("Vehicles");
        Orders.setAccelerator(KeyCombination.keyCombination("Alt+O"));
        Clients.setAccelerator(KeyCombination.keyCombination("Alt+C"));
        Goods.setAccelerator(KeyCombination.keyCombination("Alt+G"));
        Vehicles.setAccelerator(KeyCombination.keyCombination("Alt+V"));
        Clients.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+C"))) {
                stackPane.getChildren().add(tClient);
                search.requestFocus();
                keyPressed.put(KeyCombination.keyCombination("Alt+C"), true);
            }
        });
        Orders.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+O"))) {
                stackPane.getChildren().add(tOrder);
                search.requestFocus();
                keyPressed.put(KeyCombination.keyCombination("Alt+O"), true);
            }
        });
        Goods.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+G"))) {
                stackPane.getChildren().add(tGood);
                search.requestFocus();
                keyPressed.put(KeyCombination.keyCombination("Alt+G"), true);
            }
        });
        Vehicles.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+V"))) {
                stackPane.getChildren().add(tVehicle);
                search.requestFocus();
                keyPressed.put(KeyCombination.keyCombination("Alt+V"), true);
            }
        });

        view.getItems().addAll(Orders, Clients, Goods, Vehicles);
    }

    /**
     * Set and initialize table
     */
    private void initTable(){
        double offset = 0.003;
        table = new TableView();
        table.setEditable(false);
        table.setTableMenuButtonVisible(true);
        table.setMinSize(prefWidth - (prefWidth * offset), prefHeight - prefMenuHeight - (prefHeight * offset));
    }

    private void initClientTable(){
        double offset = 0.003;
        double colw = prefWidth/3;
        tClient = new TableView();
        try {
            Database database = new Database("test.db");
            ObservableList<Client> clients = FXCollections.observableList(database.getAllClients());
            tClient.setItems(clients);
            database.closeConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        tClient.setEditable(false);
        TableColumn<Client,Integer> cId = new TableColumn("Id");
        cId.setMinWidth(colw);
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Client,String> cName = new TableColumn("Name");
        cName.setMinWidth(colw);
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Client,Integer> cCharge = new TableColumn("Charge");
        cCharge.setMinWidth(colw);
        cCharge.setCellValueFactory(new PropertyValueFactory<>("charge"));
        tClient.getColumns().addAll(cId, cName, cCharge);
        tClient.setTableMenuButtonVisible(true);
        tClient.setMinSize(prefWidth - (prefWidth * offset), prefHeight - prefMenuHeight - (prefHeight * offset));
    }

    private void initOrderTable(){
        double offset = 0.003;
        double colw = prefWidth/5;
        tOrder = new TableView();
        try {
            Database database = new Database("test.db");
            ObservableList<Order> orders = FXCollections.observableList(database.getAllOrders());
            tOrder.setItems(orders);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tOrder.setEditable(false);
        TableColumn<Order,Integer> cId = new TableColumn("Id");
        cId.setMinWidth(colw);
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Order,Client> cClient = new TableColumn("Client");
        cClient.setMinWidth(colw);
        cClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        TableColumn<Order,Date> cDate = new TableColumn("Date");
        cDate.setMinWidth(colw);
        cDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<Order,Integer> cPos = new TableColumn("Pos");
        cPos.setMinWidth(colw);
        cPos.setCellValueFactory(new PropertyValueFactory<>("pos"));
        TableColumn<Order,Bin> cBin = new TableColumn("Bin");
        cBin.setMinWidth(colw);
        cBin.setCellValueFactory(new PropertyValueFactory<>("bin"));
        tOrder.getColumns().addAll(cId,cClient,cDate,cPos,cBin);
        tOrder.setTableMenuButtonVisible(true);
        tOrder.setMinSize(prefWidth - (prefWidth * offset), prefHeight - prefMenuHeight - (prefHeight * offset));
    }

    private void initGoodTable(){
        double offset = 0.003;
        double colw = prefWidth/4;
        tGood = new TableView();
        try {
            Database database = new Database("test.db");
            ObservableList<Good> goods = FXCollections.observableList(database.getAllGoods());
            tGood.setItems(goods);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tGood.setEditable(false);
        TableColumn<Good,Integer> cId = new TableColumn("Id");
        cId.setMinWidth(colw);
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Good, Object> cVolume = new TableColumn("Volume");
        cVolume.setMinWidth(colw);
        cVolume.setCellValueFactory(new PropertyValueFactory<>("volume"));
        TableColumn<Good,Integer> cQnt = new TableColumn("Qnt");
        cQnt.setMinWidth(colw);
        cQnt.setCellValueFactory(new PropertyValueFactory<>("qnt"));
        TableColumn<Good,String> cDescription = new TableColumn("Description");
        cDescription.setMinWidth(colw);
        cDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tGood.getColumns().addAll(cId,cVolume,cQnt,cDescription);
        tGood.setTableMenuButtonVisible(true);
        tGood.setMinSize(prefWidth - (prefWidth * offset), prefHeight - prefMenuHeight - (prefHeight * offset));
    }

    private void initVehicleTable(){
        double offset = 0.003;
        double colw = prefWidth/4;
        tVehicle = new TableView();
        try {
            Database database = new Database("test.db");
            ObservableList<Vehicle> vehicles = FXCollections.observableList(database.getAllVehicles());
            tVehicle.setItems(vehicles);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tVehicle.setEditable(false);
        TableColumn<Vehicle,String> cNumberPlate = new TableColumn("NumberPlate");
        cNumberPlate.setMinWidth(colw);
        cNumberPlate.setCellValueFactory(new PropertyValueFactory<>("numberPlate"));
        TableColumn<Vehicle, Object> cChargeCurrent = new TableColumn("Charge Current");
        cChargeCurrent.setMinWidth(colw);
        cChargeCurrent.setCellValueFactory(new PropertyValueFactory<>("chargeCurrent"));
        TableColumn<Vehicle,Object> cChargeMax = new TableColumn("Charge Max");
        cChargeMax.setMinWidth(colw);
        cChargeMax.setCellValueFactory(new PropertyValueFactory<>("chargeMax"));
        TableColumn<Vehicle,Bin> cBin = new TableColumn("Bin");
        cBin.setMinWidth(colw);
        cBin.setCellValueFactory(new PropertyValueFactory<>("bin"));
        tVehicle.getColumns().addAll(cNumberPlate,cChargeCurrent,cChargeMax,cBin);
        tVehicle.setTableMenuButtonVisible(true);
        tVehicle.setMinSize(prefWidth - (prefWidth * offset), prefHeight - prefMenuHeight - (prefHeight * offset));
    }

    /**
     * Set and initialize search textView item
     */
    private void initSearch(){
        search = new TextField();
        search.setPromptText("Search");
        search.setMinWidth(25.0);
    }
    /**
     * Set and initialize Root Item
     */
    private void initRootElement(){
        borderPane = new BorderPane();
        borderPane.setPrefSize(prefWidth,prefHeight);
        borderPane.setCenter(stackPane);
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
     * @param content body of the alert
     * @return alert's return value
     */
    private Optional<ButtonType> setupMessage(Alert alert, String title, String content) {
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        return alert.showAndWait();
    }
    /**
     * Error alert with the given title and header
     * @param title title of the error alert
     * @param content body of the error alert
     */
    private void errorMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        setupMessage(alert, title, content);
    }
    /**
     * Confirm message with the given title and header
     * @param title title of the confirm alert
     * @param content body of the confirm alert
     * @return alert's return value
     */
    private boolean confirmMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        return setupMessage(alert, title, content).get() != ButtonType.CANCEL;
    }
}
