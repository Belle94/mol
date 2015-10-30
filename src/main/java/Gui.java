import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;

import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import javax.swing.*;

/**
 * Class of graphic interface
 */
public class Gui {
    private BorderPane borderPane;
    private MenuBar menuBar;
    private Pane generateInputPane;
    private HBox searchPane;
    private StackPane stackPane;
    private TableView<Client> tClient;
    private TableView tOrder;
    private TableView<Good> tGood;
    private TableView<Vehicle> tVehicle;
    private TextField search;
    private HashMap<KeyCombination, Boolean> keyPressed;
    private double prefWidth = 800.0;
    private double prefHeight = 600.0;
    private double prefMenuHeight=30;
    private View graphPanel;
    private AdjacencyList adjacencyList;
    private double z;
    /**
     * builder that calls methods for configuring the interface
     */
    public Gui(){
        try {
            initKeyPressed();
            initGenerateInputPane();
            initMenu();
            initSearch();
            inithBox();
            initClientTable();
            initOrderTable();
            initGoodTable();
            initVehicleTable();
            initRootElement();
        }catch (Exception e){
            errorMessage("Something Wrong!","Error Message: "+e.getMessage());
            System.err.println("\n[ERROR] +"+e.getMessage());
        }
    }

    private void initGraph() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Graph graph = new SingleGraph("Maps");
        z = 1;
        if (adjacencyList != null){
            int n = adjacencyList.getNumNodes();
            System.out.println(n);
            Integer i;
            for(i = 0; i < n; i++) {
                Node node =graph.addNode(i.toString());
                node.addAttribute("ui.label", i.toString());
                if (i == 0) {
                    node.addAttribute("ui.style", "size: 25px;" +
                            " fill-color: #ee283d;" +
                            " stroke-mode: plain;" +
                            " stroke-color: #999;" +
                            " shadow-mode: plain;" +
                            " shadow-width: 0px;" +
                            " shadow-color: #999;" +
                            " shadow-offset: 3px, -3px;");
                }
                else{
                    node.addAttribute("ui.style", "size: 20px;" +
                            " fill-color: #a5b7c1;" +
                            " stroke-mode: plain;" +
                            " stroke-color: #999;" +
                            " shadow-mode: plain;" +
                            " shadow-width: 0px;" +
                            " shadow-color: #999;" +
                            " shadow-offset: 3px, -3px;");

                }
            }
            for(i = 0; i < n; i++){
                List<Integer> list = adjacencyList.getNeighbor(i);
                int c = 0;
                for(Integer j:list){
                    System.out.println(i + " " + j);
                    Edge edge = graph.addEdge((i.toString() +"-"+ j.toString()), i.toString(), j.toString(), true);
                    edge.addAttribute("ui.label",adjacencyList.getDistance(i,c));
                    edge.addAttribute("ui.style", "shape: cubic-curve;");
                    c++;
                }
            }
        }

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        graphPanel = viewer.addDefaultView(false);
        graphPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                char ch = e.getKeyChar();
                if (ch == '+') {
                    if (z - 0.01 > 0){
                        z -= 0.01;
                        graphPanel.getCamera().setViewPercent(z);
                    }
                }
                if(ch == '-'){
                    if (z + 0.01 < 3){
                        z += 0.01;
                        graphPanel.getCamera().setViewPercent(z);
                    }
                }
                if(ch =='0'){
                    z = 1;
                    graphPanel.getCamera().setViewPercent(z);
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {

            }
        });
    }

    /**
     * init Keys structure, and set all keys to false.
     */
    private void initKeyPressed(){
        keyPressed = new HashMap<>();
        keyPressed.put(KeyCombination.keyCombination("Ctrl+L"), false);
        keyPressed.put(KeyCombination.keyCombination("Ctrl+S"), false);
        keyPressed.put(KeyCombination.keyCombination("Ctrl+Shift+S"), false);
        keyPressed.put(KeyCombination.keyCombination("Ctrl+Q"), false);
        keyPressed.put(KeyCombination.keyCombination("Ctrl+G"), false);
        keyPressed.put(KeyCombination.keyCombination("Ctrl+D"), false);
        keyPressed.put(KeyCombination.keyCombination("Ctrl+F"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+M"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+O"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+C"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+G"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+V"), false);
    }
    /**
     * sets all keys to false except the input in true;
     * @param k input key
     */
    private void setKeyPressed(KeyCombination k){
        for(Map.Entry<KeyCombination, Boolean> entry : keyPressed.entrySet()) {
            KeyCombination key = entry.getKey();
            keyPressed.put(key,false);
        }
        keyPressed.put(k, true);
    }
    /**
     * set and init the Horizontal Box used to contain search text filed
     */
    private void inithBox(){
        searchPane = new HBox();
        searchPane.getChildren().add(search);
        searchPane.setMaxHeight(25);
        searchPane.setStyle("-fx-padding: 0 0 0 2; -fx-background-color: transparent;");
    }
    /**
     * Set and initialize Menu Item
     */
    private void initMenu(){
        menuBar = new MenuBar();
        Menu file, edit, view;
        file = initMenuFile();
        edit = initMenuEdit();
        view = initMenuView();
        menuBar.getMenus().addAll(file, edit, view);
    }
    /**
     * set and initialize file section Menu
     */
    private Menu initMenuFile(){
        Menu file = new Menu("File");
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
        return file;
    }
    /**
     * set and initialize edit section Menu
     */
    private Menu initMenuEdit(){
        Menu edit = new Menu("Edit");
        MenuItem find = new MenuItem("Find");
        MenuItem delete = new MenuItem("Delete");
        MenuItem genInputMap = new MenuItem("Generate Input");
        delete.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));
        genInputMap.setAccelerator(KeyCombination.keyCombination("Ctrl+G"));
        genInputMap.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Ctrl+G"))) {
                setKeyPressed(KeyCombination.keyCombination("Ctrl+G"));
                stackPane.getChildren().clear();
                stackPane.getChildren().add(generateInputPane);
            }
        });

        find.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
        find.setOnAction(e -> {
            if (keyPressed.get(KeyCombination.keyCombination("Ctrl+F"))) {
                stackPane.getChildren().remove(searchPane);
                keyPressed.put(KeyCombination.keyCombination("Ctrl+F"), false);
            } else {
                stackPane.getChildren().add(searchPane);
                search.requestFocus();
                keyPressed.put(KeyCombination.keyCombination("Ctrl+F"), true);
            }
        });
        edit.getItems().addAll(genInputMap,delete, find);
        return edit;
    }

    /**
     * set and Generate Input Pane.
     */
    private void initGenerateInputPane(){
        generateInputPane = new StackPane();
        GridPane container = new GridPane();
        List<Label> labels = new ArrayList<>();
        List<TextInputControl> textList = new ArrayList<>();
        HBox headerBox = new HBox();
        Button gen, cancel;
        gen = new Button("Generate");
        gen.setOnAction(e -> {
        });
        cancel = new Button("Cancel");
        cancel.setOnAction(e -> textList.forEach(javafx.scene.control.TextInputControl::clear));
        Label headerLabel = new Label("Generate Input");
        headerLabel.setStyle("-fx-font-family: 'Goha-Tibeb Zemen'; -fx-font-size: 20px; -fx-text-fill: coral;");
        headerBox.setStyle("-fx-alignment: top-left; -fx-padding: 20 0 20 -20;");
        headerBox.getChildren().add(headerLabel);
        labels.add(new Label("Numero Nodi Massimo: "));
        labels.add(new Label("Numero Archi Massimo: "));
        labels.add(new Label("Distanza Massima:"));
        for (int i=0;i<labels.size(); i++){
            labels.get(i).setStyle("-fx-pref-height:25px; -fx-alignment: center-left; " +
                    "-fx-font-family: 'Goha-Tibeb Zemen'; -fx-font-size: 14px;");
            TextField t = new TextField();
            t.setStyle("-fx-max-width: 65px; -fx-alignment: center");
            textList.add(i, t);
            container.add(labels.get(i),0,i+1);
            container.add(t,1,i+1);
        }
        container.add(headerBox, 0, 0, 2, 1);
        container.add(gen, 0, labels.size() + 1);
        container.add(cancel, 1, labels.size() + 1);
        container.setStyle("-fx-hgap: 10px; -fx-vgap: 5px; -fx-padding: 0 0 0 40px; -fx-background-color: white;");
        gen.setOnAction(e -> {
            if (!textList.get(0).getText().isEmpty() && !textList.get(1).getText().isEmpty() && !textList.get(2).getText().isEmpty()) {
                adjacencyList = Algorithms.generateRndGraph(Integer.parseInt(textList.get(0).getText())-1,
                        Integer.parseInt(textList.get(1).getText()), Integer.parseInt(textList.get(2).getText()));
                System.out.println("Generated!");
                int n = adjacencyList.getNumNodes();
                for (Integer i = 0; i < n; i++) {
                    List<Integer> list = adjacencyList.getNeighbor(i);
                    int c = 0;
                    for (Integer j : list) {
                        System.out.print(i + " " + j+" ");
                        System.out.println(adjacencyList.getDistance(i, c));
                        c++;
                    }
                }

            }
        });
        generateInputPane.getChildren().add(container);
    }

    /**
     * set and initialize View section Menu
     */
    private Menu initMenuView(){
        Menu view = new Menu("View");
        MenuItem orders = new MenuItem("Orders");
        MenuItem clients = new MenuItem("Clients");
        MenuItem goods = new MenuItem("Goods");
        MenuItem vehicles = new MenuItem("Vehicles");
        MenuItem maps = new MenuItem("Maps");
        orders.setAccelerator(KeyCombination.keyCombination("Alt+O"));
        clients.setAccelerator(KeyCombination.keyCombination("Alt+C"));
        goods.setAccelerator(KeyCombination.keyCombination("Alt+G"));
        vehicles.setAccelerator(KeyCombination.keyCombination("Alt+V"));
        maps.setAccelerator(KeyCombination.keyCombination("Alt+M"));
        clients.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+C"))) {
                borderPane.setCenter(tClient);
                setKeyPressed(KeyCombination.keyCombination("Alt+C"));
            }
        });
        orders.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+O"))) {
                borderPane.setCenter(tOrder);
                setKeyPressed(KeyCombination.keyCombination("Alt+O"));
            }
        });
        goods.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+G"))) {
                borderPane.setCenter(tGood);
                setKeyPressed(KeyCombination.keyCombination("Alt+G"));
            }
        });
        vehicles.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+V"))) {
                setKeyPressed(KeyCombination.keyCombination("Alt+V"));
                borderPane.setCenter(tVehicle);
            }
        });
        maps.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+M"))) {
                setKeyPressed(KeyCombination.keyCombination("Alt+M"));
                initGraph();
                SwingNode graphPanelNode = new SwingNode();
                createSwingContent(graphPanelNode);
                borderPane.setCenter(graphPanelNode);

            }
        });
        view.getItems().addAll(orders, clients, goods, vehicles, maps);
        return view;
    }

    private void createSwingContent(final SwingNode node){
        SwingUtilities.invokeLater(() -> node.setContent((JComponent) graphPanel));
    }

    /**
     * Set and initialize client's table
     */

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
        TableColumn cId = new TableColumn("Id");
        cId.setMinWidth(colw);
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn cName = new TableColumn("Name");
        cName.setMinWidth(colw);
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn cCharge = new TableColumn("Charge");
        cCharge.setMinWidth(colw);
        cCharge.setCellValueFactory(new PropertyValueFactory<>("charge"));
        tClient.getColumns().addAll(cId, cName, cCharge);
        tClient.setTableMenuButtonVisible(true);
        tClient.setMinSize(prefWidth - (prefWidth * offset), prefHeight - prefMenuHeight - (prefHeight * offset));
    }

    /**
     * Set and initialize Order's table
     */
    private void initOrderTable(){
        double offset = 0.003;
        double colw = prefWidth/5;
        tOrder = new TableView();
        try {
            Database database = new Database("test.db");
            ObservableList<Order> orders = FXCollections.observableList(database.getAllOrders());
            tOrder.setItems(orders);
            database.closeConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        tOrder.setEditable(false);
        TableColumn cId = new TableColumn("Id");
        cId.setMinWidth(colw);
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn cClient = new TableColumn("Client");
        cClient.setMinWidth(colw);
        cClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        TableColumn cDate = new TableColumn("Date");
        cDate.setMinWidth(colw);
        cDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn cPos = new TableColumn("Pos");
        cPos.setMinWidth(colw);
        cPos.setCellValueFactory(new PropertyValueFactory<>("pos"));
        TableColumn<Order,Bin> cBin = new TableColumn("Bin");
        cBin.setMinWidth(colw);
        cBin.setCellValueFactory(new PropertyValueFactory<>("bin"));
        tOrder.getColumns().addAll(cId, cClient, cDate, cPos, cBin);
        tOrder.setTableMenuButtonVisible(true);
        tOrder.setMinSize(prefWidth - (prefWidth * offset), prefHeight - prefMenuHeight - (prefHeight * offset));
    }

    /**
     * Set and initialize Good's table
     */

    private void initGoodTable(){
        double offset = 0.003;
        double colw = prefWidth/4;
        tGood = new TableView();
        try {
            Database database = new Database("test.db");
            ObservableList<Good> goods = FXCollections.observableList(database.getAllGoods());
            tGood.setItems(goods);
            database.closeConnection();
        } catch (SQLException | ClassNotFoundException e) {
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
        tGood.getColumns().addAll(cId, cVolume, cQnt, cDescription);
        tGood.setTableMenuButtonVisible(true);
        tGood.setMinSize(prefWidth - (prefWidth * offset), prefHeight - prefMenuHeight - (prefHeight * offset));
    }

    /**
     * Set and initialize Vehicle's table
     */

    private void initVehicleTable(){
        double offset = 0.003;
        double colw = prefWidth/4;
        tVehicle = new TableView();
        try {
            Database database = new Database("test.db");
            ObservableList<Vehicle> vehicles = FXCollections.observableList(database.getAllVehicles());
            tVehicle.setItems(vehicles);
            database.closeConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        tVehicle.setEditable(false);
        TableColumn cNumberPlate = new TableColumn("NumberPlate");
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
        stackPane = new StackPane();
        ScrollPane scrollPane = new ScrollPane();
        borderPane.setPrefSize(prefWidth,prefHeight);
        borderPane.setCenter(scrollPane);
        borderPane.setTop(menuBar);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        //scrollPane.setContent(stackPane);
        stackPane.setAlignment(Pos.TOP_LEFT);
        setKeyPressed(KeyCombination.keyCombination("Ctrl+G"));
        stackPane.getChildren().addAll(generateInputPane);
        borderPane.setCenter(stackPane);
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
