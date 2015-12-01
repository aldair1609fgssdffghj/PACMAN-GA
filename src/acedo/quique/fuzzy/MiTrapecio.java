package acedo.quique.fuzzy;

public class MiTrapecio {


	double[] param = new double[4];

	public MiTrapecio(double uno, double dos, double tres, double cuatro){
		this.param[0] = uno;
		this.param[1] = dos;
		this.param[2] = tres;
		this.param[3] = cuatro;
	}//Constructor

	public void init(){
		for(int i = 0; i < param.length; i++){
			param[i] = 0;
		}
	}//init


	/* GETTERS AND SETTERS */
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