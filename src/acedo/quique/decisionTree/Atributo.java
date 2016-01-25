package acedo.quique.decisionTree;
/**
 * @author Enrique Acedo
 * @date 20/12/2015
 * @subject Data Mining
 * @version 1.0
 */
import java.util.ArrayList;

import dataRecording.DataTuple;

public enum Atributo {	//DirectionChosen,
	//	mazeIndex,
	currentLevel,
	//	pacmanPosition,
	//	pacmanLivesLeft,
	//	currentScore,
	//	totalGameTime,
	//	currentLevelTime,
	//	numOfPillsLeft,
	//	numOfPowerPillsLeft,
	isBlinkyEdible ,
	isInkyEdible ,
	isPinkyEdible ,
	isSueEdible ,
//	blinkyDist ,
//	inkyDist ,
//	pinkyDist ,
//	sueDist ,
	blinkyDir,
	inkyDir,
	pinkyDir,
	sueDir
	//	numberOfNodesInLevel,
	//	numberOfTotalPillsInLevel,
	//	numberOfTotalPowerPillsInLevel
	;


	private static ArrayList<Atributo> atributos = new ArrayList<Atributo>();
	private static ArrayList<Integer> seleccionados = new ArrayList<Integer>();

	public static Atributo randomAtribute(){
		ArrayList<Atributo> atributos_aux = atributos;

		boolean encontrado = false;
		int rnd = 0;
		if(seleccionados.size() < 4)
			while(!encontrado){
				rnd = (int) (Math.random()*values().length);

				if(!seleccionados.contains(rnd)){
					putAtributo(values()[rnd].name());
					atributos_aux.remove(atributos_aux.indexOf(values()[rnd]));
					encontrado = true;
				}//if
			}//while

		atributos = atributos_aux;
		return values()[rnd];
	}//random


	public static boolean todosSeleccionados(){
		return (seleccionados.size() < 4) ? false : true;
	}

	public static void putAtributo(String atr){
		Atributo[] valores = values();
		int num = -1;

		for(int i = 0; i < valores.length; i++){
			if(valores[i].name() == atr)
				num = i;

		}//for

		seleccionados.add(num);	
		printSeleccionados();
	}

	private static void printSeleccionados(){
		String aux = "";
		for(int i = 0; i < seleccionados.size(); i++)
			aux += values()[seleccionados.get(i)].name() + "\t"; 

		System.out.println("\t"+aux);
	}

	public static void init(){
		for(int i= 0; i<values().length; i++){
			atributos.add(values()[i]);
		}//for
	}//init

	/**
	 * Metodo que coje los valores posibles de un atributo
	 * @param atr
	 * @param data
	 * @return ArrayList<String> con los diferenes valores posibles de atr
	 */
	public static ArrayList<String> valores(Atributo atr, DataTuple[] data){
		ArrayList<String> valores = new ArrayList<String>();

		// Para cada posible atributo hacemos un
		switch (atr) {
		case blinkyDir:
			for(int i = 0; i < data.length; i++){
				if(!valores.contains(data[i].blinkyDir.toString())){
					valores.add(data[i].blinkyDir.toString());
				}//if
			}//for
			break;
		case inkyDir:
			for(int i = 0; i < data.length; i++){
				if(!valores.contains(data[i].inkyDir.toString())){
					valores.add(data[i].inkyDir.toString());
				}//if
			}//for
			break;
		case pinkyDir:
			for(int i = 0; i < data.length; i++){
				if(!valores.contains(data[i].pinkyDir.toString())){
					valores.add(data[i].pinkyDir.toString());
				}//if
			}//for
			break;
		case sueDir:
			for(int i = 0; i < data.length; i++){
				if(!valores.contains(data[i].sueDir.toString())){
					valores.add(data[i].sueDir.toString());
				}//if
			}//for
			break;

		case currentLevel:
			for(int i = 0; i < data.length; i++){
				if(!valores.contains(String.valueOf(data[i].currentLevel))){
					valores.add(String.valueOf(data[i].currentLevel));
				}//if
			}//for
			break;

		case isBlinkyEdible:
			for(int i = 0; i < data.length; i++){
				if(!valores.contains(data[i].isBlinkyEdible)){
					valores.add((data[i].isBlinkyEdible == true) ? "true" : "false");
				}//if
			}//for
			break;

		case isPinkyEdible:
			for(int i = 0; i < data.length; i++){
				if(!valores.contains(data[i].isPinkyEdible)){
					valores.add((data[i].isPinkyEdible == true) ? "true" : "false");
				}//if
			}//for
			break;

		case isInkyEdible:
			for(int i = 0; i < data.length; i++){
				if(!valores.contains(data[i].isInkyEdible)){
					valores.add((data[i].isInkyEdible == true) ? "true" : "false");
				}//if
			}//for
			break;

		case isSueEdible:
			for(int i = 0; i < data.length; i++){
				if(!valores.contains(data[i].isSueEdible)){
					valores.add((data[i].isSueEdible == true) ? "true" : "false");
				}//if
			}//for
			break;
			
			
//		case blinkyDist:
//			for(int i = 0; i < data.length; i++){
//				if(!valores.contains(data[i].discretizeDistance(data[i].blinkyDist))){
//					valores.add(data[i].discretizeDistance(data[i].blinkyDist).toString());
//				}//if
//			}//for
//			break;
//			
//		case pinkyDist:
//			for(int i = 0; i < data.length; i++){
//				if(!valores.contains(data[i].discretizeDistance(data[i].pinkyDist))){
//					valores.add(data[i].discretizeDistance(data[i].pinkyDist).toString());
//				}//if
//			}//for
//			break;
//		case inkyDist:
//			for(int i = 0; i < data.length; i++){
//				
//				if(!valores.contains(data[i].discretizeDistance(data[i].inkyDist))){
//					System.out.println(data[i].discretizeDistance(data[i].sueDist));
//
//					valores.add(data[i].discretizeDistance(data[i].inkyDist).toString());
//				}//if
//			}//for
//			break;
//		case sueDist:
//			for(int i = 0; i < data.length; i++){
//				if(!valores.contains(data[i].discretizeDistance(data[i].sueDist))){
//					System.out.println(data[i].discretizeDistance(data[i].sueDist));
//					valores.add(data[i].discretizeDistance(data[i].sueDist).toString());
//				}//if
//			}//for
//			break;

		default:
			break;
		}//switch

		return valores;
	}//getValues

