package acedo.quique.decisionTree;

import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class DecisionTreePacmanController extends Controller<MOVE>{

	private DecisionTree decisionTree;
	private DataTuple[] data;

	public DecisionTreePacmanController(){
		super();
		data = DataSaverLoader.LoadPacManData();

		decisionTree = new DecisionTree();
		decisionTree.build();
	}//constructor

	public MOVE getMove(Game game, long timeDue) {
		return decisionTree.decide(game);
	}//getMove

	public String toString() {
		String content = "";

		for(int i = 0; i < data.length; i++){
			System.out.println("Pinky esta a: " + data[i].pinkyDist + " y esta edible: "+ data[i].isPinkyEdible + " y su movimiento ha sido "+ data[i].pinkyDir+"\t" 
					+ "Blinky esta a: " + data[i].blinkyDist + " y esta edible: "+ data[i].isBlinkyEdible + " y su movimiento ha sido "+ data[i].blinkyDir+"\t" 
					+ "Inky esta a: " + data[i].inkyDist + " y esta edible: "+ data[i].isInkyEdible + " y su movimiento ha sido "+ data[i].inkyDir+"\t" 
					+ "Sue esta a: " + data[i].sueDist + " y esta edible: "+ data[i].isSueEdible + " y su movimiento ha sido "+ data[i].sueDir+"\t" );
		}//for
		return content;
	}//toString

}//class
