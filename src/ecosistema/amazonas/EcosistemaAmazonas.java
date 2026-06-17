package ecosistema.amazonas;

import ecosistema.amazonas.controlador.ControladorEcosistema;
import ecosistema.amazonas.vista.vistaPrincipal;

public class EcosistemaAmazonas {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                vistaPrincipal vista = new vistaPrincipal();
                ControladorEcosistema controlador = new ControladorEcosistema();

                vista.setControlador(controlador);
                vista.setLocationRelativeTo(null);
                vista.setVisible(true);
            }
        });
    }
}