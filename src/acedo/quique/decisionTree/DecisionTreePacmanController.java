package acedo.quique.decisionTree;

/**
 * @author Enrique Acedo
 * @date 20/12/2015
 * @subject Data Mining
 * @version 1.0
 */

/**
 * Controlador basado en un arbol de decision que genera
 */

import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class DecisionTreePacmanController extends Controller<MOVE>{

	/** ATRIBUTOS **/
	private DecisionTree decisionTree;
	private DataTuple[] data;
	private SimpleNode tree;
	boolean construido;

	/** CONSTRUCTOR **/
	public DecisionTreePacmanController(){
		super();
		data = DataSaverLoader.LoadPacManData();
		decisionTree = new DecisionTree(data);
	
		//Construyo el arbol
		construido = this.construirArbol();

	}//constructor

	
	/** METODOS **/
	
	/**
	 * Metodo que ejecuta los comandos necesarios para construir el arbol
	 * @return TRUE si se construye correctamente
	 * @return FALSE si se produce algun fallo
	 */
	private boolean construirArbol(){
//		try{
		decisionTree.atr = Atributo.currentLevel;
			tree = decisionTree.build(data, Atributo.atributos());
//		}catch(Exception e){
//			return false;
//		}//try-catch

		return true;
	}//construirArbol

	/**
	 * 
	 */
	public MOVE getMove(Game game, long timeDue) {
		return decisionTree.decide(game, tree);
	}//getMove

	/**
	 * Metodo utilizado para pruebas que genera un string personalizado
	 * @param data
	 * @return string
	 */
	public String toString(DataTuple[] data ) {
		String content = "";

		for(int i = 0; i < data.length; i++){
			System.out.println("Pinky esta a: " + data[i].pinkyDist + " y esta edible: "+ data[i].isPinkyEdible + " y su movimiento ha sido "+ data[i].pinkyDir+"\t" 
					+ "Blinky esta a: " + data[i].blinkyDist + " y esta edible: "+ data[i].isBlinkyEdible + " y su movimiento ha sido "+ data[i].blinkyDir+"\t" 
					+ "Inky esta a: " + data[i].inkyDist + " y esta edible: "+ data[i].isInkyEdible + " y su movimiento ha sido "+ data[i].inkyDir+"\t" 
					+ "Sue esta a: " + data[i].sueDist + " y esta edible: "+ data[i].isSueEdible + " y su movimiento ha sido "+ data[i].sueDir+"\t"
					+ "Pacman se mueve: "+ data[i].DirectionChosen+"\t");
		}//for
		return content;
	}//toString


	/**
	 * 
	 * @return SimpleNode tree
	 */
	public SimpleNode getTree(){
		return tree;
	}

	/**
	 * 
	 * @return TRUE si esta construido el arbol
	 * @return FALSE si no esta construido
	 */
	public boolean isConstruido(){
		return construido;
	}
}//class
