package acedo.quique.redNeuronas;

/**
 * @author Enrique Acedo
 * @date 31/01/2016
 */

public class Peso {

	/** ATRIBUTOS **/
	private double value;
	
	/** METODOS **/
	public Peso (double value){
		this.value = value;
	}//constructor
	
	/** GETTERS AND SETTERS **/
	public double getValue(){
		return value;
	}
	public void setValue(double value){
		this.value = value;
	}
}//class
