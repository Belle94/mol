import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

/**
 * Class of graphic interface
 */
public class Gui {
    private BorderPane borderPane;
    private MenuBar menuBar;
    private HBox hBox;
    private ArrayList <NodeFX> node;
    private ArrayList <EdgeFX> edge;
    private Canvas canvas;
    private GraphicsContext gc;
    private StackPane stackPane;
    private TableView table;
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
            initStackPane();
            initRootElement();
        }catch (Exception e){
            errorMessage("Something Wrong!","Error Message: "+e.getMessage());
        }
    }

    /**
     * init nodes and edges on graph
     */
    private  void initGraph(){
        int x = 10 , y = 10;
        int n=500;
        boolean control = false;

        node = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            while(control == false){
                Random rand = new Random();
                x = 10 + rand.nextInt(770);
                y = 10 + rand.nextInt(540);
                control = true;
                for (int j = 0; j < i; j++) {
                    int oldx = node.get(j).getPx();
                    int oldy = node.get(j).getPy();
                    int rag = node.get(j).getArea_non_amm();
                    if (x - rag < oldx + rag && x + rag > oldx - rag && y - rag < oldy + rag && y + rag > oldy - rag) {
                        control = false;
                        System.out.println("collisione");
                        break;
                    }
                    else {
                        control = true;
                    }
                }
            }
            node.add(new NodeFX(x, y, Color.RED));
            control = false;
        }
        /*Random rand = new Random();
        x = rand.nextInt(770);
        y = rand.nextInt(540);
        startnode = new NodeFX(x,y);
        x = rand.nextInt(770);
        y = rand.nextInt(540);
        finalnode = new NodeFX(x,y);
        edge = new EdgeFX(startnode,finalnode,Color.BLACK,true,1.0);*/
    }

    /**
     * init Keys structure, and set all keys to false.
     */
    private void initKeyPressed(){
        keyPressed = new HashMap<>();
        keyPressed.put(KeyCombination.keyCombination("Ctrl+F"), false);
        keyPressed.put(KeyCombination.keyCombination("Alt+M"), false);
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
     * set and init the stackPane. StackPane contain ScrollPane and consequently the Table.
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
     * set and init the Canvas pane. That pane is used to draw nodes and edges.
     */
    private void initCanvasPane(){
        canvas = new Canvas(prefWidth,prefHeight-prefMenuHeight);
        gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
    }

    /**
     * draw the object
     * @param gc
     */
    private void drawShapes(GraphicsContext gc){
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        /*gc.setFill(startnode.colore);
        gc.fillOval(startnode.getPx() - startnode.getDim_rag(), startnode.getPy() - startnode.getDim_rag(), startnode.getDim_rag() * 2, startnode.getDim_rag() * 2);
        gc.setFill(finalnode.colore);
        gc.fillOval(finalnode.getPx() - finalnode.getDim_rag(), finalnode.getPy() - finalnode.getDim_rag(), finalnode.getDim_rag() * 2, finalnode.getDim_rag() * 2);
        gc.setFill(edge.getColor());
        gc.setLineWidth(edge.getWeight());
        gc.strokeLine(startnode.getPx(),startnode.getPy(),finalnode.getPx(),finalnode.getPy());*/
        for(NodeFX n:node) {
            gc.setFill(n.getColore());
            gc.fillOval(n.getPx() - n.getDim_rag(), n.getPy() - n.getDim_rag(), n.getDim_rag() * 2, n.getDim_rag() * 2);
            gc.setFill(Color.BLACK);
            //gc.strokeRect(10, 10, 780, 550);
            //gc.strokeOval(n.getPx() - n.getDist_nodi(), n.getPy() - n.getDist_nodi(), n.getDist_nodi() * 2, n.getDist_nodi() * 2);
            //gc.strokeOval(n.getPx()-n.getArea_non_amm(), n.getPy()-n.getArea_non_amm(), n.getArea_non_amm()*2, n.getArea_non_amm()*2);
        }
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

        quit.setOnAction(e-> {
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
        //MenuItem add = new MenuItem("Add");
        //MenuItem modify = new MenuItem("Modify");
        MenuItem find = new MenuItem("Find");
        MenuItem delete = new MenuItem("Delete");
        //add.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        //modify.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
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

        edit.getItems().addAll(delete, find);
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
        MenuItem Maps = new MenuItem("Maps");
        Orders.setAccelerator(KeyCombination.keyCombination("Alt+O"));
        Clients.setAccelerator(KeyCombination.keyCombination("Alt+C"));
        Goods.setAccelerator(KeyCombination.keyCombination("Alt+G"));
        Itineraries.setAccelerator(KeyCombination.keyCombination("Alt+I"));
        Vehicles.setAccelerator(KeyCombination.keyCombination("Alt+V"));
        Maps.setAccelerator(KeyCombination.keyCombination("Alt+M"));

        view.getItems().addAll(Orders, Clients, Goods, Vehicles, Itineraries, Maps);

        Maps.setOnAction(event -> {
            if(keyPressed.get(KeyCombination.keyCombination("Alt+M"))) {
                borderPane.setCenter(stackPane);
                keyPressed.put(KeyCombination.keyCombination("Alt+M"), false);
            }else {
                borderPane.getChildren().remove(stackPane);
                initGraph();
                initCanvasPane();
                borderPane.setCenter(canvas);
                keyPressed.put(KeyCombination.keyCombination("Alt+M"), true);
            }
        });
    }
    /**
     * Set and initialize table
     */
    private void initTable(){
        double offset = 0.003;
        table = new TableView();
        table.setEditable(false);
        table.setTableMenuButtonVisible(true);
        table.setMinSize(prefWidth-(prefWidth*offset),prefHeight-prefMenuHeight-(prefHeight*offset));
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
