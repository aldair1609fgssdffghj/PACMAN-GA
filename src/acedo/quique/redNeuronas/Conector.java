package acedo.quique.redNeuronas;

import java.util.HashMap;

public class Conector {

	/** ATRIBUTOS **/
	private int id;
	private int capa;
	private HashMap<Integer, Peso> pesos;

	/** METODOS **/
	public Conector(int id, int capa){
		this.id = id;
		this.capa = capa;
		iniciarConectores();
	}//constructor

	private void iniciarConectores(){
		if(capa != RedNeuronas.NUM_CAPAS - 1){
			pesos = new HashMap<Integer, Peso>();
			
			int num_neuronas_capa = RedNeuronas.capas.get(capa).getNeuronas().size();
			int num_neuronas_prox_capa = RedNeuronas.capas.get(capa+1).getNeuronas().size();
			
			int num_conectores = num_neuronas_capa * num_neuronas_prox_capa;
			for(int i = 0; i < num_conectores; i++){
				float value = 0;
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
}//class
