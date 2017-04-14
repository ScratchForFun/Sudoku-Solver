package net.vaagen;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {
	
	public static void solve(Sudoku sudoku) {
		long timeMs = System.currentTimeMillis();
		
		List<Sudoku> parents = new ArrayList<Sudoku>();
		for (int i = 0; i < References.PARENT_KEEP_AMOUNT; i++) {
			Sudoku s = new Sudoku().setHelpArray(sudoku.getHelpArray()).fillGrid();
			s.calculateFitness();
			parents.add(s);
		}
		
		Population population = new Population(parents);
		population.grow();
		population.age();
		//System.out.println(population.getBestFitness());
		while(population.getBestFitness() > 0) {
			population = new Population(population.getBestGenerations());
			population.grow();
			population.age();
			//System.out.println(population.getBestFitness());
		}

		System.out.println("Solved! (" + (System.currentTimeMillis() - timeMs) + " ms)");
		System.out.println(population.getBestSudoku().toString());
	}

}
