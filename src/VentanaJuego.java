import javax.swing.*;
import java.awt.*;

/**
 * Esta clase representa la ventana principal
 * donde se desarrolla la partida.
 */
public class VentanaJuego extends JFrame {

    /*
     * Estado actual de la partida.
     *
     * Este objeto contiene el tablero y el jugador actual.
     */
    private EstadoJuego estado;

    /*
     * Guarda las opciones que elegimos al comenzar.
     */
    private final Configuracion configuracion;

    /*
     * Objeto que contiene nuestra inteligencia artificial.
     */
    private final Minimax minimax;

    /*
     * Matriz de botones que representa visualmente
     * las nueve casillas del tablero.
     */
    private JButton[][] botones;

    /*
     * Texto que indica de quién es el turno.
     */
    private JLabel etiquetaTurno;

    /*
     * Texto que muestra mensajes al jugador.
     */
    private JLabel etiquetaEstado;

    /*
     * Texto que indica si estamos colocando
     * o moviendo una ficha.
     */
    private JLabel etiquetaModo;

    /*
     * Botón para seleccionar el modo colocar.
     */
    private JButton botonColocar;

    /*
     * Botón para seleccionar el modo mover.
     */
    private JButton botonMover;

    /*
     * Fila de la ficha que el usuario seleccionó
     * cuando quiere moverla.
     */
    private int filaSeleccionada = -1;

    /*
     * Columna de la ficha seleccionada.
     */
    private int columnaSeleccionada = -1;

    /*
     * Tenemos dos formas de jugar:
     *
     * COLOCAR -> poner una ficha nueva.
     * MOVER -> mover una ficha existente.
     */
    private enum Modo {

        COLOCAR,
        MOVER
    }

    /*
     * Por defecto comenzamos colocando fichas.
     */
    private Modo modoActual = Modo.COLOCAR;

    /**
     * Constructor de la ventana.
     *
     * @param configuracion opciones seleccionadas
     *                      antes de comenzar
     */
    public VentanaJuego(
            Configuracion configuracion) {

        /*
         * Guardamos la configuración.
         */
        this.configuracion = configuracion;

        /*
         * Creamos el primer estado del juego.
         */
        this.estado =
                new EstadoJuego(
                        configuracion.getJugadorInicial()
                );

        /*
         * Creamos la IA usando el símbolo
         * que seleccionó el usuario.
         */
        this.minimax =
                new Minimax(
                        configuracion.getJugadorIA()
                );

        /*
         * Título de la ventana.
         */
        setTitle(
                "Triqui-Móvil - Grupo 4"
        );

        /*
         * Tamaño de la ventana.
         */
        setSize(
                720,
                820
        );

        /*
         * Centramos la ventana.
         */
        setLocationRelativeTo(null);

        /*
         * Si cerramos la ventana,
         * cerramos completamente el programa.
         */
        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        /*
         * Construimos la interfaz.
         */
        construirInterfaz();

        /*
         * Mostramos el estado inicial
         * en pantalla.
         */
        actualizarInterfaz();

        /*
         * Si comienza la IA,
         * hacemos automáticamente su primer movimiento.
         */
        if (estado.getJugadorActual()
                == configuracion.getJugadorIA()) {

            realizarMovimientoIA();
        }
    }

    /**
     * Construye todos los elementos de la ventana.
     */
    private void construirInterfaz() {

        /*
         * Panel principal.
         */
        JPanel principal =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        /*
         * Espacio alrededor del contenido.
         */
        principal.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        // =========================================================
        // ENCABEZADO
        // =========================================================

        /*
         * Panel donde aparecerá el título,
         * el turno y el modo actual.
         */
        JPanel encabezado =
                new JPanel(
                        new GridLayout(
                                3,
                                1
                        )
                );

        /*
         * Título principal.
         */
        JLabel titulo =
                new JLabel(
                        "TRIQUI-MÓVIL",
                        SwingConstants.CENTER
                );

        titulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );

        /*
         * Texto que indicará el turno.
         */
        etiquetaTurno =
                new JLabel(
                        "",
                        SwingConstants.CENTER
                );

        etiquetaTurno.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        /*
         * Texto que indicará el modo actual.
         */
        etiquetaModo =
                new JLabel(
                        "",
                        SwingConstants.CENTER
                );

        etiquetaModo.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        /*
         * Agregamos los tres elementos al encabezado.
         */
        encabezado.add(titulo);
        encabezado.add(etiquetaTurno);
        encabezado.add(etiquetaModo);

