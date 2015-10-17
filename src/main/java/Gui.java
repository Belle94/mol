import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.geometry.*;
import javafx.scene.control.*;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;

/**
 * Class of graphic interface
 * Created by bruce on 17/10/15.
 */
public class Gui {
    private BorderPane menuPane,borderPane;
    private FlowPane flowPane;
    private VBox vBox,vBox2;
    private Label labelSearch, labelFunctions, labelTitle, labelFooter;
    private TextField textField;
    private ScrollPane scrollPane;
    private double prefWidth = 780.0;
    private double prefHeight = 620.0;

    /**
     * builder, call methods for configuring the interface
     */
    public Gui(){
        settingLabels();
        settingVbox();
        settingFlow();
        settingScrollPane();
        settingRootElement();
    }

    /**
     * @return main panel
     */
    public Pane getRootElement(){
        return borderPane;
    }

    /**
     * set and configure left panel with buttons, labels and textfield
     */
    public void settingVbox(){
        vBox = new VBox();
        vBox2 = new VBox();
        vBox.setPrefSize(prefWidth * 0.26, prefHeight * 0.84);
        vBox2.setPrefSize(prefWidth, prefHeight * 0.08);
        vBox.setStyle("-fx-background-color: #272527; -fx-spacing: 10px; -fx-border-width: 1px; -fx-border-color: #505150; -fx-padding: 5px;");
        vBox2.setStyle("-fx-background-color: #272527; -fx-spacing: 10px; -fx-border-width: 1px; -fx-border-color: #505150; -fx-padding: 5px;");
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().add(labelSearch);
        settingTextField();
        vBox.getChildren().add(textField);
        vBox.getChildren().add(labelFunctions);
        vBox.getChildren().addAll(settingButton());
        vBox2.getChildren().add(new Button("Add"));
    }

    /**
     * Set the scroll pane for the FlowPane
     */
    public void settingScrollPane(){
        scrollPane = new ScrollPane();
        scrollPane.setPrefSize(prefWidth * 0.74, prefHeight * 0.84);
        scrollPane.setStyle("-fx-padding: 0; - fx-border-color: #515051;-fx-background-insets: 0; -fx-background: #000000; -fx-hbar-policy: never;");
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(flowPane);
    }

    /**
     * configure colors and graphic of search bar
     */
    public void settingTextField() {
        textField = new TextField();
        textField.setPromptText("Title or Author");
        textField.setFont(Font.font("Arial", 12));
        textField.setStyle("-fx-background-color: #212021; -fx-border-color: #515051; -fx-border-radius: 3px; -fx-border-width: 2px; -fx-text-fill: #ffffff");
    }

    /**
     * configure graphic of panel that contain table and maps
     */
    public void settingFlow() {
        flowPane = new FlowPane();
        menuPane = new BorderPane();
        flowPane.setAlignment(Pos.TOP_LEFT);
        flowPane.setNodeOrientation(NodeOrientation.INHERIT);
        flowPane.setOrientation(Orientation.HORIZONTAL);
        flowPane.setColumnHalignment(HPos.CENTER);
        flowPane.setRowValignment(VPos.CENTER);
        flowPane.setVgap(20.0);
        flowPane.setHgap(20.0);
        flowPane.setStyle("-fx-background-color: #000000; -fx-padding: 15px;");
        menuPane.setTop(vBox2);
        menuPane.setCenter(flowPane);
    }

    /**
     * configure main panel
     */
    public void settingRootElement() {
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #000000;");
        BorderPane.setAlignment(labelTitle, Pos.CENTER);
        BorderPane.setAlignment(labelFooter, Pos.CENTER);
        borderPane.setTop(labelTitle);
        borderPane.setBottom(labelFooter);
        borderPane.setLeft(vBox);
        borderPane.setCenter(menuPane);
    }

    /**
     * configure general interface's labels
     */
    public void settingLabels() {
        labelTitle = new Label("MOL");
        labelTitle.setTextFill(Color.web("#ffffff"));
        labelTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        labelTitle.setAlignment(Pos.CENTER);
        labelTitle.setPrefHeight(prefHeight * 0.05);
        labelFooter = new Label("Powered by: Belle, Tave and Bruce ");
        labelFooter.setFont(Font.font("System", 12));
        labelFooter.setTextFill(Color.web("#414041"));
        labelFooter.setPrefHeight(prefHeight * 0.03);
        labelFooter.setAlignment(Pos.CENTER);
        labelSearch = new Label("Search");
        labelSearch.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        labelSearch.setTextFill(Color.web("#ebc31c"));
        labelSearch.setTextAlignment(TextAlignment.LEFT);
        labelSearch.setAlignment(Pos.BOTTOM_LEFT);
        labelSearch.setPrefSize(prefWidth * 0.26, 30.0);
        labelFunctions = new Label("Functions");
        labelFunctions.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        labelFunctions.setTextFill(Color.web("#ebc31c"));
        labelFunctions.setTextAlignment(TextAlignment.LEFT);
        labelFunctions.setAlignment(Pos.BOTTOM_LEFT);
        labelFunctions.setPrefSize(prefWidth * 0.26, 30.0);
    }

    /**
     * configure buttons
     * @return button's vector of left panel of graphic interface
     */
    public ArrayList<Button> settingButton() {
        Button buttonAdd, buttonEdit, buttonRemove;
        buttonAdd = new Button("Add");
        buttonEdit = new Button("Edit");
        buttonRemove = new Button("Remove");
        ArrayList<Button> buttonsVbox = new ArrayList<>();
        buttonsVbox.add(buttonAdd);
        buttonsVbox.add(buttonEdit);
        buttonsVbox.add(buttonRemove);
        for (Button b : buttonsVbox) {
            b.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            b.setAlignment(Pos.TOP_LEFT);
            b.setTextAlignment(TextAlignment.LEFT);
            b.setTextFill(Color.web("#ffffff"));
            b.setStyle("-fx-background-color: #414041; -fx-border-color: #525052; -fx-border-width: 2px; -fx-border-radius: 2px;");
            b.setPrefSize(prefWidth * 0.24, 28.0);
            b.setNodeOrientation(NodeOrientation.INHERIT);
            b.setOnMouseEntered(event -> b.setStyle("-fx-background-color: #525052; -fx-border-color: #414041; -fx-border-width: 2px; -fx-border-radius: 2px;"));
            b.setOnMouseExited(event -> b.setStyle("-fx-background-color: #414041; -fx-border-color: #525052; -fx-border-width: 2px; -fx-border-radius: 2px;"));
        }
        return buttonsVbox;
    }

}
