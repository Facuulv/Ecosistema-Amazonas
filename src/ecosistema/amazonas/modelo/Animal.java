package ecosistema.amazonas.modelo;

public abstract class Animal extends Entidad {

    private int velocidad;
    private double peso;

    public Animal(String nombre, int edad, double energia, boolean viva, int fila, int columna, int velocidad, double peso) {
        super(nombre, edad, energia, viva, fila, columna);
        setVelocidad(velocidad);
        setPeso(peso);
    }

    public int getVelocidad() {
        return velocidad;
    }

    public double getPeso() {
        return peso;
    }

    public void setVelocidad(int velocidad) {
        if (velocidad >= 0) {
            this.velocidad = velocidad;
        } else {
            this.velocidad = 0;
        }
    }

    public void setPeso(double peso) {
        if (peso >= 0) {
            this.peso = peso;
        } else {
            this.peso = 0;
        }
    }

   
    public void setearVelocidad(int velocidad) {
        setVelocidad(velocidad);
    }

    public void setearPeso(double peso) {
        setPeso(peso);
    }
}