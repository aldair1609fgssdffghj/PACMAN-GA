package acedo.quique.GA;

import acedo.quique.fuzzyPacman.SistemaCodec;

/**
 * @author Quique
 * @date 25/11/2015
 * @version 1.0
 */

public class Gene {

	/** VARIABLES **/
	/**
	 * Fitness evaluates to how "close" the current gene is to the
	 * optimal solution (i.e. contains only 1s in its chromosome)
	 * A gene with higher fitness value from another signifies that
	 * it has more 1s in its chromosome, and is thus a better solution
	 * While it is common that fitness is a floating point between 0..1
	 * this is not necessary: the only constraint is that a better solution
	 * must have a strictly higher fitness than a worse solution
	 */
	protected double mFitness;
	/**
	 * The chromosome contains only integers 0 or 1 (we choose to avoid
	 * using a boolean type to make computations easier)
	 */
	protected int mChromosome[];

	protected boolean evaluado;

	/** METODOS **/
	/**
	 * Allocates memory for the mChromosome array and initializes any other data, such as fitness
	 * We chose to use a constant variable as the chromosome size, but it can also be
	 * passed as a variable in the constructor
	 */
	public Gene() {
		// allocating memory for the chromosome array
		mChromosome = new int[GeneticAlgorithm.CHROMOSOME_SIZE];
		// initializing fitness
		mFitness = 0.f;
		evaluado = false;
	}//Constructor

	/**
	 * Randomizes the numbers on the mChromosome array to values 0 to 1
	 */
	public void randomizeChromosome(){
		// code for randomization of initial weights goes HERE

		for(int i = 0; i < mChromosome.length; i++){
			mChromosome[i] = (int) (Math.random()*2);
		}//for
	}//randomizeChromosome

	/**
	 * Creates a number of offspring by combining (using crossover) the current
	 * Gene's chromosome with another Gene's chromosome.
	 * Usually two parents will produce an equal amount of offpsring, although
	 * in other reproduction strategies the number of offspring produced depends
	 * on the fitness of the parents.
	 * @param other: the other parent we want to create offpsring from
	 * @return Array of Gene offspring (default length of array is 2).
	 * These offspring will need to be added to the next generation.
	 */
	public Gene[] reproduce(Gene other){
		System.out.println("\tReproduciendo: ");

		Gene[] result = new Gene[2];

		// initilization of offspring chromosome goes HERE
		for(int i = 0; i < result.length; i++){
			result[i] = new Gene();
		}//for

		// Cruce en 2 puntos

		//Genero los 2 puntos Random corte1 < corte
		int corte1 = (int) (Math.random()*mChromosome.length);
		int corte2 = (int) (Math.random()*mChromosome.length);

		if(corte1 > corte2){
			int aux = corte1;
			corte1 = corte2;
			corte2 = aux;
		}//if

		System.out.println(this.genotipoToString());
		System.out.println(other.genotipoToString());
		System.out.println("\t Corte 1: "+corte1 + "\tCorte 2: " + corte2);
		System.out.println("GENERAN:");
		//Desde 0 hasta el primer punto
		// result[0] --> this
		// result[1] --> other
		for(int i = 0; i < corte1;i++){
			result[0].mChromosome[i] = mChromosome[i];
			result[1].mChromosome[i] = other.mChromosome[i];
		}//for1

		//Desde corte1 hasta el corte2
		// result[0] --> other
		// result[1] --> this
		for(int i = corte1; i < corte2;i++){
			result[0].mChromosome[i] = other.mChromosome[i];
			result[1].mChromosome[i] = mChromosome[i];
		}//for1

		//Desde corte2 hasta el final
		// result[0] --> this
		// result[1] --> other
		for(int i = corte2; i < mChromosome.length;i++){
			result[0].mChromosome[i] = mChromosome[i];
			result[1].mChromosome[i] = other.mChromosome[i];
		}//for1

		for(int i= 0; i < result.length; i++){
			System.out.println(result[i].genotipoToString());
		}//for

		return result;
	}//reproduce

	/**
	 * Mutates a gene using inversion, random mutation or other methods.
	 * This function is called after the mutation chance is rolled.
	 * Mutation can occur (depending on the designer's wishes) to a parent
	 * before reproduction takes place, an offspring at the time it is created,
	 * or (more often) on a gene which will not produce any offspring afterwards.
	 */
	public void mutate(){
		// Mutacion por intercambio

		System.out.println("\tMutando: ");
		System.out.println(this.genotipoToString());
		//Genero los 2 puntos Random corte1 < corte
		// Cojo 2 genes aleatorios del gen
		int gen1 = (int) (Math.random()*mChromosome.length);
		int gen2 = (int) (Math.random()*mChromosome.length);

		System.out.println("\tCambio de gen 1: "+gen1 + "\tgen 2: " + gen2);

		// Los intercambio
		int aux = mChromosome[gen1];
		mChromosome[gen1] = mChromosome[gen2];
		mChromosome[gen2] = aux;

		System.out.println("\tHa mutado a: ");
		System.out.println(this.genotipoToString());
	}//mutate

	/**
	 * Sets the fitness, after it is evaluated in the GeneticAlgorithm class.
	 * @param value: the fitness value to be set
	 */
	public void setFitness(double value) { 
		mFitness = value; 
	}//setFitness

	/**
	 * @return the gene's fitness value
	 */
	public double getFitness() { 
		return mFitness; 
	}//getFitness

	/**
	 * Returns the element at position <b>index</b> of the mChromosome array
	 * @param index: the position on the array of the element we want to access
	 * @return the value of the element we want to access (0 or 1)
	 */
	public int getChromosomeElement(int index){ 
		return mChromosome[index]; 
	}//getChromosomeElement

	/**
	 * Sets a <b>value</b> to the element at position <b>index</b> of the mChromosome array
	 * @param index: the position on the array of the element we want to access
	 * @param value: the value we want to set at position <b>index</b> of the mChromosome array (0 or 1)
	 */
	public void setChromosomeElement(int index, int value){
		mChromosome[index]=value; 
	}//setChromosomeElement

	/**
	 * Returns the size of the chromosome (as provided in the Gene constructor)
	 * @return the size of the mChromosome array
	 */
	public int getChromosomeSize() { 
		return mChromosome.length; 
	}//getChromosomeSize

	/**
	 * Corresponds the chromosome encoding to the phenotype, which is a representation
	 * that can be read, tested and evaluated by the main program.
	 * @return a String with a length equal to the chromosome size, composed of A's
	 * at the positions where the chromosome is 1 and a's at the posiitons
	 * where the chromosme is 0
	 */
	public String getPhenotype() {
		// create an empty string

		String[] result = SistemaCodec.decodificar(mChromosome);
		String fenotipo ="";
		for(int i = 0; i < result.length; i++)
			fenotipo +=  result[i] + " ";
			return fenotipo;
	}//getPhenotype

	public int[] getChromosome(){
		return mChromosome;
	}//getChromosome

	public String genotipoToString() {
		String result = "";

		for(int i = 0; i < mChromosome.length; i++){
			result += mChromosome[i] + " ";
		}//for
		return result;
	}//genotipoToString

	public void setEvaluado(boolean estado){
		evaluado = estado;
	}//setEvaluado

	public boolean isEvaluado(){
		return evaluado;
	}//getEvaluado

}//class