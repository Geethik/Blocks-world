/**
 * Created by Geethik on 24/09/2017.
 */
import java.io.*;
import java.util.*;

public class Node {

    Node parent;
    private State state;
    private int fScore;
    private int gScore;
    private int hScore;
    private int depthNode;

    public Node(){
    }
    public Node(State state){
        this.state = state;
    }
    public Node(State state, int fScore){
        this.state = state;
        this.fScore = fScore;
    }

    public void setfScore(){
        this.fScore = this.gScore+this.hScore;
    }
    public void setgScore(int gScore){
        this.gScore = gScore;
    }
    public void sethScore(int hScore){
        this.hScore = hScore;
    }
    public void setParent(Node parent){
        this.parent = parent;
    }
    public void setDepthNode(int depthNode){
        this.depthNode = depthNode;
    }

    public int getfScore(){
        return this.fScore;
    }
    public int getgScore(){
        return this.gScore;
    }
    public int gethScore(){
        return this.hScore;
    }
    public State getState(){
        return this.state;
    }
    public Node getParent(){
        return this.parent;
    }
    public int getDepthNode(){
        return this.depthNode;
    }

}
