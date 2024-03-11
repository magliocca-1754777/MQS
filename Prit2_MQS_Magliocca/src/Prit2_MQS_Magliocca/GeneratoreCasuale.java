package Prit2_MQS_Magliocca;

/*
 * La classe GeneratoreCasuale genera numeri casuali reali compresi tra 0 e 1
 */
public class GeneratoreCasuale {
	//
	//impostato a dispari nel costruttore
	long x;
	//scelto in modo tale da assicurare il periodo massimo m e scelta in modo tale da assicurare una buona casualità
	long a;
	//periodo massimo
	long m;

    /*
     * il costruttore prende in input il seme e setta gli attributi della classe
     */
	public GeneratoreCasuale(long seme) {
		x = seme;  
	    a = 1220703125; 
	    m = 2147483648L;
	}
	
	/*
	 * funzione di supporto per restituire il valore del seme successivo da passare a un nuovo generatore
	 */
	public long getSuccSeme() {
		return x;
	}
	
	/*
	 * funzione che calcola un numero reale casuale e setta il seme al numero generato
	 */
	public double get() {
		x = (a*x)%m;
		double r = (double)x/m;
		return r;
	}

}
