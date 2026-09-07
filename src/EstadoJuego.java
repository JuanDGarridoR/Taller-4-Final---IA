import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa el estado completo del juego.
 *
 * Un estado nos dice dos cosas importantes:
 *
 * 1. Cómo está el tablero.
 * 2. Qué jugador tiene el turno.
 *
 * El tablero tiene 3 filas y 3 columnas.
 *
 * Una casilla puede contener:
 *
 * 'X'  -> ficha del jugador X
 * 'O'  -> ficha del jugador O
 * ' '  -> casilla vacía
 */
public class EstadoJuego {

    /*
     * El tablero siempre tendrá un tamaño de 3 x 3.
     */
    public static final int TAM = 3;

    /*
     * Aquí guardamos las fichas del tablero.
     *
     * La primera posición representa la fila.
     * La segunda posición representa la columna.
     */
    private char[][] tablero;

    /*
     * Guarda quién tiene el turno actualmente.
     *
     * Puede ser 'X' u 'O'.
     */
    private char jugadorActual;

    /**
     * Constructor que crea un tablero completamente vacío.
     *
     * @param jugadorInicial jugador que comienza la partida
     */
    public EstadoJuego(char jugadorInicial) {

        /*
         * Creamos la matriz de 3 x 3.
         */
        tablero = new char[TAM][TAM];

        /*
         * Recorremos todas las posiciones del tablero.
         */
        for (int fila = 0; fila < TAM; fila++) {

            for (int columna = 0;
                 columna < TAM;
                 columna++) {

                /*
                 * Al comenzar, todas las casillas están vacías.
                 */
                tablero[fila][columna] = ' ';
            }
        }

        /*
         * Guardamos quién comienza.
         */
        jugadorActual = jugadorInicial;
    }

    /**
     * Este constructor se utiliza para crear una copia
     * exacta de otro estado.
     *
     * Lo necesitamos porque MIN-MAX debe probar movimientos
     * sin destruir el tablero real de la partida.
     */
    private EstadoJuego(
            char[][] tablero,
            char jugadorActual) {

        /*
         * Creamos un tablero nuevo.
         */
        this.tablero = new char[TAM][TAM];

        /*
         * Copiamos fila por fila el tablero anterior.
         */
        for (int fila = 0; fila < TAM; fila++) {

            System.arraycopy(
                    tablero[fila],
                    0,
                    this.tablero[fila],
                    0,
                    TAM
            );
        }

        /*
         * También copiamos el jugador actual.
         */
        this.jugadorActual = jugadorActual;
    }

    /**
     * Devuelve el jugador que tiene actualmente el turno.
     */
    public char getJugadorActual() {

        return jugadorActual;
    }

    /**
     * Devuelve qué hay en una casilla determinada.
     *
     * @param fila fila de la casilla
     * @param columna columna de la casilla
     */
    public char getCasilla(
            int fila,
            int columna) {

        return tablero[fila][columna];
    }

    /**
     * Comprueba si una casilla está vacía.
     *
     * Devuelve true si está vacía.
     */
    public boolean estaVacia(
            int fila,
            int columna) {

        return tablero[fila][columna] == ' ';
    }

    /**
     * Crea una copia del estado actual.
     *
     * Esto es muy importante para MIN-MAX.
     *
     * La IA puede crear un estado imaginario y probar
     * un movimiento sin modificar el tablero real.
     */
    public EstadoJuego copiar() {

        return new EstadoJuego(
                tablero,
                jugadorActual
        );
    }

    /**
     * Cambia el turno.
     *
     * Si estaba jugando X, pasa a O.
     *
     * Si estaba jugando O, pasa a X.
     */
    private void cambiarTurno() {

        if (jugadorActual == 'X') {

            jugadorActual = 'O';

        } else {

            jugadorActual = 'X';
        }
    }

