package acedo.quique.redNeuronas;

public class Neurona {

	/** ATRIBUTOS **/
	private int id;
	private float value;
	private float umbral;
	private int capa;

	/** METODOS **/

	/**
	 * Constructor para la capa de entrada
	 * @param id
	 * @param value
	 */
	public Neurona(int id, float value,float umbral, int capa){
		this.value = value;
		this.id = id;
		this.umbral = umbral;
		this.capa = capa;
	}//constructor


	/** GETTERS AND SETTERS **/
	public float getUmbral() {
		return umbral;
	}
	public void setUmbral(float umbral) {
		this.umbral = umbral;
	}
	public int getId() {
		return id;
	}
	public int getCapa() {
		return capa;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
}//class
