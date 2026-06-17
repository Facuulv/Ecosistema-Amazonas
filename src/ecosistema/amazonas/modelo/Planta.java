package ecosistema.amazonas.modelo;

public class Planta extends Entidad {

    private int tamanio;

    public Planta(String nombre, int edad, double energia, boolean viva, int fila, int columna, int tamanio) {
        super(nombre, edad, energia, viva, fila, columna);
        setTamanio(tamanio);
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        if (tamanio >= 1 && tamanio <= 5) {
            this.tamanio = tamanio;
        } else if (tamanio < 1) {
            this.tamanio = 1;
        } else {
            this.tamanio = 5;
        }
    }

    // Alias para mantener compatibilidad con nombres anteriores
    public void setearTamanio(int tamanio) {
        setTamanio(tamanio);
    }
}