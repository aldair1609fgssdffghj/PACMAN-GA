package acedo.quique.fuzzyGhosts;

import java.util.EnumMap;
import com.fuzzylite.Engine;
import pacman.controllers.Controller;
import pacman.game.Constants.*;
import pacman.game.Game;

public class Quique_Ghosts extends Controller<EnumMap<GHOST,MOVE>>{

	
	EnumMap<GHOST, MOVE> movimientos = new EnumMap<GHOST, MOVE>(GHOST.class);
	Engine motor= new Engine();
	Inputs_Ghosts inputs = new Inputs_Ghosts();
	Outputs_Ghosts outputs = new Outputs_Ghosts();
	Reglas_Ghosts reglas = new Reglas_Ghosts();

	/**
	 * Constructor de la clase QuiqueGhosts
	 * Inicaliza las variables input y output y las reglas 
	 * y las agrega al motor
	 */
	public Quique_Ghosts(){
		motor.setName("Fuzzy-Ghost");

		// Inicializamos los inputs
		inputs.init();
		
		// Inicializamos los outputs
		outputs.init();
		
		// Agregamos los inputs al motor
		motor = inputs.meterVariables(motor);
		
		// Agregamos los outputs al motor
		motor = outputs.meterVariables(motor);
		
		// Iniciamos las reglas
		reglas.init(motor);
		
		// Agregamos las reglas al motor
		motor = reglas.meterReglas(motor);		

	}//constructor

	
	public EnumMap<GHOST, MOVE> getMove(Game juego, long timeDue) {

		for(GHOST fantasma : GHOST.values()){	//para cada fantasma
			if(juego.doesGhostRequireAction(fantasma)){		//si requiere una accion

				double distance_to_pacman = juego.getShortestPathDistance(juego.getGhostCurrentNodeIndex(fantasma),juego.getPacmanCurrentNodeIndex());
				double time_edible = juego.getGhostEdibleTime(fantasma);
				double number_power_pills = juego.getActivePowerPillsIndices().length;
				double number_pills = juego.getActivePillsIndices().length;

				inputs.getDistancia().setInputValue(distance_to_pacman);
				inputs.getTiempoEdible().setInputValue(time_edible);
				inputs.getNumeroPowerPills().setInputValue(number_power_pills);
				inputs.getNumeroPills().setInputValue(number_pills);

				motor.process();
				
//				System.out.println("********** " + outputs.getAccion().defuzzify() + " **********");

				if(outputs.getAccion().defuzzify() < 3.333)
					movimientos.put(fantasma, huir(fantasma, juego));

				else if(outputs.getAccion().defuzzify() > 6.666)
					movimientos.put(fantasma, atacar(fantasma,juego));

				else if(outputs.getAccion().defuzzify() >= 3.333 && outputs.getAccion().defuzzify() <= 6.666)
					movimientos.put(fantasma, esperar(fantasma,juego));

			}//if
		}//for 

		return movimientos;
	}//getMove()

	/**
	 * 
	 * @param fantasma
	 * @param juego
	 * @return siguiente movimiento para atacar a Pacman
	 */
	private MOVE atacar(GHOST fantasma, Game juego){
//		System.out.println("************ ATACANDO *************");
		return juego.getApproximateNextMoveTowardsTarget(juego.getGhostCurrentNodeIndex(fantasma),
				juego.getPacmanCurrentNodeIndex(),juego.getGhostLastMoveMade(fantasma),DM.PATH);
	}//atacar

	/**
	 * 
	 * @param fantasma
	 * @param juego
	 * @return sigiuente movimiento para huir de Pacman
	 */
	private MOVE huir(GHOST fantasma, Game juego){
//		System.out.println("************ HUYENDO *************");
		return juego.getApproximateNextMoveAwayFromTarget(juego.getGhostCurrentNodeIndex(fantasma),
				juego.getPacmanCurrentNodeIndex(),juego.getGhostLastMoveMade(fantasma),DM.PATH);
	}//huir

	/**
	 * Busca la power-pill mas cercana y en caso de no haber va a cualquier sitio (random)
	 * Nunca deberia generarse el random ya que nunca esperara si no hay power-pills
	 * @param fantasma
	 * @param juego
	 * @return siguiente movimiento hacia la Power-Pill mas cercana o sino, Random
	 */
	private MOVE esperar(GHOST fantasma, Game juego){
//		System.out.println("************ ESPERANDO *************");

		int[] powerPills = juego.getPowerPillIndices();
		int distancia_a_power_pill = 0;
		int siguiente_power_pill = -1;

		for(int i = 0; i < powerPills.length; i++){
			if(juego.isPowerPillStillAvailable(powerPills[i]))
				if(juego.getShortestPathDistance(juego.getGhostCurrentNodeIndex(fantasma), powerPills[i]) > distancia_a_power_pill){
					distancia_a_power_pill = juego.getShortestPathDistance(juego.getGhostCurrentNodeIndex(fantasma), powerPills[i]);
					siguiente_power_pill = powerPills[i];
				}//if
		}//for

		if(siguiente_power_pill < 0){
			siguiente_power_pill = (int) Math.random()*juego.getActivePillsIndices().length;
		}//if
		
		return juego.getApproximateNextMoveTowardsTarget(juego.getGhostCurrentNodeIndex(fantasma),
				siguiente_power_pill,juego.getGhostLastMoveMade(fantasma),DM.PATH);
	}//esperar
	
}//class
