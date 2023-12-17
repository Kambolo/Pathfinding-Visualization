package com.example.pathfinding;

import javafx.scene.shape.Rectangle;

public class DijkstraCell implements Comparable<DijkstraCell>{
    private Rectangle rectangle;
    private DijkstraCell parent;
    private double value;

    DijkstraCell(){
        rectangle = null;
        parent = null;
        value = Double.MAX_VALUE;
    }

    DijkstraCell(Rectangle rectangle){
        this.rectangle = rectangle;
        parent = null;
        value = Double.MAX_VALUE;
    }
    DijkstraCell(Rectangle rectangle, DijkstraCell parent){
        this.rectangle = rectangle;
        this.parent = parent;
        this.value = Double.MAX_VALUE;
    }
    DijkstraCell(Rectangle rectangle, DijkstraCell parent, double value){
        this.rectangle = rectangle;
        this.parent = parent;
        this.value = value;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public DijkstraCell getParent() {
        return parent;
    }

    public void setParent(DijkstraCell parent) {
        this.parent = parent;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int compareTo(DijkstraCell o) {
        return Double.compare(this.value, o.value);
    }
}
