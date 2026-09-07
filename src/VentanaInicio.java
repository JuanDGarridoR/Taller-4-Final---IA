import javax.swing.*;
import java.awt.*;

/**
 * Esta clase representa la primera ventana del programa.
 *
 * Desde aquí configuramos la partida antes de comenzar.
 */
public class VentanaInicio extends JFrame {

    /*
     * Botón para indicar que el jugador humano comienza.
     */
    private JRadioButton humanoInicia;

    /*
     * Botón para indicar que la IA comienza.
     */
    private JRadioButton iaInicia;

    /*
     * Opción para que la IA utilice X.
     */
    private JRadioButton iaX;

    /*
     * Opción para que la IA utilice O.
     */
    private JRadioButton iaO;

    /**
     * Constructor de la ventana.
     */
    public VentanaInicio() {

        /*
         * Texto que aparecerá en la parte superior de la ventana.
         */
        setTitle(
                "Triqui-Móvil - Taller 4 IA"
        );

        /*
         * Tamaño de la ventana.
         */
        setSize(520, 560);

        /*
         * Hace que la ventana aparezca centrada
         * en la pantalla.
         */
        setLocationRelativeTo(null);

        /*
         * Si cerramos esta ventana,
         * termina el programa.
         */
        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        /*
         * Construimos todos los elementos visuales.
         */
        construirInterfaz();
    }

