package acedo.quique.fuzzyPacman;

/**
 * Mi_Codificacion.java
 * @author Quique
 * @version 1.0
 * @date 2/12/2015
 */
public class SistemaCodec {

	/**
	 * Codifica el fenotipo formado por palabras a un array de numeros binarios
	 * @param fenotipo
	 * @return array de binarios representando numeros del 0-3 00|01|10|11|.....
	 */
	public static int[] codificar(String[] fenotipo){
		int[] genotipo = new int[fenotipo.length * 2];

		for(int i = 0; i < genotipo.length; i += 2){
			switch (fenotipo[i]) {
			case "HUIR":
				genotipo[i] = 0;
				genotipo[i+1] = 0;
				break;
			case "COMER_POWER":
				genotipo[i] = 0;
				genotipo[i+1] = 1;
				break;
			case "COMER_PILL":
				genotipo[i] = 1;
				genotipo[i+1] = 0;
				break;
			case "ATACAR":
				genotipo[i] = 1;
				genotipo[i+1] = 1;
				break;
			default:
				System.out.println("Error en codificacion del fenotipo");
				break;
			}//switch
		}//for

		return genotipo;
	}//codificar

	/**
	 * Decodifica el genotipo binario a el fenotipo de String
	 * @param genotipo
	 * @return array de String con el fenotipo
	 */
	public static String[] decodificar(int[] genotipo){
		String[] fenotipo = new String[genotipo.length / 2];
		
		for(int i = 0; i < fenotipo.length; i++){
			int gen_Int = binToInteger(genotipo[i*2], genotipo[i*2+1]);
			switch (gen_Int) {
			case 0:
				fenotipo[i] ="HUIR";
				break;
			case 1:
				fenotipo[i] = "COMER_POWER";
				break;
			case 2:
				fenotipo[i] = "COMER_PILL";
				break;
			case 3: 
				fenotipo[i] = "ATACAR";
				break;
			default:
				System.out.println("Error en decodificacion del genotipo");
				break;
			}//switch
		}//for
			
		return fenotipo;
	}//decodificar
	
	private static int binToInteger(int a, int b){
		return (int) Integer.parseUnsignedInt(""+a+b, 2);
	}//binToInteger

}//class
