package acedo.quique.redNeuronas;

/**
 * @author Enrique Acedo
 * @date 31/01/2016
 */

import java.util.HashMap;

public class Capa {

	/** ATRIBUTOS **/
	private HashMap<Integer, Neurona> neuronas;
	private int id;


	/** METODOS **/
	public Capa(int id, int num_neuronas){
		this.id = id;
		iniciarNeuronas(num_neuronas);
	}//Constructor

	private void iniciarNeuronas(int n){
		neuronas = new HashMap<Integer, Neurona>();
		for(int i = 0; i < n; i++){
			double value = 0;
			double umbral = RedNeuronas.randomValue();
			
			neuronas.put(i, new Neurona(i, value, umbral, this.id));
		}//for
	}//iniciarNeuronas


	/** GETTERS AND SETTERS **/
	public HashMap<Integer, Neurona> getNeuronas() {
		return neuronas;
	}
	public void setNeuronas(HashMap<Integer, Neurona> neuronas) {
		this.neuronas = neuronas;
	}
	public int getId() {
		return id;
	}

}//class