    /**
     * Aplica un movimiento y devuelve el nuevo estado.
     *
     * IMPORTANTE:
     *
     * No modificamos directamente el estado original.
     * Primero hacemos una copia.
     */
    public EstadoJuego aplicarMovimiento(
            Movimiento movimiento) {

        /*
         * Creamos una copia del estado.
         */
        EstadoJuego nuevoEstado = copiar();

        /*
         * Guardamos quién está haciendo el movimiento.
         */
        char jugador = nuevoEstado.jugadorActual;

        /*
         * Comprobamos si el movimiento es colocar.
         */
        if (movimiento.getTipo()
                == Movimiento.Tipo.COLOCAR) {

            /*
             * Colocamos la ficha del jugador
             * en la casilla indicada.
             */
            nuevoEstado.tablero[
                    movimiento.getFilaDestino()
            ][
                    movimiento.getColumnaDestino()
            ] = jugador;

        } else {

            /*
             * Si es un movimiento de desplazamiento,
             * primero quitamos la ficha de su posición anterior.
             */
            nuevoEstado.tablero[
                    movimiento.getFilaOrigen()
            ][
                    movimiento.getColumnaOrigen()
            ] = ' ';

            /*
             * Después colocamos la ficha en la nueva posición.
             */
            nuevoEstado.tablero[
                    movimiento.getFilaDestino()
            ][
                    movimiento.getColumnaDestino()
            ] = jugador;
        }

        /*
         * Después de realizar la jugada,
         * cambiamos el turno.
         */
        nuevoEstado.cambiarTurno();

        /*
         * Devolvemos el nuevo estado.
         */
        return nuevoEstado;
    }

    /**
     * Genera todas las jugadas posibles desde este estado.
     *
     * El jugador puede:
     *
     * 1. Colocar una ficha en una casilla vacía.
     * 2. Mover una ficha propia a una casilla vacía
     *    que esté inmediatamente arriba, abajo,
     *    izquierda o derecha.
     */
    public List<Movimiento> generarMovimientos() {

        /*
         * Creamos una lista donde vamos a guardar
         * todos los movimientos posibles.
         */
        List<Movimiento> movimientos =
                new ArrayList<>();

        /*
         * Guardamos el jugador que debe hacer la jugada.
         */
        char jugador = jugadorActual;

        // =========================================================
        // PARTE 1: COLOCAR FICHAS
        // =========================================================

        /*
         * Recorremos todo el tablero.
         */
        for (int fila = 0; fila < TAM; fila++) {

            for (int columna = 0;
                 columna < TAM;
                 columna++) {

                /*
                 * Si la casilla está vacía,
                 * podemos colocar una ficha.
                 */
                if (tablero[fila][columna] == ' ') {

                    movimientos.add(
                            new Movimiento(
                                    fila,
                                    columna
                            )
                    );
                }
            }
        }

        // =========================================================
        // PARTE 2: MOVER FICHAS
        // =========================================================

        /*
         * Estas cuatro parejas representan:
         *
         * {-1, 0} -> arriba
         * { 1, 0} -> abajo
         * { 0,-1} -> izquierda
         * { 0, 1} -> derecha
         */
        int[][] direcciones = {

                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };

        /*
         * Recorremos todas las casillas.
         */
        for (int fila = 0; fila < TAM; fila++) {

            for (int columna = 0;
                 columna < TAM;
                 columna++) {

                /*
                 * Si la casilla no contiene una ficha
                 * del jugador actual, no podemos moverla.
                 */
                if (tablero[fila][columna] != jugador) {

                    continue;
                }

                /*
                 * Probamos las cuatro direcciones.
                 */
                for (int[] direccion : direcciones) {

                    /*
                     * Calculamos la nueva fila.
                     */
                    int nuevaFila =
                            fila + direccion[0];

                    /*
                     * Calculamos la nueva columna.
                     */
                    int nuevaColumna =
                            columna + direccion[1];

                    /*
                     * Comprobamos que la nueva posición
                     * siga dentro del tablero.
                     */
                    if (nuevaFila >= 0 &&
                            nuevaFila < TAM &&
                            nuevaColumna >= 0 &&
                            nuevaColumna < TAM) {

                        /*
                         * La posición de destino tiene
                         * que estar vacía.
                         */
                        if (tablero[nuevaFila][nuevaColumna]
                                == ' ') {

                            /*
                             * Creamos el movimiento
                             * y lo agregamos a la lista.
                             */
                            movimientos.add(
                                    new Movimiento(
                                            fila,
                                            columna,
                                            nuevaFila,
                                            nuevaColumna
                                    )
                            );
                        }
                    }
                }
            }
        }

        /*
         * Devolvemos todas las jugadas posibles.
         */
        return movimientos;
    }

