import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Geethik on 24/09/2017.
 */
public class Solver {
    private static int numStacks;
    private static int numBlocks;

    public static void main(String [] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        /*Declaring the number of Stacks and Blocks through console*/
        System.out.println("Enter number of stacks:");
        StringTokenizer st = new StringTokenizer(br.readLine());
        numStacks = Integer.parseInt(st.nextToken());

        System.out.println("Enter number of Blocks:");
        st = new StringTokenizer(br.readLine());
        numBlocks = Integer.parseInt(st.nextToken());

        ProblemGenerator probGenObj = new ProblemGenerator();
        State initialState = probGenObj.randomState(numStacks,numBlocks);

        System.out.println("Initial State:");
        printStateInfo(initialState);
        State goalState = probGenObj.goalState(numStacks,numBlocks);
        System.out.println("Goal State:");
        printStateInfo(goalState);

        Node initialNode = new Node(initialState);
        Node goalNode = new Node(goalState);
        while(true){
            System.out.println();
            System.out.println("Enter Heuristic choice:");
            System.out.println("Choose for heuristics from below:");
            System.out.println("Press 0 for Blocks out of place");
            System.out.println("Press 1 for Blocks counted twice in stack one");
            System.out.println("Press any other key to exit");
            st = new StringTokenizer(br.readLine());
            int heuristic = Integer.parseInt(st.nextToken());
            switch(heuristic){
                case 0: aStarSolver(initialNode, goalNode, heuristic);
                        break;
                case 1: aStarSolver(initialNode, goalNode, heuristic);
                        break;
                default: return;
            }



        }
    }

    //Print State info
    private static void printStateInfo(State s){
        for(int i=0;i<s.getStacks().size();i++){
            System.out.print((i+1)+" | ");
            for(int j=0;j<s.getStacks().get(i).size();j++){
                char ch = (char)(s.getStacks().get(i).get(j)-1);
                ch+='A';
                System.out.print(ch+" ");
            }
            System.out.println();
        }
    }

