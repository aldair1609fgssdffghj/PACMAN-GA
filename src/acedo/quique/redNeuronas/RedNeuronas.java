package acedo.quique.redNeuronas;

/**
 * @author Enrique Acedo
 * @date 31/01/2016
 */

import java.util.HashMap;
import java.util.Random;

public class RedNeuronas {

	/** ATRIBUTOS **/
	public static final int NUM_CAPAS = 3;
	public final double C;
	public  HashMap<Integer, Capa> capas;
	public  HashMap<Integer, Conector> conectores;

	private static RedNeuronas red = null;

	/** METODOS 
	 * @param  */
	private RedNeuronas(int num_neuronas_entrada, int num_neuronas_ocultas,int num_tuplas){
		C = 0.01;

		capas = new HashMap<Integer, Capa>();

		capas.put(0, new Capa(0, num_neuronas_entrada));
		capas.put(1, new Capa(1, num_neuronas_ocultas));
		capas.put(2, new Capa(2, 5));

		conectores = new HashMap<Integer, Conector>();

		for(int i= 0; i < capas.size()-1; i++){
			int num_neuronas_capa =capas.get(i).getNeuronas().size();
			int num_neuronas_prox_capa =capas.get(i+1).getNeuronas().size();
			int num_conectores = num_neuronas_capa * num_neuronas_prox_capa;
			conectores.put(i, new Conector(i, i, num_conectores));
		}//for
	}//constructor

	/**
	 * Funcion recursiva que es llamada una vez que se han metido
	 * los valores de entrada y los va propagando capa por capa
	 * @param capa
	 */
	public void propagarValor(int capa){
		if(capa < NUM_CAPAS){
			HashMap<Integer, Neurona> neuronas_entrada = capas.get(capa-1).getNeuronas();
			HashMap<Integer, Peso> pesos = conectores.get(capa-1).getPesos();
			HashMap<Integer, Neurona> neuronas = capas.get(capa).getNeuronas();

			double[] valores = new double[neuronas.size()];

			for(int i = 0; i < neuronas.size(); i++){
				double result = 0;
				for(int j = 0; j < neuronas_entrada.size(); j++){
					double valor_ij = neuronas_entrada.get(j).getValue() ;
					double peso_ij = pesos.get(j*neuronas.size() + i).getValue();
					result += valor_ij * peso_ij;
				}//for2

				result += neuronas.get(i).getUmbral();
				valores[i] = result;
			}//for1

			// Calculamos el valore de salida de las neuronas ocultas
			if(capa > 0 && capa < NUM_CAPAS){
				for(int i = 0; i < valores.length; i++)
					valores[i] = calcularSalidaOcultas(valores[i]);
			}//if

			for(int i = 0; i < valores.length; i++)
				neuronas.get(i).setValue(valores[i]);

			propagarValor(capa+1);
		}//if

	}//propagar

	/** 
	 * Funcion recursiva que se llama una vez se calcula el error en la salida
	 * @param capa
	 */
	public void propagarError(int capa){
		if(capa < NUM_CAPAS && capa > 1){
			HashMap<Integer, Neurona> neuronas_entrada = capas.get(capa-1).getNeuronas();
			HashMap<Integer, Peso> pesos = conectores.get(capa-1).getPesos();
			HashMap<Integer, Neurona> neuronas = capas.get(capa).getNeuronas();

			for(int i = 0; i < neuronas_entrada.size(); i++){
				double result = 0;
				double valor_ij = neuronas_entrada.get(i).getValue();

				for(int j = 0; j < neuronas.size(); j++){
					double peso_ij = pesos.get(i*neuronas.size() + j).getValue();
					double error_k = neuronas.get(j).getError() ;
					result += peso_ij * error_k;
//					System.out.println("SUM += " + peso_ij + " x " + error_k);
				}//for2

				result = valor_ij*(1-valor_ij)*result;
//				System.out.println("ERROR = SUM X (1 - " + valor_ij + ") x " + valor_ij + "\n");


				neuronas_entrada.get(i).setError(result);
			}//for1
			propagarError(capa-1);
		}//if
	}//propagarError

	/**
	 * Procesa los datos de entrada en la red de neuronas
	 * @param valores
	 */
	public void procesarDatosEntrada(double[] valores){

		HashMap<Integer, Neurona> neuronas_entrada = red.capas.get(0).getNeuronas();

		for(int i= 0; i < valores.length; i++)
			neuronas_entrada.get(i).setValue(valores[i]);

		//		printRed();
		red.propagarValor(1);

	}//procesarDatosEntrada

	/**
	 * Calcula el error de las neuronas de salida con el valor esperado
	 * @param valor_esperado
	 */
	public void calcularErrorSalida(double[] valor_esperado){
		HashMap<Integer, Neurona> neuronas = capas.get(NUM_CAPAS-1).getNeuronas();

		for(int i = 0; i < neuronas.size(); i++){
			double valor = neuronas.get(i).getValue();
			double error = valor*(1-valor)*(valor_esperado[i]-valor);
			neuronas.get(i).setError(error);
		}//for

//				pintRed();
		propagarError(NUM_CAPAS-1);
	}//calcularError

