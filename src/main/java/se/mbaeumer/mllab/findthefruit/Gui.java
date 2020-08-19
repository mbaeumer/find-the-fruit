package se.mbaeumer.mllab.findthefruit;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
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
import java.util.ArrayList;
import java.util.List;

public class Gui extends Application {

    private static int XSTART = 50;
    private static int YSTART = 50;
    private static String DEFAULT_FRUIT_POS = "2,3";
    private static String DEFAULT_BOARD_LENGTH = "4";
    private static String DEFAULT_PLAYER_POS = "0,0";

    private BorderPane borderPane;
    private Group root = new Group();
    private Scene scene;
    private FlowPane flowConfig;
    private Label lblBoardLength;
    private TextField tfBoardLength;
    private Label lblPlayerPos;
    private TextField tfPlayerPos;
    private Label lblFruitPos;
    private TextField tfFruitPos;
    private Label lblIterations;
    private ComboBox<String> cmbIterations;
    private Button btnRun;
    private Label lblStatus;
    private FlowPane flowRight;
    private TableView tvSolution;
    private TableView tvActions;
    private ComboBox cmbSolutions;
    private Label lblSolutionStatus;
    private Game game;
    private ConfigValidationService configValidationService = new ConfigValidationService();

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
        this.flowConfig.setPadding(new Insets(5,5,5,5));
        this.flowConfig.setHgap(5);

