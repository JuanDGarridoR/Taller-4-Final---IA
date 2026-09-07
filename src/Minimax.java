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
 * La profundidad utilizada es de 2 niveles:
 *
 * Nivel 1 -> MAX
 * Nivel 2 -> MIN
 *
 * Después de esos dos niveles usamos
 * la función heurística para valorar el estado.
 */
public class Minimax {

    /*
     * Profundidad máxima solicitada en el taller.
     */
    private static final int PROFUNDIDAD = 2;

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
         * Calculamos automáticamente el símbolo contrario.
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
        heuristica =
                new Heuristica(jugadorIA);
    }

    /**
     * Busca el mejor movimiento para la IA.
     *
     * Este es el punto donde comienza MIN-MAX.
     */
    public Movimiento obtenerMejorMovimiento(
            EstadoJuego estado) {

        /*
         * Obtenemos todas las jugadas que puede realizar
         * la IA en el estado actual.
         */
        List<Movimiento> movimientos =
                estado.generarMovimientos();

        /*
         * Si no hay movimientos posibles,
         * devolvemos null.
         */
        if (movimientos.isEmpty()) {

            return null;
        }

        /*
         * Aquí guardaremos el movimiento que finalmente
         * consideremos mejor.
         */
        Movimiento mejorMovimiento = null;

        /*
         * Inicialmente tenemos el valor más pequeño posible.
         *
         * Como MAX quiere obtener valores grandes,
         * cualquier primera jugada será mejor que esto.
         */
        int mejorValor =
                Integer.MIN_VALUE;

        /*
         * Probamos uno por uno todos los movimientos
         * que puede realizar la IA.
         */
        for (Movimiento movimiento : movimientos) {

            /*
             * Creamos el estado que resultaría
             * después de realizar este movimiento.
             */
            EstadoJuego sucesor =
                    estado.aplicarMovimiento(
                            movimiento
                    );

            /*
             * Si con esta jugada ganamos directamente,
             * es una jugada excelente.
             */
            if (sucesor.hayGanador(jugadorIA)) {

                /*
                 * Le damos el valor máximo.
                 */
                mejorValor = 100000;

                /*
                 * Guardamos esta jugada.
                 */
                mejorMovimiento = movimiento;

                /*
                 * No necesitamos seguir buscando:
                 * ya encontramos una victoria inmediata.
                 */
                break;
            }

            /*
             * Si no ganamos inmediatamente,
             * ahora simulamos la respuesta del oponente.
             *
             * Aquí comienza el nivel MIN.
             */
            int valor =
                    minValor(
                            sucesor,
                            PROFUNDIDAD - 1
                    );

            /*
             * MAX quiere quedarse con el valor más alto.
             *
             * Por eso comparamos el valor encontrado
             * con el mejor que teníamos hasta ahora.
             */
            if (valor > mejorValor) {

                /*
                 * Guardamos el nuevo mejor valor.
                 */
                mejorValor = valor;

                /*
                 * Guardamos el movimiento que produjo
                 * ese resultado.
                 */
                mejorMovimiento = movimiento;
            }
        }

        /*
         * Finalmente devolvemos la jugada que MAX considera
         * más conveniente.
         */
        return mejorMovimiento;
    }

    /**
     * Esta función representa al jugador MIN.
     *
     * MIN intenta encontrar la respuesta que produzca
     * el peor resultado posible para la IA.
     *
     * Esto representa al oponente jugando de la mejor
     * manera posible contra nosotros.
     */
    private int minValor(
            EstadoJuego estado,
            int profundidad) {

        /*
         * Si el estado ya terminó,
         * simplemente lo evaluamos.
         */
        if (estado.esTerminal()) {

            return heuristica.evaluar(estado);
        }

        /*
         * Si ya alcanzamos la profundidad definida,
         * también dejamos de buscar y evaluamos.
         */
        if (profundidad == 0) {

            return heuristica.evaluar(estado);
        }

        /*
         * Obtenemos las respuestas posibles del oponente.
         */
        List<Movimiento> movimientos =
                estado.generarMovimientos();

        /*
         * Si por alguna razón no existen movimientos,
         * evaluamos el estado.
         */
        if (movimientos.isEmpty()) {

            return heuristica.evaluar(estado);
        }

        /*
         * MIN comienza con el valor más grande posible.
         *
         * Esto permite que la primera respuesta encontrada
         * sea menor y pueda convertirse en el nuevo mínimo.
         */
        int peorValor =
                Integer.MAX_VALUE;

        /*
         * Probamos todas las respuestas del oponente.
         */
        for (Movimiento movimiento : movimientos) {

            /*
             * Creamos el estado que resultaría
             * después de la respuesta del oponente.
             */
            EstadoJuego sucesor =
                    estado.aplicarMovimiento(
                            movimiento
                    );

            /*
             * Como estamos utilizando dos niveles:
             *
             * MAX -> movimiento de la IA
             * MIN -> respuesta del oponente
             *
             * Después de MIN evaluamos directamente
             * el estado.
             */
            int valor =
                    heuristica.evaluar(
                            sucesor
                    );

            /*
             * MIN quiere el resultado más pequeño,
             * porque representa la peor situación
             * para la IA.
             */
            if (valor < peorValor) {

                peorValor = valor;
            }
        }

        /*
         * Devolvemos la peor respuesta que encontró MIN.
         */
        return peorValor;
    }
}