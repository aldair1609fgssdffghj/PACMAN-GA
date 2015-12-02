package acedo.quique.fuzzy;

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

	private HashMap<String,String[]> variables = new HashMap<String,String[]>();
	
	private InputVariable distancia_ghost = new InputVariable();
	private InputVariable tiempo_edible = new InputVariable();
	private InputVariable distanciaPowerPills = new InputVariable();
	private InputVariable distanciaPill = new InputVariable();

	private InputVariable[] inputs = {distancia_ghost, tiempo_edible, distanciaPowerPills, distanciaPill};

	public void init(){
		
		// DISTANCIA A FANTASMA MAS CERCANO
		MiTrapecio[] distancia_param = new MiTrapecio[4];

		for(int i = 0; i < distancia_param.length; i++){
			distancia_param[i] = new MiTrapecio();
			distancia_param[i].init();
		}//for

		distancia_ghost.setEnabled(true);
		distancia_ghost.setName("DistanciaGhosts");
		distancia_ghost.setRange(0.000, 150.000);

		String[] valores_distancia = {"MUY_CERCA", "CERCA", "MEDIA", "LEJOS"};
		variables.put("DistanciaGhosts", valores_distancia);

		double[] distancia_aux;
		for(int i = 0; i < valores_distancia.length;i ++){
			distancia_aux = distancia_param[i].getParam();
			distancia_ghost.addTerm(new Trapezoid(valores_distancia[i], distancia_aux[0], distancia_aux[1], distancia_aux[2], distancia_aux[3]));
		}//for


		// MENOR TIEMPO EDIBLE DE LOS FANTASMAS
		MiTrapecio[] tiempo_edible_param = new MiTrapecio[4];

		for(int i = 0; i < tiempo_edible_param.length; i++){
			tiempo_edible_param[i] = new MiTrapecio();
			tiempo_edible_param[i].init();
		}//for

		double tiempo = pacman.game.Constants.EDIBLE_TIME;
		tiempo_edible.setRange(0.0, tiempo);
		tiempo_edible.setEnabled(true);
		tiempo_edible.setName("TiempoEdible");

		String[] valores_tiempo = {"MUY_POCO","POCO" ,"MEDIO", "MUCHO"};
		variables.put("TiempoEdible", valores_tiempo);

		double[] tiempo_aux;
		for(int i = 0; i < valores_tiempo.length;i ++){
			tiempo_aux = tiempo_edible_param[i].getParam();
			tiempo_edible.addTerm(new Trapezoid(valores_tiempo[i], tiempo_aux[0], tiempo_aux[1], tiempo_aux[2], tiempo_aux[3]));
		}//for


		// DISTANCIA POWER PILL MAS CERCANA
		MiTrapecio[] power_pill_param = new MiTrapecio[4];

		for(int i = 0; i < power_pill_param.length; i++){
			power_pill_param[i] = new MiTrapecio();
			power_pill_param[i].init();
		}//for

		distanciaPowerPills.setEnabled(true);
		distanciaPowerPills.setName("DistanciaPowerPill");
		distanciaPowerPills.setRange(0.000, 150.000);

		String[] valores_distancia_power = {"MUY_CERCA", "CERCA", "MEDIA", "LEJOS"};
		variables.put("DistanciaPowerPill", valores_distancia_power);

		double[] distancia_power_aux;
		for(int i = 0; i < valores_distancia_power.length;i ++){
			distancia_power_aux = power_pill_param[i].getParam();
			distanciaPowerPills.addTerm(new Trapezoid(valores_distancia_power[i], distancia_power_aux[0], distancia_power_aux[1], distancia_power_aux[2], distancia_power_aux[3]));
		}//for

		// DISTANCIA A PILL MAS CERCANA
		MiTrapecio[] pill_param = new MiTrapecio[4];

		for(int i = 0; i < pill_param.length; i++){
			pill_param[i] = new MiTrapecio();
			pill_param[i].init();
		}//for

		distanciaPill.setEnabled(true);
		distanciaPill.setName("DistanciaPills");
		distanciaPill.setRange(0.000, 150.000);
		
		String[] valores_distancia_pill = {"MUY_CERCA", "CERCA", "MEDIA", "LEJOS"};
		variables.put("DistanciaPills", valores_distancia_pill);

		double[] distancia_pill_aux;
		for(int i = 0; i < valores_distancia_pill.length;i ++){
			distancia_pill_aux = pill_param[i].getParam();
			distanciaPill.addTerm(new Trapezoid(valores_distancia_power[i], distancia_pill_aux[0], distancia_pill_aux[1], distancia_pill_aux[2], distancia_pill_aux[3]));
		}//for

	}//init

	public Engine meterVariables(Engine motor){
		for(int i = 0; i < inputs.length; i++){
			motor.addInputVariable(inputs[i]);
		}//for

		return motor;
	}//meterVariables

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
