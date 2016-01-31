package acedo.quique.redNeuronas;

import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

/**
 * @author Enrique Acedo
 * @date 31/01/2016
 */

public class TestRedNeuronas {

	public static void main(String[] args) {


//		RedNeuronas  red = new RedNeuronas(10,4);
//				
//		double[] datos = {0.40, 0.32, 0.76, 0.98, 0.43, 0.40, 0.32, 0.76, 0.98, 0.43};
//		red.procesarDatosEntrada(datos);
//
////		red.printRed();
//
//		
//		double[] valor_esperado = {0,0,1,0,0};
//		
//		red.calcularErrorSalida(valor_esperado);
//		red.printRed();
//		red.actualizarRed();
////		red.printRed();

		
		// Me creo y entreno a la red
				DataTuple[] data = DataSaverLoader.LoadPacManData();

				RedNeuronas[] redes = new RedNeuronas[16];
				int best_red = -1;
				double best_porcentaje = -1;

				DataTuple[] data_bueno = data;//crearData(data);

				for(int j = 1; j < redes.length; j++){

					RedNeuronas red = new RedNeuronas(10, j);

					//			System.out.println(" CREANDO  CONJUNTO DE DATOS....");

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
//					red.printRed();
//					System.out.println("------------------------------------------------\n\n\n");
				}//for
				
				
				RedNeuronas red = redes[best_red];
				
				for(int i = 0; i < data.length; i++){
					
					double[] datos_entrada = getDatos(data[i]);
					String datos = "";
					for(int j = 0; j < datos_entrada.length; j++){
						datos+= datos_entrada[j] + " - ";
					}
					
					red.procesarDatosEntrada(datos_entrada);

					System.out.println(datos);
					
//					red.printRed();
					
					int best_move = red.cogerMejorNeurona();

					System.out.println(datos + "\t" + best_move);
					
				}//for

		
	}//main
	
	
	

	
	private static DataTuple[] toDataTuple(Object[] obj){
		DataTuple[] data = new DataTuple[obj.length];

		for(int i = 0; i < obj.length; i++){
			data[i] = (DataTuple)obj[i];
		}//for

		return data;
	}

	/**
	 * Crea un array con los datos normalizados que nos interesan.
	 * @param data
	 * @return
	 */
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
	 * Crea un array con los valores esperados dado un movimiento
	 * 0 --> NEUTRAL
	 * 1 --> UP
	 * 2 --> RIGHT
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

		case "right":
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

	/**
	 * Evalua la red con los datos que le pasan
	 * Calcula el numero de aciertos
	 * @param data
	 * @param red
	 * @return
	 */
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
