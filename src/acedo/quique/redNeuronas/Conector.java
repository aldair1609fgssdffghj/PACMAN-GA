package acedo.quique.redNeuronas;

/**
 * @author Enrique Acedo
 * @date 31/01/2016
 */

import java.util.HashMap;

public class Conector {

	/** ATRIBUTOS **/
	private int id;
	private int capa;
	private HashMap<Integer, Peso> pesos;

	/** METODOS **/
	public Conector(int id, int capa, int size){
		this.id = id;
		this.capa = capa;
		iniciarConectores(size);
	}//constructor

	private void iniciarConectores(int size){
		if(capa != RedNeuronas.NUM_CAPAS - 1){
			pesos = new HashMap<Integer, Peso>();

			for(int i = 0; i < size; i++){
				double value = RedNeuronas.randomValue();
				pesos.put(i, new Peso(value));
			}//for
		}//if
	}//iniciarConectores

	public boolean cambiarPeso(int peso, float valor){


		return true;
	}//cambiar peso

	/** GETTERS AND SETTERS **/
	public HashMap<Integer, Peso> getPesos(){
		return pesos;
	}
	public int getId(){
		return id;
	}
	public int size(){
		return pesos.size();
	}//size()
}//class
