import java.util.List;

/**
 * Esta clase contiene la inteligencia artificial
 * del juego.
 *
 * Utilizamos el algoritmo MIN-MAX.
 *
 * La IA representa MAX.
 *
 * El jugador contrario representa MIN.
 *
 * La profundidad se puede cambiar fácilmente
 * modificando la constante PROFUNDIDAD.
 */
public class Minimax {

    /*
     * =========================================================
     * PROFUNDIDAD DE LA BÚSQUEDA
     * =========================================================
     *
     * Este es el número que puedes cambiar mañana.
     *
     * Ejemplos:
     *
     * 2 = MAX -> MIN
     * 3 = MAX -> MIN -> MAX
     * 4 = MAX -> MIN -> MAX -> MIN
     * 5 = MAX -> MIN -> MAX -> MIN -> MAX
     *
     * Si el profesor dice "profundidad 4",
     * simplemente cambias el 2 por 4.
     */
    private static final int PROFUNDIDAD = 6; //C. PROF.

    /*
     * Símbolo de la inteligencia artificial.
     */
    private final char jugadorIA;

    /*
     * Símbolo del oponente.
     */
    private final char jugadorOponente;

    /*
     * Objeto encargado de valorar los estados.
     */
    private final Heuristica heuristica;


    /**
     * Constructor de la inteligencia artificial.
     *
     * @param jugadorIA símbolo que utilizará la IA
     */
    public Minimax(char jugadorIA) {

        /*
         * Guardamos el símbolo de la IA.
         */
        this.jugadorIA = jugadorIA;

        /*
         * Calculamos automáticamente
         * el símbolo contrario.
         */
        if (jugadorIA == 'X') {

            jugadorOponente = 'O';

        } else {

            jugadorOponente = 'X';
        }

        /*
         * Creamos la función heurística utilizando
         * el símbolo de nuestra IA.
         */
        heuristica = new Heuristica(jugadorIA);
    }


    /**
     * Busca el mejor movimiento para la IA.
     *
     * Aquí comienza el algoritmo MIN-MAX.
     *
     * MAX representa a la IA.
     */
    public Movimiento obtenerMejorMovimiento(
            EstadoJuego estado) {

        /*
         * Obtenemos TODOS los movimientos posibles
         * de la IA.
         *
         * Esto incluye:
         *
         * - Colocar una ficha.
         * - Mover una ficha existente.
         */
        List<Movimiento> movimientos =
                estado.generarMovimientos();

        /*
         * Si no existen movimientos,
         * no podemos realizar ninguna jugada.
         */
        if (movimientos.isEmpty()) {

            return null;
        }

        /*
         * Aquí guardaremos la mejor jugada encontrada.
         */
        Movimiento mejorMovimiento = null;

        /*
         * MAX quiere obtener el valor más grande.
         *
         * Por eso comenzamos con el valor mínimo posible.
         */
        int mejorValor = Integer.MIN_VALUE;

        /*
         * Probamos todas las jugadas posibles de la IA.
         */
        for (Movimiento movimiento : movimientos) {

            /*
             * Creamos el nuevo estado después
             * de realizar la jugada.
             */
            EstadoJuego sucesor =
                    estado.aplicarMovimiento(movimiento);

            /*
             * Si esta jugada hace que la IA gane
             * inmediatamente, es la mejor posible.
             */
            if (sucesor.hayGanador(jugadorIA)) {

                mejorValor = 100000;

                mejorMovimiento = movimiento;

                /*
                 * No necesitamos revisar más jugadas.
                 */
                break;
            }

            /*
             * Ahora comienza la parte MIN.
             *
             * Le pasamos PROFUNDIDAD - 1 porque
             * ya utilizamos el primer nivel
             * haciendo la jugada de MAX.
             */
            int valor =
                    minValor(
                            sucesor,
                            PROFUNDIDAD - 1
                    );

            /*
             * MAX quiere el valor MÁS ALTO.
             */
            if (valor > mejorValor) {

                mejorValor = valor;

                mejorMovimiento = movimiento;
            }
        }

        /*
         * Devolvemos la mejor jugada encontrada.
         */
        return mejorMovimiento;
    }


