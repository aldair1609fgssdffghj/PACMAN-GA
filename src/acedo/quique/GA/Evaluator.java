package acedo.quique.GA;

/**
 * @author Quique
 * @date 25/11/2015
 * @version 1.0
 */

import acedo.quique.fuzzyGhosts.Quique_Ghosts;
import acedo.quique.fuzzyPacman.Quique_Pacman;
import pacman.Executor;

public class Evaluator extends Thread{

	int id = 0;
	Gene gen;

	/**
	 * Metodo que se ejecuta cuando se llama al metodo start del trhead
	 */
	public void run(){
//		System.out.println("Evaluando indviduo " + id);
		Executor executor = new Executor();
		Quique_Ghosts controladorFantasmas = new Quique_Ghosts();
		Quique_Pacman controladorPacman = new Quique_Pacman();
		controladorPacman.init(gen.getChromosome());
		gen.setFitness(executor.runMiExperiment(controladorPacman, controladorFantasmas, 100));
//		System.out.println("\tIndviduo " + id + " evaluado");

		
	}//run
	
	// GETTERS AND SETTERS 
	public void setId(int i){
		id = i;
	}
	public void setGen(Gene ge){
		gen = ge;
	}
}//class
