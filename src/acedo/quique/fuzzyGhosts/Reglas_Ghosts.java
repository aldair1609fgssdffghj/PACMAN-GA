package acedo.quique.fuzzyGhosts;

import com.fuzzylite.Engine;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.Minimum;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;

public class Reglas_Ghosts {
	private RuleBlock mis_reglas = new RuleBlock();

	private RuleBlock[] reglas = {mis_reglas}; 

	public void init(Engine engine){
		mis_reglas.setEnabled(true);
		mis_reglas.setName("");
		mis_reglas.setConjunction(new Minimum());
		mis_reglas.setDisjunction(new Maximum());
		mis_reglas.setActivation(new Minimum());

		mis_reglas.addRule(Rule.parse("if NumeroPowerPills is not NINGUNA  and TiempoEdible is MUCHO or TiempoEdible is MEDIO then Accion is HUIR", engine));
		mis_reglas.addRule(Rule.parse("if NumeroPowerPills is NINGUNA and TiempoEdible is MUCHO or TiempoEdible is MEDIO  then Accion is ATACAR", engine));
		mis_reglas.addRule(Rule.parse("if NumeroPowerPills is NINGUNA and TiempoEdible is POCO and Distancia is CERCA then Accion is ESPERAR", engine));
		mis_reglas.addRule(Rule.parse("if NumeroPowerPills is NINGUNA and TiempoEdible is POCO and Distancia is not CERCA then Accion is ATACAR", engine));

		mis_reglas.addRule(Rule.parse("if TiempoEdible is POCO and Distancia is LEJOS then Accion is ATACAR", engine));
		mis_reglas.addRule(Rule.parse("if TiempoEdible is POCO and Distancia is MEDIA and NumeroPills is POCAS then Accion is ATACAR", engine));
		mis_reglas.addRule(Rule.parse("if TiempoEdible is POCO and Distancia is MEDIA and NumeroPills is MUCHAS then Accion is HUIR", engine));
		mis_reglas.addRule(Rule.parse("if TiempoEdible is POCO and Distancia is CERCA and NumeroPowerPills is not NINGUNA then Accion is HUIR", engine));

		mis_reglas.addRule(Rule.parse("if TiempoEdible is MUY_POCO and NumeroPills is not POCAS and Distancia is not LEJOS then Accion is ATACAR", engine));
		mis_reglas.addRule(Rule.parse("if TiempoEdible is MUY_POCO and NumeroPills is not POCAS and Distancia is LEJOS then Accion is ESPERAR", engine));
		mis_reglas.addRule(Rule.parse("if TiempoEdible is MUY_POCO and NumeroPills is POCAS then Accion is ATACAR", engine));
		mis_reglas.addRule(Rule.parse("if TiempoEdible is MUY_POCO and NumeroPowerPills is MUCHAS and Distancia is LEJOS then Accion is ESPERAR", engine));
		mis_reglas.addRule(Rule.parse("if TiempoEdible is MUY_POCO and NumeroPowerPills is MUCHAS and Distancia is not LEJOS then Accion is ATACAR", engine));

		mis_reglas.addRule(Rule.parse("if TiempoEdible is MUY_POCO and NumeroPowerPills is not MUCHAS then Accion is ATACAR", engine));

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

}//class
