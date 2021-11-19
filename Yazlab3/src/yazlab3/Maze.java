/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yazlab3;

/**
 *
 * @author Lenovo
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Maze {

    private int size; // need to be size % 10 = 0
    private int[][] matrix;
    private char[][] Arr;
    private Scanner scan = new Scanner(System.in);
    private int StartX,StartY,EndX,EndY;

    public Maze() {
        this.setSize(50);
        this.setStartX(0);
        this.setStartY(0);
        this.setEndX(49);
        this.setEndY(49);
        matrix = new int[size][size];
        Arr = new char[this.size][this.size];
        this.initMazeArray();
        this.initCharArray(this.getMatrix());
    }

    public Maze(int size,int StartX, int StartY, int EndX, int EndY) {
        this.setSize(size);
        this.setStartX(StartX);
        this.setStartY(StartY);
        this.setEndX(EndX);
        this.setEndY(EndY);
        matrix = new int[size][size];
        Arr = new char[this.size][this.size];
        System.out.println("##### Initialize Array #####");
        this.initMazeArray();
        this.initCharArray(this.getMatrix());
        this.writeToTxt();
    }

    private int findBarrierNum() { // %30 -1
        int mp = this.size * size;
        double temp = (mp * 30) / 100;
        int Value = (int) Math.floor(temp);
        return Value;

    }

    public void writeMaze() {
        int i, j;
        System.out.println("\n##### WRITING  MAZE #####\n");
        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                if (matrix[i][j] == -1) {
                    System.out.print("B ");
                } else if (matrix[i][j] == 1) {
                    System.out.print("S ");
                } else if (matrix[i][j] == 2) {
                    System.out.print("E ");
                } else {
                    System.out.print(matrix[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void initCharArray(int[][] matrix) {
        int i, j;
        // first assignment
        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                Arr[i][j] = '0';
            }
        }
        // adjustment
        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                if (matrix[i][j] == -1) {
                    Arr[i][j] = 'B';
                } else if (matrix[i][j] == 1) {
                    Arr[i][j] = 'S';
                } else if (matrix[i][j] == 2) {
                    Arr[i][j] = 'E';
                } else {
                    Arr[i][j] = '0';
                }
            }
        }
    }

    private void initMazeArray() {

        int i, j;
        // first assignment 
        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                matrix[i][j] = 0;
            }
        }
        // set the starting and ending point
        matrix[this.getStartX()][this.getStartY()] = 1;
        matrix[this.getEndX()][this.getEndY()] = 2;

        // adjustment
        int AllcountBarrier = 0;
        int countBarrier = 0;
        int k = 0;
        int index;
        Random rand = new Random();
        int ValueCount = this.findBarrierNum();
        int CountBarrierCond = ValueCount / this.size;
        while (AllcountBarrier != ValueCount) {
            index = rand.nextInt(this.size);

            if (matrix[index][k] != 1 && matrix[index][k] != 2
                    && matrix[index][k] != -1) {
                matrix[index][k] = -1;
                countBarrier++;
                AllcountBarrier++;
            }

            if (countBarrier == CountBarrierCond) {
                countBarrier = 0;
                k++;
            }
            if (k == this.size) {
                k = 0;
            }

        }
    }
    
    private void writeToTxt(){
        try {
      File myObj = new File("engel.txt");
      if (myObj.createNewFile()) {
        System.out.println("File created: " + myObj.getName());
      } else {
        System.out.println("File already exists.");
      }
      FileWriter Writer = new FileWriter(myObj);
      Writer.write("##### WRITING TO MAZE #####\n");
      Writer.write("[B : Barrier] - [S : Start] - [E : Ending] \n\n");
      int i,j;
      for(i=0;i<size;i++){
          for(j=0;j<size;j++){
              Writer.write(Arr[i][j] + " ");
          }
          Writer.write("\n");
      }
      Writer.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    }
    
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public char[][] getArr() {
        return Arr;
    }

    public void setArr(char[][] Arr) {
        this.Arr = Arr;
    }

    public int getStartX() {
        return StartX;
    }

    public void setStartX(int StartX) {
        this.StartX = StartX;
    }

    public int getStartY() {
        return StartY;
    }

    public void setStartY(int StartY) {
        this.StartY = StartY;
    }

    public int getEndX() {
        return EndX;
    }

    public void setEndX(int EndX) {
        this.EndX = EndX;
    }

    public int getEndY() {
        return EndY;
    }

    public void setEndY(int EndY) {
        this.EndY = EndY;
    }
    
    
}
