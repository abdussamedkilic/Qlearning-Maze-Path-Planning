/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yazlab3;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Lenovo
 */
public class MazeView {

    SquareButtons[][] maze; // Kare'nin kenarı (e*e boyutunda olacak) buraya gelecek
    JFrame frame;
    private char[][] Arr;

    public MazeView() {
        maze = new SquareButtons[5][5];
        frame = new JFrame("Maze");
        frame.setSize(1000, 800); // M * N  buraya gelecek, 
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 5));//board boyutu ile aynı olmalı.Grid--->Hucre 
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                SquareButtons b = new SquareButtons(i, j);
                frame.add(b);
                maze[i][j] = b;
            }
        }
        this.run();
    }

    public MazeView(char[][] Arr) {
        this.setArr(Arr);
        int size = Arr.length;
        Icon StartPoint = new ImageIcon("./src/Picture/starting-point-64px.png");
        Icon EndPoint = new ImageIcon("./src/Picture/finish-line-64px.png");
        Icon Path = new ImageIcon("./src/Picture/road-64px.png");
        Icon Barrier = new ImageIcon("./src/Picture/barrier-64px.png");
        Icon Go = new ImageIcon("./src/Picture/right-arrow-64px.png");
        
        maze = new SquareButtons[size][size];
        frame = new JFrame("Maze");
        frame.setSize(1000, 900);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(size, size));//board boyutu ile aynı olmalı.Grid--->Hucre 
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                SquareButtons b = new SquareButtons(i, j);
                if (Arr[i][j] == 'S') {
                    b.setIcon(StartPoint);
                }
                if (Arr[i][j] == 'E') {
                    b.setIcon(EndPoint);
                }
                if (Arr[i][j] == 'B') {
                    b.setIcon(Barrier);
                }
                if (Arr[i][j] == '0') {
                    b.setIcon(Path);
                }
                frame.add(b);
                maze[i][j] = b;
            }
        }
    }
    
    public void update(char [][] newArr){
        int i,j;
        int size = newArr.length;
        for(i=0;i<size;i++){
            for(j=0;j<size;j++){
                if(newArr[i][j] == 'P'){
                    maze[i][j].setBackground(Color.red);
                }
            }
        }
    }
    public void run() {
        frame.setVisible(true);// ekte olması işimizi kolaylaştırır
    }
    public void stop(){
        frame.setVisible(false);
    }

    public SquareButtons[][] getboard() {
        return maze;
    }

    public void setboard(SquareButtons[][] board) {
        this.maze = board;
    }

    public char[][] getArr() {
        return Arr;
    }

    public void setArr(char[][] Arr) {
        this.Arr = Arr;
    }

}

class SquareButtons extends JButton { // JButton or another things can use who have border

    private int rowNum, colNum;
    private Icon icon;

    SquareButtons(int row, int col) {
        super();
        this.rowNum = row;
        this.colNum = col;
        //this.setEnabled(false);// unable to click able
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
