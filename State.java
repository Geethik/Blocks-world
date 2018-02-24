import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Geethik on 24/09/2017.
 */
public class State {
    ArrayList<ArrayList<Integer>> stacks;

    public State(){
        stacks = new ArrayList<ArrayList<Integer>>();
    }
    public State(ArrayList<ArrayList<Integer>> stacks){
        this.stacks = stacks;
    }
    public void setStacks(ArrayList<ArrayList<Integer>> stacks){
        this.stacks = stacks;
    }
    public ArrayList<ArrayList<Integer>> getStacks(){
        return this.stacks;
    }

}