    /**
     * Crea los elementos que aparecen en la ventana.
     */
    private void construirInterfaz() {

        /*
         * Panel principal de la ventana.
         *
         * BorderLayout permite dividir la ventana
         * en diferentes zonas.
         */
        JPanel principal =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        /*
         * Dejamos espacio alrededor del contenido
         * para que no quede pegado a los bordes.
         */
        principal.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        40,
                        30,
                        40
                )
        );

        // =========================================================
        // TÍTULO
        // =========================================================

        /*
         * Creamos el título.
         *
         * HTML nos permite poner dos líneas
         * y cambiar un poco el tamaño del texto.
         */
        JLabel titulo =
                new JLabel(
                        "<html><center>" +
                        "TRIQUI-MÓVIL<br>" +
                        "<font size='4'>" +
                        "Taller 4 - Inteligencia Artificial" +
                        "</font>" +
                        "</center></html>",
                        SwingConstants.CENTER
                );

        /*
         * Elegimos una fuente grande y en negrita.
         */
        titulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        /*
         * Colocamos el título arriba.
         */
        principal.add(
                titulo,
                BorderLayout.NORTH
        );

        // =========================================================
        // CONFIGURACIÓN
        // =========================================================

        /*
         * Panel donde estarán las opciones.
         */
        JPanel configuracion =
                new JPanel();

        /*
         * Los elementos aparecerán uno debajo de otro.
         */
        configuracion.setLayout(
                new BoxLayout(
                        configuracion,
                        BoxLayout.Y_AXIS
                )
        );

        // =========================================================
        // ¿QUIÉN INICIA?
        // =========================================================

        JLabel etiquetaInicio =
                new JLabel(
                        "¿Quién inicia la partida?"
                );

        /*
         * Hacemos que la pregunta sea visible.
         */
        etiquetaInicio.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        configuracion.add(
                etiquetaInicio
        );

        /*
         * Dejamos un pequeño espacio.
         */
        configuracion.add(
                Box.createVerticalStrut(10)
        );

        /*
         * Opción para que comience el humano.
         */
        humanoInicia =
                new JRadioButton(
                        "Jugador humano"
                );

        /*
         * Opción para que comience la IA.
         */
        iaInicia =
                new JRadioButton(
                        "Inteligencia Artificial"
                );

        /*
         * Por defecto comenzará el humano.
         */
        humanoInicia.setSelected(true);

        /*
         * Creamos un grupo para que solamente
         * se pueda elegir una opción.
         */
        ButtonGroup grupoInicio =
                new ButtonGroup();

        /*
         * Metemos las dos opciones en el mismo grupo.
         */
        grupoInicio.add(
                humanoInicia
        );

        grupoInicio.add(
                iaInicia
        );

        /*
         * Agregamos las opciones visualmente.
         */
        configuracion.add(
                humanoInicia
        );

        configuracion.add(
                iaInicia
        );

        /*
         * Dejamos espacio antes de la siguiente pregunta.
         */
        configuracion.add(
                Box.createVerticalStrut(25)
        );

        // =========================================================
        // SÍMBOLO DE LA IA
        // =========================================================

        JLabel etiquetaIA =
                new JLabel(
                        "¿Qué símbolo utilizará la IA?"
                );

        etiquetaIA.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        configuracion.add(
                etiquetaIA
        );

        configuracion.add(
                Box.createVerticalStrut(10)
        );

        /*
         * Opción X.
         */
        iaX =
                new JRadioButton("X");

        /*
         * Opción O.
         */
        iaO =
                new JRadioButton("O");

        /*
         * Por defecto la IA utilizará O.
         */
        iaO.setSelected(true);

        /*
         * Creamos otro grupo para que solo se pueda
         * seleccionar X u O.
         */
        ButtonGroup grupoIA =
                new ButtonGroup();

        grupoIA.add(iaX);
        grupoIA.add(iaO);

        /*
         * Agregamos las opciones a la pantalla.
         */
        configuracion.add(iaX);
        configuracion.add(iaO);

        /*
         * Colocamos el panel de configuración
         * en el centro de la ventana.
         */
        principal.add(
                configuracion,
                BorderLayout.CENTER
        );

        // =========================================================
        // BOTÓN COMENZAR
        // =========================================================

        JButton comenzar =
                new JButton(
                        "COMENZAR PARTIDA"
                );

        /*
         * Hacemos el texto del botón grande.
         */
        comenzar.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        /*
         * Definimos un tamaño cómodo para el botón.
         */
        comenzar.setPreferredSize(
                new Dimension(
                        250,
                        55
                )
        );

        /*
         * Cuando el usuario pulse el botón,
         * llamamos al método iniciarPartida().
         */
        comenzar.addActionListener(
                e -> iniciarPartida()
        );

        /*
         * Creamos un panel para centrar el botón.
         */
        JPanel panelBoton =
                new JPanel();

        panelBoton.add(
                comenzar
        );

        /*
         * Colocamos el botón en la parte inferior.
         */
        principal.add(
                panelBoton,
                BorderLayout.SOUTH
        );

        /*
         * Finalmente colocamos el panel principal
         * dentro de nuestra ventana.
         */
        add(principal);
    }

    /**
     * Lee las opciones seleccionadas y crea la partida.
     */
    private void iniciarPartida() {

        /*
         * Revisamos qué símbolo escogió la IA.
         */
        char simboloIA;

        if (iaX.isSelected()) {

            simboloIA = 'X';

        } else {

            simboloIA = 'O';
        }

        /*
         * Aquí guardaremos quién comienza.
         */
        char jugadorInicial;

        /*
         * Si seleccionaron "Jugador humano"...
         */
        if (humanoInicia.isSelected()) {

            /*
             * El humano tiene el símbolo contrario
             * al de la IA.
             */
            if (simboloIA == 'X') {

                jugadorInicial = 'O';

            } else {

                jugadorInicial = 'X';
            }

        } else {

            /*
             * Si seleccionaron que inicia la IA,
             * entonces empieza utilizando su propio símbolo.
             */
            jugadorInicial = simboloIA;
        }

        /*
         * Creamos el objeto que contiene
         * toda la configuración.
         */
        Configuracion configuracion =
                new Configuracion(
                        simboloIA,
                        jugadorInicial
                );

        /*
         * Cerramos esta ventana.
         */
        dispose();

        /*
         * Abrimos la ventana principal del juego.
         */
        SwingUtilities.invokeLater(() -> {

            VentanaJuego ventana =
                    new VentanaJuego(
                            configuracion
                    );

            ventana.setVisible(true);
        });
    }
}