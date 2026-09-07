import javax.swing.SwingUtilities;

/**
 * Esta es la clase desde la que comienza
 * nuestro programa.
 */
public class Main {

    /**
     * Método principal.
     *
     * Java comienza la ejecución aquí.
     */
    public static void main(String[] args) {

        /*
         * SwingUtilities.invokeLater se utiliza para
         * abrir correctamente nuestra interfaz gráfica.
         *
         * No necesitamos preocuparnos demasiado por esto:
         * simplemente significa que Java abrirá la ventana
         * de forma segura.
         */
        SwingUtilities.invokeLater(() -> {

            /*
             * Creamos la ventana inicial.
             */
            VentanaInicio ventana =
                    new VentanaInicio();

            /*
             * Hacemos visible la ventana.
             */
            ventana.setVisible(true);
        });
    }
}