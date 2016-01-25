package acedo.quique.decisionTree;
/**
 * @author Enrique Acedo
 * @date 20/12/2015
 * @subject Data Mining
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import dataRecording.DataTuple;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class DecisionTree {

	private DataTuple[] original_data = null;

	public DecisionTree(DataTuple[] data){
		original_data = data;
	}//constructor

	/**
	 * Metodo que a partir de un dataTuple y unos atributos genera un nodo
	 * con sus nodos hijos y todo lo necesario para construir un arbol de decision
	 * Metodo recursivo
	 * @param data
	 * @param atributos
	 * @return nuevo nodo con sus hijos
	 */
	public Atributo atr;

	public SimpleNode build(DataTuple[] data, ArrayList<Atributo> atributos){
		ArrayList<Atributo> atributos_i;
		SimpleNode nodo = null;
		if(data.length > 0){
			//Cojo atributo y nueva lista de atributos
			if(atributos.size() > 0 ){
				// Como no funciona bien lo he comentado y elijo el primre atributo de la lista atributo
				//atr = elegirMejorAtributo(data, atr, atributos);
				atr = atributos.get(0);
				atributos_i = new ArrayList<Atributo>();
				for(int i = 0; i < atributos.size(); i++){
					if(atributos.get(i) != atr)
						atributos_i.add(atributos.get(i));
				}//for
			}else{
				// SI NO QUEDAN MAS ATRIBUTOS
				String clase = getClass(data);

				if(clase == null){
					clase = getBestClass(data);
				}//if

				nodo = new SimpleNode(clase);
				return nodo;
			}//if-else

			// SI TODOS TIENEN LA MISMA CLASE
			if(haveSameClass(data)){
				nodo = new SimpleNode(getClass(data));
				return nodo;
			}//if

			ArrayList<String> valores = getValues(atr, data);

			// SI el atributo tiene valores
			if(valores.size() > 0){
				// Creo el nodo
				nodo = new SimpleNode(atr.name());
				// Creo un array de nodos hijos
				SimpleNode[] nodos = new SimpleNode[valores.size()];

				// Por cada nodo hijo
				for(int i = 0; i < nodos.length; i++){
					// Selecciono el nuevo dataTuple 
					DataTuple[] data_i = select(data, atr, valores.get(i));
					// Construyo el arbol con el conjunto dado
					nodos[i] = build(data_i,atributos_i);
				}//for

				// Añado los hijos al nodo padre
				for(int i = 0; i < nodos.length; i++){
					nodo.addChild(valores.get(i), nodos[i]);
				}//for
			}else {
				// SI no tiene valores
				nodo = new SimpleNode(getRandomClass());
			}//if-else_2
		}else{
			//SI DATA.length < 0
			nodo = new SimpleNode(getRandomClass());
		}//if-else_1

		return nodo;
	}//build

