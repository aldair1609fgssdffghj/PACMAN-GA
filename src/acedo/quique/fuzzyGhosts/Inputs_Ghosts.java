package acedo.quique.fuzzyGhosts;

import com.fuzzylite.Engine;
import com.fuzzylite.term.*;
import com.fuzzylite.variable.InputVariable;

public class Inputs_Ghosts {

	private InputVariable distancia = new InputVariable();

	private InputVariable tiempo_edible = new InputVariable();
	
	private InputVariable numeroPowerPills = new InputVariable();
 
	private InputVariable numeroPills = new InputVariable();


	private InputVariable[] inputs = {distancia, tiempo_edible, numeroPowerPills, numeroPills};
	
	public void init(){
		// DISTANCIA A FANTASMA MAS CERCANO
		distancia.setEnabled(true);
		distancia.setName("Distancia");
		distancia.setRange(0.000, 150.000);
		distancia.addTerm(new Ramp("CERCA", 60.000, 15.000));
		distancia.addTerm(new Trapezoid("MEDIA", 30.000, 65.000, 85.000, 120.000));
		distancia.addTerm(new Ramp("LEJOS", 90.000, 135.000));

		// TIEMPO EDIBLE DE LOS FANTASMAS
		double tiempo = pacman.game.Constants.EDIBLE_TIME;
		tiempo_edible.setRange(0.0, tiempo);
		tiempo_edible.setEnabled(true);
		tiempo_edible.setName("TiempoEdible");
		tiempo_edible.addTerm(new Triangle("POCO",3.000, 3.000, tiempo*2/5));
		tiempo_edible.addTerm(new Triangle("MEDIO", tiempo*1/4, tiempo/2, tiempo*3/4));
		tiempo_edible.addTerm(new Ramp("MUCHO", tiempo*3/5, tiempo));
		tiempo_edible.addTerm(new Ramp("MUY_POCO", 3.000, 0.000));
		
		// NUMERO POWER PILLS
		numeroPowerPills.setEnabled(true);
		numeroPowerPills.setName("NumeroPowerPills");
		numeroPowerPills.setRange(0.000, 4.000);
		numeroPowerPills.addTerm(new Rectangle("NINGUNA", 0.000, 0.500));
		numeroPowerPills.addTerm(new Trapezoid("POCAS", 0.500, 1.000, 2.000, 2.500));
		numeroPowerPills.addTerm(new Trapezoid("MUCHAS", 2.000, 2.500, 4.000, 4.000));
		
		// NUMERO PILLS
		numeroPills.setEnabled(true);
		numeroPills.setName("NumeroPills");
		numeroPills.setRange(0.000, 240.000);
		numeroPills.addTerm(new Ramp("POCAS", 80.000, 10.000));
		numeroPills.addTerm(new Ramp("MUCHAS", 160.000, 230.000));
		numeroPills.addTerm(new Trapezoid("A_MEDIAS", 40.000, 100.000, 140.000, 200.000));
	}//init

	public Engine meterVariables(Engine motor){
		for(int i = 0; i < inputs.length; i++){
			motor.addInputVariable(inputs[i]);
		}//for
		
		return motor;
	}//meterVariables
	
	public InputVariable getDistancia() {
		return distancia;
	}//getDistancia

	public InputVariable getTiempoEdible(){
		return tiempo_edible;
	}//getTiempoEdible
	
	public InputVariable getNumeroPowerPills(){
		return numeroPowerPills;
	}//getNumeroPowerPills
	
	public InputVariable getNumeroPills(){
		return numeroPills;
	}//getNumeroPills

}//class
