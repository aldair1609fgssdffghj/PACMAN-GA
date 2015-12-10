package acedo.quique.fuzzyPacman;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import acedo.quique.GA.Gene;
import acedo.quique.GA.GeneticAlgorithm;

/**
 * Test.java
 * @author Quique
 * @version 1.0
 * @date 2/12/2015
 */

public class Test {

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {

		// Scanner para leer por teclado
		Scanner entradaTeclado = new Scanner(System.in);

		// Le pregunto cuanta poblacion inicial desea
		System.out.println("Cuanta poblacion inicial desea?");
		int poblacion_size = entradaTeclado.nextInt();

		// Creo el algoritmo con el tamaño introducido por pantalla
		GeneticAlgorithm genAl = new GeneticAlgorithm(poblacion_size);



		Iterator<Gene> poblacion = genAl.getPopulation().iterator();
		int[] genotipo;
		Quique_Pacman controladorQuique;
		int opcion;
		int count = 1;
		boolean simular = false;

		// Recorro el iterador de la poblacion
		while(poblacion.hasNext()){
			boolean terminado = false;
			System.out.println("\tGenerando genotipo "+ count++ +"...");
			Gene gen = poblacion.next();
			genotipo = gen.getChromosome();
			controladorQuique = new Quique_Pacman();
			controladorQuique.init(genotipo);

			while(!terminado && !simular){
				System.out.println("\t\tOpciones:");
				System.out.println("\t\t\t[1] Mostrar Genotipo:");
				System.out.println("\t\t\t[2] Mostrar Fenotipo:");
				System.out.println("\t\t\t[3] Siguiente Genotipo:");
				System.out.println("\t\t\t[0] Simular resto:");

				opcion = entradaTeclado.nextInt();

				if(opcion == 1){
					System.out.println(gen.genotipoToString());
				}else if(opcion == 2){
					System.out.println(controladorQuique.fenotipoToString());
				}else if(opcion == 3){
					terminado = true;
				}else if(opcion == 0){
					simular = true;
				}else{
					System.out.println("Opcion incorrecta, eliga otra");
				}//if-else
			}//while
		}//while

		// TEST DE REPRODUCCION
		System.out.println("\n\nGenes:");
		for(int i = 0; i < genAl.size(); i++){
			System.out.println("- " +i);
		}//for

		System.out.println("\t¿Que genes desea reproducir?");
		int gen1 = entradaTeclado.nextInt();
		int gen2 = entradaTeclado.nextInt();

		genAl.getGene(gen1).reproduce(genAl.getGene(gen2));


		// TEST DE MUTACION
		System.out.println("\n\nGenes:");
		for(int i = 0; i < genAl.size(); i++){
			System.out.println("- " +i);
		}//for

		System.out.println("\t¿Que genes desea mutar?");
		int gen3 = entradaTeclado.nextInt();

		genAl.getGene(gen3).mutate();

		entradaTeclado.close();
	}//main

}//class
