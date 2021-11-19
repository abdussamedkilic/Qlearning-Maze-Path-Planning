/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yazlab3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lenovo
 */
import java.util.ArrayList;
import java.util.Random;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class QLearning {

    private final double alpha = 0.1; // Learning rate
    private final double gamma = 0.8; // Eagerness - 0 looks in the near future, 1 looks in the distant future

    private int mazeWidth;//sizeRow ile eşit olmalı
    private int mazeHeight;//sizeColumn ile eşit olmalı
    private int statesCount;

    private final int reward = 5;
    private final int penalty = -5;

    private char[][] maze;  // Maze read from file
    private int[][] R;       // Reward lookup
    private double[][] Q;    // Q learning

    private ArrayList<Double> episodeViaCost = new ArrayList<>();
    private ArrayList<Integer> episodeViaStep = new ArrayList<>();

    public QLearning(char[][] arr) {
        this.maze = arr;
        this.mazeHeight = this.mazeWidth = arr[0].length;
        this.statesCount = this.mazeHeight * this.mazeWidth;
        R = new int[statesCount][statesCount];
        Q = new double[statesCount][statesCount];

    }

    public void init() {

        int i = 0;
        int j = 0;

        // We will navigate through the reward matrix R using k index
        for (int k = 0; k < statesCount; k++) {

            // We will navigate with i and j through the maze, so we need
            // to translate k into i and j
            i = k / mazeWidth;
            j = k - i * mazeWidth;

            // Fill in the reward matrix with -1
            for (int s = 0; s < statesCount; s++) {
                R[k][s] = -1;
            }

            // If not in final state or a wall try moving in all directions in the maze
            if (maze[i][j] != 'E') {

                // Try to move left in the maze
                int goLeft = j - 1;
                if (goLeft >= 0) {
                    int target = i * mazeWidth + goLeft;
                    if (maze[i][goLeft] == '0' || maze[i][goLeft] == 'S') {
                        R[k][target] = 3;
                    } else if (maze[i][goLeft] == 'E') {
                        R[k][target] = reward;
                    } else {
                        R[k][target] = penalty;
                    }
                }

                // Try to move right in the maze
                int goRight = j + 1;
                if (goRight < mazeWidth) {
                    int target = i * mazeWidth + goRight;
                    if (maze[i][goRight] == '0' || maze[i][goRight] == 'S') {
                        R[k][target] = 3;
                    } else if (maze[i][goRight] == 'E') {
                        R[k][target] = reward;
                    } else {
                        R[k][target] = penalty;
                    }
                }

                // Try to move up in the maze
                int goUp = i - 1;
                if (goUp >= 0) {
                    int target = goUp * mazeWidth + j;
                    if (maze[goUp][j] == '0' || maze[goUp][j] == 'S') {
                        R[k][target] = 3;
                    } else if (maze[goUp][j] == 'E') {
                        R[k][target] = reward;
                    } else {
                        R[k][target] = penalty;
                    }
                }

                // Try to move down in the maze
                int goDown = i + 1;
                if (goDown < mazeHeight) {
                    int target = goDown * mazeWidth + j;
                    if (maze[goDown][j] == '0' || maze[goDown][j] == 'S') {
                        R[k][target] = 3;
                    } else if (maze[goDown][j] == 'E') {
                        R[k][target] = reward;
                    } else {
                        R[k][target] = penalty;
                    }
                }

                // Try to move left upper cross in the maze
                int leftUpCrossRow = i - 1;
                int leftUpCrossCol = j - 1;
                if (leftUpCrossRow >= 0 && leftUpCrossCol >= 0) {
                    int target = leftUpCrossRow * mazeWidth + leftUpCrossCol;
                    if (maze[leftUpCrossRow][leftUpCrossCol] == '0'
                            || maze[leftUpCrossRow][leftUpCrossCol] == 'S') {
                        R[k][target] = 3;
                    } else if (maze[leftUpCrossRow][leftUpCrossCol] == 'E') {
                        R[k][target] = reward;
                    } else {
                        R[k][target] = penalty;
                    }
                }

                // Try to move right upper cross in the maze
                int rightUpCrossRow = i - 1;
                int rightUpCrossCol = j + 1;
                if (rightUpCrossRow >= 0 && rightUpCrossCol < mazeWidth) {
                    int target = rightUpCrossRow * mazeWidth + rightUpCrossCol;
                    if (maze[rightUpCrossRow][rightUpCrossCol] == '0'
                            || maze[rightUpCrossRow][rightUpCrossCol] == 'S') {
                        R[k][target] = 3;
                    } else if (maze[rightUpCrossRow][rightUpCrossCol] == 'E') {
                        R[k][target] = reward;
                    } else {
                        R[k][target] = penalty;
                    }
                }

                // Try to move left down cross in the maze
                int leftDownCrossRow = i + 1;
                int leftDownCrossCol = j - 1;
                if (leftDownCrossRow < mazeWidth && leftDownCrossCol >= 0) {
                    int target = leftDownCrossRow * mazeWidth + leftDownCrossCol;
                    if (maze[leftDownCrossRow][leftDownCrossCol] == '0'
                            || maze[leftDownCrossRow][leftDownCrossCol] == 'S') {
                        R[k][target] = 3;
                    } else if (maze[leftDownCrossRow][leftDownCrossCol] == 'E') {
                        R[k][target] = reward;
                    } else {
                        R[k][target] = penalty;
                    }
                }

                // Try to move right down cross in the maze
                int rightDownCrossRow = i + 1;
                int rightDownCrossCol = j + 1;
                if (rightDownCrossRow < mazeWidth && rightDownCrossCol < mazeWidth) {
                    int target = rightDownCrossRow * mazeWidth + rightDownCrossCol;
                    if (maze[rightDownCrossRow][rightDownCrossCol] == '0'
                            || maze[rightDownCrossRow][rightDownCrossCol] == 'S') {
                        R[k][target] = 3;
                    } else if (maze[rightDownCrossRow][rightDownCrossCol] == 'E') {
                        R[k][target] = reward;
                    } else {
                        R[k][target] = penalty;
                    }
                }
            }
        }
        initializeQ();
        printR(R);
    }

    //Set Q values to R values
    void initializeQ() {
        for (int i = 0; i < statesCount; i++) {
            for (int j = 0; j < statesCount; j++) {
                Q[i][j] = (double) R[i][j];
            }
        }
    }

    // Used for debug
    void printR(int[][] matrix) {
        System.out.printf("%25s", "States: ");
        for (int i = 0; i <= statesCount; i++) {
            System.out.printf("%4s", i);
        }
        System.out.println();

        for (int i = 0; i < statesCount; i++) {
            System.out.print("Possible states from " + i + " :[");
            for (int j = 0; j < statesCount; j++) {
                System.out.printf("%4s", matrix[i][j]);
            }
            System.out.println("]");
        }
    }

    void calculateQ() {
        Random rand = new Random();

        for (int i = 0; i < 1000; i++) { // Train cycles
            // Select random initial state
            int crtState = rand.nextInt(statesCount);
            double totalCost = 0;
            int stepNum = 0;
            while (!isFinalState(crtState)) {
                int[] actionsFromCurrentState = possibleActionsFromState(crtState);

                // Pick a random action from the ones possible
                int index = rand.nextInt(actionsFromCurrentState.length);
                int nextState = actionsFromCurrentState[index];

                // Q(state,action)= Q(state,action) + alpha * (R(state,action) + gamma * Max(next state, all actions) - Q(state,action))
                double q = Q[crtState][nextState];
                double maxQ = maxQ(nextState);
                int r = R[crtState][nextState];

                double value = (r + gamma * maxQ); //  q + alpha * (r + gamma * maxQ - q)
                totalCost += value;
                stepNum++;
                Q[crtState][nextState] = value;

                crtState = nextState;
            }
            episodeViaCost.add(totalCost);
            episodeViaStep.add(stepNum);
        }
    }

    boolean isFinalState(int state) {
        int i = state / mazeWidth;
        int j = state - i * mazeWidth;

        return maze[i][j] == 'E';
    }

    int[] possibleActionsFromState(int state) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < statesCount; i++) {
            if (R[state][i] != -1) {
                result.add(i);
            }
        }

        return result.stream().mapToInt(i -> i).toArray();
    }

    int[] possibleActionsForShortestPath(int state) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < statesCount; i++) {
            if (R[state][i] != -1 || R[state][i] != -5) { // engelleri almıyor
                result.add(i);
            }
        }

        return result.stream().mapToInt(i -> i).toArray();
    }

    double maxQ(int nextState) {
        int[] actionsFromState = possibleActionsFromState(nextState);
        //the learning rate and eagerness will keep the W value above the lowest reward
        double maxValue = -10;
        for (int nextAction : actionsFromState) {
            double value = Q[nextState][nextAction];

            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }

    void printPolicy() {
        System.out.println("\nPrint policy");
        for (int i = 0; i < statesCount; i++) {
            System.out.println("From state " + i + " goto state " + getPolicyFromState(i));
        }
    }

    int getPolicyFromState(int state) {
        int[] actionsFromState = possibleActionsFromState(state);

        double maxValue = Double.MIN_VALUE;
        int policyGotoState = state;

        // Pick to move to the state that has the maximum Q value
        for (int nextState : actionsFromState) {
            double value = Q[state][nextState];

            if (value >= maxValue) {
                maxValue = value;
                policyGotoState = nextState;
            }
        }
        return policyGotoState;
    }

    void printQ() {
        System.out.println("Q matrix");
        for (int i = 0; i < Q.length; i++) {
            System.out.print("From state " + i + ":  ");
            for (int j = 0; j < Q[i].length; j++) {
                System.out.printf("%6.2f ", (Q[i][j]));
            }
            System.out.println();
        }
    }

    public void drawCostChart() {
        int i;
        double[] episode = new double[1000];
        for (i = 0; i < 1000; i++) {
            episode[i] = i;
        }
        double[] cost = new double[episodeViaCost.size()];
        for (i = 0; i < episodeViaCost.size(); i++) {
            cost[i] = episodeViaCost.get(i);
        }
        XYChart chart;
        chart = QuickChart.getChart("Episode via Cost", "Episode", "Cost", "C(e)", episode, cost);
        new SwingWrapper(chart).displayChart();
    }

    public void drawStepChart() {
        int i;
        double[] episode = new double[1000];
        for (i = 0; i < 1000; i++) {
            episode[i] = i;
        }
        double[] step = new double[episodeViaStep.size()];
        for (i = 0; i < episodeViaStep.size(); i++) {
            step[i] = episodeViaStep.get(i);
        }

        XYChart chart;
        chart = QuickChart.getChart("Episode via Step", "Episode", "Step", "S(e)", episode, step);
        SwingWrapper sw = new SwingWrapper(chart);
        sw.displayChart();
    }

    int policy(int state, int[] actionsFromState) {
        double maxValue = Double.MIN_VALUE;
        int policyGotoState = state;

        // Pick to move to the state that has the maximum Q value
        for (int nextState : actionsFromState) {
            double value = Q[state][nextState];

            if (value >= maxValue) {
                maxValue = value;
                policyGotoState = nextState;
            }
        }
        return policyGotoState;
    }

    boolean checkPath(ArrayList<Integer> path, int check) {
        if (path.size() == 0) {
            return true;
        }
        int i;
        for (i = 0; i < path.size(); i++) {
            if (path.get(i) == check) {
                return false;
            }
        }
        return true;
    }

    int [] removeElementFromActions(int[] actionsFromState, int check) {
        int i;
        ArrayList<Integer> newAction = new ArrayList<>();
        for (i = 0; i < actionsFromState.length; i++) {
            if (actionsFromState[i] == check) {
                actionsFromState[i] = -1; // -1 atamak kötü, hata vermesine sebep açıyor
            }
        }
        
        for(i=0;i<actionsFromState.length;i++){
            if(actionsFromState[i] != -1){
                newAction.add(i);
            }
        }
        
        return newAction.stream().mapToInt(j -> j).toArray();
    }

    public char[][] findShortestPath(int startState, int endState) {
        ArrayList<Integer> statePath = new ArrayList<>();
        ArrayList<int []> allActions = new ArrayList<>();

        int state;
        int goalState = endState;
        state = startState;
        statePath.add(state);
        int i, count = -1;
        for(i=0;i<statesCount;i++){
            int [] oneActions = possibleActionsForShortestPath(i);
            allActions.add(oneActions);
        }
        System.out.println("Path");
        while (count != 10000000) {
            if (((state + 1) == goalState) || ((state -1) == goalState) ||
                    ((state + mazeWidth -1) == goalState) || ((state + mazeWidth) == goalState) ||
                    ((state + mazeWidth +1) == goalState) || ((state - mazeWidth +1) == goalState) ||
                    ((state - mazeWidth) == goalState) || ((state - mazeWidth -1) == goalState) || (state ==goalState)) {
                break;
            }
            int[] actionsFromState = allActions.get(state);
            int check = policy(state, actionsFromState);
            if (checkPath(statePath, check)) {
                state = check;
                statePath.add(state);
            } 
            if(actionsFromState.length == 0){
                int[] temp = removeElementFromActions(actionsFromState, check);
                allActions.remove(state);
                allActions.add(state, temp);
                
                statePath.clear();
                state = startState;
                statePath.add(state);
            }
        }
        statePath.add(goalState);
        for (i = 0; i < statePath.size(); i++) {
            System.out.println("path : " + statePath.get(i));
        }

        int j;
        char[][] newArr = new char[mazeHeight][mazeWidth];
        for (i = 0; i < mazeHeight; i++) {
            for (j = 0; j < mazeHeight; j++) {
                newArr[i][j] = this.maze[i][j];
            }
        }

        for (i = 0; i < statePath.size(); i++) {
            int x = statePath.get(i) / mazeHeight;
            int y = statePath.get(i) % mazeHeight;
            newArr[x][y] = 'P';
        }

        return newArr;
    }

    public ArrayList<Double> getEpisodeViaCost() {
        return episodeViaCost;
    }

    public void setEpisodeViaCost(ArrayList<Double> episodeViaCost) {
        this.episodeViaCost = episodeViaCost;
    }

    public ArrayList<Integer> getEpisodeViaStep() {
        return episodeViaStep;
    }

    public void setEpisodeViaStep(ArrayList<Integer> episodeViaStep) {
        this.episodeViaStep = episodeViaStep;
    }

}
