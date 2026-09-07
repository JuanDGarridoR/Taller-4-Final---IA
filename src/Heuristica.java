/**
 * Esta clase se encarga de darle un valor a un estado
 * del juego.
 *
 * La idea es sencilla:
 *
 * Un estado bueno para la IA recibe un valor positivo.
 *
 * Un estado malo para la IA recibe un valor negativo.
 *
 * Utilizamos los mismos indicadores propuestos
 * en las diapositivas del taller.
 */
public class Heuristica {

    /*
     * Símbolo que utiliza la inteligencia artificial.
     */
    private final char jugadorIA;

    /*
     * Símbolo del jugador contrario.
     */
    private final char jugadorOponente;

    /**
     * Constructor.
     *
     * @param jugadorIA símbolo utilizado por la IA
     */
    public Heuristica(char jugadorIA) {

        /*
         * Guardamos el símbolo de la IA.
         */
        this.jugadorIA = jugadorIA;

        /*
         * Si la IA es X, el oponente es O.
         *
         * Si la IA es O, el oponente es X.
         */
        if (jugadorIA == 'X') {

            this.jugadorOponente = 'O';

        } else {

            this.jugadorOponente = 'X';
        }
    }

    /**
     * Calcula el valor heurístico de un estado.
     *
     * Los indicadores son:
     *
     * Ind1 = opciones de ganar de la IA
     * Ind2 = opciones de ganar del oponente
     * Ind3 = esquinas de la IA
     * Ind4 = esquinas del oponente
     * Ind5 = centro de la IA
     * Ind6 = centro del oponente
     */
    public int evaluar(
            EstadoJuego estado) {

        // =========================================================
        // CASOS ESPECIALES
        // =========================================================

        /*
         * Si la IA ya ganó, queremos que este estado
         * tenga un valor extremadamente bueno.
         */
        if (estado.hayGanador(jugadorIA)) {

            return 100000;
        }

        /*
         * Si ganó el oponente, queremos que este estado
         * tenga un valor extremadamente malo.
         */
        if (estado.hayGanador(jugadorOponente)) {

            return -100000;
        }

        // =========================================================
        // CALCULAR INDICADORES
        // =========================================================

        /*
         * Ind1:
         * Número de líneas que todavía pueden ser
         * completadas por la IA.
         */
        int ind1 =
                contarOpcionesDeGanar(
                        estado,
                        jugadorIA
                );

        /*
         * Ind2:
         * Número de líneas que todavía pueden ser
         * completadas por el oponente.
         */
        int ind2 =
                contarOpcionesDeGanar(
                        estado,
                        jugadorOponente
                );

        /*
         * Ind3:
         * Número de esquinas ocupadas por la IA.
         */
        int ind3 =
                contarEsquinas(
                        estado,
                        jugadorIA
                );

        /*
         * Ind4:
         * Número de esquinas ocupadas por el oponente.
         */
        int ind4 =
                contarEsquinas(
                        estado,
                        jugadorOponente
                );

        /*
         * Ind5:
         * Vale 1 si el centro pertenece a la IA.
         * Vale 0 si no pertenece a la IA.
         */
        int ind5 =
                estado.getCasilla(1, 1)
                        == jugadorIA
                        ? 1
                        : 0;

        /*
         * Ind6:
         * Vale 1 si el centro pertenece al oponente.
         * Vale 0 si no pertenece al oponente.
         */
        int ind6 =
                estado.getCasilla(1, 1)
                        == jugadorOponente
                        ? 1
                        : 0;

        // =========================================================
        // CALCULAR EL VALOR FINAL
        // =========================================================

        /*
         * Aplicamos exactamente los pesos definidos
         * para los indicadores.
         *
         * H =
         *
         * 10(Ind1)
         * -10(Ind2)
         * +5(Ind3)
         * -5(Ind4)
         * +7(Ind5)
         * -7(Ind6)
         */
        int valor =
                (10 * ind1)
                - (10 * ind2)
                + (5 * ind3)
                - (5 * ind4)
                + (7 * ind5)
                - (7 * ind6);

        /*
         * Devolvemos el resultado.
         */
        return valor;
    }

