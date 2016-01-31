package acedo.quique.redNeuronas;

import pacman.controllers.Controller;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RedNeuronasController extends Controller<MOVE>{

	/** ATRIBUTOS **/
	private RedNeuronas red;
	private int count = 0;
	
	/** METODOS **/
	
	public RedNeuronasController(RedNeuronas red){
		this.red = red;
	}
	public MOVE getMove(Game game, long timeDue) {		

		double pinkyDist, inkyDist, blinkyDist, sueDist;
		double pinkyEdible, inkyEdible, blinkyEdible, sueEdible;
		double powerPills, pills;

		pinkyDist = normalizar(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.PINKY)), 150);
		inkyDist = normalizar(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.INKY)), 150);
		blinkyDist = normalizar(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.BLINKY)), 150);
		sueDist = normalizar(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.SUE)),150);

		pinkyEdible = normalizarBoolean(game.isGhostEdible(GHOST.PINKY));
		inkyEdible = normalizarBoolean(game.isGhostEdible(GHOST.INKY));
		blinkyEdible = normalizarBoolean(game.isGhostEdible(GHOST.BLINKY));
		sueEdible = normalizarBoolean(game.isGhostEdible(GHOST.SUE));

		pills = normalizar(game.getNumberOfActivePills(), game.getNumberOfPills());
		powerPills = normalizar(game.getNumberOfActivePowerPills(), game.getNumberOfPowerPills());

		double[] datos_entrada = {pinkyDist,  inkyDist, blinkyDist, sueDist,pinkyEdible, 
				inkyEdible, blinkyEdible, sueEdible,powerPills, pills};

		count++;
		if(count == 100)
			red.printRed();
		red.procesarDatosEntrada(datos_entrada);
		if(count == 100)
			red.printRed();


		int best_move = red.cogerMejorNeurona();

		return traducirMovimiento(best_move);
	}//getMove


	/**
	 * Traduce el id de la neurona en el movimiento
	 * 0 --> NEUTRAL
	 * 1 --> UP
	 * 2 --> RIGTH
	 * 3 --> DOWN
	 * 4--> LEFT
	 * @param best
	 * @return MOVE
	 */
	private MOVE traducirMovimiento(int best){
		//		System.out.println("ENTRA "+ best);
		MOVE movimiento = null;

		switch (best) {
		case 0:
			movimiento = MOVE.NEUTRAL;
			break;
		case 1:
			movimiento = MOVE.UP;
			break;
		case 2:
			movimiento = MOVE.RIGHT;
			break;
		case 3:
			movimiento = MOVE.DOWN;
			break;
		case 4:
			movimiento = MOVE.LEFT;
			break;

		default:
			movimiento = MOVE.NEUTRAL;
			break;
		}//Switch

		return movimiento;
	}//traducirMovimiento


	/** 
	 * Normaliza a 1
	 * @param a
	 * @param max
	 * @return
	 */
	public static double normalizar(double a, double max){		
		return a/max;
	}//normalizar

	public static double normalizarBoolean(boolean b){
		double result = 0.0;

		if(b)
			result = 1.0;

		return result;
	}//normalizarBoolean
}//class