        this.borderPane.setTop(this.flowConfig);
    }

    private void createConfigComponents(){
        this.lblBoardLength = new Label("Length: ");
        this.flowConfig.getChildren().add(this.lblBoardLength);
        this.tfBoardLength = new TextField();
        this.tfBoardLength.setText(DEFAULT_BOARD_LENGTH);
        this.tfBoardLength.textProperty().addListener((observable, oldValue, newValue) -> {
            hideErrorMessage();
            try {
                int length = configValidationService.validateBoardLength(newValue);
                initBoard(length);
            }catch (NumberFormatException ex){
                showErrorMessage(ex.getMessage());
                this.root.getChildren().clear();
            }catch (IllegalArgumentException ex){
                showErrorMessage(ex.getMessage());
                this.root.getChildren().clear();
            }
        });
        this.flowConfig.getChildren().add(this.tfBoardLength);

        this.lblPlayerPos = new Label("Player pos:");
        this.flowConfig.getChildren().add(this.lblPlayerPos);
        this.tfPlayerPos = new TextField();
        this.tfPlayerPos.setText(DEFAULT_PLAYER_POS);
        this.tfPlayerPos.textProperty().addListener(this::handleInput);
        this.flowConfig.getChildren().add(this.tfPlayerPos);

        this.lblFruitPos = new Label("Fruit pos:");
        this.flowConfig.getChildren().add(this.lblFruitPos);
        this.tfFruitPos = new TextField();
        this.tfFruitPos.setText(DEFAULT_FRUIT_POS);
        this.tfFruitPos.textProperty().addListener(this::handleInput);
        this.flowConfig.getChildren().add(this.tfFruitPos);

        List<String> list = List.of("10", "50", "100", "1000");
        this.lblIterations = new Label("Iterations:");
        this.flowConfig.getChildren().add(this.lblIterations);
        this.cmbIterations = new ComboBox<>();
        this.cmbIterations.setItems(FXCollections.observableArrayList(list));
        this.cmbIterations.getSelectionModel().selectFirst();
        this.flowConfig.getChildren().add(this.cmbIterations);

        this.btnRun = new Button("Run");
        this.btnRun.setOnAction(actionEvent -> {
            try {
                game = new Game(configValidationService.validateBoardLength(tfBoardLength.getText()),
                        configValidationService.validateFruitPosition(tfPlayerPos.getText(), Integer.parseInt(tfBoardLength.getText())),
                        configValidationService.validateFruitPosition(tfFruitPos.getText(), Integer.parseInt(tfBoardLength.getText())),
                        Integer.parseInt(cmbIterations.getValue()));
                game.initGame();
                try {
                    game.start();
                    populateActionTableView();
                    populateSolutionsCombobox();
                    populateSolutionTableView();
                } catch (FileNotFoundException ex) {

                }
            }catch (NumberFormatException ex){
                showErrorMessage(ex.getMessage());
            }catch (IllegalArgumentException ex){
                showErrorMessage(ex.getMessage());
            }
        });

        this.flowConfig.getChildren().add(btnRun);
    }

    private void handleInput(ObservableValue<? extends String> observableValue, String s, String t1){
        hideErrorMessage();
        try {
            configValidationService.validateFruitPosition(t1, Integer.parseInt(tfBoardLength.getText()));
        }catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
            showErrorMessage(ex.getMessage());
        }
    }

    private void showErrorMessage(final String message){
        this.lblStatus = new Label(message);
        this.flowConfig.getChildren().add(this.lblStatus);
    }

    private void hideErrorMessage(){
        if (this.flowConfig.getChildren().contains(this.lblStatus)) {
            this.flowConfig.getChildren().remove(this.lblStatus);
        }
    }

    private void initBoard(final int length){
        this.root.getChildren().clear();
        DropShadow dropShadow = new DropShadow(5, Color.GRAY);
        //i = y
        //j = x
        for (int i = 0; i < length ; i++){
            for (int j = 0; j < length; j++){
                Label label = new Label("X");
                label.setLayoutX(XSTART + 25*(j+1));
                label.setLayoutY(YSTART + 25*(i+1));
                label.setEffect(dropShadow);
                root.getChildren().add(label);
            }
        }
    }

    private void createRightFlowPane(){
        this.flowRight = new FlowPane();
        this.flowRight.setOrientation(Orientation.HORIZONTAL);

        this.borderPane.setCenter(this.flowRight);
    }

    private void populateActionTableView(){
        if (this.tvActions == null){
            createActionTableView();
        }

        Player player = game.getPlayer();
        this.tvActions.setItems(FXCollections.observableList(player.getLessons()));
    }

    private void createActionTableView(){
        this.tvActions = new TableView();
        this.tvActions.setEditable(false);

        TableColumn oldXCol = new TableColumn("Old X");
        oldXCol.setCellValueFactory(new PropertyValueFactory("oldX"));

        TableColumn oldYCol = new TableColumn("Old Y");
        oldYCol.setCellValueFactory(new PropertyValueFactory("oldY"));

        TableColumn newXCol = new TableColumn("New X");
        newXCol.setCellValueFactory(new PropertyValueFactory("newX"));

        TableColumn newYCol = new TableColumn("New Y");
        newYCol.setCellValueFactory(new PropertyValueFactory("newY"));

        TableColumn xDeltaCol = new TableColumn("X delta");
        xDeltaCol.setCellValueFactory(new PropertyValueFactory("xDelta"));

        TableColumn yDeltaCol = new TableColumn("Y delta");
        yDeltaCol.setCellValueFactory(new PropertyValueFactory("yDelta"));

        TableColumn rewardCol = new TableColumn("Reward");
        rewardCol.setCellValueFactory(new PropertyValueFactory("reward"));

        this.tvActions.getColumns().addAll(oldXCol, oldYCol, xDeltaCol, yDeltaCol, newXCol, newYCol, rewardCol);

        this.flowRight.getChildren().add(this.tvActions);
        this.tvActions.prefWidthProperty().bind(this.flowRight.widthProperty());
    }

    private void populateSolutionsCombobox(){
        if (this.cmbSolutions == null){
            this.cmbSolutions = new ComboBox();
            this.flowRight.getChildren().add(this.cmbSolutions);
            this.cmbSolutions.prefWidthProperty().bind(this.flowRight.widthProperty());
            this.cmbSolutions.valueProperty().addListener((observable, oldValue, newValue) -> {
                this.tvSolution.getItems().clear();
                populateSolutionTableView();

            });
        }

        this.cmbSolutions.getItems().clear();

        GameStateEvaluator gameStateEvaluator = game.getGameStateEvaluator();
        if (gameStateEvaluator.getSolutions().size() > 0){
            this.cmbSolutions.setDisable(false);
            List<String> solutionStrings = new ArrayList<>();
            for (int i = 0; i < gameStateEvaluator.getSolutions().size(); i++){
                solutionStrings.add("Solution " + i);
            }
            System.out.println("Solutions: " + gameStateEvaluator.getSolutions().size());
            this.cmbSolutions.setItems(FXCollections.observableArrayList(solutionStrings));
        }else{
            this.cmbSolutions.setDisable(true);
        }
    }

    private void populateSolutionTableView(){
        if (tvSolution == null){
            this.createSolutionTableView();
        }

        GameStateEvaluator gameStateEvaluator = game.getGameStateEvaluator();

        if (gameStateEvaluator.getSolutions().size() > 0){
            this.tvSolution.setItems(FXCollections
                    .observableArrayList(gameStateEvaluator.getSolutions().get(cmbSolutions.getSelectionModel().getSelectedIndex()).getPositions()));
        }

    }
    private void createSolutionTableView(){
        this.tvSolution = new TableView();
        this.tvSolution.setEditable(false);

        TableColumn xCol = new TableColumn("X");
        xCol.setCellValueFactory(new PropertyValueFactory("x"));

        TableColumn yCol = new TableColumn("Y");
        yCol.setCellValueFactory(new PropertyValueFactory("y"));

        this.tvSolution.getColumns().addAll(xCol, yCol);
        this.tvSolution.setPrefHeight(200);

        this.flowRight.getChildren().add(this.tvSolution);
    }

    private void createSolutionStatus(){
        if (this.lblSolutionStatus == null) {
            this.lblSolutionStatus = new Label("Solution");
            this.flowRight.getChildren().add(this.lblSolutionStatus);
            this.lblSolutionStatus.prefWidthProperty().bind(this.flowRight.widthProperty());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}