    /**
     * Cuenta cuántas líneas pueden convertirse
     * en una línea ganadora para un jugador.
     *
     * Una línea cuenta cuando:
     *
     * - Tiene al menos una ficha del jugador.
     * - No tiene ninguna ficha del oponente.
     * - Tiene al menos un espacio vacío.
     */
    private int contarOpcionesDeGanar(
            EstadoJuego estado,
            char jugador) {

        /*
         * Determinamos quién es el oponente.
         */
        char oponente;

        if (jugador == 'X') {

            oponente = 'O';

        } else {

            oponente = 'X';
        }

        /*
         * Aquí guardaremos el número de líneas disponibles.
         */
        int contador = 0;

        // =========================================================
        // FILAS
        // =========================================================

        for (int fila = 0; fila < 3; fila++) {

            /*
             * Revisamos las tres posiciones de la fila.
             */
            if (esLineaDisponible(
                    estado,
                    jugador,
                    oponente,
                    fila, 0,
                    fila, 1,
                    fila, 2)) {

                contador++;
            }
        }

        // =========================================================
        // COLUMNAS
        // =========================================================

        for (int columna = 0;
             columna < 3;
             columna++) {

            /*
             * Revisamos las tres posiciones de la columna.
             */
            if (esLineaDisponible(
                    estado,
                    jugador,
                    oponente,
                    0, columna,
                    1, columna,
                    2, columna)) {

                contador++;
            }
        }

        // =========================================================
        // DIAGONAL PRINCIPAL
        // =========================================================

        if (esLineaDisponible(
                estado,
                jugador,
                oponente,
                0, 0,
                1, 1,
                2, 2)) {

            contador++;
        }

        // =========================================================
        // DIAGONAL SECUNDARIA
        // =========================================================

        if (esLineaDisponible(
                estado,
                jugador,
                oponente,
                0, 2,
                1, 1,
                2, 0)) {

            contador++;
        }

        /*
         * Devolvemos cuántas líneas encontramos.
         */
        return contador;
    }

    /**
     * Comprueba si una línea todavía puede ser ganada
     * por un jugador.
     */
    private boolean esLineaDisponible(
            EstadoJuego estado,
            char jugador,
            char oponente,
            int f1, int c1,
            int f2, int c2,
            int f3, int c3) {

        /*
         * Guardamos las tres casillas de la línea.
         */
        char[] valores = {

                estado.getCasilla(f1, c1),
                estado.getCasilla(f2, c2),
                estado.getCasilla(f3, c3)
        };

        /*
         * Nos sirve para saber si encontramos
         * al menos una ficha propia.
         */
        boolean tieneFichaPropia = false;

        /*
         * Nos sirve para saber si encontramos
         * al menos una casilla vacía.
         */
        boolean tieneEspacio = false;

        /*
         * Revisamos las tres casillas.
         */
        for (char valor : valores) {

            /*
             * Si aparece una ficha del oponente,
             * esa línea ya no puede ser ganada
             * por nuestro jugador.
             */
            if (valor == oponente) {

                return false;
            }

            /*
             * Si encontramos nuestra ficha,
             * guardamos que existe una.
             */
            if (valor == jugador) {

                tieneFichaPropia = true;
            }

            /*
             * Si encontramos un espacio vacío,
             * guardamos que existe un espacio.
             */
            if (valor == ' ') {

                tieneEspacio = true;
            }
        }

        /*
         * Para que sea una opción de ganar,
         * necesitamos ambas condiciones:
         *
         * ficha propia + espacio vacío.
         */
        return tieneFichaPropia && tieneEspacio;
    }

    /**
     * Cuenta cuántas esquinas pertenecen
     * al jugador indicado.
     */
    private int contarEsquinas(
            EstadoJuego estado,
            char jugador) {

        /*
         * Inicialmente no tenemos ninguna esquina.
         */
        int contador = 0;

        /*
         * Las cuatro esquinas del tablero son:
         *
         * (0,0)
         * (0,2)
         * (2,0)
         * (2,2)
         */
        int[][] esquinas = {

                {0, 0},
                {0, 2},
                {2, 0},
                {2, 2}
        };

        /*
         * Revisamos cada esquina.
         */
        for (int[] esquina : esquinas) {

            /*
             * Si pertenece al jugador,
             * aumentamos el contador.
             */
            if (estado.getCasilla(
                    esquina[0],
                    esquina[1]
            ) == jugador) {

                contador++;
            }
        }

        /*
         * Devolvemos el número de esquinas.
         */
        return contador;
    }
}