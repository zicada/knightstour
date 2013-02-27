package com.algdat.oblig2;

import java.awt.Point;
import java.util.Stack;

class KnightsTour {

	private boolean[][] board;
	private int[][] solution;
	private int count, spaces;
	private Stack<String> stack;

	// The eight possible moves a knight can make from any given position
	private static final Point[] MOVES = new Point[] { 
			new Point(-2, -1),
			new Point(-2, 1), 
			new Point(2, -1), 
			new Point(2, 1),
			new Point(-1, -2), 
			new Point(-1, 2), 
			new Point(1, -2),
			new Point(1, 2) };


	public KnightsTour(int rows, int cols) {
		board = new boolean[rows][cols];
		solution = new int[rows][cols];
		stack = new Stack<String>();
		spaces = rows * cols;
		count = 0;
	}
	
	private boolean solve(int row, int col) {
		count++;
		board[row][col] = true;

		if (count == spaces)
			return true;

		for (Point p : MOVES) {
			int nextRow = row + p.x;
			int nextCol = col + p.y;
			
			// Are we out of bounds ?
			if (nextRow < 0 || nextRow >= board.length)
				continue;
			else if (nextCol < 0 || nextCol >= board.length)
				continue;

			// Have we been here before ?
			if (board[nextRow][nextCol])
				continue;

			// Add this move to the solution in case we're on the right track
			// If not it's overwritten later
			solution[row][col] = count;
			
			// Try this move, if it leads anywhere, push it onto the stack
			if (solve(nextRow, nextCol)) {
				addMove(row, col, nextRow, nextCol);
				return true;
			}
		}
		
		count--;
		board[row][col] = false;

		return false;

	}

	// Using stack since moves will be added in reverse order due to the nature of recursion
	private void addMove(int row, int col, int nextRow, int nextCol) {
		stack.add("(" + row + " " + col + ") (" + nextRow + " " + nextCol + ")");	
	}

	private void printSolution() {
		System.out.println("Solution shown on board\n");
		for (int[] rows : solution) {
			for (int r : rows) {
				if (r == 0)
					r = count;
				System.out.printf("%2d ", r);
			}
			System.out.println();
		}
	}

	private void printBoard() {
		System.out.println("Steps taken, on the form (fromX, fromY) (toX, toY):\n");
		while(!stack.isEmpty()) {
			System.out.println(stack.pop());
		}
		System.out.println("\n");
	}

	public static void main(String[] args) {
		KnightsTour tour = new KnightsTour(5, 5);
		
		if(tour.solve(0,0)){
			tour.printBoard();
			tour.printSolution();
		} else {
			System.out.print("No solution found");
		}
	}
}
