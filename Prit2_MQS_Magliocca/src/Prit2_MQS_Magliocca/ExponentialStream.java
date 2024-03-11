package Prit2_MQS_Magliocca;

/*
 * La classe ExponentialStream genera una sequenza di numeri casauli esponenziali ottenuti a partire dalla sequenza 
 * dei reali generata da GeneratoreCasuale
 */
public class ExponentialStream {
	//tempo medio
	private double T;
	//generatore casuale di numeri reali
	GeneratoreCasuale gen;
    
	/*
	 * il costruttore prende in input il tempo medio e il seme da passare al generatore casuale e setta gli attributi della classe
	 */
	public ExponentialStream(double media, long seme){
		T=media;
		gen = new GeneratoreCasuale(seme);
		
	}
	
	/*
	 * funzione di supporto per restituire il valore del seme successivo da passare a un nuovo generatore
	 */
	public long getSuccSeme() {
		return gen.getSuccSeme();
	}
	
	/*
	 * funzione che restituisce un numero esponenziale casuale
	 */
	public double getNumber() {
		return -T* Math.log(gen.get());
		
	}

}

