package acedo.quique.redNeuronas;

/**
 * @author Enrique Acedo
 * @date 31/01/2016
 */

import pacman.controllers.Controller;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RedNeuronasController extends Controller<MOVE>{

	/** ATRIBUTOS **/
	private RedNeuronas red;
	
	/** METODOS **/
	
	public RedNeuronasController(RedNeuronas red){
		this.red = red;
	}//constructor
	
	
	public MOVE getMove(Game game, long timeDue) {		

		double[] datos_entrada = getDatos(game);

		red.procesarDatosEntrada(datos_entrada);

		int best_move = red.cogerMejorNeurona();

		System.out.println(best_move);
		return traducirMovimiento(best_move);
	}//getMove


	private double[] getDatos(Game game) {
		double pinkyDist, inkyDist, blinkyDist, sueDist;
		double pinkyEdible, inkyEdible, blinkyEdible, sueEdible;
		double powerPills, pills;

		pinkyDist = normalizarDistancia(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.PINKY)));
		inkyDist = normalizarDistancia(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.INKY)));
		blinkyDist = normalizarDistancia(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.BLINKY)));
		sueDist = normalizarDistancia(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.SUE)));

		pinkyEdible = normalizarBoolean(game.isGhostEdible(GHOST.PINKY));
		inkyEdible = normalizarBoolean(game.isGhostEdible(GHOST.INKY));
		blinkyEdible = normalizarBoolean(game.isGhostEdible(GHOST.BLINKY));
		sueEdible = normalizarBoolean(game.isGhostEdible(GHOST.SUE));

		pills = normalizarPills(game.getNumberOfActivePills(), game.getNumberOfPills());
		powerPills = normalizarPills(game.getNumberOfActivePowerPills(), game.getNumberOfPowerPills());

		double[] datos_entrada = {pinkyDist,  inkyDist, blinkyDist, sueDist,pinkyEdible, 
				inkyEdible, blinkyEdible, sueEdible,powerPills, pills};
		
		return datos_entrada;
	}


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
	 * @param dist
	 * @param maxDistance
	 * @return valor normalizado
	 */
	public static double normalizarDistancia(double dist){		
		return ((dist - 0) / (double) (150 - 0)) * (1 - 0) + 0;
	}//normalizar
	
	
	/** 
	 * Normaliza a 1
	 * @param pills
	 * @param totalPills
	 * @return valor normalizado
	 */
	public static double normalizarPills(double pills, int totalPills){		
		return ((pills - 0) / (double) (totalPills - 0)) * (1 - 0) + 0;
	}//normalizar

	
	public static double normalizarBoolean(boolean bool){
		if (bool) {
			return 1.0;
		} else {
			return 0.0;
		}
	}//normalizarBoolean
}//class
