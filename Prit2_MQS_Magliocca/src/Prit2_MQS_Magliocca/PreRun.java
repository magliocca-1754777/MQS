package Prit2_MQS_Magliocca;

import java.util.Arrays;
/*
 * La classe PreRun simula un prerun con n lifetime esponenziali di tasso lambda_i utilizzando la classe ExponentialStream
 * e calcola il tasso iniziale di failure per difetto
 */
public class PreRun {
	//genratore di numeri esponenziali casuali
	ExponentialStream generatoreSequenza;
	//numero di failure simulate
	private int numFailure;
	//sequenza di lifetime esponenziali
	double[] lifeTimes;
	//numero inizale di difetti latenti (N_0)
	int numDifettiLatenti;
	
	/*
	 * il costruttore prende in input il tasso lambda_i di failure/ora, il numero di failure da generare,
	 * il numero iniziale di difetti latenti e il seme da passare al generatore e setta i valori degli attributi
	 */
	public PreRun(double lambda_i, int numFailure, int numDifettiLatenti,long seme){
		this.numFailure = numFailure;
		this.numDifettiLatenti = numDifettiLatenti;
		generatoreSequenza = new ExponentialStream(1/lambda_i,seme);
		lifeTimes = new double[numFailure];
	}
	
	/*
	 * funzione che genera una sequenza di lifetime esponenziali utilizzando il generatore esponenziale
	 */
	public double[] generaLifetime() {
		for(int i=0;i<numFailure;i++) { 
			lifeTimes[i] = generatoreSequenza.getNumber();	
		}
		return lifeTimes;
	}
	
	/*
	 * funzione che calcola il tempo totale di prerun come somma dei tempi di interfailure (lifetimes)
	 */
	public double calcolaTempoTotalePreRun() {
		double tempoTotale=0;
		for(int i=0;i<numFailure;i++) { 
			tempoTotale = tempoTotale + lifeTimes[i];	
		}
		return tempoTotale;
	}
	
	/*
	 * funzione che calcola il tasso inziale di failure per difetto
	 */
	public double stimaTassoInizialeFailure() {
		
		double tassoComplessivoFailure = numFailure/calcolaTempoTotalePreRun();
		double tassoInizialeFailure = tassoComplessivoFailure/numDifettiLatenti;
		return tassoInizialeFailure;
	}
	
	/*
	 *  funzione di supporto per restituire il valore del seme successivo da passare a un nuovo generatore
	 */
	public double getSuccSeme() {
		return generatoreSequenza.getSuccSeme();
	}
	
	/*
	 * viene simulato un prerun con con lifetime esponenziali di tasso lambda_i = 0.00785 failure/ora, identico per tutti gli i, 
	 * e di durata sufficiente a produrre 100 failure con numero di difetti iniziali = 60
	 * e stampa la sequenza di lifetime simulata e il tasso iniziale di failure per difetto phi_0
	 */
	public static void main(String[] args) {
        double lambda_i = 0.00785;
        int numFailures = 100;
        int N_0 = 60;
        long seme = 55;
        PreRun prerun = new PreRun(lambda_i,numFailures,N_0,seme);
        double[] lifeTimes = prerun.generaLifetime();
        System.out.println("LifeTimes: "+Arrays.toString(lifeTimes)+ "\nphi_0: "+ prerun.stimaTassoInizialeFailure());
    }

}
