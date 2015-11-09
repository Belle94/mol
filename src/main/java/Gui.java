import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import java.awt.event.KeyListener;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.*;

import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import javax.swing.*;

/**
 * Class of graphic interface
 */
public class Gui {
    private BorderPane rootPane;
    private MenuBar menuBar;
    private Pane generateInputPane,addEdgePane,delEdgePane, pathPane;
    private HBox searchPane;
    private StackPane mainPane;
    private TableView<Order> tOrder;
    private TableView<Client> tClient;
    private TableView<GoodOrder> tGoodOrder;
    private TableView<Good> tGood;
    private TableView<Vehicle> tVehicle;
    private TextField search;
    private HashMap<KeyCombination, Boolean> keyPressed;
    private double prefWidth = 800.0;
    private double prefHeight = 600.0;
    private double prefMenuHeight=30;
    private Graph graph;
    private View graphPanel;
    private double zoom;
    private AdjacencyList adjacencyList;
    private List<Good> goods;
    private List<Client> clients;
    private List<Vehicle> vehicles;
    private List<Bin> bins;
    private List<GoodOrder> goodOrders;
    private List<Order> orders;

    /**
     * builder that calls methods for configuring the interface
     */
    public Gui(){
        try {
            initData();
            initKeyPressed();
            initGenerateInputPane();
            initAddEdgePane();
            initDelEdgePane();
            initPathPane();
            initMenu();
            initSearch();
            initClientTable();
            initOrderTable();
            initGoodTable();
            initVehicleTable();
            initMainPane();
            initRootElement();
        }catch (Exception e){
            errorMessage("Something Wrong!","Error Message: "+e.getMessage());
            System.err.println("\n[ERROR] +"+e.getMessage());
        }
    }

