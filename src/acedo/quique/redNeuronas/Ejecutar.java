package acedo.quique.redNeuronas;

/**
 * @author Enrique Acedo
 * @date 31/01/2016
 */

import java.util.ArrayList;
import acedo.quique.fuzzyGhosts.Quique_Ghosts;
import dataRecording.DataCollectorController;
import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;
import pacman.Executor;
import pacman.controllers.KeyBoardInput;
import pacman.game.Constants.MOVE;

public class Ejecutar {

	public static void main(String[] args) {

		Executor exec=new Executor();


		// Ejecuto el juego para recoger los datos
				exec.runGameTimed(new DataCollectorController(new KeyBoardInput()),new Quique_Ghosts(),true);

		// Me creo y entreno a la red
		DataTuple[] data = DataSaverLoader.LoadPacManData();

		RedNeuronas[] redes = new RedNeuronas[10];
		int best_red = -1;
		double best_porcentaje = -1;

		for(int j = 1; j < 10; j++){

			RedNeuronas red = RedNeuronas.getRed(10, j, 1000);

			//			System.out.println(" CREANDO  CONJUNTO DE DATOS....");
			DataTuple[] data_bueno = crearData(data);

			for(int i = 0; i < data_bueno.length; i++){
				//Procesamos los datos de entrada
				//				System.out.println(" PROCESANDO DATOS "+i+ " ....");
				red.procesarDatosEntrada(getDatos(data_bueno[i]));

				// Calculamos el error
				double[] valor_esperado=crearValorEsperado(data_bueno[i].DirectionChosen);

				//				System.out..println(" CALCULANDO ERROR "+i+ " ....");
				red.calcularErrorSalida(valor_esperado);

				//				System.out.println(" ACTUALIZACION "+i+ " ....");
				// Actualizamos los pesos y los umbrales
				red.actualizarRed();
				//				System.out.println("Procesado " + i);

				//				sum_aciertos += evaluarRed(data, red);
			}//for
			//			red.printRed();

			double porcentaje = (evaluarRed(data, red)*100/data.length);

			if(porcentaje > best_porcentaje){
				best_porcentaje = porcentaje;
				best_red = j;
			}//if

			System.out.println(j + " ---> " + porcentaje + "%");

			redes[j] = red;
			// Lanzo el juego con el controllador RedNeuronasController
		}//for

		RedNeuronas red = redes[best_red];

		exec.runGameTimed(new RedNeuronasController(red),new Quique_Ghosts(),true);
	}//main

	private static DataTuple[] crearData(DataTuple[] data) {
		int cont_N = 0;
		int cont_U = 0;
		int cont_R = 0;
		int cont_D = 0;
		int cont_L = 0;

		int tam = data.length/5*3;

		ArrayList<DataTuple> tuplas = new ArrayList<DataTuple>();
		for(int i = 0; i < data.length; i++){
				if(data[i].DirectionChosen.name() == "NEUTRAL"){
					if(cont_N <= tam / 5){
						tuplas.add(data[i]);
						cont_N++;
					}//if3
				}//if2

				if(data[i].DirectionChosen.name() == "UP"){
					if(cont_U <= tam / 5){
						tuplas.add(data[i]);
						cont_U++;
					}//if3
				}//if2

				if(data[i].DirectionChosen.name() == "RIGHT"){
					if(cont_R <= tam / 5){
						tuplas.add(data[i]);
						cont_R++;
					}//if3
				}//if2

				if(data[i].DirectionChosen.name() == "LEFT"){
					if(cont_L <= tam / 5){
						tuplas.add(data[i]);
						cont_L++;
					}//if3
				}//if2

				if(data[i].DirectionChosen.name() == "DOWN"){
					if(cont_D <= tam / 5){
						tuplas.add(data[i]);
						cont_D++;
					}//if3
				}//if2
		}//for

		return toDataTuple(tuplas.toArray());
	}//crearData
	
	private static DataTuple[] toDataTuple(Object[] obj){
		DataTuple[] data = new DataTuple[obj.length];
		
		for(int i = 0; i < obj.length; i++){
			data[i] = (DataTuple)obj[i];
		}//for
		
		return data;
	}

	private static double[] getDatos(DataTuple data) {
		double pinkyDist, inkyDist, blinkyDist, sueDist;
		double pinkyEdible, inkyEdible, blinkyEdible, sueEdible;
		double powerPills, pills;

		pinkyDist = data.normalizeDistance(data.pinkyDist);
		inkyDist = data.normalizeDistance(data.inkyDist);
		blinkyDist = data.normalizeDistance(data.blinkyDist);
		sueDist = data.normalizeDistance(data.sueDist);

		pinkyEdible = data.normalizeBoolean(data.isPinkyEdible);
		inkyEdible = data.normalizeBoolean(data.isInkyEdible);
		blinkyEdible = data.normalizeBoolean(data.isBlinkyEdible);
		sueEdible = data.normalizeBoolean(data.isSueEdible);

		pills = data.normalizeNumberOfPills(data.numOfPillsLeft);
		powerPills = data.normalizeNumberOfPowerPills(data.numOfPowerPillsLeft);

		double[] datos_entrada = {pinkyDist,  inkyDist, blinkyDist, sueDist,pinkyEdible, 
				inkyEdible, blinkyEdible, sueEdible,powerPills, pills};

		return datos_entrada;
	}//getDatos

	/**
	 * 0 --> NEUTRAL
	 * 1 --> UP
	 * 2 --> RIGTH
	 * 3 --> DOWN
	 * 4--> LEFT
	 * @param move
	 * @return
	 */
	private static double[] crearValorEsperado(MOVE move){
		double[] result = new double[5];
		switch (move.toString().toLowerCase()) {
		case "neutral":
			result[0] = 1.0;
			break;

		case "up":
			result[1] = 1.0;
			break;

		case "rigth":
			result[2] = 1.0;
			break;

		case "down":
			result[3] = 1.0;
			break;

		case "left":
			result[4] = 1.0;
			break;

		default:
			break;
		}

		return result;
	}//crearValorEsperado

	private static double evaluarRed(DataTuple[] data, RedNeuronas red){
		int aciertos = 0;

		for(int i = 0; i < data.length; i++){
			//Procesamos los datos de entrada
			red.procesarDatosEntrada(getDatos(data[i]));

			//Vemos si acierta
			double[] valor_esperado=crearValorEsperado(data[i].DirectionChosen);
			int valor = red.cogerMejorNeurona();

			if(valor_esperado[valor] == 1.0){
				aciertos++;
				//				System.out.println("Acierto en " + valor);
			}
		}//for


		return aciertos;// + "/" + data.length + " ---> " + porcentaje + "%";
	}//evaluarRed
}//class
