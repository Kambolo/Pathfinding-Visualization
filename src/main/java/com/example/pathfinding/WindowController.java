package com.example.pathfinding;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class WindowController {
    private final int WIDTH = 600;
    private final int HEIGHT = 400;
    private final int SIZE = 20;
    private Rectangle start;    //rectangle that is a beging of a path
    private Rectangle stop;     //end of a path
    @FXML
    private VBox rightPanel;
    private Pane root;
    private ArrayList<ArrayList<Rectangle>> board;

    private boolean isAlgRunning;

    public void initialize(){
        board = new ArrayList<ArrayList<Rectangle>>();
        BackgroundFill bgf = new BackgroundFill(Color.GREY, null, null);
        rightPanel.setBackground(new Background(bgf));
        isAlgRunning = false;
    }
    public void setRoot(Pane root){
        this.root = root;
    }
    public void makeBoard(){
        int rows = HEIGHT/SIZE;
        int columns = WIDTH/SIZE;

        for(int y = 0; y < rows; y++){
            board.add(new ArrayList<>(columns));
            for(int x =0; x < columns; x++){
                Rectangle rectangle = new Rectangle(SIZE, SIZE, Color.BLUE);

                //setting position
                rectangle.setX(x * SIZE);
                rectangle.setY(y * SIZE);

                //setting borders
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(2);

                //setting onMouseClicked so I can interact with rectangles
                rectangle.setOnMouseClicked(event -> {
                    //if it is a right click and stop point hasn't been added and color of a rectangle is blue
                    if (event.getButton() == MouseButton.SECONDARY && stop == null && rectangle.getFill().equals(Color.BLUE)) {
                        // Change the color of the rectangle
                        rectangle.setFill(Color.RED);
                        stop = rectangle; //set the stop rectangle
                    }
                    if (event.getButton() == MouseButton.PRIMARY && rectangle.getFill().equals(Color.BLUE)) { // Check if it's a left-click
                        if(start == null){
                            // Change the color of the rectangle
                            rectangle.setFill(Color.GREEN);
                            start = rectangle; //set the start rectangle
                        }
                        else{
                            // Change the color of the rectangle
                            rectangle.setFill(Color.BLACK);
                        }

                    }
                });
                root.getChildren().add(rectangle);
                board.get(y).add(rectangle);
            }
        }
    }
    public void reset(ActionEvent evt){
        start = null;
        stop = null;

        for(ArrayList<Rectangle> row : board){
            for(Rectangle x : row){
                x.setFill(Color.BLUE);
            }
        }
        isAlgRunning=false;
    }
    public void changeAlgWithoutReset(){
        for(ArrayList<Rectangle> row : board){
            for(Rectangle x : row){
                if(x.getFill() == Color.LIGHTBLUE || x.getFill() == Color.YELLOW){
                    x.setFill(Color.BLUE);
                }
            }
        }
    }
    public void startBfs(ActionEvent evt) throws InterruptedException {
        if(isAlgRunning) changeAlgWithoutReset();
        isAlgRunning = true;

        BfsAlg bfs= new BfsAlg(board, start, stop);

        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(start));
        bfs.startAlg(queue);
    }
    public void startDjikstra(ActionEvent evt){
        if(isAlgRunning) changeAlgWithoutReset();
        isAlgRunning = true;

        DijkstraAlg dAlg = new DijkstraAlg(board, start, stop);

        PriorityQueue<DijkstraCell> queue = new PriorityQueue<>();
        queue.add(new DijkstraCell(start, null, 0));
        dAlg.startAlg(queue);
        //dAlg.print();
    }
}