    /**
     * =========================================================
     * FUNCIÓN MAX
     * =========================================================
     *
     * MAX representa a la IA.
     *
     * Busca el valor MÁS GRANDE.
     *
     * Esta función se utiliza cuando le corresponde
     * jugar a la IA dentro del árbol.
     */
    private int maxValor(
            EstadoJuego estado,
            int profundidad) {

        /*
         * Si el juego terminó, evaluamos el estado.
         */
        if (estado.esTerminal()) {

            return heuristica.evaluar(estado);
        }

        /*
         * Si llegamos a profundidad 0,
         * dejamos de explorar y usamos la heurística.
         */
        if (profundidad == 0) {

            return heuristica.evaluar(estado);
        }

        /*
         * Obtenemos los movimientos posibles de la IA.
         */
        List<Movimiento> movimientos =
                estado.generarMovimientos();

        /*
         * Si no hay movimientos,
         * evaluamos el estado.
         */
        if (movimientos.isEmpty()) {

            return heuristica.evaluar(estado);
        }

        /*
         * MAX comienza con el valor más pequeño posible.
         */
        int mejorValor = Integer.MIN_VALUE;

        /*
         * Probamos cada movimiento posible.
         */
        for (Movimiento movimiento : movimientos) {

            /*
             * Creamos el estado después de la jugada.
             */
            EstadoJuego sucesor =
                    estado.aplicarMovimiento(movimiento);

            /*
             * Llamamos a MIN.
             *
             * Reducimos la profundidad porque
             * acabamos de explorar un nivel.
             */
            int valor =
                    minValor(
                            sucesor,
                            profundidad - 1
                    );

            /*
             * MAX se queda con el valor más grande.
             */
            if (valor > mejorValor) {

                mejorValor = valor;
            }
        }

        /*
         * Devolvemos el mejor valor encontrado por MAX.
         */
        return mejorValor;
    }


    /**
     * =========================================================
     * FUNCIÓN MIN
     * =========================================================
     *
     * MIN representa al oponente.
     *
     * Busca el valor MÁS PEQUEÑO,
     * porque ese es el peor resultado para la IA.
     */
    private int minValor(
            EstadoJuego estado,
            int profundidad) {

        /*
         * Si el juego terminó,
         * evaluamos el estado.
         */
        if (estado.esTerminal()) {

            return heuristica.evaluar(estado);
        }

        /*
         * Si llegamos a profundidad 0,
         * dejamos de explorar.
         */
        if (profundidad == 0) {

            return heuristica.evaluar(estado);
        }

        /*
         * Obtenemos las respuestas posibles
         * del jugador contrario.
         */
        List<Movimiento> movimientos =
                estado.generarMovimientos();

        /*
         * Si no existen movimientos,
         * evaluamos el estado.
         */
        if (movimientos.isEmpty()) {

            return heuristica.evaluar(estado);
        }

        /*
         * MIN comienza con el valor más grande posible.
         */
        int peorValor = Integer.MAX_VALUE;

        /*
         * Probamos todas las respuestas del oponente.
         */
        for (Movimiento movimiento : movimientos) {

            /*
             * Creamos el estado después
             * de la respuesta del oponente.
             */
            EstadoJuego sucesor =
                    estado.aplicarMovimiento(movimiento);

            /*
             * Ahora volvemos a MAX.
             *
             * Esto es lo que permite aumentar
             * la profundidad del árbol.
             */
            int valor =
                    maxValor(
                            sucesor,
                            profundidad - 1
                    );

            /*
             * MIN quiere el valor MÁS PEQUEÑO.
             */
            if (valor < peorValor) {

                peorValor = valor;
            }
        }

        /*
         * Devolvemos la peor opción para la IA.
         */
        return peorValor;
    }
}