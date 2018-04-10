package com.jarnoluu.Tetris;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TetrisApplication extends Application {

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        
        int WIDTH = 500;
        int HEIGHT = 350;
        
        Label label = new Label("Tetris: The Game");
        label.setTranslateY(5);
        label.setBackground(Background.EMPTY);
        
        Canvas canvas = new Canvas(150, 300);
        canvas.setTranslateY(25);
        GraphicsContext g = canvas.getGraphicsContext2D();
        
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        root.getChildren().addAll(canvas, label);
        
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        StackPane.setAlignment(canvas, Pos.TOP_CENTER);
        
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        
        //root.prefWidthProperty().bind(scene.widthProperty());
        //root.prefHeightProperty().bind(scene.heightProperty());

        primaryStage.setTitle("Tetris");
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