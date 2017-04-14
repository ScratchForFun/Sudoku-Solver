package net.vaagen;

import java.util.Random;

public class Sudoku {

	private int[][] grid;
	private int[][] helpArray;
	
	private float fitness;
	private boolean isOld;
	
	public Sudoku() {
		this.grid = new int[9][9];
		this.isOld = false;
	}
	
	public Sudoku fillGrid() {
		for (int y1 = 0; y1 < grid[0].length / 3; y1++) {
			for (int x1 = 0; x1 < grid.length / 3; x1++) {
				int[] n = new int[9];
				
				for (int y2 = 0; y2 < grid[0].length / 3; y2++) {
					for (int x2 = 0; x2 < grid.length / 3; x2++) {
						if (grid[x1*3+x2][y1*3+y2] == 0) continue;
						n[grid[x1*3+x2][y1*3+y2]-1]++;
					}
				}
				
				for (int y2 = 0; y2 < grid[0].length / 3; y2++) {
					for (int x2 = 0; x2 < grid.length / 3; x2++) {
						if (grid[x1*3+x2][y1*3+y2] == 0) {
							int i = 0;
							while (i == 0) {
								i = new Random().nextInt(9)+1;
								if (n[i-1] == 0) {
									n[i-1]++;
									break;
								} else {
									i = 0;
								}
							}
							
							grid[x1*3+x2][y1*3+y2] = i;
						}
					}
				}
			}
		}
		
		return this;
	}
	
	public Sudoku crossBreed(Sudoku sudoku) {
		int[][] newGrid = deepCopyGrid();
		for (int y = 0; y < newGrid[0].length; y++) {
			for (int x = 0; x < newGrid.length; x++) {
				if (new Random().nextBoolean()) newGrid[x][y] = sudoku.getGrid()[x][y];
			}
		}
		
		return new Sudoku().setHelpArray(newGrid);
	}
	
	public Sudoku mutate() {
		int mutations = References.MUTATION_AMOUNT;
		
		for (int i = 0; i < mutations; i++)
			grid[new Random().nextInt(9)][new Random().nextInt(9)] = new Random().nextInt(9)+1;
		
		return this;
	}
	
	public float calculateFitness() {
		float fitness = 0;
		
		for (int x = 0; x < grid.length; x++) {
			for (int i = 1; i <= grid[0].length; i++) {
				int n = 0; 
				for (int y = 0; y < grid[0].length; y++) {
					if (grid[x][y] == i)
						n++;
				}
				if (n > 0) fitness += n-1;
			}
		}
		
		for (int y = 0; y < grid[0].length; y++) {
			for (int i = 1; i <= grid.length; i++) {
				int n = 0; 
				for (int x = 0; x < grid.length; x++) {
					if (grid[x][y] == i)
						n++;
				}
				if (n > 0) fitness += n-1;
			}
		}
		
		this.fitness = fitness;
		return fitness;
	}
	
	public float getFitness() {
		return fitness;
	}
	
	public void age() {
		if (isOld) fitness+=References.AGE_AMOUNT;
		isOld = true;
	}
	
	public Sudoku setHelpArray(int[][] helpArray) {
		this.helpArray = helpArray;
		for (int y = 0; y < grid[0].length; y++) {
			for (int x = 0; x < grid.length; x++) {
				grid[x][y] = helpArray[x][y];
			}
		}
		
		return this;
	}
	
	public int[][] getHelpArray() {
		return helpArray;
	}
	
	public int[][] getGrid() {
		return grid;
	}
	
	public String toString() {
		String s = "";
		
		for (int y = 0; y < grid[0].length; y++) {
			for (int x = 0; x < grid.length; x++) {
				s += (grid[x][y] < 0 ? " " : grid[x][y]) + (x % 3 == 2 ? "  " : "");
			}
			s+="\n";
			if (y % 3 == 2) s+="\n";
		}
		
		return s;
	}
	
	private int[][] deepCopyGrid() {
		int[][] newGrid = new int[9][9];
		for (int x = 0; x < newGrid.length; x++) {
			for (int y = 0; y < newGrid[0].length; y++) {
				newGrid[x][y] = grid[x][y];
			}
		}
		
		return newGrid;
	}
}
