package com.example.pathfinding;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BfsAlg {
    private static final Duration DELAY_DURATION = Duration.millis(10);
    private Timeline timeline;
    private Rectangle start;
    private Rectangle stop;
    private ArrayList<ArrayList<Rectangle>> board;
    final int WIDTH=600;
    final int HEIGHT=400;

    BfsAlg(ArrayList<ArrayList<Rectangle>> board, Rectangle start, Rectangle stop){
        this.board = board;
        this.start = start;
        this.stop = stop;
    }
    private ArrayList<Rectangle> getAdjancedCells(Rectangle cell) {
        //calcualte number of rows and cells
        int row = (int)cell.getY()/20;
        int col = (int)cell.getX()/20;

        ArrayList<Rectangle> result = new ArrayList<>();

        /*
        check if neighbours up, down, left, right are color blue(empty) or
         red(end) if so add them to the neighbours array and change its color
        */
        if(col-1 >= 0 && checkIfVisited(row, col-1)){
            result.add(board.get(row).get(col-1));
        }
        if(col+1 < WIDTH/20 && checkIfVisited(row, col+1)){
            result.add(board.get(row).get(col+1));
        }
        if(row-1 >= 0 && checkIfVisited(row-1, col)){
            result.add(board.get(row-1).get(col));
        }
        if(row+1 < HEIGHT/20 && checkIfVisited(row+1, col)){
            result.add(board.get(row+1).get(col));
        }
        return result;
    }
    private boolean checkIfVisited(int row, int col){
        if(board.get(row).get(col).getFill() == Color.BLUE){
            board.get(row).get(col).setFill(Color.LIGHTBLUE);
            return true;
        }
        else if(board.get(row).get(col).getFill() == Color.RED){ return true;}
        return false;
    }
    public void startAlg(Queue<Node> queue) {
        if(!queue.isEmpty()) {
            Node temp = queue.remove();  //remove first item in queue and returns it to temp

            //check if our cell is end cell by checking its colors
            if (temp.getRectangle().getFill() == stop.getFill()) {

                temp = temp.getParent();
                //while our cell isnt a start cell
                while (temp.getRectangle().getFill() != start.getFill()) {
                    //make a path yellow
                    temp.getRectangle().setFill(Color.YELLOW);

                    //set temp to its parent
                    temp = temp.getParent();
                }
                return;
            }

            ArrayList<Rectangle> adjacenedCells = getAdjancedCells(temp.getRectangle());

            //made Nodes out of adjancedCells and set theirs parent to temp node
            for (int i = 0; i < adjacenedCells.size(); i++) {
                Node node = new Node(adjacenedCells.get(i), temp);
                node.setParent(temp);
                queue.add(node);
            }
            PauseTransition pause = new PauseTransition(DELAY_DURATION);
            pause.setOnFinished(event -> Platform.runLater(() -> startAlg(queue)));
            pause.play();
        }
    }
}
