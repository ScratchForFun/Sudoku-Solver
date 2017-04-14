package net.vaagen;

import java.util.ArrayList;
import java.util.List;

public class Population {
	
	private List<Sudoku> parents;
	private List<Sudoku> generations;
	
	public Population(List<Sudoku> parents) {
		this.parents = parents;
		this.generations = new ArrayList<Sudoku>();
		this.generations.addAll(parents);
	}
	
	public void grow() {
		for (int i = 0; i < parents.size(); i++) {
			for (int j = 0; j < i; j++) {
				Sudoku sudoku = parents.get(i).crossBreed(parents.get(j));
				sudoku.mutate();
				sudoku.calculateFitness();
				
				for (int k = 0; k < generations.size(); k++) {
					float fit = generations.get(k).getFitness(); // Already calculated
					if (fit > sudoku.getFitness()) {
						generations.add(k, sudoku);
						break;
					}
				}
			}
		}
	}
	
	public Sudoku getBestSudoku() {
		return generations.get(0);
	}
	
	public List<Sudoku> getBestGenerations() {
		List<Sudoku> bestList = new ArrayList<Sudoku>();
		for (int i = 0; i < References.PARENT_KEEP_AMOUNT; i++) {
			bestList.add(generations.get(i));
		}
		
		return bestList;
	}
	
	public float getBestFitness() {
		return generations.get(0).getFitness();
	}
	
	public void age() {
		for (Sudoku sudoku : generations) sudoku.age();
	}
}
