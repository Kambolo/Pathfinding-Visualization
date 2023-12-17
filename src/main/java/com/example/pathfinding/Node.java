package com.example.pathfinding;

import javafx.scene.shape.Rectangle;

public class Node {
    private Rectangle rectangle;
    private Node parent;

    Node(Rectangle start){
        this.rectangle = start;
        setParent(null);
    }
    Node(Rectangle rectangle, Node parent){
        this.rectangle = rectangle;
        setParent(parent);
    }
    Node(){
        this.rectangle = null;
        this.parent = null;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
