package acedo.quique.redNeuronas;

/**
 * @author Enrique Acedo
 * @date 31/01/2016
 */

public class TestRedNeuronas {

	public static void main(String[] args) {


		RedNeuronas  red = RedNeuronas.getRed(10,4,10);
				
		double[] datos = {0.40, 0.32, 0.76, 0.98, 0.43, 0.40, 0.32, 0.76, 0.98, 0.43};
		red.procesarDatosEntrada(datos);

//		red.printRed();

		
		double[] valor_esperado = {0,0,1,0,0};
		
		red.calcularErrorSalida(valor_esperado);
		red.printRed();
		red.actualizarRed();
//		red.printRed();

	}//main

}//class
