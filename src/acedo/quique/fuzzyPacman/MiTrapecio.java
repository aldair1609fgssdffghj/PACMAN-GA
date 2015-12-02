package acedo.quique.fuzzyPacman;

/**
 * Mi_Trapecio.java
 * @author Quique
 * @version 1.0
 * @date 2/12/2015
 */

public class MiTrapecio {

	double[] param = new double[4];

	
	public MiTrapecio(){};//Constructor default
	
	public MiTrapecio(double uno, double dos, double tres, double cuatro){
		this.param[0] = uno;
		this.param[1] = dos;
		this.param[2] = tres;
		this.param[3] = cuatro;
	}//Constructor
	
	public void setTrapecio(double uno, double dos, double tres, double cuatro){
		this.param[0] = uno;
		this.param[1] = dos;
		this.param[2] = tres;
		this.param[3] = cuatro;
	}//setTrapecio

	/**
	 * Inicializa los valores a 0
	 */
	public void init(){
		for(int i = 0; i < param.length; i++){
			param[i] = 0;
		}
	}//init

	/**
	 * Inicializa los valores random
	 */
	public void initRandom(){
		for(int i = 0; i < param.length; i++){
			param[i] = 0;
		}//for
	}//initRandom


	/** GETTERS AND SETTERS */
	public double[] getParam() {
		return param;
	}

	public void setParam(double uno, double dos, double tres, double cuatro) {
		this.param[0] = uno;
		this.param[1] = dos;
		this.param[2] = tres;
		this.param[3] = cuatro;
	}

}//class