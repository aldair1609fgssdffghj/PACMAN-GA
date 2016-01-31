package acedo.quique.redNeuronas;

/**
 * @author Enrique Acedo
 * @date 31/01/2016
 */

public class Neurona {

	/** ATRIBUTOS **/
	private int id;
	private double value;
	private double umbral;
	private int capa;
	private double error;

	/** METODOS **/

	/**
	 * Constructor para la capa de entrada
	 * @param id
	 * @param value2
	 */
	public Neurona(int id, double value2,double umbral2, int capa){
		this.value = value2;
		this.id = id;
		
		if(capa != 0)
			this.umbral = umbral2;
		else
			this.umbral = 0;
		
		this.capa = capa;
		this.error = 0;
	}//constructor


	/** GETTERS AND SETTERS **/
	public double getUmbral() {
		return umbral;
	}
	public void setUmbral(double umbral) {
		this.umbral = umbral;
	}
	public int getId() {
		return id;
	}
	public int getCapa() {
		return capa;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double valores) {
		this.value = valores;
	}
	public double getError(){
		return error;
	}
	public void setError(double e){
		error = e;
	}
}//class
