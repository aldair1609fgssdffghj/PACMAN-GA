package acedo.quique.fuzzy;

/**
 * Outputs_Pacman.java
 * @author Quique
 * @version 1.0
 * @date 2/12/2015
 */

import com.fuzzylite.Engine;
import com.fuzzylite.defuzzifier.Centroid;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.term.Trapezoid;
import com.fuzzylite.variable.OutputVariable;

public class Outputs_Pacman {
	
	private OutputVariable accion = new OutputVariable();
	
	private OutputVariable[] outputs = {accion};


	public void init() {
		MiTrapecio[] accion_param = new MiTrapecio[3];

		for(int i = 0; i < accion_param.length; i++){
			accion_param[i] = new MiTrapecio();
			accion_param[i].init();
		}//for


		accion.setEnabled(true);
		accion.setName("Accion");
		accion.setRange(0.000, 10.000);
		accion.fuzzyOutput().setAccumulation(new Maximum());
		accion.setDefuzzifier(new Centroid(200));
		accion.setDefaultValue(0.00);

		String[] valores_accion = {"HUIR", "COMER", "ATACAR"};
		
		double[] acccion_aux;

		for(int i = 0; i < valores_accion.length;i ++){
			acccion_aux = accion_param[i].getParam();
			accion.addTerm(new Trapezoid(valores_accion[i], acccion_aux[0], acccion_aux[1], acccion_aux[2], acccion_aux[3]));
		}//for

	}//init

	public Engine meterVariables(Engine motor){

		for(int i = 0; i < outputs.length; i++){
			motor.addOutputVariable(outputs[i]);
		}//for

		return motor;
	}//meterVariables

	public OutputVariable getAccion() {
		return accion;
	}//getAccion

}//class
