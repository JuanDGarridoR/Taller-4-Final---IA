/**
 * Esta clase representa un movimiento que puede hacer
 * uno de los jugadores en el Triqui-Móvil.
 *
 * Hay dos tipos de movimientos:
 *
 * 1. COLOCAR:
 *    El jugador pone una ficha en una casilla vacía.
 *
 * 2. MOVER:
 *    El jugador mueve una ficha que ya tiene en el tablero
 *    hacia una casilla vacía que esté al lado.
 */
public class Movimiento {

    /*
     * Aquí definimos los dos tipos de movimiento
     * que existen en nuestro juego.
     */
    public enum Tipo {

        // Colocar una ficha nueva.
        COLOCAR,

        // Mover una ficha que ya existe.
        MOVER
    }

    /*
     * Guarda qué tipo de movimiento estamos haciendo.
     */
    private final Tipo tipo;

    /*
     * Estas dos variables indican de dónde sale una ficha.
     *
     * Cuando el movimiento es COLOCAR no necesitamos
     * una posición inicial, por eso tendrán el valor -1.
     */
    private final int filaOrigen;
    private final int columnaOrigen;

    /*
     * Estas dos variables indican dónde termina la ficha.
     *
     * En un movimiento de colocación representan la casilla
     * donde vamos a poner la nueva ficha.
     *
     * En un movimiento de desplazamiento representan
     * la casilla a la que vamos a mover la ficha.
     */
    private final int filaDestino;
    private final int columnaDestino;

    /**
     * Constructor utilizado cuando queremos COLOCAR
     * una ficha nueva.
     *
     * @param filaDestino fila donde se colocará la ficha
     * @param columnaDestino columna donde se colocará la ficha
     */
    public Movimiento(
            int filaDestino,
            int columnaDestino) {

        // Indicamos que este movimiento es de colocación.
        this.tipo = Tipo.COLOCAR;

        /*
         * Como no existe una posición de origen,
         * usamos -1 para indicar que no aplica.
         */
        this.filaOrigen = -1;
        this.columnaOrigen = -1;

        // Guardamos la posición donde se pondrá la ficha.
        this.filaDestino = filaDestino;
        this.columnaDestino = columnaDestino;
    }

    /**
     * Constructor utilizado cuando queremos MOVER
     * una ficha que ya está en el tablero.
     *
     * @param filaOrigen fila donde está actualmente la ficha
     * @param columnaOrigen columna donde está actualmente la ficha
     * @param filaDestino fila a la que se moverá
     * @param columnaDestino columna a la que se moverá
     */
    public Movimiento(
            int filaOrigen,
            int columnaOrigen,
            int filaDestino,
            int columnaDestino) {

        // Indicamos que este movimiento es de desplazamiento.
        this.tipo = Tipo.MOVER;

        // Guardamos la posición actual de la ficha.
        this.filaOrigen = filaOrigen;
        this.columnaOrigen = columnaOrigen;

        // Guardamos la nueva posición de la ficha.
        this.filaDestino = filaDestino;
        this.columnaDestino = columnaDestino;
    }

    /**
     * Devuelve el tipo de movimiento.
     */
    public Tipo getTipo() {

        return tipo;
    }

    /**
     * Devuelve la fila de origen.
     */
    public int getFilaOrigen() {

        return filaOrigen;
    }

    /**
     * Devuelve la columna de origen.
     */
    public int getColumnaOrigen() {

        return columnaOrigen;
    }

    /**
     * Devuelve la fila de destino.
     */
    public int getFilaDestino() {

        return filaDestino;
    }

    /**
     * Devuelve la columna de destino.
     */
    public int getColumnaDestino() {

        return columnaDestino;
    }

    /**
     * Este método convierte el movimiento en un texto
     * que podamos leer fácilmente.
     *
     * Por ejemplo:
     *
     * "Colocar en (1, 2)"
     *
     * o:
     *
     * "Mover de (0, 0) a (0, 1)"
     */
    @Override
    public String toString() {

        // Si estamos colocando una ficha...
        if (tipo == Tipo.COLOCAR) {

            return "Colocar en (" +
                    filaDestino +
                    ", " +
                    columnaDestino +
                    ")";
        }

        // Si estamos moviendo una ficha...
        return "Mover de (" +
                filaOrigen +
                ", " +
                columnaOrigen +
                ") a (" +
                filaDestino +
                ", " +
                columnaDestino +
                ")";
    }
}