        /*
         * Ponemos el encabezado arriba.
         */
        principal.add(
                encabezado,
                BorderLayout.NORTH
        );

        // =========================================================
        // TABLERO
        // =========================================================

        /*
         * Creamos una cuadrícula de 3 x 3.
         */
        JPanel tableroPanel =
                new JPanel(
                        new GridLayout(
                                3,
                                3,
                                8,
                                8
                        )
                );

        /*
         * Dejamos un espacio alrededor del tablero.
         */
        tableroPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        /*
         * Creamos la matriz de botones.
         */
        botones =
                new JButton[3][3];

        /*
         * Recorremos las tres filas.
         */
        for (int fila = 0;
             fila < 3;
             fila++) {

            /*
             * Recorremos las tres columnas.
             */
            for (int columna = 0;
                 columna < 3;
                 columna++) {

                /*
                 * Guardamos estos valores porque
                 * los utilizaremos dentro del botón.
                 */
                final int f = fila;
                final int c = columna;

                /*
                 * Creamos un botón que representa
                 * una casilla.
                 */
                JButton boton =
                        new JButton();

                /*
                 * Tamaño de X y O.
                 */
                boton.setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                70
                        )
                );

                /*
                 * Quitamos el borde de enfoque
                 * que aparece al pulsar.
                 */
                boton.setFocusPainted(false);

                /*
                 * Tamaño de cada casilla.
                 */
                boton.setPreferredSize(
                        new Dimension(
                                150,
                                150
                        )
                );

                /*
                 * Cuando hacemos clic,
                 * llamamos al método procesarClick().
                 */
                boton.addActionListener(
                        e -> procesarClick(
                                f,
                                c
                        )
                );

                /*
                 * Guardamos el botón en nuestra matriz.
                 */
                botones[fila][columna] =
                        boton;