	/**
	 * Metodo que actualiza los pesos y umbrales de la red de neuronas
	 */
	public void actualizarRed(){

		for(int i = 1; i < capas.size(); i++){
			HashMap<Integer, Neurona> neuronas = capas.get(i).getNeuronas();
			HashMap<Integer, Peso> pesos = conectores.get(i-1).getPesos();
			HashMap<Integer, Neurona> neuronas_capa_inferior = capas.get(i-1).getNeuronas();

			// Actualizo umbrales
			for(int j = 0; j < neuronas.size(); j++){
				double incremento = C * neuronas.get(j).getError();
				double nuevo_umbral = neuronas.get(j).getUmbral() + incremento;

				neuronas.get(j).setUmbral(nuevo_umbral);
			}//for2

			// Actualizo pesos
			int count = 0;
			for(int j = 0; j < pesos.size(); j++){
				if(j != 0 && j % neuronas.size() == 0)
					count++;

				double incremento = C * neuronas.get(j%neuronas.size()).getError() * neuronas_capa_inferior.get(count).getValue();
				double nuevo_peso = pesos.get(j).getValue() + incremento;

				if(nuevo_peso > 1){
					//					System.out.println("PESO MAYOR QUE 1");
				}
				pesos.get(j).setValue(nuevo_peso);

			}//for2
		}//for1

	}//actualizarRed

	/**
	 * Te muestra por pantalla la red de neuronas
	 */
	public void printRed(){
		String result = "";

		for(int i = 0; i < NUM_CAPAS; i++){
			result += "\n****************************************************************************************************************************************************\n";
			result += " Capa "+ i + "\n";
			result += "----------------------------------------------------------------------------------------------------------------------------------------------------\n\t";

			HashMap<Integer, Neurona> neuronas = capas.get(i).getNeuronas();
			for(int j = 0; j < neuronas.size(); j++){
				result += "Neurona "+ j + "\t\t|  ";
			}//for

			result += "\n\t";

			for(int j = 0; j < neuronas.size(); j++){
				result += "Valor: " + round(neuronas.get(j).getValue(),2) + "\t\t|  ";
			}//for

			result += "\n\t";

			for(int j = 0; j < neuronas.size(); j++){
				result += "Umbral:" + round(neuronas.get(j).getUmbral(), 2) + "\t\t|  ";
			}//for

			result += "\n\t";

			for(int j = 0; j < neuronas.size(); j++){
				result += "Error:" + round(neuronas.get(j).getError(), 2) + "\t\t|  ";
			}//for
			result += "\n----------------------------------------------------------------------------------------------------------------------------------------------------\n\t";

			try{
				Conector conector = conectores.get(i);

				for(int a = 0; a < capas.get(i+1).getNeuronas().size(); a++){
					int count = 0;
					for(int j = 0; j < conector.size(); j++){
						int num_siguiente_capa = capas.get(i+1).getNeuronas().size();
						if(j != 0 && j % num_siguiente_capa == 0)
							count++;
						if(j%num_siguiente_capa == a)
							result += "W" + count +(j%num_siguiente_capa) + ": " + round(conector.getPesos().get(j).getValue(),2) + "\t\t|  ";
					}//for

					result += "\n\t";
				}//for1
			}catch(Exception e){};

		}//for1


		System.out.println(result);
	}//printRed

	/**
	 * Genera random [-1, 1]
	 * @return random double
	 */
	public static double randomValue(){
		double result = 0;
		Random rnd = new Random();

		result =(double) Math.random();

		if(!rnd.nextBoolean())
			result = result*(-1);


		return result;
	}//randomValue

	/**
	 * Te devuelve el valor con el numero de decimales que le indiques
	 * @param value
	 * @param places
	 * @return
	 */
	private static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}//round

	/**
	 * Coge la neurona que mejor valor tenga
	 * @return id mejor neurona
	 */
	public int cogerMejorNeurona(){
		HashMap<Integer, Neurona> neuronas = red.capas.get(RedNeuronas.NUM_CAPAS-1).getNeuronas();


		int best_move = -1;
		double best_value = -1;
		for(int i = 0; i < neuronas.size(); i++){
			double new_value = neuronas.get(i).getValue();

			if(new_value > best_value){
				best_move = i;
				best_value = new_value;
			}//if
		}//for	

		return best_move;
	}//cogerMejorNeurona

	/**
	 * Calcula las salidas de las neuronas
	 * @param Ij
	 * @return 1 / (1 + e^ -Ij)
	 */
	private double calcularSalidaOcultas(double Ij){
		return 1/(1+Math.exp(-Ij));
	}//calcularSalidaOcultas

	/** GETTERS AND SETTERS */
	public static RedNeuronas getRed(int num_neuronas_entrada, int num_neuronas_ocultas, int num_tuplas){
		if(red == null)
			red = new RedNeuronas(num_neuronas_entrada, num_neuronas_ocultas, num_tuplas);
		return red;
	}//getRed

}//class
