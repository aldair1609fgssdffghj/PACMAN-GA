package acedo.quique.fuzzyPacman;

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

	/** ATRIBUTOS */
	private OutputVariable accion = new OutputVariable();

	private OutputVariable[] outputs = {accion};

	/** METODOS */

	/**
	 * Metodo que inicializa las variables output
	 */
	public void init() {
		// 1) Me creo los trapecios que van a dar forma a la grafica
		MiTrapecio[] accion_param = new MiTrapecio[4];

		// 2) Los inicializo
		for(int i = 0; i < accion_param.length; i++){
			accion_param[i] = new MiTrapecio();
		}//for

		// 3) Meto los valores
		accion_param[0].setParam(0.000, 0.000, 0.000, 3.500);
		accion_param[1].setParam(1.500, 3.500, 3.500, 5.500);
		accion_param[2].setParam(4.400, 6.500, 6.500, 8.500);
		accion_param[3].setParam(6.500, 10.000, 10.000, 10.000);
		
		// 4) Inicializo la variable input
		accion.setEnabled(true);
		accion.setName("Accion");
		accion.setRange(0.000, 10.000);
		accion.fuzzyOutput().setAccumulation(new Maximum());
		accion.setDefuzzifier(new Centroid(200));
		accion.setDefaultValue(0.00);

		// 5) Creo las etiquetas de los nombres 
		String[] valores_accion = {"HUIR", "COMER_POWER", "COMER_PILL", "ATACAR"};

		// 6) Añado los terminos al input
		double[] acccion_aux;
		for(int i = 0; i < valores_accion.length;i ++){
			acccion_aux = accion_param[i].getParam();
			accion.addTerm(new Trapezoid(valores_accion[i], acccion_aux[0], acccion_aux[1], acccion_aux[2], acccion_aux[3]));
		}//for

	}//init

	/**
	 * Mete las variables en el motor
	 * @param motor
	 * @return el motor con las variables metidas
	 */
	public Engine meterVariables(Engine motor){

		for(int i = 0; i < outputs.length; i++){
			motor.addOutputVariable(outputs[i]);
		}//for

		return motor;
	}//meterVariables

	/** GETTERS AND SETTERS */
	public OutputVariable getAccion() {
		return accion;
	}//getAccion

}//class
