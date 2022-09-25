import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import javax.swing.JPanel;

public class MazeGridPanel extends JPanel{
	private int rows;
	private int cols;
	private Cell[][] maze;

	// top,right,down,left
	private int[] xDir = {-1,0,1,0};
	private int[] yDir = {0,1,0,-1};

	// extra credit
	public void genDFSMaze() {
		boolean[][] visited = new boolean[rows][cols];
		Stack<Cell> stack  = new Stack<Cell>();
		Random random = new Random();

		Cell start = maze[0][0];
		stack.push(start);
		visited[0][0] = true;

		while (!stack.isEmpty()){

			Cell curLocation = stack.pop();
			
			ArrayList<Integer> possible = new ArrayList<Integer>();
			
			if (curLocation.northWall && curLocation.row - 1 >= 0 &&!visited[curLocation.row - 1][curLocation.col]){ possible.add(0); }
			if (curLocation.eastWall && curLocation.col + 1 < cols && !visited[curLocation.row][curLocation.col + 1]){ possible.add(1); }
			if (curLocation.southWall && curLocation.row + 1 < rows && !visited[curLocation.row + 1][curLocation.col]){ possible.add(2); }
			if (curLocation.westWall && curLocation.col - 1 >= 0 && !visited[curLocation.row][curLocation.col - 1]){ possible.add(3); }

			if (possible.size() > 0){
				stack.push(curLocation);
				int dir = possible.get(random.nextInt(possible.size()));

				Cell newLoc = maze[curLocation.row + xDir[dir]][curLocation.col + yDir[dir]];

				if (dir == 0){
					curLocation.northWall = false;
					newLoc.southWall = false;
				}
				else if (dir == 1){
					curLocation.eastWall = false;
					newLoc.westWall = false;
				}
				else if (dir == 2){
					curLocation.southWall = false;
					newLoc.northWall = false;
				}
				else {
					curLocation.westWall = false;
					newLoc.eastWall = false;
				}

				visited[newLoc.row][newLoc.col] = true;
				stack.push(newLoc);
			}
			maze[curLocation.row][curLocation.col].repaint();
		}
		this.repaint();

	}
	
	public void genNWMaze() {

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {

				if (row == 0 && col == 0) {
					continue;
				}

				else if (row == 0) {
					maze[row][col].westWall = false;
					maze[row][col - 1].eastWall = false;
				} else if (col == 0) {
					maze[row][col].northWall = false;
					maze[row - 1][col].southWall = false;
				} else {
					boolean north = Math.random() < 0.5;
					if (north) {
						maze[row][col].northWall = false;
						maze[row - 1][col].southWall = false;
					} else { // remove west
						maze[row][col].westWall = false;
						maze[row][col - 1].eastWall = false;
					}
					maze[row][col].repaint();
				}
			}
		}
		this.repaint();

	}

	//homework
	public void solveMaze() {
		Stack<Cell> stack  = new Stack<Cell>();
		Cell start = maze[0][0];
		start.setBackground(Color.GREEN);
		Cell finish = maze[rows-1][cols-1];
		finish.setBackground(Color.RED);
		stack.push(start);
		
		while (!stack.isEmpty()){

			Cell curLocation = stack.peek();

			if ( curLocation.row == finish.row && curLocation.col == finish.col) {
				break;
			}

			curLocation.setBackground(Color.YELLOW);

			if (!curLocation.northWall && !visited(curLocation.row - 1, curLocation.col)){
				Cell toAdd = maze[curLocation.row - 1][curLocation.col];
				stack.push(toAdd);

			}
			else if (!curLocation.southWall && !visited(curLocation.row + 1, curLocation.col)) {
				Cell toAdd = maze[curLocation.row + 1][curLocation.col];
				stack.push(toAdd);

			}
			else if (!curLocation.eastWall && !visited(curLocation.row, curLocation.col + 1)) {
				Cell toAdd = maze[curLocation.row][curLocation.col + 1];
				stack.push(toAdd);

			}
			else if (!curLocation.westWall && !visited(curLocation.row, curLocation.col - 1)){
				Cell toAdd = maze[curLocation.row][curLocation.col - 1];
				stack.push(toAdd);
			}
			else{

				curLocation.setBackground(Color.LIGHT_GRAY);
				stack.pop();
			}

		}

		start.setBackground(Color.GREEN);
	}



	public boolean visited(int row, int col) {
		Cell c = maze[row][col];
		Color status = c.getBackground();
		if(status.equals(Color.WHITE)  || status.equals(Color.RED)  ) {
			return false;
		}


		return true;


	}




	public MazeGridPanel(int rows, int cols) {
		this.setPreferredSize(new Dimension(800,800));
		this.rows = rows;
		this.cols = cols;
		this.setLayout(new GridLayout(rows,cols));
		this.maze =  new Cell[rows][cols];
		for(int row = 0 ; row  < rows ; row++) {
			for(int col = 0; col < cols; col++) {

				maze[row][col] = new Cell(row,col);

				this.add(maze[row][col]);
			}

		}


		// this.genNWMaze();
		this.genDFSMaze();
		this.solveMaze();
		
	}




}
