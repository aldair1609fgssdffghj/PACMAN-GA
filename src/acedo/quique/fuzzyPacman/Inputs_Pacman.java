package acedo.quique.fuzzyPacman;

/**
 * Inputs_Pacman.java
 * @author Quique
 * @version 1.0
 * @date 2/12/2015
 */

import java.util.HashMap;
import com.fuzzylite.Engine;
import com.fuzzylite.term.*;
import com.fuzzylite.variable.InputVariable;

public class Inputs_Pacman {

	/** ATRIBUTOS */
	private HashMap<String,String[]> variables = new HashMap<String,String[]>();
	
	private InputVariable distancia_ghost = new InputVariable();
	private InputVariable tiempo_edible = new InputVariable();
	private InputVariable distanciaPowerPills = new InputVariable();
	private InputVariable distanciaPill = new InputVariable();
	
	private InputVariable[] inputs = {distancia_ghost, tiempo_edible, distanciaPowerPills, distanciaPill};

	/** METODOS */

	
	/**
	 * Metodo que inicia las variables input
	 */
	public void init(){
		
		// Por cada variable input:
		// 1) Me creo los trapecios que van a dar forma a la grafica
		// 2) Los inicializo
		// 3) Meto los valores
		// 4) Inicializo la variable input
		// 5) Creo las etiquetas de los nombres y los añado a las variables
		// 6) Añado los terminos al input
		
		// DISTANCIA A FANTASMA MAS CERCANO
		// 1)
		MiTrapecio[] distancia_param = new MiTrapecio[4];
		
		// 2)
		for(int i = 0; i < distancia_param.length; i++){
			distancia_param[i] = new MiTrapecio();
		}//for
		
		// 3)
		distancia_param[0].setParam(0.000, 0.000, 10.000, 40.000);
		distancia_param[1].setParam(20.000, 50.000, 50.000, 80.000);
		distancia_param[2].setParam(70.000, 100.000, 100.000, 130.000);
		distancia_param[3].setParam(110.000, 140.000, 150.000, 150.000);

		// 4)
		distancia_ghost.setEnabled(true);
		distancia_ghost.setName("DistanciaGhosts");
		distancia_ghost.setRange(0.000, 150.000);

		// 5)
		String[] valores_distancia = {"MUY_CERCA", "CERCA", "MEDIA", "LEJOS"};
		variables.put("DistanciaGhosts", valores_distancia);

		// 6)
		double[] distancia_aux;
		for(int i = 0; i < valores_distancia.length;i ++){
			distancia_aux = distancia_param[i].getParam();
			distancia_ghost.addTerm(new Trapezoid(valores_distancia[i], distancia_aux[0], distancia_aux[1], distancia_aux[2], distancia_aux[3]));
		}//for


		// MENOR TIEMPO EDIBLE DE LOS FANTASMAS
		// 1)
		MiTrapecio[] tiempo_edible_param = new MiTrapecio[4];

		// 2)
		for(int i = 0; i < tiempo_edible_param.length; i++){
			tiempo_edible_param[i] = new MiTrapecio();
		}//for
		
		// 3)
		double tiempo = pacman.game.Constants.EDIBLE_TIME;
		tiempo_edible_param[0].setParam(0, 0, 0.5, 3);
		tiempo_edible_param[1].setParam(3.000, 3.000, 3.000, tiempo*2/5);
		tiempo_edible_param[2].setParam(tiempo*1/4, tiempo/2, tiempo/2, tiempo*3/4);
		tiempo_edible_param[3].setParam(tiempo*3/5, tiempo, tiempo, tiempo);

		// 4)
		tiempo_edible.setRange(0.0, tiempo);
		tiempo_edible.setEnabled(true);
		tiempo_edible.setName("TiempoEdible");

		// 5)
		String[] valores_tiempo = {"MUY_POCO","POCO" ,"MEDIO", "MUCHO"};
		variables.put("TiempoEdible", valores_tiempo);

		// 6)
		double[] tiempo_aux;
		for(int i = 0; i < valores_tiempo.length;i ++){
			tiempo_aux = tiempo_edible_param[i].getParam();
			tiempo_edible.addTerm(new Trapezoid(valores_tiempo[i], tiempo_aux[0], tiempo_aux[1], tiempo_aux[2], tiempo_aux[3]));
		}//for


		// DISTANCIA POWER PILL MAS CERCANA
		// 1)
		MiTrapecio[] power_pill_param = new MiTrapecio[4];

		// 2)
		for(int i = 0; i < power_pill_param.length; i++){
			power_pill_param[i] = new MiTrapecio();
		}//for
		
		// 3)
		power_pill_param[0].setParam(0.000, 0.000, 10.000, 40.000);
		power_pill_param[1].setParam(20.000, 50.000, 50.000, 80.000);
		power_pill_param[2].setParam(70.000, 100.000, 100.000, 130.000);
		power_pill_param[3].setParam(110.000, 140.000, 150.000, 150.000);

		// 4)
		distanciaPowerPills.setEnabled(true);
		distanciaPowerPills.setName("DistanciaPowerPill");
		distanciaPowerPills.setRange(0.000, 150.000);

		// 5)
		String[] valores_distancia_power = {"MUY_CERCA", "CERCA", "MEDIA", "LEJOS"};
		variables.put("DistanciaPowerPill", valores_distancia_power);

		// 6)
		double[] distancia_power_aux;
		for(int i = 0; i < valores_distancia_power.length;i ++){
			distancia_power_aux = power_pill_param[i].getParam();
			distanciaPowerPills.addTerm(new Trapezoid(valores_distancia_power[i], distancia_power_aux[0], distancia_power_aux[1], distancia_power_aux[2], distancia_power_aux[3]));
		}//for

		// DISTANCIA A PILL MAS CERCANA
		// 1)
		MiTrapecio[] pill_param = new MiTrapecio[4];

		// 2)
		for(int i = 0; i < pill_param.length; i++){
			pill_param[i] = new MiTrapecio();
		}//for
		
		// 3)
		pill_param[0].setParam(0.000, 0.000, 10.000, 40.000);
		pill_param[1].setParam(20.000, 50.000, 50.000, 80.000);
		pill_param[2].setParam(70.000, 100.000, 100.000, 130.000);
		pill_param[3].setParam(110.000, 140.000, 150.000, 150.000);

		// 4)
		distanciaPill.setEnabled(true);
		distanciaPill.setName("DistanciaPills");
		distanciaPill.setRange(0.000, 150.000);
		
		// 5)
		String[] valores_distancia_pill = {"MUY_CERCA", "CERCA", "MEDIA", "LEJOS"};
		variables.put("DistanciaPills", valores_distancia_pill);

		// 6)
		double[] distancia_pill_aux;
		for(int i = 0; i < valores_distancia_pill.length;i ++){
			distancia_pill_aux = pill_param[i].getParam();
			distanciaPill.addTerm(new Trapezoid(valores_distancia_power[i], distancia_pill_aux[0], distancia_pill_aux[1], distancia_pill_aux[2], distancia_pill_aux[3]));
		}//for

	}//init

	/**
	 * Mete las variables en el motor
	 * @param motor
	 * @return el motor con las variables metidas
	 */
	public Engine meterVariables(Engine motor){
		for(int i = 0; i < inputs.length; i++){
			motor.addInputVariable(inputs[i]);
		}//for

		return motor;
	}//meterVariables

	/** GETTERS AND SETTERS */
	public InputVariable getDistanciaGhosts() {
		return distancia_ghost;
	}//getDistancia

	public InputVariable getTiempoEdible(){
		return tiempo_edible;
	}//getTiempoEdible

	public InputVariable getDistanciaPowerPills(){
		return distanciaPowerPills;
	}//getNumeroPowerPills

	public InputVariable getDistanciaPills(){
		return distanciaPill;
	}//getNumeroPills
	
	public HashMap<String, String[]> getVariables(){
		return variables;
	}
}//class
