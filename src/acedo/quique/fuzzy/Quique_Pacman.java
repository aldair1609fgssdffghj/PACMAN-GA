package acedo.quique.fuzzy;

import com.fuzzylite.Engine;
import pacman.controllers.Controller;
import pacman.game.Constants.*;
import pacman.game.Game;

public class Quique_Pacman extends Controller<MOVE>{


	MOVE movimiento;

	Engine motor= new Engine();
	Inputs_Pacman inputs = new Inputs_Pacman();
	Outputs_Pacman outputs = new Outputs_Pacman();
	Reglas_Pacman reglas = new Reglas_Pacman(inputs.getVariables());

	/**
	 * Init de la clase Quique_Pacman
	 * Inicaliza las variables input y output y las reglas 
	 * y las agrega al motor
	 */
	public void init(int[] genotipo){
		motor.setName("Fuzzy-Pacman");

		// Inicializamos los inputs
		inputs.init();

		// Inicializamos los outputs
		outputs.init();

		// Agregamos los inputs al motor
		motor = inputs.meterVariables(motor);

		// Agregamos los outputs al motor
		motor = outputs.meterVariables(motor);

		// Iniciamos las reglas
		reglas.init(motor, genotipo);

		// Agregamos las reglas al motor
		motor = reglas.meterReglas(motor);		

	}//constructor


	public MOVE getMove(Game juego, long timeDue) {

		double short_distance_to_ghots = 150;
		double short_time_edible = pacman.game.Constants.EDIBLE_TIME;

		for(GHOST fantasma : GHOST.values()){	//para cada fantasma
			// Distancia a pacman
			double short_distance_to_ghots_aux = juego.getShortestPathDistance(juego.getGhostCurrentNodeIndex(fantasma),juego.getPacmanCurrentNodeIndex());
			// Si es mas pequeña, actualizo
			if(short_distance_to_ghots > short_distance_to_ghots_aux)
				short_distance_to_ghots = short_distance_to_ghots_aux;

			// Tiempo Edible
			double time_edible_aux = juego.getGhostEdibleTime(fantasma);
			// Si es mas pequeña, actualizo
			if(short_time_edible > time_edible_aux)
				short_time_edible = time_edible_aux;

		}//for 

		int[] powerPills = juego.getActivePowerPillsIndices();
		double short_distance_to_power = 150;

		for(int i = 0; i < powerPills.length; i++){
			double short_distance_to_power_aux = juego.getShortestPathDistance(juego.getPowerPillIndex(powerPills[i]),juego.getPacmanCurrentNodeIndex());
			// Si es mas pequeña, actualizo
			if(short_distance_to_power > short_distance_to_power_aux)
				short_distance_to_power = short_distance_to_power_aux;
		}//for

		int[] pills = juego.getActivePillsIndices();
		double short_distance_to_pill = 150;

		for(int i = 0; i < powerPills.length; i++){
			double short_distance_to_pill_aux = juego.getShortestPathDistance(juego.getPillIndex(pills[i]),juego.getPacmanCurrentNodeIndex());
			// Si es mas pequeña, actualizo
			if(short_distance_to_pill > short_distance_to_pill_aux)
				short_distance_to_pill = short_distance_to_pill_aux;
		}//for

		inputs.getDistanciaGhosts().setInputValue(short_distance_to_ghots);
		inputs.getTiempoEdible().setInputValue(short_time_edible);
		inputs.getDistanciaPowerPills().setInputValue(short_distance_to_power);
		inputs.getDistanciaPills().setInputValue(short_distance_to_pill);

		motor.process();

		//				System.out.println("********** " + outputs.getAccion().defuzzify() + " **********");

		if(outputs.getAccion().defuzzify() < 3.333)
			movimiento= huir(juego);

		else if(outputs.getAccion().defuzzify() > 6.666)
			movimiento= atacar(juego);

		else if(outputs.getAccion().defuzzify() >= 3.333 && outputs.getAccion().defuzzify() <= 6.666)
			movimiento = comer(juego);


		return movimiento;
	}//getMove()

	/**
	 * 
	 * @param juego
	 * @return siguiente movimiento para atacar a Pacman
	 */
	private MOVE atacar(Game juego){
		//		System.out.println("************ ATACANDO *************");

		GHOST fantasma = fantasmaEdibleCercano(juego);
		return juego.getApproximateNextMoveTowardsTarget(juego.getPacmanCurrentNodeIndex(),
			    juego.getGhostCurrentNodeIndex(fantasma),juego.getPacmanLastMoveMade(),DM.PATH);
	}//atacar

	/**
	 * 
	 * @param juego
	 * @return sigiuente movimiento para huir de Pacman
	 */
	private MOVE huir(Game juego){
		//		System.out.println("************ HUYENDO *************");
		GHOST fantasma = fantasmaEdibleCercano(juego);
		return juego.getApproximateNextMoveAwayFromTarget(juego.getPacmanCurrentNodeIndex(),
				juego.getGhostCurrentNodeIndex(fantasma),juego.getPacmanLastMoveMade(),DM.PATH);
	}//huir

	/**
	 * Busca la power-pill mas cercana y en caso de no haber va a cualquier sitio (random)
	 * Nunca deberia generarse el random ya que nunca esperara si no hay power-pills
	 * @param fantasma
	 * @param juego
	 * @return siguiente movimiento hacia la Power-Pill mas cercana o sino, Random
	 */
	private MOVE comer(Game juego){
		//		System.out.println("************ ESPERANDO *************");

		int[] pills = juego.getPillIndices();
		int distancia_a_pill = 0;
		int siguiente_pill = -1;

		for(int i = 0; i < pills.length; i++){
			if(juego.isPillStillAvailable(pills[i]))
				if(juego.getShortestPathDistance(juego.getPacmanCurrentNodeIndex(), pills[i]) > distancia_a_pill){
					distancia_a_pill = juego.getShortestPathDistance(juego.getPacmanCurrentNodeIndex(), pills[i]);
					siguiente_pill = pills[i];
				}//if
		}//for

		return juego.getApproximateNextMoveTowardsTarget(juego.getPacmanCurrentNodeIndex(),
				siguiente_pill,juego.getPacmanLastMoveMade(),DM.PATH);
	}//comer

	private GHOST fantasmaEdibleCercano(Game juego){
		GHOST fantasma = null;

		double short_distance_to_ghots = 150;

		for(GHOST fantasma_aux : GHOST.values()){	//para cada fantasma
			if(juego.isGhostEdible(fantasma_aux)){
				// Distancia a pacman
				double short_distance_to_ghots_aux = juego.getShortestPathDistance(juego.getGhostCurrentNodeIndex(fantasma_aux),juego.getPacmanCurrentNodeIndex());
				// Si es mas pequeña, actualizo
				if(short_distance_to_ghots > short_distance_to_ghots_aux){
					short_distance_to_ghots = short_distance_to_ghots_aux;
					fantasma = fantasma_aux;
				}//if
			}//if

		}//for 
		return fantasma;
	}//fantasmaEdibleCercano


	public String fenotipoToString(){
		String result = "";
		String[] reglas_string = reglas.getStringReglas();
		for(int i = 0; i < reglas_string.length;i++)
			result += reglas_string[i] + "\n";

		return result;
	}//toString

}//class
