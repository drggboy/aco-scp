package eu.andredick.aco.algorithm;

/**
 * Schablone für Algorithmen, deren Instanzen parallel
 * ausgeführt werden können (Runnable).
 */
public abstract class AbstractAlgorithm implements Runnable {

    protected Long timeStart;
    protected Long timeEnd;

    @Override
    public void run() {
        this.preprocessing();
        this.go();
        this.postprocessing();
    }

    protected void preprocessing() {
        System.out.println("preprocessing... ");
        this.timeStart = System.currentTimeMillis();
        System.out.println("computing... ");
    }

    /**
     * Die Methode ist bei Ableitung der Klasse mit Logik auszustatten
     */
    protected abstract void go();

    protected void postprocessing() {
        System.out.println("postprocessing... ");
        this.timeEnd = System.currentTimeMillis();
        System.out.println("Verlaufszeit der Schleife: " + (timeEnd - timeStart) / 1000 + " Sek.");
    }

    public abstract Statistics getStatistics();
}