    private void initGraph() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        graph = new SingleGraph("Maps");
        zoom = 1;
        if (adjacencyList != null){
            int n = adjacencyList.getNumNodes();
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
                    if (zoom - 0.01 > 0) {
                        zoom -= 0.01;
                        graphPanel.getCamera().setViewPercent(zoom);
                    }
                }
                if (ch == '-') {
                    if (zoom + 0.01 < 3) {
                        zoom += 0.01;
                        graphPanel.getCamera().setViewPercent(zoom);
                    }
                }
                if (ch == '0') {
                    zoom = 1;
                    graphPanel.getCamera().setViewPercent(zoom);
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
        keyPressed.put(KeyCombination.keyCombination("Ctrl+S"), true);
        keyPressed.put(KeyCombination.keyCombination("Ctrl+Shift+S"), true);
        keyPressed.put(KeyCombination.keyCombination("Ctrl+Q"), false);
        keyPressed.put(KeyCombination.keyCombination("Ctrl+G"), false);
        keyPressed.put(KeyCombination.keyCombination("Ctrl+D"), false);
        keyPressed.put(KeyCombination.keyCombination("Ctrl+F"), false);
        keyPressed.put(KeyCombination.keyCombination("Ctrl+B"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+M"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+O"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+C"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+G"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+H"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+P"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+V"), false);
    }
    /**
     * sets all keys to false except the input in true;
     * @param k input key
     */
    private void setKeyPressed(KeyCombination k){
        for(Map.Entry<KeyCombination, Boolean> entry : keyPressed.entrySet()) {
            KeyCombination key = entry.getKey();
            if (!(key.equals(KeyCombination.keyCombination("Ctrl+S"))
                    ||
                  key.equals(KeyCombination.keyCombination("Ctrl+Shift+S"))
            ))
            keyPressed.put(key,false);
        }
        keyPressed.put(k, true);
    }
    /**
     * set and init the Horizontal Box used to contain search text filed
     */
    private void initSearchPane(){
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
        MenuItem save_as = new MenuItem("Save as");
        MenuItem quit = new MenuItem("Quit");
        load.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        save_as.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));
        quit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        load.setOnAction(e->{
            FileChooser fs = new FileChooser();
            fs.setTitle("Select Database");
            fs.getExtensionFilters().add(new FileChooser.ExtensionFilter("Database", "*.db"));
            fs.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
            File f =fs.showOpenDialog(new Stage());
            if (f != null)
            try{
                Database db = new Database(f.getAbsolutePath());
                goods = db.getAllGoods();
                clients = db.getAllClients();
                vehicles = db.getAllVehicles();
                bins = db.getAllBins();
                goodOrders = db.getAllGoodOrders();
                orders = db.getAllOrders();
                adjacencyList = db.getAdjacencyList();
                initGoodOrderTable();
                initVehicleTable();
                initOrderTable();
                initGoodTable();
                initClientTable();
                initGraph();
                db.closeConnection();
                keyPressed.put(KeyCombination.keyCombination("Ctrl+S"), false);
                infoMessage("Load", "Done!");
            }catch (ClassNotFoundException | SQLException err) {
                err.printStackTrace();
                errorMessage("Error", "msg:" + err.getMessage());
            }catch (IllegalArgumentException e1){
                e1.printStackTrace();
                errorMessage("Illegal File Format!", "Choose a valid file format (*.db)");
            }
        });
        quit.setOnAction(e -> {
            if (confirmMessage("Exit Confirmation", "Are u sure u want to exit?"))
                System.exit(0);
        });
        save.setOnAction(e -> {
            if (! keyPressed.get(KeyCombination.keyCombination("Ctrl+S"))) {
                try {
                    Database db = new Database("database.db");
                    db.clearTables();
                    db.addOrders(orders);
                    db.addGoods(goods);
                    db.addGoodOrders(goodOrders);
                    db.addBins(bins);
                    db.addVehicles(vehicles);
                    db.addAdjacencyList(adjacencyList);
                    db.closeConnection();
                    keyPressed.put(KeyCombination.keyCombination("Ctrl+S"), true);
                    infoMessage("Save", "Done!");
                } catch (SQLException | IllegalArgumentException | ClassNotFoundException e1) {
                    errorMessage("Error", "msg:"+e1.getMessage());
                    e1.printStackTrace();
                }
            }else{
                infoMessage("Info","nothing to save");
            }
        });
        save_as.setOnAction(e -> {
            if (! keyPressed.get(KeyCombination.keyCombination("Ctrl+Shift+S"))) {
                try {
                    FileChooser fs = new FileChooser();
                    fs.setTitle("Save Database");
                    fs.getExtensionFilters().add(new FileChooser.ExtensionFilter("Database", "*.db"));
                    fs.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
                    File f =fs.showSaveDialog(new Stage());
                    if (f==null)
                        return;
                    String nameFile = (f.getAbsolutePath().endsWith(".db"))
                                ? f.getAbsolutePath() : f.getAbsolutePath().concat(".db") ;
                    Database db = new Database(nameFile);
                    db.clearTables();
                    db.addClients(clients);
                    db.addOrders(orders);
                    db.addGoods(goods);
                    db.addGoodOrders(goodOrders);
                    db.addBins(bins);
                    db.addVehicles(vehicles);
                    db.addAdjacencyList(adjacencyList);
                    db.closeConnection();
                    infoMessage("Save", "Done!");
                } catch (SQLException | IllegalArgumentException | ClassNotFoundException e1) {
                    errorMessage("Error", "msg:"+e1.getMessage());
                    e1.printStackTrace();
                }
            }else{
                infoMessage("Info","nothing to save");
            }
        });
        
        file.getItems().addAll(load, save, save_as, quit);
        return file;
    }
    /**
     * set and initialize edit section Menu
     */
    private Menu initMenuEdit(){
        Menu edit = new Menu("Edit");
        MenuItem addEdge = new MenuItem("Add Edge");
        MenuItem delEdge = new MenuItem("Delete Edge");
        MenuItem find = new MenuItem("Find");
        MenuItem genInputMap = new MenuItem("Generate Input");
        addEdge.setAccelerator(KeyCombination.keyCombination("Ctrl+B"));
        delEdge.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));
        genInputMap.setAccelerator(KeyCombination.keyCombination("Ctrl+G"));
        genInputMap.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Ctrl+G"))) {
                setKeyPressed(KeyCombination.keyCombination("Ctrl+G"));
                mainPane.getChildren().clear();
                mainPane.getChildren().add(generateInputPane);
            }
        });
        addEdge.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Ctrl+B"))) {
                setKeyPressed(KeyCombination.keyCombination("Ctrl+B"));
                mainPane.getChildren().clear();
                mainPane.getChildren().add(addEdgePane);
            }
        });
        delEdge.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Ctrl+D"))) {
                setKeyPressed(KeyCombination.keyCombination("Ctrl+D"));
                mainPane.getChildren().clear();
                mainPane.getChildren().add(delEdgePane);
            }
        });
        find.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
        find.setOnAction(e -> {
            if (keyPressed.get(KeyCombination.keyCombination("Ctrl+F"))) {
                searchPane.setVisible(false);
                keyPressed.put(KeyCombination.keyCombination("Ctrl+F"), false);
            } else {
                searchPane.setVisible(true);
                search.requestFocus();
                keyPressed.put(KeyCombination.keyCombination("Ctrl+F"), true);
            }
        });
        edit.getItems().addAll(genInputMap,addEdge,delEdge,find);
        return edit;
    }

    /**
     * set and Generate Add Edge Pane
     */
    private void initAddEdgePane(){
        addEdgePane = new StackPane();
        GridPane container = new GridPane();
        List<Label> labels = new ArrayList<>();
        List<TextInputControl> textList = new ArrayList<>();
        HBox headerBox = new HBox();
        Button add, cancel;
        add = new Button("Add");
        cancel = new Button("Cancel");
        cancel.setOnAction(e -> textList.forEach(javafx.scene.control.TextInputControl::clear));
        Label headerLabel = new Label("Add New Edge");
        headerLabel.setFont(new Font("Goha-tibeb Zeman", 14));
        headerLabel.setStyle("-fx-text-fill: coral;");
        headerBox.setStyle("-fx-alignment: top-left; -fx-padding: 20 0 20 -20;");
        headerBox.getChildren().add(headerLabel);
        labels.add(new Label("Partenza:"));
        labels.add(new Label("Destinatione:"));
        labels.add(new Label("Peso:"));
        for (int i=0;i<labels.size(); i++){
            labels.get(i).setFont(new Font("Goha-tibeb Zeman",  14));
            labels.get(i).setStyle("-fx-pref-height:25px; -fx-alignment: center-left; ");
            TextField t = new TextField();
            t.setStyle("-fx-max-width: 65px; -fx-alignment: center");
            textList.add(i, t);
            container.add(labels.get(i),0,i+1);
            container.add(t,1,i+1);
        }
        container.add(headerBox, 0, 0, 2, 1);
        container.add(add, 0, labels.size() + 1);
        container.add(cancel, 1, labels.size() + 1);
        container.setStyle("-fx-hgap: 10px; -fx-vgap: 5px; -fx-padding: 0 0 0 40px; -fx-background-color: white;");
        add.setOnAction(e -> {
            if (!isListEmpty(textList)) {
                adjacencyList.addEdge(Integer.parseInt(textList.get(0).getText()), Integer.parseInt(textList.get(1).getText()), Double.valueOf(textList.get(2).getText()));
                initGraph();
                infoMessage("Node","added");
                keyPressed.put(KeyCombination.keyCombination("Ctrl+S"),false);
            }else
                infoMessage("Not Generated","please let complete the blanks");
        });
        addEdgePane.getChildren().add(container);
    }

    /**
     * set and Generate Delete Edge Pane
     */
    private void initDelEdgePane(){
        delEdgePane = new StackPane();
        GridPane container = new GridPane();
        List<Label> labels = new ArrayList<>();
        List<TextInputControl> textList = new ArrayList<>();
        HBox headerBox = new HBox();
        Button delete, cancel;
        delete = new Button("Delete");
        cancel = new Button("Cancel");
        cancel.setOnAction(e -> textList.forEach(javafx.scene.control.TextInputControl::clear));
        Label headerLabel = new Label("Delete Edge");
        headerLabel.setFont(new Font("Goha-tibeb Zeman", 14));
        headerLabel.setStyle("-fx-text-fill: coral;");
        headerBox.setStyle("-fx-alignment: top-left; -fx-padding: 20 0 20 -20;");
        headerBox.getChildren().add(headerLabel);
        labels.add(new Label("Partenza:"));
        labels.add(new Label("Destinatione:"));
        for (int i=0;i<labels.size(); i++){
            labels.get(i).setFont(new Font("Goha-tibeb Zeman",  14));
            labels.get(i).setStyle("-fx-pref-height:25px; -fx-alignment: center-left; ");
            TextField t = new TextField();
            t.setStyle("-fx-max-width: 65px; -fx-alignment: center");
            textList.add(i, t);
            container.add(labels.get(i),0,i+1);
            container.add(t,1,i+1);
        }
        container.add(headerBox, 0, 0, 2, 1);
        container.add(delete, 0, labels.size() + 1);
        container.add(cancel, 1, labels.size() + 1);
        container.setStyle("-fx-hgap: 10px; -fx-vgap: 5px; -fx-padding: 0 0 0 40px; -fx-background-color: white;");
        delete.setOnAction(e -> {
            if (!isListEmpty(textList)) {
                adjacencyList.deleteEdge(Integer.parseInt(textList.get(0).getText()), Integer.parseInt(textList.get(1).getText()));
                initGraph();
                infoMessage("Node", "Deleted");
                keyPressed.put(KeyCombination.keyCombination("Ctrl+S"),false);
            } else
                infoMessage("Not Generated", "please let complete the blanks");
        });
        delEdgePane.getChildren().add(container);
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
        cancel = new Button("Cancel");
        cancel.setOnAction(e -> textList.forEach(javafx.scene.control.TextInputControl::clear));
        Label headerLabel = new Label("Generate Input");
        headerLabel.setFont(new Font("Goha-tibeb Zeman",  14));
        headerLabel.setStyle("-fx-text-fill: coral;");
        headerBox.setStyle("-fx-alignment: top-left; -fx-padding: 20 0 20 -20;");
        headerBox.getChildren().add(headerLabel);
        labels.add(new Label("Clienti:"));
        labels.add(new Label("Collegamenti max:"));
        labels.add(new Label("Distanza max:"));
        labels.add(new Label("Merce:"));
        labels.add(new Label("M. max qnt:"));
        labels.add(new Label("M. max vol:"));
        labels.add(new Label("Veicoli:"));
        labels.add(new Label("V. capienza:"));
        labels.add(new Label("max ord in un clt:"));
        labels.add(new Label("max goods in un ord:"));
        for (int i=0;i<labels.size(); i++){
            labels.get(i).setFont(new Font("Goha-tibeb Zeman",  14));
            labels.get(i).setStyle("-fx-pref-height:25px; -fx-alignment: center-left; ");
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
            if (!isListEmpty(textList)) {
                if (!generateData(textList))
                    return;
                initGraph();
                initClientTable();
                initGoodTable();
                initGoodOrderTable();
                initOrderTable();
                initVehicleTable();
                keyPressed.put(KeyCombination.keyCombination("Ctrl+S"), false);
                keyPressed.put(KeyCombination.keyCombination("Ctrl+Shift+S"), false);
                infoMessage("Info","graph generated");
            }
            else
                infoMessage("Not Generated","please let complete the blanks");
        });
        generateInputPane.getChildren().add(container);
    }


    private boolean isListEmpty(List<TextInputControl> list){
        for (TextInputControl text : list)
            if (text.getText().isEmpty())
                return true;
        return false;
    }

    private boolean generateData(List<TextInputControl> texts){
        try {
            Pair<List<Client>, AdjacencyList> pair = Algorithms.generateRndGraph(
                    Integer.parseInt(texts.get(0).getText()),
                    Integer.parseInt(texts.get(1).getText()),
                    Integer.parseInt(texts.get(2).getText())
            );
            adjacencyList = pair.getValue();
            clients = pair.getKey();
            int maxDistance = (int) adjacencyList.getMaxDistance();
            Algorithms.generateRndCharge(clients, maxDistance);
            goods = Algorithms.generateGoods(
                    Integer.parseInt(texts.get(3).getText()),
                    Integer.parseInt(texts.get(4).getText()),
                    Double.parseDouble(texts.get(5).getText())
            );
            bins = Algorithms.generateBins(
                    Integer.parseInt(texts.get(6).getText()),
                    Double.parseDouble(texts.get(7).getText())
            );
            vehicles = Algorithms.generateVehicle(maxDistance, bins);
            orders = Algorithms.generateOrders(clients,
                    Integer.parseInt(texts.get(8).getText())
            );
            goodOrders = Algorithms.generateGoodOrder(
                    Integer.parseInt(texts.get(9).getText()),
                    orders,
                    goods
            );
            return true;
        }catch (Exception e){
            errorMessage("Not Generated", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void initData(){
        adjacencyList = new AdjacencyList();
        goods = new ArrayList<>();
        clients = new ArrayList<>();
        vehicles = new ArrayList<>();
        bins = new ArrayList<>();
        goodOrders = new ArrayList<>();
        orders = new ArrayList<>();
    }

    /**
     * set and initialize View section Menu
     */
    private Menu initMenuView(){
        Menu view = new Menu("View");
        MenuItem orders = new MenuItem("Orders");
        MenuItem clients = new MenuItem("Clients");
        MenuItem goods = new MenuItem("Goods");
        MenuItem goodOrder = new MenuItem("Good Order");
        MenuItem vehicles = new MenuItem("Vehicles");
        MenuItem maps = new MenuItem("Maps");
        MenuItem path = new MenuItem("Path Vehicles");
        orders.setAccelerator(KeyCombination.keyCombination("Alt+O"));
        clients.setAccelerator(KeyCombination.keyCombination("Alt+C"));
        goods.setAccelerator(KeyCombination.keyCombination("Alt+G"));
        goodOrder.setAccelerator(KeyCombination.keyCombination("Alt+H"));
        vehicles.setAccelerator(KeyCombination.keyCombination("Alt+V"));
        maps.setAccelerator(KeyCombination.keyCombination("Alt+M"));
        path.setAccelerator(KeyCombination.keyCombination("Alt+P"));
        clients.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+C"))) {
                setMainPane(tClient);
                setKeyPressed(KeyCombination.keyCombination("Alt+C"));
            }
        });
        orders.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+O"))) {
                setMainPane(tOrder);
                setKeyPressed(KeyCombination.keyCombination("Alt+O"));
            }
        });
        goods.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+G"))) {
                setMainPane(tGood);
                setKeyPressed(KeyCombination.keyCombination("Alt+G"));
            }
        });
        goodOrder.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+H"))) {
                setMainPane(tGoodOrder);
                setKeyPressed(KeyCombination.keyCombination("Alt+H"));
            }
        });
        vehicles.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+V"))) {
                setMainPane(tVehicle);
                setKeyPressed(KeyCombination.keyCombination("Alt+V"));
            }
        });
        maps.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+M"))) {
                setMainPaneGraph();
                setKeyPressed(KeyCombination.keyCombination("Alt+M"));
            }
        });

        path.setOnAction(e -> {
            if (!keyPressed.get(KeyCombination.keyCombination("Alt+P"))) {
                setKeyPressed(KeyCombination.keyCombination("Alt+P"));
                mainPane.getChildren().clear();
                mainPane.getChildren().add(pathPane);

            }
        });
        view.getItems().addAll(orders, clients, goods, goodOrder, vehicles, maps,path);
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
        tClient = new TableView<>();
        ObservableList<Client> clientObservableList = FXCollections.observableList(clients);
        tClient.getItems().clear();
        tClient.setItems(clientObservableList);
        tClient.setEditable(false);
        TableColumn<Client,Integer> cId = new TableColumn<>("Id");
        cId.setMinWidth(colw);
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Client,String> cName = new TableColumn<>("Name");
        cName.setMinWidth(colw);
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Client,Integer> cCharge = new TableColumn<>("Charge");
        cCharge.setMinWidth(colw);
        cCharge.setCellValueFactory(new PropertyValueFactory<>("charge"));
        tClient.getColumns().add(cId);
        tClient.getColumns().add(cName);
        tClient.getColumns().add(cCharge);
        tClient.setTableMenuButtonVisible(true);
        tClient.setMinSize(prefWidth - (prefWidth * offset), prefHeight - prefMenuHeight - (prefHeight * offset));
    }

    /**
     * Set and initialize Order's table
     */
    private void initOrderTable(){
        double offset = 0.003;
        double colw = prefWidth/5;
        tOrder = new TableView<>();
        ObservableList<Order> orderObservableList = FXCollections.observableList(orders);
        tOrder.getItems().clear();
        tOrder.setItems(orderObservableList);
        tOrder.setEditable(false);
        TableColumn<Order,Integer> cId = new TableColumn<>("Id");
        cId.setMinWidth(colw);
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Order,Client> cClient = new TableColumn<>("Client");
        cClient.setMinWidth(colw);
        cClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        TableColumn<Order,Date> cDate = new TableColumn<>("Date");
        cDate.setMinWidth(colw);
        cDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<Order,Integer> cPos = new TableColumn<>("Pos");
        cPos.setMinWidth(colw);
        cPos.setCellValueFactory(new PropertyValueFactory<>("pos"));
        TableColumn<Order,Vehicle> cVehicle = new TableColumn<>("Vehicle");
        cVehicle.setMinWidth(colw);
        cVehicle.setCellValueFactory(new PropertyValueFactory<>("vehicle"));
        tOrder.getColumns().add(cId);
        tOrder.getColumns().add(cClient);
        tOrder.getColumns().add(cDate);
        tOrder.getColumns().add(cPos);
        tOrder.getColumns().add(cVehicle);
        tOrder.setTableMenuButtonVisible(true);
        tOrder.setMinSize(prefWidth - (prefWidth * offset), prefHeight - prefMenuHeight - (prefHeight * offset));
    }

    /**
     * Set and initialize Good's table
     */

    private void initGoodTable(){
        double offset = 0.003;
        double colw = prefWidth/4;
        tGood = new TableView<>();
        ObservableList<Good> goodObservableList = FXCollections.observableList(goods);
        tGood.getItems().clear();
        tGood.setItems(goodObservableList);
        tGood.setEditable(false);
        TableColumn<Good,Integer> cId = new TableColumn<>("Id");
        cId.setMinWidth(colw);
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Good, Object> cVolume = new TableColumn<>("Volume");
        cVolume.setMinWidth(colw);
        cVolume.setCellValueFactory(new PropertyValueFactory<>("volume"));
        TableColumn<Good,Integer> cQnt = new TableColumn<>("Qnt");
        cQnt.setMinWidth(colw);
        cQnt.setCellValueFactory(new PropertyValueFactory<>("qnt"));
        TableColumn<Good,String> cDescription = new TableColumn<>("Description");
        cDescription.setMinWidth(colw);
        cDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tGood.getColumns().add(cId);
        tGood.getColumns().add(cVolume);
        tGood.getColumns().add(cQnt);
        tGood.getColumns().add(cDescription);
        tGood.setTableMenuButtonVisible(true);
        tGood.setMinSize(prefWidth - (prefWidth * offset), prefHeight - prefMenuHeight - (prefHeight * offset));
    }

    /**
     * Set and initialize Vehicle's table
     */
    private void initVehicleTable(){
        double offset = 0.003;
        double colw = prefWidth/4;
        tVehicle = new TableView<>();
        ObservableList<Vehicle> vehiclesObservableList  = FXCollections.observableList(vehicles);
        tVehicle.getItems().clear();
        tVehicle.setItems(vehiclesObservableList);
        tVehicle.setEditable(false);
        TableColumn<Vehicle,String> cNumberPlate = new TableColumn<>("NumberPlate");
        cNumberPlate.setMinWidth(colw);
        cNumberPlate.setCellValueFactory(new PropertyValueFactory<>("numberPlate"));
        TableColumn<Vehicle, Object> cChargeCurrent = new TableColumn<>("Charge Current");
        cChargeCurrent.setMinWidth(colw);
        cChargeCurrent.setCellValueFactory(new PropertyValueFactory<>("chargeCurrent"));
        TableColumn<Vehicle,Object> cChargeMax = new TableColumn<>("Charge Max");
        cChargeMax.setMinWidth(colw);
        cChargeMax.setCellValueFactory(new PropertyValueFactory<>("chargeMax"));
        TableColumn<Vehicle,Bin> cBin = new TableColumn<>("Bin");
        cBin.setMinWidth(colw);
        cBin.setCellValueFactory(new PropertyValueFactory<>("bin"));
        tVehicle.getColumns().add(cNumberPlate);
        tVehicle.getColumns().add(cChargeCurrent);
        tVehicle.getColumns().add(cChargeMax);
        tVehicle.getColumns().add(cBin);
        tVehicle.setTableMenuButtonVisible(true);
        tVehicle.setMinSize(prefWidth - (prefWidth * offset), prefHeight - prefMenuHeight - (prefHeight * offset));
    }

    /**
     * Set and initialize Vehicle's table
     */
    private void initGoodOrderTable(){
        double offset = 0.003;
        double colw = prefWidth/4;
        tGoodOrder = new TableView<>();
        ObservableList<GoodOrder> goodOrderObservableList  = FXCollections.observableList(goodOrders);
        tGoodOrder.getItems().clear();
        tGoodOrder.setItems(goodOrderObservableList);
        tGoodOrder.setEditable(false);
        TableColumn<GoodOrder,Integer> cId = new TableColumn<>("Id");
        cId.setMinWidth(colw);
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<GoodOrder, Order> cOrder = new TableColumn<>("Order");
        cOrder.setMinWidth(colw);
        cOrder.setCellValueFactory(new PropertyValueFactory<>("order"));
        TableColumn<GoodOrder, Good> cGood = new TableColumn<>("Good");
        cGood.setMinWidth(colw);
        cGood.setCellValueFactory(new PropertyValueFactory<>("good"));
        TableColumn<GoodOrder,Integer> cQnt = new TableColumn<>("Qnt");
        cQnt.setMinWidth(colw);
        cQnt.setCellValueFactory(new PropertyValueFactory<>("qnt"));
        tGoodOrder.getColumns().add(cId);
        tGoodOrder.getColumns().add(cOrder);
        tGoodOrder.getColumns().add(cGood);
        tGoodOrder.getColumns().add(cQnt);
        tGoodOrder.setTableMenuButtonVisible(true);
        tGoodOrder.setMinSize(prefWidth - (prefWidth * offset), prefHeight - prefMenuHeight - (prefHeight * offset));
    }

    /**
     * Set and initialize search textView item
     */
    private void initSearch(){
        search = new TextField();
        search.setPromptText("Search");
        search.setMinWidth(25.0);
        initSearchPane();
    }
    /**
     * Set and initialize Root Item
     */
    private void initRootElement(){
        rootPane = new BorderPane();
        mainPane = new StackPane();
        rootPane.setPrefSize(prefWidth, prefHeight);
        rootPane.setTop(menuBar);
        mainPane.setAlignment(Pos.TOP_LEFT);
        keyPressed.put(KeyCombination.keyCombination("Ctrl+G"), true);
        mainPane.getChildren().addAll(generateInputPane);
        rootPane.setCenter(mainPane);
    }

    private void setMainPane(TableView<?> tablePane){
        mainPane.getChildren().clear();
        mainPane.getChildren().add(tablePane);
        mainPane.getChildren().add(searchPane);
        searchPane.setVisible(false);
    }

    private void setMainPaneGraph(){
        mainPane.getChildren().clear();
        SwingNode graphPanelNode = new SwingNode();
        createSwingContent(graphPanelNode);
        mainPane.getChildren().add(graphPanelNode);
        searchPane.setVisible(false);
    }

    /**
     * that function design the path of vehicles on graph changing the color of edges
     */
    private void pathOnGraph(){
        Edge e1 = graph.getEdge(0);
        e1.addAttribute("ui.style",
                "fill-color: red;");
    }

    private void initPathPane(){
        pathPane = new StackPane();
        GridPane container = new GridPane();
        List<Label> labels = new ArrayList<>();
        List<TextInputControl> textList = new ArrayList<>();
        HBox headerBox = new HBox();
        Button show, cancel;
        show = new Button("Show");
        cancel = new Button("Cancel");
        cancel.setOnAction(e -> textList.forEach(javafx.scene.control.TextInputControl::clear));
        Label headerLabel = new Label("Path Vehicles");
        headerLabel.setFont(new Font("Goha-tibeb Zeman", 14));
        headerLabel.setStyle("-fx-text-fill: coral;");
        headerBox.setStyle("-fx-alignment: top-left; -fx-padding: 20 0 20 -20;");
        headerBox.getChildren().add(headerLabel);
        labels.add(new Label("Bin:"));
        for (int i=0;i<labels.size(); i++){
            labels.get(i).setFont(new Font("Goha-tibeb Zeman",  14));
            labels.get(i).setStyle("-fx-pref-height:25px; -fx-alignment: center-left; ");
            TextField t = new TextField();
            t.setStyle("-fx-max-width: 65px; -fx-alignment: center");
            textList.add(i, t);
            container.add(labels.get(i),0,i+1);
            container.add(t,1,i+1);
        }
        container.add(headerBox, 0, 0, 2, 1);
        container.add(show, 0, labels.size() + 1);
        container.add(cancel, 1, labels.size() + 1);
        container.setStyle("-fx-hgap: 10px; -fx-vgap: 5px; -fx-padding: 0 0 0 40px; -fx-background-color: white;");
        show.setOnAction(e -> {
            if (!isListEmpty(textList)) {
                pathOnGraph();
                setMainPaneGraph();
            }else
                infoMessage("Not Generated","please let complete the blanks");
        });
        pathPane.getChildren().add(container);
    }

    private void initMainPane(){
        mainPane = new StackPane();
        mainPane.getChildren().addAll(generateInputPane);
    }
    /**
     * @return main panel
     */
    public Pane getRootElement(){
        return rootPane;
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

    private void infoMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        setupMessage(alert, title, content);
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
