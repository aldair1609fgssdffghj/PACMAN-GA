package acedo.quique.decisionTree;

import dataRecording.DataCollectorController;
import pacman.Executor;
import pacman.controllers.KeyBoardInput;
import pacman.controllers.examples.StarterGhosts;

public class TestDecisionTree {

	public static void main(String[] args) {
		Executor executor = new Executor();

		// Se ejecuta un juego visual MAQUINA vs HUMANO para recolectar datos 
		executor.runGameTimed(new DataCollectorController(new KeyBoardInput()),new StarterGhosts(),true);

		// Inicio los atributos
		Atributo.init();

		// Creo el controlador y se crea el arbol
		DecisionTreePacmanController controladorDTQuique = new DecisionTreePacmanController();

		// Si se ha construido correctamente
		if(controladorDTQuique.isConstruido()){
			// Lo muestro por pantalla
			TreeConverter converter = new TreeConverter();
			converter.tree2graph(controladorDTQuique.getTree()).display();

			// Ejecuto un juego con el controlador
			executor.runGameTimed(controladorDTQuique, new StarterGhosts(), true);
		}else{
			System.out.println("Error al generar el arbol");
		}//if-else

	}//main
}//class
