package ecosistema.amazonas.modelo;

public class Lobo extends Animal {

    private int exitosCaza;

    public Lobo(String nombre, int edad, double energia, boolean viva, int fila, int columna, int velocidad, double peso, int exitosCaza) {
        super(nombre, edad, energia, viva, fila, columna, velocidad, peso);
        setExitosCaza(exitosCaza);
    }

    public int getExitosCaza() {
        return exitosCaza;
    }

    public void setExitosCaza(int exitosCaza) {
        if (exitosCaza >= 0) {
            this.exitosCaza = exitosCaza;
        } else {
            this.exitosCaza = 0;
        }
    }

    // Alias para mantener compatibilidad con nombres anteriores
    public void setearExitosCaza(int exitosCaza) {
        setExitosCaza(exitosCaza);
    }
}