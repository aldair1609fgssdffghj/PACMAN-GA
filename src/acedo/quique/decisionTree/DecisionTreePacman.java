package acedo.quique.decisionTree;

import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class DecisionTreePacman extends Controller<MOVE>{

	DecisionTree decisionTree;

	public DecisionTreePacman(){
		super();

		decisionTree = new DecisionTree();
		decisionTree.build();
	}//constructor

	public MOVE getMove(Game game, long timeDue) {
		return decisionTree.decide(game);
	}//getMove

}//class
