import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Geethik on 24/09/2017.
 */

public class ProblemGenerator {
    public ProblemGenerator(){
    }

    public State randomState(int numStacks,int numBlocks){
        ArrayList<ArrayList<Integer>> randomStack = new ArrayList<ArrayList<Integer>>();
        int countBlock=0;
        for(int i=0;i<numStacks;i++){
            randomStack.add(new ArrayList<Integer>());
        }
        Random randGen = new Random();
        for(int j=1;j<=numBlocks;j++){
            randomStack.get(randGen.nextInt(numStacks)).add(j);
        }
        return new State(randomStack);

    }
    public State goalState(int numStacks,int numBlocks){
        ArrayList<ArrayList<Integer>> goalStack = new ArrayList<ArrayList<Integer>>();
        for(int i=0;i<numStacks;i++){
            ArrayList<Integer> tempGoal = new ArrayList<Integer>();
            if(i==0){
                for(int j=0;j<numBlocks;j++){
                    tempGoal.add(j+1);
                }
            }
            goalStack.add(tempGoal);
        }
        return new State(goalStack);
    }

    public State customState(int numStacks, int numBlocks){
        ArrayList<ArrayList<Integer>> customStack = new ArrayList<ArrayList<Integer>>();
        for(int i=0;i<numStacks;i++){
            ArrayList<Integer> tempCustom = new ArrayList<Integer>();
            if(i==0){
                tempCustom.add(4);
                tempCustom.add(5);
            }else if(i==1){
                tempCustom.add(1);
                tempCustom.add(3);
                tempCustom.add(6);
                tempCustom.add(7);
            }else if(i==2){
                tempCustom.add(2);
            }
            customStack.add(tempCustom);
        }
        return new State(customStack);
    }
}
