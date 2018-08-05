package eu.andredick.aco.algorithm;

/**
 * Schablone für Algorithmen, deren Instanzen parallel ausgeführt werden können (Runnable).<br>
 * Ein realer Algorithmus leitet diese abstrakte Klasse ab und <br>
 * implementiert in der Methode "go()" den Start seines Ablaufes.
 */
public abstract class AbstractAlgorithm implements Runnable {

    /**
     * Start- und Endzeit für die Messung der Laufdauer des Algorithmus
     */
    protected Long timeStart;
    protected Long timeEnd;

    /**
     * Methode zum Starten des Algorithmus.<br>
     * Zunächst werden vorbereitende Operationen durch "preprocessing()" ausgeführt.<br>
     * Anschließend wird die zu implementierende Methode "go()" gestartet.<br>
     * Schließlich werden durch "postprocessing()" nachbereitende Operationen ausgeführt.
     */
    @Override
    public void run() {
        this.preprocessing();
        this.go();
        this.postprocessing();
    }

    /**
     * Vorbereitende allgemeine Operationen des Algorithmus.<br>
     * Die Operationen sind nicht von einem konkreten Algorithmus abhängig.<br>
     * In der vorliegenden Version werd die Startzeit des Algorithmusablaufes initialisiert.
     */
    protected void preprocessing() {
        System.out.println("preprocessing... ");
        this.timeStart = System.currentTimeMillis();
        System.out.println("computing... ");
    }

    /**
     * Abstrakte Methode zum Start des Ablaufes eines Algorithmus.<br>
     * Die Methode ist bei Ableitung der abstrakten Klasse zu implementieren.<br>
     * Sie wird nicht von aussen gestartet (protected) sondern in der Methode "run()" aufgerufen.
     */
    protected abstract void go();

    /**
     * Nachbereitende allgemeine Operationen des Algorithmus.<br>
     * Die Operationen sind nicht von einem konkreten Algorithmus abhängig.<br>
     * In der vorliegenden Version werd die Endzeit des Algorithmusablaufes initialisiert und <br>
     * die Dauer des Ablaufes auf der Konsole ausgegeben.
     */
    protected void postprocessing() {
        System.out.println("postprocessing... ");
        this.timeEnd = System.currentTimeMillis();
        System.out.println("Verlaufszeit der Schleife: " + (timeEnd - timeStart) / 1000 + " Sek.");
    }

    /**
     * Liefert Statistiken zum Ablauf des Algorithmus
     * @return Statistiken zum Ablauf des Algorithmus
     */
    public abstract Statistics getStatistics();
}
