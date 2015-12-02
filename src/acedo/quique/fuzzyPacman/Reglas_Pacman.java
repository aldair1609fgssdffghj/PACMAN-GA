package acedo.quique.fuzzyPacman;

/**
 * Reglas_Pacman.java
 * @author Quique
 * @version 1.0
 * @date 2/12/2015
 */

import java.util.HashMap;
import com.fuzzylite.Engine;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.Minimum;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;

public class Reglas_Pacman {
	
	/** ATRIBUTOS */
	private RuleBlock mis_reglas = new RuleBlock();

	private RuleBlock[] reglas = {mis_reglas}; 

	private HashMap<String, String[]> variables_input;
	
	private String[] reglas_string = new String[256];

	public Reglas_Pacman(HashMap<String, String[]> variables_input){
		this.variables_input = variables_input;
	}//Constructor
	
	
	/**
	 * 
	 * @param engine
	 */
	public void init(Engine engine, int[] genotipo){
		mis_reglas.setEnabled(true);
		mis_reglas.setName("");
		mis_reglas.setConjunction(new Minimum());
		mis_reglas.setDisjunction(new Maximum());
		mis_reglas.setActivation(new Minimum());
		
		Object[] variables = variables_input.keySet().toArray();
		
		SistemaCodec sistema = new SistemaCodec();
		String[] accion = sistema.decodificar(genotipo);
		
		int count = 0;
		String[] input_0 = variables_input.get(variables[0]);
		for(int i0 = 0; i0 < input_0.length; i0++){
			String[] input_1 = variables_input.get(variables[1]);
			for(int i1 = 0; i1 < input_1.length; i1++){
				String[] input_2 = variables_input.get(variables[2]);
				for(int i2 = 0; i2 < input_2.length; i2++){
					String[] input_3 = variables_input.get(variables[3]);
					for(int i3 = 0; i3 < input_3.length; i3++){
						reglas_string[count] = "if " + variables[0].toString() + " is " + input_0[i0] +
											   " and " + variables[1].toString() + " is " + input_1[i1] +
											   " and " + variables[2].toString() + " is " + input_2[i2] +
											   " and " + variables[3].toString() + " is " + input_3[i3] +
											   " then Accion is " + accion[count];
						
						mis_reglas.addRule(Rule.parse(reglas_string[count], engine));
						count++;
					}//for_4
				}//for_3
			}//for_2
		}//for_1
	}//init

	public Engine meterReglas(Engine motor){

		for(int i = 0; i < reglas.length; i++){
			motor.addRuleBlock(reglas[i]);
		}//for

		return motor;
	}//meterVariables

	public RuleBlock getMisReglas() {
		return mis_reglas;
	}//getReglas
	
	public String[] getStringReglas() {
		return reglas_string;
	}//getReglas

}//class