	/**
	 * Selecciona un nuevo data tuple dado un atributo y un valor
	 * @param data
	 * @param atr
	 * @param valor
	 * @return dataTuple con las tuplas de data cuyo atributo atr = valor
	 */
	public static DataTuple[] seleccionar(DataTuple[] data, Atributo atr, String valor){
		ArrayList<DataTuple> dataAux = new ArrayList<DataTuple>();

		switch (atr) {
		case blinkyDir:
			for(int i = 0; i < data.length; i++){
				if(data[i].blinkyDir.toString() == valor){
					dataAux.add(data[i]);
				}//if
			}//for
			break;
		case inkyDir:
			for(int i = 0; i < data.length; i++){
				if(data[i].inkyDir.name() == valor){
					dataAux.add(data[i]);
				}//if
			}//for
			break;
		case pinkyDir:
			for(int i = 0; i < data.length; i++){
				if(data[i].pinkyDir.toString() == valor){
					dataAux.add(data[i]);
				}//if
			}//for
			break;
		case sueDir:
			for(int i = 0; i < data.length; i++){
				if(data[i].sueDir.toString() == valor){
					dataAux.add(data[i]);
				}//if
			}//for
			break;

		case currentLevel:
			for(int i = 0; i < data.length; i++){
				if((String.valueOf(data[i].currentLevel)).equals(valor)){
					dataAux.add(data[i]);
				}//if
			}//for
			break;

		case isBlinkyEdible:
			for(int i = 0; i < data.length; i++){
				if(data[i].isBlinkyEdible == ((valor.toLowerCase()=="true")?true:false)){
					dataAux.add(data[i]);
				}//if
			}//for
			break;

		case isInkyEdible:
			for(int i = 0; i < data.length; i++){
				if(data[i].isBlinkyEdible == ((valor.toLowerCase()=="true")?true:false)){
					dataAux.add(data[i]);
				}//if
			}//for
			break;

		case isPinkyEdible:
			for(int i = 0; i < data.length; i++){
				if(data[i].isBlinkyEdible == ((valor.toLowerCase()=="true")?true:false)){
					dataAux.add(data[i]);
				}//if
			}//for
			break;

		case isSueEdible:
			for(int i = 0; i < data.length; i++){
				if(data[i].isBlinkyEdible == ((valor.toLowerCase()=="true")?true:false)){
					dataAux.add(data[i]);
				}//if
			}//for
			break;
			
//		case sueDist:
//			for(int i = 0; i < data.length; i++){
//				if(data[i].discretizeDistance(data[i].sueDist).toString() == valor){
//					dataAux.add(data[i]);
//				}//if
//			}//for
//			break;
//			
//		case blinkyDist:
//			for(int i = 0; i < data.length; i++){
//				if(data[i].discretizeDistance(data[i].blinkyDist).toString() == valor){
//					dataAux.add(data[i]);
//				}//if
//			}//for
//			break;
//			
//		case pinkyDist:
//			for(int i = 0; i < data.length; i++){
//				if(data[i].discretizeDistance(data[i].pinkyDist).toString() == valor){
//					dataAux.add(data[i]);
//				}//if
//			}//for
//			break;
//			
//		case inkyDist:
//			for(int i = 0; i < data.length; i++){
//				if(data[i].discretizeDistance(data[i].inkyDist).toString() == valor){
//					dataAux.add(data[i]);
//				}//if
//			}//for
//			break;

		default:
			break;
		}//switch


		DataTuple[] newData = new DataTuple[dataAux.size()];

		for(int i = 0; i < newData.length; i++){
			newData[i] = dataAux.get(i);
		}//for


		return newData;
	}//select
	
	public static String getValor(DataTuple data, Atributo atr){
	
		String valor = "";

		switch (atr) {
		case blinkyDir:
			valor = data.blinkyDir.name();
			break;
		case inkyDir:
			valor = data.inkyDir.name();

			break;
		case pinkyDir:
			valor = data.pinkyDir.name();

			break;
		case sueDir:
			valor = data.sueDir.name();

			break;

		case currentLevel:
			valor = String.valueOf(data.currentLevel);
			break;

		case isBlinkyEdible:
			valor = (data.isBlinkyEdible == true)?"true":"false";
			break;

		case isInkyEdible:
			valor = (data.isInkyEdible == true)?"true":"false";

			break;

		case isPinkyEdible:
			valor = (data.isPinkyEdible == true)?"true":"false";

			break;

		case isSueEdible:
			valor = (data.isSueEdible == true)?"true":"false";

			break;
		}
		
		return valor;
	}//getValor

	public static ArrayList<Atributo> atributos(){
		ArrayList<Atributo> result = new ArrayList<Atributo>();
		
		for(int i = 0; i < values().length; i++){
			result.add(values()[i]);
		}
		
		return result;
	}
}//class