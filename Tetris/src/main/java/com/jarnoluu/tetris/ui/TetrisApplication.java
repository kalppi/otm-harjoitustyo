package com.jarnoluu.tetris.ui;

import com.jarnoluu.tetris.ui.graphics.FancyGraphics;
import com.jarnoluu.tetris.ui.graphics.IGraphics;
import com.jarnoluu.tetris.domain.GameManager;
import com.jarnoluu.tetris.domain.Game;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TetrisApplication extends Application {
    private final int BLOCK_SIZE = 15;
    private final int AREA_WIDTH = 10;
    private final int AREA_HEIGHT = 20;
    
    private Game game;
    private IGraphics graphics;
    private GameManager gm;
    private VBox vbox;

    @Override
    public void init() throws Exception {
        super.init();
    }
    
    private void startGame(String name) {
        this.gm.start(name);
        this.vbox.setVisible(false);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {        
        Canvas wellCanvas = new Canvas(BLOCK_SIZE * AREA_WIDTH, BLOCK_SIZE * AREA_HEIGHT);        
        Canvas statsCanvas = new Canvas(100, BLOCK_SIZE * AREA_HEIGHT);
        
        this.game = new Game(BLOCK_SIZE, AREA_WIDTH, AREA_HEIGHT);
        this.graphics = new FancyGraphics(game, wellCanvas, statsCanvas);
        this.gm = new GameManager(this.game, this.graphics);
        
        StackPane pane = new StackPane();
        HBox hbox = new HBox();
        
        hbox.getChildren().addAll(wellCanvas, statsCanvas);
        
        this.vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        
        Label label = new Label("Nimi:");
        label.setTextFill(Color.WHITE);
        
        TextField textField = new TextField();
        textField.setMaxWidth(BLOCK_SIZE * AREA_WIDTH + 100 - 50);
        textField.setTranslateY(5);
        textField.setAlignment(Pos.CENTER);
        
        textField.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER))
            {
                try {
                    this.startGame(textField.getText());
                } catch (IllegalArgumentException e) {
                    
                }
            }
        });
        
        vbox.getChildren().addAll(label, textField);
        vbox.setStyle("-fx-background-color: rgba(0, 100, 100, 0.6);");
        
        pane.getChildren().addAll(hbox, vbox);
        
        Scene scene = new Scene(pane, BLOCK_SIZE * AREA_WIDTH + 100, BLOCK_SIZE * AREA_HEIGHT);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            KeyCode code = key.getCode();
            
            switch (code) {
                case LEFT:
                    game.moveLeft();
                    break;
                case RIGHT:
                    game.moveRight();
                    break;
                case UP:
                    game.rotate();
                    break;
                case DOWN:
                    game.moveDown();
                    break;
                default:
                    break;
            }
        });
        
        primaryStage.setTitle("Tetris");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}