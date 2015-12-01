package acedo.quique.fuzzy;

import com.fuzzylite.Engine;
import com.fuzzylite.defuzzifier.Centroid;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.term.Ramp;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.OutputVariable;

public class Outputs_Ghosts {

	private OutputVariable accion = new OutputVariable();
	
	private OutputVariable[] outputs = {accion};

	public void init() {
		accion.setEnabled(true);
		accion.setName("Accion");
		accion.setRange(0.000, 10.000);
		accion.fuzzyOutput().setAccumulation(new Maximum());
		accion.setDefuzzifier(new Centroid(200));
		accion.setDefaultValue(Double.NaN);
		accion.addTerm(new Ramp("HUIR", 5.000, 0.000));
		accion.addTerm(new Triangle("ESPERAR", 2.500, 5.000, 7.500));
		accion.addTerm(new Ramp("ATACAR", 5.000, 10.000));	
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