    /**
     * Comprueba si un jugador consiguió tres fichas
     * consecutivas.
     *
     * Revisamos:
     *
     * - Las tres filas.
     * - Las tres columnas.
     * - La diagonal principal.
     * - La diagonal secundaria.
     */
    public boolean hayGanador(char jugador) {

        // =========================================================
        // REVISAR FILAS
        // =========================================================

        for (int fila = 0; fila < TAM; fila++) {

            /*
             * Comprobamos si las tres posiciones
             * de la fila pertenecen al jugador.
             */
            if (tablero[fila][0] == jugador &&
                    tablero[fila][1] == jugador &&
                    tablero[fila][2] == jugador) {

                return true;
            }
        }

        // =========================================================
        // REVISAR COLUMNAS
        // =========================================================

        for (int columna = 0;
             columna < TAM;
             columna++) {

            /*
             * Comprobamos las tres posiciones
             * de cada columna.
             */
            if (tablero[0][columna] == jugador &&
                    tablero[1][columna] == jugador &&
                    tablero[2][columna] == jugador) {

                return true;
            }
        }

        // =========================================================
        // DIAGONAL PRINCIPAL
        // =========================================================

        if (tablero[0][0] == jugador &&
                tablero[1][1] == jugador &&
                tablero[2][2] == jugador) {

            return true;
        }

        // =========================================================
        // DIAGONAL SECUNDARIA
        // =========================================================

        if (tablero[0][2] == jugador &&
                tablero[1][1] == jugador &&
                tablero[2][0] == jugador) {

            return true;
        }

        /*
         * Si ninguna fila, columna o diagonal
         * tiene tres fichas iguales,
         * entonces este jugador no ha ganado.
         */
        return false;
    }

    /**
     * Determina si el juego ya terminó.
     *
     * El juego termina cuando:
     *
     * - X consiguió tres en línea.
     * - O consiguió tres en línea.
     * - Ya no existen movimientos posibles.
     */
    public boolean esTerminal() {

        /*
         * Primero revisamos si alguno de los jugadores ganó.
         */
        if (hayGanador('X') ||
                hayGanador('O')) {

            return true;
        }

        /*
         * Si no hay ganador, comprobamos si todavía
         * existe algún movimiento.
         *
         * Si la lista está vacía, no se puede jugar más.
         */
        return generarMovimientos().isEmpty();
    }

    /**
     * Devuelve quién ganó.
     *
     * Si ganó X devuelve 'X'.
     * Si ganó O devuelve 'O'.
     * Si todavía no hay ganador devuelve ' '.
     */
    public char obtenerGanador() {

        if (hayGanador('X')) {

            return 'X';
        }

        if (hayGanador('O')) {

            return 'O';
        }

        return ' ';
    }

    /**
     * Convierte el tablero en texto.
     *
     * Esto nos sirve principalmente para hacer pruebas
     * desde la consola si algún día necesitamos revisar
     * el funcionamiento interno.
     */
    @Override
    public String toString() {

        StringBuilder resultado =
                new StringBuilder();

        /*
         * Recorremos las tres filas.
         */
        for (int fila = 0;
             fila < TAM;
             fila++) {

            resultado.append("   ");

            /*
             * Recorremos las tres columnas.
             */
            for (int columna = 0;
                 columna < TAM;
                 columna++) {

                /*
                 * Si la casilla está vacía,
                 * mostramos un espacio.
                 *
                 * Si tiene X u O, mostramos la ficha.
                 */
                resultado.append(
                        tablero[fila][columna] == ' '
                                ? " "
                                : tablero[fila][columna]
                );

                /*
                 * Ponemos separadores entre las casillas.
                 */
                if (columna < TAM - 1) {

                    resultado.append(" | ");
                }
            }

            resultado.append("\n");

            /*
             * Ponemos una línea entre cada fila.
             */
            if (fila < TAM - 1) {

                resultado.append(
                        "  -----------\n"
                );
            }
        }

        return resultado.toString();
    }
}