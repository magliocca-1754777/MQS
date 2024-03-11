package Prit2_MQS_Magliocca;

import java.util.Arrays;

/* La classe Run simula un run con n lifetime esponenziali di tasso lambda_i, calcolato per ogni i secondo il modello MUSA,
 * e calcola gli istanti di failure 
 * La sequenza degli esponenziale viene generata come conseguente della sequenza del prerun
 */
public class Run {
	//numero di failure simulate
	private int numFailures;
	//sequenza di lifetime esponenziali
    private double[] lifeTimes;
    //sequenza di istanti di failure
    private double[] failureTimes;
    //tasso di failure/ora
    private double lambda_i;
  //genratore di numeri esponenziali casuali
    private ExponentialStream generatore ;
    //tasso iniziale di failure per difetto ottenuto in prerun
    private double phi_0;
  //numero inizale di difetti latenti (N_0)
    private int n_0;
    
   
    /*
     * Il costruttore prende in input il tasso lambda_i di failure/ora, il numero di failure da generare,
	 * il numero iniziale di difetti latenti e il seme per simulare un prerun.
	 * Inoltre prende in input il numero di failure da generare nella simulazione del run.
	 * Vengono settati i valori degli attributi della classe e viene simulato un prerun con gli input passati al costruttore.
	 * Il generatore della sequenza di lifetime del run viene impostato al generatore della sequenza di lifetime del prerun,
	 * in modo tale che la sequenza generata sarà il seguito della sequenza generata in fase di prerun
     */
    public Run(double lambdaPreRun,int numFailuresPreRun, int numFailures,int n_0, long seme) {
    	this.n_0=n_0;
        this.numFailures = numFailures;
        lifeTimes = new double[numFailures];
        failureTimes = new double[numFailures+1];
        PreRun prerun = new PreRun(lambdaPreRun,numFailuresPreRun,n_0,seme);
        prerun.generaLifetime();
        phi_0 = prerun.stimaTassoInizialeFailure();
        generatore=prerun.generatoreSequenza;
    }
    
    /*
     * funzione che simula il run generando la sequenza di lifetime esponenziali di tasso lambda_i, 
     * calcolato per ogni i secondo il modello MUSA, e calcolando la relativa sequenza di istanti 
     * di failure come somma cumulativa dei lifetime
     */
    public void simulaRun() {
    	failureTimes[0]=0;
        for (int i = 1; i < numFailures+1; i++) {
        	lambda_i=1/(phi_0*n_0* Math.exp(-phi_0*0.95*failureTimes[i-1]));
        	lifeTimes[i-1] = generaLifeTime(lambda_i);
        	failureTimes[i] = calcolaFailureTime(i);
        }
    }
    
    /*
     * funzione che genera la sequenza di lifetime esponenziali 
     */
    private double generaLifeTime(double lambda_i) {
    	generatore= new ExponentialStream(lambda_i, generatore.getSuccSeme());
    	return generatore.getNumber();
    }
    
    /*
     * funzione che calcola la relativa sequenza di istanti 
     * di failure come somma cumulativa dei lifetime
     */
    private double calcolaFailureTime(int i) {
        double failureTime = 0;
        for (int j = 0; j <= i-1; j++) {
            failureTime += lifeTimes[j];
        }
        return failureTime;
    }
    
    /*
     * funzione che restituisce la sequenza degli istanti di failure 
     */
    public double[] getFailureTimes() {
        return failureTimes;
    }
    /*
     * funzione che restituisce la sequenza di lifetime generata
     */
    public double[] getLifetimes() {
        return lifeTimes;
    }
    
    /*
     * viene simulato un run con con lifetime esponenziali di tasso lambda_i, calcolato per ogni i secondo il modello MUSA, 
	 * e di durata sufficiente a produrre 50 failure. 
	 * Stampa la sequenza di lifetime simulata e la sequenza di istanti di failure
     */
    public static void main(String[] args) {
        double lambda_i = 0.00785;
        int numFailures = 50;
        int n_0 = 60;
        int numFailuresPreRun = 100;
        long seme = 55;
        Run simulation = new Run(lambda_i,numFailuresPreRun, numFailures,n_0, seme);
        simulation.simulaRun();
        double[] failureTimes = simulation.getFailureTimes();
        double[] lifeTimes = simulation.getLifetimes();
        System.out.println("Failure Times: " + Arrays.toString(failureTimes)+"\nLifeTimes: "+Arrays.toString(lifeTimes));
    }
}