                /*
                 * Agregamos el botón al tablero.
                 */
                tableroPanel.add(
                        boton
                );
            }
        }

        /*
         * Colocamos el tablero en el centro.
         */
        principal.add(
                tableroPanel,
                BorderLayout.CENTER
        );

        // =========================================================
        // PARTE INFERIOR
        // =========================================================

        /*
         * Panel para los controles inferiores.
         */
        JPanel inferior =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        /*
         * Mensaje que aparecerá debajo del tablero.
         */
        etiquetaEstado =
                new JLabel(
                        "Selecciona una acción.",
                        SwingConstants.CENTER
                );

        etiquetaEstado.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        /*
         * Ponemos el mensaje arriba de los botones.
         */
        inferior.add(
                etiquetaEstado,
                BorderLayout.NORTH
        );

        // =========================================================
        // BOTONES DE ACCIONES
        // =========================================================

        JPanel acciones =
                new JPanel();

        /*
         * Botón para colocar.
         */
        botonColocar =
                new JButton(
                        "COLOCAR FICHA"
                );

        /*
         * Botón para mover.
         */
        botonMover =
                new JButton(
                        "MOVER FICHA"
                );

        /*
         * Fuente de los botones.
         */
        botonColocar.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        botonMover.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        /*
         * Cuando pulsamos "Colocar",
         * cambiamos al modo colocar.
         */
        botonColocar.addActionListener(
                e -> {

                    /*
                     * Cambiamos el modo.
                     */
                    modoActual = Modo.COLOCAR;

                    /*
                     * Quitamos cualquier selección anterior.
                     */
                    filaSeleccionada = -1;
                    columnaSeleccionada = -1;

                    /*
                     * Actualizamos la pantalla.
                     */
                    actualizarInterfaz();
                }
        );

        /*
         * Cuando pulsamos "Mover",
         * cambiamos al modo mover.
         */
        botonMover.addActionListener(
                e -> {

                    /*
                     * Cambiamos al modo mover.
                     */
                    modoActual = Modo.MOVER;

                    /*
                     * No tenemos ninguna ficha seleccionada todavía.
                     */
                    filaSeleccionada = -1;
                    columnaSeleccionada = -1;

                    /*
                     * Mostramos una instrucción.
                     */
                    etiquetaEstado.setText(
                            "Selecciona la ficha que quieres mover."
                    );

                    /*
                     * Actualizamos la interfaz.
                     */
                    actualizarInterfaz();
                }
        );

        /*
         * Agregamos los dos botones.
         */
        acciones.add(
                botonColocar
        );

        acciones.add(
                botonMover
        );

        /*
         * Colocamos las acciones en el centro.
         */
        inferior.add(
                acciones,
                BorderLayout.CENTER
        );

        // =========================================================
        // BOTONES FINALES
        // =========================================================

        JPanel opciones =
                new JPanel();

        /*
         * Botón para reiniciar.
         */
        JButton reiniciar =
                new JButton(
                        "Reiniciar"
                );

        /*
         * Botón para volver al menú.
         */
        JButton menu =
                new JButton(
                        "Menú principal"
                );

        /*
         * Reiniciar llama al método correspondiente.
         */
        reiniciar.addActionListener(
                e -> reiniciarPartida()
        );

        /*
         * Menú vuelve a la pantalla inicial.
         */
        menu.addActionListener(
                e -> volverAlMenu()
        );

        /*
         * Agregamos ambos botones.
         */
        opciones.add(reiniciar);
        opciones.add(menu);

        /*
         * Colocamos los botones abajo.
         */
        inferior.add(
                opciones,
                BorderLayout.SOUTH
        );

        /*
         * Agregamos todo el panel inferior
         * a la ventana.
         */
        principal.add(
                inferior,
                BorderLayout.SOUTH
        );

        /*
         * Finalmente ponemos el panel principal
         * dentro de la ventana.
         */
        add(principal);
    }

    /**
     * Se ejecuta cuando el usuario hace clic
     * sobre una casilla del tablero.
     */
    private void procesarClick(
            int fila,
            int columna) {

        /*
         * Primero verificamos que realmente sea
         * el turno del jugador humano.
         *
         * Si es el turno de la IA,
         * ignoramos el clic.
         */
        if (estado.getJugadorActual()
                != configuracion.getJugadorHumano()) {

            return;
        }

        // =========================================================
        // MODO COLOCAR
        // =========================================================

        if (modoActual == Modo.COLOCAR) {

            /*
             * No podemos colocar una ficha
             * encima de otra.
             */
            if (!estado.estaVacia(
                    fila,
                    columna
            )) {

                mostrarMensaje(
                        "La casilla ya está ocupada."
                );

                return;
            }

            /*
             * Creamos el movimiento de colocación.
             */
            Movimiento movimiento =
                    new Movimiento(
                            fila,
                            columna
                    );

            /*
             * Aplicamos el movimiento al estado.
             */
            estado =
                    estado.aplicarMovimiento(
                            movimiento
                    );

            /*
             * Terminamos el turno humano.
             */
            finalizarTurnoHumano();

            return;
        }

        // =========================================================
        // MODO MOVER
        // =========================================================

        if (modoActual == Modo.MOVER) {

            /*
             * Si todavía no hemos seleccionado
             * ninguna ficha...
             */
            if (filaSeleccionada == -1) {

                /*
                 * Comprobamos que la ficha seleccionada
                 * pertenezca al jugador humano.
                 */
                if (estado.getCasilla(
                        fila,
                        columna
                ) != configuracion
                        .getJugadorHumano()) {

                    mostrarMensaje(
                            "Debes seleccionar una ficha propia."
                    );

                    return;
                }

                /*
                 * Guardamos la posición de la ficha.
                 */
                filaSeleccionada = fila;
                columnaSeleccionada = columna;

                /*
                 * Indicamos al usuario que ahora
                 * debe elegir el destino.
                 */
                etiquetaEstado.setText(
                        "Ahora selecciona una casilla vacía adyacente."
                );

                /*
                 * Actualizamos la interfaz.
                 */
                actualizarInterfaz();

                return;
            }

            /*
             * Llegamos aquí cuando ya habíamos seleccionado
             * una ficha y ahora queremos seleccionar
             * dónde moverla.
             */

            /*
             * El destino debe estar vacío.
             */
            if (!estado.estaVacia(
                    fila,
                    columna
            )) {

                mostrarMensaje(
                        "El destino debe estar vacío."
                );

                return;
            }

            /*
             * Calculamos cuánto nos movimos en filas.
             */
            int distanciaFila =
                    Math.abs(
                            fila -
                            filaSeleccionada
                    );

            /*
             * Calculamos cuánto nos movimos en columnas.
             */
            int distanciaColumna =
                    Math.abs(
                            columna -
                            columnaSeleccionada
                    );

            /*
             * Para ser válido:
             *
             * arriba    -> 1
             * abajo     -> 1
             * izquierda -> 1
             * derecha   -> 1
             *
             * Cualquier movimiento diagonal o de más
             * de una casilla será rechazado.
             */
            if (distanciaFila +
                    distanciaColumna != 1) {

                mostrarMensaje(
                        "Solo puedes mover horizontalmente " +
                        "o verticalmente una casilla."
                );

                return;
            }

            /*
             * Creamos el movimiento de desplazamiento.
             */
            Movimiento movimiento =
                    new Movimiento(
                            filaSeleccionada,
                            columnaSeleccionada,
                            fila,
                            columna
                    );

            /*
             * Aplicamos el movimiento.
             */
            estado =
                    estado.aplicarMovimiento(
                            movimiento
                    );

            /*
             * Quitamos la selección.
             */
            filaSeleccionada = -1;
            columnaSeleccionada = -1;

            /*
             * Terminamos el turno humano.
             */
            finalizarTurnoHumano();
        }
    }

    /**
     * Termina el turno del jugador humano.
     */
    private void finalizarTurnoHumano() {

        /*
         * Actualizamos inmediatamente el tablero.
         */
        actualizarInterfaz();

        /*
         * Comprobamos si el jugador ganó
         * o si la partida terminó.
         */
        if (verificarFinDelJuego()) {

            return;
        }

        /*
         * Si el juego continúa,
         * le toca jugar a la IA.
         */
        realizarMovimientoIA();
    }

    /**
     * Hace que la IA piense y realice su movimiento.
     */
    private void realizarMovimientoIA() {

        /*
         * Si el juego ya terminó,
         * no hacemos nada.
         */
        if (estado.esTerminal()) {

            return;
        }

        /*
         * Mostramos un mensaje mientras la IA calcula.
         */
        etiquetaEstado.setText(
                "La Inteligencia Artificial está pensando..."
        );

        /*
         * Desactivamos los botones humanos
         * mientras la IA está jugando.
         */
        botonColocar.setEnabled(false);
        botonMover.setEnabled(false);

        /*
         * SwingWorker permite ejecutar el cálculo
         * sin congelar la ventana.
         */
        SwingWorker<Movimiento, Void> worker =
                new SwingWorker<>() {

                    /*
                     * Este método realiza el cálculo
                     * de MIN-MAX.
                     */
                    @Override
                    protected Movimiento doInBackground() {

                        return minimax
                                .obtenerMejorMovimiento(
                                        estado
                                );
                    }

                    /*
                     * Este método se ejecuta cuando
                     * la IA terminó de pensar.
                     */
                    @Override
                    protected void done() {

                        try {

                            /*
                             * Obtenemos el movimiento calculado.
                             */
                            Movimiento movimiento =
                                    get();

                            /*
                             * Si existe un movimiento,
                             * lo aplicamos.
                             */
                            if (movimiento != null) {

                                estado =
                                        estado.aplicarMovimiento(
                                                movimiento
                                        );
                            }

                            /*
                             * Actualizamos el tablero.
                             */
                            actualizarInterfaz();

                            /*
                             * Comprobamos si la IA ganó.
                             */
                            if (!verificarFinDelJuego()) {

                                /*
                                 * Si la partida continúa,
                                 * volvemos a habilitar los controles.
                                 */
                                botonColocar.setEnabled(true);

                                botonMover.setEnabled(true);

                                /*
                                 * Indicamos que es el turno humano.
                                 */
                                etiquetaEstado.setText(
                                        "Tu turno. Selecciona una acción."
                                );
                            }

                        } catch (Exception ex) {

                            /*
                             * Si ocurre un error,
                             * lo mostramos en la consola.
                             */
                            ex.printStackTrace();

                            /*
                             * Y mostramos un mensaje al usuario.
                             */
                            mostrarMensaje(
                                    "Ocurrió un error al ejecutar la IA."
                            );
                        }
                    }
                };

        /*
         * Iniciamos el trabajo de la IA.
         */
        worker.execute();
    }

    /**
     * Comprueba si la partida ya terminó.
     *
     * Devuelve:
     *
     * true  -> la partida terminó.
     * false -> la partida continúa.
     */
    private boolean verificarFinDelJuego() {

        /*
         * Preguntamos si existe un ganador.
         */
        char ganador =
                estado.obtenerGanador();

        /*
         * Si encontramos un ganador...
         */
        if (ganador != ' ') {

            /*
             * Aquí guardaremos el mensaje.
             */
            String mensaje;

            /*
             * Comprobamos si ganó la IA.
             */
            if (ganador ==
                    configuracion.getJugadorIA()) {

                mensaje =
                        "¡La Inteligencia Artificial ha ganado!";

            } else {

                /*
                 * Si no ganó la IA,
                 * ganó el jugador humano.
                 */
                mensaje =
                        "¡Has ganado la partida!";
            }

            /*
             * Mostramos el resultado en pantalla.
             */
            etiquetaEstado.setText(
                    mensaje
            );

            /*
             * Desactivamos los botones.
             */
            botonColocar.setEnabled(false);
            botonMover.setEnabled(false);

            /*
             * Mostramos una ventana con el resultado.
             */
            mostrarMensaje(
                    mensaje
            );

            /*
             * Indicamos que la partida terminó.
             */
            return true;
        }

        /*
         * Si no hubo ganador, revisamos si quedan movimientos.
         */
        if (estado.generarMovimientos().isEmpty()) {

            /*
             * Mostramos empate.
             */
            etiquetaEstado.setText(
                    "La partida terminó en empate."
            );

            /*
             * Desactivamos los controles.
             */
            botonColocar.setEnabled(false);
            botonMover.setEnabled(false);

            /*
             * Mostramos el mensaje.
             */
            mostrarMensaje(
                    "¡Empate!"
            );

            return true;
        }

        /*
         * Si llegamos aquí,
         * la partida todavía continúa.
         */
        return false;
    }

    /**
     * Actualiza lo que vemos en pantalla
     * según el estado actual del juego.
     */
    private void actualizarInterfaz() {

        /*
         * Recorremos las nueve casillas.
         */
        for (int fila = 0;
             fila < 3;
             fila++) {

            for (int columna = 0;
                 columna < 3;
                 columna++) {

                /*
                 * Preguntamos qué hay en esa casilla.
                 */
                char valor =
                        estado.getCasilla(
                                fila,
                                columna
                        );

                /*
                 * Obtenemos el botón correspondiente.
                 */
                JButton boton =
                        botones[fila][columna];

                /*
                 * Si está vacía...
                 */
                if (valor == ' ') {

                    /*
                     * Dejamos el botón sin texto.
                     */
                    boton.setText("");

                } else {

                    /*
                     * Si hay X u O,
                     * mostramos esa letra.
                     */
                    boton.setText(
                            String.valueOf(valor)
                    );
                }
            }
        }

        /*
         * Obtenemos quién tiene el turno.
         */
        char turno =
                estado.getJugadorActual();

        /*
         * Aquí guardaremos si juega la IA o el humano.
         */
        String tipoJugador;

        /*
         * Comparamos el turno con el símbolo de la IA.
         */
        if (turno ==
                configuracion.getJugadorIA()) {

            tipoJugador =
                    "Inteligencia Artificial";

        } else {

            tipoJugador =
                    "Jugador humano";
        }

        /*
         * Mostramos quién tiene el turno.
         */
        etiquetaTurno.setText(
                "Turno: " +
                turno +
                " — " +
                tipoJugador
        );

        /*
         * Mostramos el modo actual.
         */
        if (modoActual == Modo.COLOCAR) {

            etiquetaModo.setText(
                    "Modo actual: Colocar ficha"
            );

        } else {

            etiquetaModo.setText(
                    "Modo actual: Mover ficha"
            );
        }
    }

    /**
     * Reinicia la partida manteniendo
     * la misma configuración.
     */
    private void reiniciarPartida() {

        /*
         * Creamos nuevamente el estado inicial.
         */
        estado =
                new EstadoJuego(
                        configuracion.getJugadorInicial()
                );

        /*
         * Volvemos al modo colocar.
         */
        modoActual = Modo.COLOCAR;

        /*
         * Quitamos cualquier selección.
         */
        filaSeleccionada = -1;
        columnaSeleccionada = -1;

        /*
         * Habilitamos los botones.
         */
        botonColocar.setEnabled(true);
        botonMover.setEnabled(true);

        /*
         * Actualizamos la pantalla.
         */
        actualizarInterfaz();

        /*
         * Si la IA comienza,
         * hacemos nuevamente su primer movimiento.
         */
        if (estado.getJugadorActual()
                == configuracion.getJugadorIA()) {

            realizarMovimientoIA();
        }
    }

    /**
     * Regresa a la pantalla inicial.
     */
    private void volverAlMenu() {

        /*
         * Cerramos la ventana actual.
         */
        dispose();

        /*
         * Creamos nuevamente la ventana inicial.
         */
        SwingUtilities.invokeLater(() -> {

            VentanaInicio inicio =
                    new VentanaInicio();

            inicio.setVisible(true);
        });
    }

    /**
     * Muestra una ventana pequeña con un mensaje.
     */
    private void mostrarMensaje(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Triqui-Móvil",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}