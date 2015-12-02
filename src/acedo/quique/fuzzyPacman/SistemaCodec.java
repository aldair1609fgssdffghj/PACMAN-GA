package acedo.quique.fuzzyPacman;

/**
 * Mi_Codificacion.java
 * @author Quique
 * @version 1.0
 * @date 2/12/2015
 */
public class SistemaCodec {

	public int[] codificar(String[] fenotipo){
		int[] genotipo = new int[fenotipo.length];

		for(int i = 0; i < fenotipo.length; i++){
			switch (fenotipo[i]) {
			case "HUIR":
				genotipo[i] = 0;
				break;
			case "COMER":
				genotipo[i] = 1;
				break;
			case "ATACAR":
				genotipo[i] = 2;
				break;
			default:
				genotipo[i] = 0;
				break;
			}//switch
		}//for

		return genotipo;
	}//codificar

	public String[] decodificar(int[] genotipo){
		String[] fenotipo = new String[genotipo.length];
		
		for(int i = 0; i < genotipo.length; i++){
			switch (genotipo[i]) {
			case 0:
				fenotipo[i] ="HUIR";
				break;
			case 1:
				fenotipo[i] = "COMER";
				break;
			case 2:
				fenotipo[i] = "ATACAR";
				break;
			default:
				fenotipo[i] = "HUIR";
				break;
			}//switch
		}//for
			
		return fenotipo;
	}//decodificar

}//class