/**
 * NO IMPLEMENTADO
 * @param game
 * @param tree
 * @return
 */
	public MOVE decide(Game game, SimpleNode tree) {

		String[] valores = new String[5];

		valores[0] = String.valueOf(game.getCurrentLevel());
		valores[1] = String.valueOf(game.getGhostLastMoveMade(GHOST.BLINKY));
		valores[2] = String.valueOf(game.getGhostLastMoveMade(GHOST.INKY));
		valores[3] = String.valueOf(game.getGhostLastMoveMade(GHOST.PINKY));
		valores[4] = String.valueOf(game.getGhostLastMoveMade(GHOST.SUE));

		for(int i = 0; i < valores.length; i++){
			if(!tree.isLeaf()){
				TreeMap<String, SimpleNode>hijos = tree.getChildren();
				for(int j = 0; j < hijos.size(); j++){
					//					System.out.println(hijos.);
				}//for
			}else{

			}//if-else

		}//for

		return null;
	}//decide

	/**
	 * Metodo que comprueba que haya mas de una clase en las tuplas
	 * @param data
	 * @return TRUE si todas las tuplas tienen la misma clase
	 * @return FALSE si hay varias clases en las tuplas
	 */
	private boolean haveSameClass(DataTuple[] data){
		boolean result = true;
		MOVE clase = null;

		if(data.length > 0) {
			clase = data [0].DirectionChosen;

			for(int i = 0; i < data.length && result; i++)
				if(data[i].DirectionChosen != clase)
					result = false;
		}//if


		return result;
	}//haveSameClass

	/**
	 * Metodo que selecciona la clase mas repetida en de data
	 * @param data
	 * @return nombre de la clase mas repetida
	 */
	private String getBestClass(DataTuple[] data){
		HashMap<String, Integer> clases = new HashMap<String, Integer>();

		for(int i = 0; i < data.length; i++){
			String actual = data[i].DirectionChosen.toString();
			if(clases.containsKey(actual)){
				Integer num_veces = clases.get(actual);
				clases.remove(actual);
				clases.put(actual, num_veces+1);
			}else{
				clases.put(actual, 1);
			}//if-else
		}//for1

		Object [] clases_aux = clases.keySet().toArray();
		int best_class = -1;
		int best_count = -1;

		for(int i = 0; i < clases_aux.length; i++){
			if(clases.get(clases_aux[i]) > best_count){
				best_class = i;
				best_count = clases.get(clases_aux[i]);
			}
		}//for

		return clases_aux[best_class].toString();
	}//haveSameClass

	/**
	 * Devuelve la clase en caso de ser todas iguales 
	 * @param data
	 * @return nombre de la clase si todas las tuplas tienen la misma clase
	 * @return NULL si alguna clase no coincide
	 */
	private String getClass(DataTuple[] data){
		boolean result = true;
		MOVE clase = null;

		if(data.length > 0) {
			clase = data [0].DirectionChosen;

			for(int i = 0; i < data.length && result; i++)
				if(data[i].DirectionChosen != clase)
					result = false;
		}//if

		return clase.toString();
	}//haveSameClass

	/**
	 * Metodo que coje los valores posibles de un atributo
	 * @param atr
	 * @param data
	 * @return ArrayList<String> con los diferenes valores posibles de atr
	 */
	private ArrayList<String> getValues(Atributo atr, DataTuple[] data){
		return Atributo.valores(atr,data);
	}//getValues


	/**
	 * Selecciona un nuevo data tuple dado un atributo y un valor
	 * @param data
	 * @param atr
	 * @param valor
	 * @return dataTuple con las tuplas de data cuyo atributo atr = valor
	 */
	private DataTuple[] select(DataTuple[] data, Atributo atr, String valor){
		return Atributo.seleccionar(data, atr, valor);
	}//select


	/**
	 * Selecciona una clase aleatoria del dataTuple original
	 * @return clase random
	 */
	private String getRandomClass(){
		int rnd = (int) (Math.random()*original_data.length);
		return original_data[rnd].DirectionChosen.name();
	}//getRandomClass


	/**
	 * Calcula la entropia del conjunto
	 * @param data
	 * @return entropia
	 */
	public Double entropia(DataTuple[] data) {
		Map<String, Double> frecuencias = new HashMap<String, Double>();
		Double result = 0.0;

		// Calculamos las frecuencias
		for (DataTuple tupla : data) {
			String valor_atributo = Atributo.getValor(tupla, atr);
			if (frecuencias.containsKey(valor_atributo)) {
				frecuencias.put(valor_atributo, frecuencias.get(valor_atributo)+1);
			} else {
				frecuencias.put(valor_atributo, 1.0);
			}//if-else
		}//for

		// Calculamos la entropia
		for (Double frecuencia : frecuencias.values()) {
			result += (-frecuencia/data.length) * (Math.log(frecuencia/data.length) / Math.log(2));
		}//for

		return result;
	}//entropia


	/**
	 * Metodo que calcula la ganancia del atributo en el conjunto
	 * @param data
	 * @param atributo
	 * @return ganancia
	 */
	public Double calcularGanancia(DataTuple[] data, Atributo atributo) {
		double result;
		Map<String, Double> frecuencia = new HashMap<String, Double>();
		Double entropia_i = 0.0;

		//Calculamos las frecuencias
		for (DataTuple tupla : data) {
			String valor = Atributo.getValor(tupla, atributo);

			if (frecuencia.containsKey(valor)) {
				frecuencia.put(valor, frecuencia.get(valor)+1);
			} else {
				frecuencia.put(valor, 1.0);
			}//if-else

		}//for

		// Calculamos la suma de las entropias para cada subconjunto
		for (String valor : frecuencia.keySet()) {

			// Calculamos la probabilidad
			double probabilidad = frecuencia.get(valor) / data.length;

			// Creamos el subconjunto
			DataTuple[] data_i = Atributo.seleccionar(data, atributo, valor);

			// Añadimos a la entropia total la probablidad x la entropia del subconjunto
			entropia_i += probabilidad * entropia(data_i);

		}

		result = entropia(data) - entropia_i;
		return result;
	}

	/**
	 * Elige el mejor atributo de un Dataset
	 * @param data
	 * @param atributo
	 * @param atributos
	 * @return
	 */
	public Atributo elegirMejorAtributo (DataTuple[] data, Atributo atributo, ArrayList<Atributo> atributos) {
		Atributo mejorAtributo = null;
		double mejorGanancia = 0;

		//Para cada atributo
		for (int i = 0; i < atributos.size(); i++){
			Atributo atr = atributos.get(i);

			if (atr.name() != atributo.name()) {

				//Calculamos la ganancia
				Double ganancia = calcularGanancia(data, atr);

				// Si es mayor que la mejor, actualizamos
				if (ganancia > mejorGanancia) {
					mejorGanancia = ganancia;
					mejorAtributo = atr;
				}//if
			}//if

		}//for

		return mejorAtributo;
	}//elegirMejorAtributo

}//class
