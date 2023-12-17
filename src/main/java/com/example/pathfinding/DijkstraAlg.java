package com.example.pathfinding;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraAlg {
    private static final Duration DELAY_DURATION = Duration.millis(10);
    private final int WIDTH=600;
    private final int HEIGH=400;
    private PriorityQueue<DijkstraCell> queue;
    private Rectangle start;
    private Rectangle stop;
    private ArrayList<ArrayList<DijkstraCell>> board;
    DijkstraAlg(ArrayList<ArrayList<Rectangle>>board, Rectangle start, Rectangle stop){
        this.stop = stop;
        this.start = start;

        this.board = new ArrayList<>();

        for(ArrayList<Rectangle> row : board){
            this.board.add(new ArrayList<>());
            for(Rectangle col : row){
                if(col.getFill() == start.getFill()){
                    this.board.getLast().add(new DijkstraCell(col, null, 0.0d));
                    continue;
                }
                if(col.getFill() == Color.BLACK){
                    this.board.getLast().add(new DijkstraCell(col, null, -1.0d));
                    continue;
                }
                this.board.getLast().add(new DijkstraCell(col));
            }
        }
    }
    public ArrayList<DijkstraCell> getAdjancedCells(DijkstraCell parentCell) {
        //calcualte number of rows and cells
        int row = (int) parentCell.getRectangle().getY() / 20;
        int col = (int) parentCell.getRectangle().getX() / 20;

        ArrayList<DijkstraCell> result = new ArrayList<>();
        DijkstraCell currCell;
        double pathValue;


        if (row - 1 >= 0) {
            if (col - 1 >= 0) {
                if (board.get(row).get(col - 1).getValue() >= 0 &&
                        board.get(row - 1).get(col).getValue() >= 0) {

                    currCell = board.get(row - 1).get(col - 1);
                    pathValue = parentCell.getValue() + 1.4;

                    if (currCell.getValue() > pathValue) {
                        currCell.setValue(pathValue);
                        currCell.getRectangle().setFill(Color.LIGHTBLUE);
                        result.add(currCell);
                    }
                }
            }
            if (col + 1 < WIDTH / 20) {
                if (board.get(row).get(col + 1).getValue() >= 0 &&
                        board.get(row - 1).get(col).getValue() >= 0) {

                    currCell = board.get(row - 1).get(col + 1);
                    pathValue = parentCell.getValue() + 1.4;

                    if (currCell.getValue() > pathValue) {
                        currCell.setValue(pathValue);
                        currCell.getRectangle().setFill(Color.LIGHTBLUE);
                        result.add(currCell);
                    }
                }
            }

            currCell = board.get(row - 1).get(col);
            pathValue = parentCell.getValue() + 1.0d;
            if (currCell.getValue() > pathValue) {
                currCell.setValue(pathValue);
                currCell.getRectangle().setFill(Color.LIGHTBLUE);
                result.add(currCell);
            }
        }
        if (row + 1 < HEIGH/20) {
            if (col - 1 >= 0) {
                if(board.get(row).get(col-1).getValue() >= 0 &&
                        board.get(row+1).get(col).getValue() >= 0){

                    currCell = board.get(row + 1).get(col - 1);
                    pathValue = parentCell.getValue() + 1.4;

                    if (currCell.getValue() > pathValue) {
                        currCell.setValue(pathValue);
                        currCell.getRectangle().setFill(Color.LIGHTBLUE);
                        result.add(currCell);
                    }
                }

            }
            if (col + 1 < WIDTH / 20) {
                if(board.get(row).get(col+1).getValue() >= 0 &&
                        board.get(row+1).get(col).getValue() >= 0){

                    currCell = board.get(row + 1).get(col + 1);
                    pathValue = parentCell.getValue() + 1.4;

                    if (currCell.getValue() > pathValue) {
                        currCell.setValue(pathValue);
                        currCell.getRectangle().setFill(Color.LIGHTBLUE);
                        result.add(currCell);
                    }
                }
            }

            currCell = board.get(row + 1).get(col);
            pathValue = parentCell.getValue() + 1.0d;
            if (currCell.getValue() > pathValue) {
                currCell.setValue(pathValue);
                currCell.getRectangle().setFill(Color.LIGHTBLUE);
                result.add(currCell);
            }
        }
        if(col-1>=0){
            currCell = board.get(row).get(col-1);
            pathValue = parentCell.getValue() + 1.0d;
            if (currCell.getValue() > pathValue) {
                currCell.setValue(pathValue);
                currCell.getRectangle().setFill(Color.LIGHTBLUE);
                result.add(currCell);
            }
        }
        if(col+1<WIDTH/20){
            currCell = board.get(row).get(col+1);
            pathValue = parentCell.getValue() + 1.0d;
            if (currCell.getValue() > pathValue) {
                currCell.setValue(pathValue);
                currCell.getRectangle().setFill(Color.LIGHTBLUE);
                result.add(currCell);
            }
        }
        return result;
    }
    public void startAlg(PriorityQueue<DijkstraCell> queue){
        if(!queue.isEmpty()){
            DijkstraCell temp = queue.poll();
            if(temp.getRectangle().getX() == stop.getX() &&
                temp.getRectangle().getY() == stop.getY()){

                temp.getRectangle().setFill(Color.RED);
                temp = temp.getParent();
                while (temp.getRectangle().getFill() != start.getFill()) {
                    temp.getRectangle().setFill(Color.YELLOW);
                    //set temp to its parent
                    temp = temp.getParent();
                }
                return;
            }

            ArrayList<DijkstraCell> adjancedCells = getAdjancedCells(temp);

            for(DijkstraCell currCell : adjancedCells){
                currCell.setParent(temp);
                queue.add(currCell);
            }

            PauseTransition pause = new PauseTransition(DELAY_DURATION);
            pause.setOnFinished(event -> Platform.runLater(() -> startAlg(queue)));
            pause.play();
        }
    }
}