    //A star search function
    private static void aStarSolver(Node initialNode,Node goalNode,int heurisitic){
        int depth = 0, maxQueue=0, goalTestCount=0, iter_count=0;

        Comparator<Node> fScoreComparator = new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                if(n1.getfScore()<n2.getfScore()){
                    return -1;
                }
                if(n1.getfScore()>n2.getfScore()){
                    return 1;
                }
                return 0;
            }
        };

        PriorityQueue<Node> frontier = new PriorityQueue<Node>(1,fScoreComparator);
        HashSet<State> visited = new HashSet<State>();
        initialNode.setgScore(0);
        if(heurisitic==0)
            initialNode.sethScore(h0Function(initialNode.getState()));
        else if(heurisitic==1)
            initialNode.sethScore(h1Function(initialNode.getState()));
        initialNode.setfScore();
        initialNode.setDepthNode(0);

        frontier.add(initialNode);

        while(!frontier.isEmpty()){
            iter_count++;
            if(iter_count>10000)break;
            maxQueue = Math.max(maxQueue,frontier.size());
            Node current = frontier.poll();
            goalTestCount++;
            System.out.println("iter_count: "+iter_count);

            if(goalTest(current.getState(),goalNode.getState())){
                System.out.println("Success!!");
                System.out.println("Solution:");
                depth = traceBack(current,initialNode);
                System.out.print("Depth: "+depth);
                System.out.print(" Goal_tests: "+goalTestCount);
                System.out.println(" Max_Queue_size: "+maxQueue);
                return;
            }

            visited.add(current.getState());
            ArrayList<Node> child = successor(current,visited,heurisitic);

            for(int k=0;k<child.size();k++){
                int fContains = 0;
                Iterator<Node> it = frontier.iterator();
                while(it.hasNext()){
                    Node tempChild = it.next();
                    if(compareState(tempChild.getState(), child.get(k).getState())){
                        System.out.println("I am the New node!!!");
                        if(child.get(k).getDepthNode()<tempChild.getDepthNode()){
                            frontier.remove(tempChild);
                            frontier.add(child.get(k));
                            fContains = 1;
                            break;
                        }
                    }
                }
                if(fContains==0){
                    frontier.add(child.get(k));
                }
                visited.add(child.get(k).getState());
            }
        }
    }

    //Heurisitic Functions
    private static int h0Function(State state){
        //h0 - number of Blocks out of place
        int countH=0;
        int sizeStackZero = state.getStacks().get(0).size();
        for(int i=0;i<sizeStackZero;i++){
            if((i+1)!=state.getStacks().get(0).get(i)){
                countH++;
            }
        }
        countH += numBlocks - sizeStackZero;
        return countH;
    }

    private static int h1Function(State state){
        //h1 - number of Blocks in stack one with wrong position counted twice
        //number of blocks in other stacks counted once
        int countH=0,score=0;
        int sizeStackZero =state.getStacks().get(0).size();
        for(int i=0;i<sizeStackZero;i++){
            if((i+1)==state.getStacks().get(0).get(i)){
                countH++;
            }else{
                break;
            }
        }
        score=(sizeStackZero - countH);
        score+= (state.getStacks().size())*(numBlocks - countH);
        for(int i=1;i<state.getStacks().size();i++){
            if(state.getStacks().get(i).size()==0)
                continue;
            int miniBlock=Collections.min(state.getStacks().get(i));
            int ind=state.getStacks().get(i).indexOf(miniBlock);
            countH=state.getStacks().get(i).size()-1-ind;
            score+=countH;
        }
        return score;

    }

    private static boolean compareState(State a, State b){
        if(a.getStacks().size()!=b.getStacks().size()){
            return false;
        }
        for(int i=0;i<a.getStacks().size();i++){
            if(a.getStacks().get(i).size()!= b.getStacks().get(i).size()){
                return false;
            }
            for(int j=0;j<a.getStacks().get(i).size();j++){
                if(a.getStacks().get(i).get(j) != b.getStacks().get(i).get(j)){
                    return false;
                }
            }
        }
        return true;
    }

    //Goal Test function
    private static boolean goalTest(State current, State goal){
        if(current.getStacks().size()!=goal.getStacks().size()){
            return false;
        }
        for(int i=0;i<current.getStacks().size();i++){
            if(current.getStacks().get(i).size()!= goal.getStacks().get(i).size()){
                return false;
            }
            for(int j=0;j<current.getStacks().get(i).size();j++){
                if(current.getStacks().get(i).get(j) != goal.getStacks().get(i).get(j)){
                    return false;
                }
            }
        }
        return true;
    }

    //To copy state contents
    private  static State dupState(State s){
        State duplicate = new State();
        ArrayList<ArrayList<Integer>> tempStacks = new ArrayList<ArrayList<Integer>>();
        for(int i=0;i<s.getStacks().size();i++){
            ArrayList<Integer> tempCh = new ArrayList<Integer>();
            for(int j=0;j<s.getStacks().get(i).size();j++){
                tempCh.add(s.getStacks().get(i).get(j));
            }
            tempStacks.add(tempCh);
        }
        duplicate.setStacks(tempStacks);
        return duplicate;
    }

    //Successor function
    private static ArrayList<Node> successor(Node current, HashSet<State> visited, int heuristic){
        ArrayList<Node> childNodes = new ArrayList<Node>();
        State currentState = current.getState();

        for(int i=0;i<numStacks;i++){
            int sizeOfStack = currentState.getStacks().get(i).size();
            if(sizeOfStack!=0){
                int topBlock = currentState.getStacks().get(i).get(sizeOfStack-1);
                for(int j=0;j<numStacks;j++){
                    if(i!=j){
                        State tempState = dupState(currentState);
                        tempState.getStacks().get(i).remove(sizeOfStack-1);
                        tempState.getStacks().get(j).add(topBlock);

                        int flag1=0,flag2=0;
                        Iterator<State> iterVisit = visited.iterator();
                        while(iterVisit.hasNext()){
                            if(compareState(iterVisit.next(),tempState)){
                                flag1=1;
                                break;
                            }
                        }
                        if(flag1==0){
                            Node tempChild = new Node(tempState);
                            tempChild.setgScore(current.getgScore()+1);
                            if(heuristic==0)
                                tempChild.sethScore(h0Function(tempState));
                            else if(heuristic==1)
                                tempChild.sethScore(h1Function(tempState));
                            tempChild.setfScore();
                            tempChild.setParent(current);
                            tempChild.setDepthNode(current.getDepthNode()+1);
                            childNodes.add(tempChild);
                        }
                    }
                }
            }
        }
        return childNodes;
    }

    //Traceback function
    private static int traceBack(Node goal,Node initial){
        int depth=0;
        ArrayList<State> solutionSet = new ArrayList<State>();
        while(!compareState(goal.getState(),initial.getState())){
            solutionSet.add(goal.getState());
            goal = goal.getParent();
            depth++;
        }
        solutionSet.add(initial.getState());
        for(int i=solutionSet.size()-1;i>=0;i--){
            printStateInfo(solutionSet.get(i));
            System.out.println();
        }
        return depth;
    }
}
