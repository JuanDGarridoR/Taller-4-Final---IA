/**
 * Esta clase guarda las opciones con las que
 * queremos comenzar una partida.
 *
 * Guarda:
 *
 * - Qué símbolo utiliza la IA.
 * - Qué símbolo utiliza el jugador humano.
 * - Quién comienza.
 */
public class Configuracion {

    /*
     * Símbolo de la IA.
     */
    private final char jugadorIA;

    /*
     * Símbolo del jugador humano.
     */
    private final char jugadorHumano;

    /*
     * Jugador que comienza la partida.
     */
    private final char jugadorInicial;

    /**
     * Constructor.
     *
     * @param jugadorIA símbolo que utilizará la IA
     * @param jugadorInicial jugador que comenzará
     */
    public Configuracion(
            char jugadorIA,
            char jugadorInicial) {

        /*
         * Guardamos el símbolo de la IA.
         */
        this.jugadorIA = jugadorIA;

        /*
         * El jugador humano siempre utiliza
         * el símbolo contrario al de la IA.
         */
        if (jugadorIA == 'X') {

            this.jugadorHumano = 'O';

        } else {

            this.jugadorHumano = 'X';
        }

        /*
         * Guardamos quién comienza.
         */
        this.jugadorInicial = jugadorInicial;
    }

    /**
     * Devuelve el símbolo de la IA.
     */
    public char getJugadorIA() {

        return jugadorIA;
    }

    /**
     * Devuelve el símbolo del jugador humano.
     */
    public char getJugadorHumano() {

        return jugadorHumano;
    }

    /**
     * Devuelve quién comienza.
     */
    public char getJugadorInicial() {

        return jugadorInicial;
    }
}