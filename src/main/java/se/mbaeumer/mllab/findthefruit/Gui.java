package se.mbaeumer.mllab.findthefruit;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Gui extends Application {

    private static int LENGTH = 10;
    private static int XSTART = 50;
    private static int YSTART = 50;

    private BorderPane borderPane;
    private Group root = new Group();
    private Scene scene;
    private FlowPane flowConfig;
    private Label lblBoardLength;
    private TextField tfBoardLength;
    private Button btnRun;
    private FlowPane flowRight;
    private TableView tvSolution;
    private Game game;

    @Override
    public void start(Stage stage) throws Exception {
        borderPane = new BorderPane();
        this.scene = new Scene(this.borderPane, 1100, 700, Color.LIGHTGRAY);
        stage.setTitle("FInd the fruit");

        this.createConfigFowPane();
        this.createConfigComponents();
        this.borderPane.setLeft(this.root);
        this.createRightFlowPane();
        stage.setScene(this.scene);
        stage.show();

    }

    private void createConfigFowPane(){
        this.flowConfig = new FlowPane();

        this.borderPane.setTop(this.flowConfig);
    }

    private void createConfigComponents(){
        this.lblBoardLength = new Label("Length: ");
        this.flowConfig.getChildren().add(this.lblBoardLength);
        this.tfBoardLength = new TextField();
        this.tfBoardLength.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int length = Integer.parseInt(newValue);
                if (length < 4 || length > 15){

                }else{
                    initBoard(length);
                }
            }catch (NumberFormatException ex){
                this.root.getChildren().clear();

            }
        });
        this.flowConfig.getChildren().add(this.tfBoardLength);

        this.btnRun = new Button("Run");

        this.btnRun.setOnAction(actionEvent -> {
            game = new Game(4);
            game.initGame();
            try {
                game.start();
                createSolutionTableView();
            }catch (FileNotFoundException ex){

            }
        });

        this.flowConfig.getChildren().add(btnRun);
    }

    private void initBoard(final int length){
        this.root.getChildren().clear();
        DropShadow dropShadow = new DropShadow(5, Color.GRAY);
        //i = y
        //j = x
        for (int i = 0; i < length ; i++){
            for (int j = 0; j < length; j++){
                Label label = new Label("X");
                label.setLayoutX(XSTART *(j+1));
                label.setLayoutY(YSTART *(i+1));
                label.setEffect(dropShadow);
                root.getChildren().add(label);
            }
        }
    }

    private void createRightFlowPane(){
        this.flowRight = new FlowPane();
        this.flowRight.setOrientation(Orientation.VERTICAL);

        this.borderPane.setCenter(this.flowRight);

    }

    private void createSolutionTableView(){
        this.tvSolution = new TableView();
        this.tvSolution.setEditable(false);

        Player player = game.getPlayer();

        if (player.getSolutions().size() > 0){
            this.tvSolution.setItems(FXCollections.observableList(player.getSolutions().get(0).getPositions()));
        }

        TableColumn xCol = new TableColumn("X");
        xCol.setCellValueFactory(new PropertyValueFactory("x"));

        TableColumn yCol = new TableColumn("Y");
        yCol.setCellValueFactory(new PropertyValueFactory("y"));

        this.tvSolution.getColumns().addAll(xCol, yCol);

        this.flowRight.getChildren().add(this.tvSolution);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
