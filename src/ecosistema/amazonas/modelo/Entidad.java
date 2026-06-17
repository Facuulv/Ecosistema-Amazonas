package ecosistema.amazonas.modelo;

public abstract class Entidad {

    private String nombre;
    private double energia;
    private int edad;
    private boolean viva;
    private int fila;
    private int columna;

    public Entidad(String nombre, int edad, double energia, boolean viva, int fila, int columna) {
        setNombre(nombre);
        setEdad(edad);
        setEnergia(energia);
        setViva(viva);
        setFila(fila);
        setColumna(columna);
    }

    public String getNombre() {
        return nombre;
    }

    public double getEnergia() {
        return energia;
    }

    public int getEdad() {
        return edad;
    }

    public boolean isViva() {
        return viva;
    }

    public boolean getViva() {
        return viva;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        } else {
            this.nombre = "Entidad";
        }
    }

    public void setEnergia(double energia) {
        if (energia < 0) {
            this.energia = 0;
        } else {
            this.energia = energia;
        }
    }

    public void setEdad(int edad) {
        if (edad >= 0) {
            this.edad = edad;
        } else {
            this.edad = 0;
        }
    }

    public void setViva(boolean viva) {
        this.viva = viva;

        if (!viva) {
            this.energia = 0;
        }
    }

    public void setFila(int fila) {
        if (fila >= 0) {
            this.fila = fila;
        } else {
            this.fila = 0;
        }
    }

    public void setColumna(int columna) {
        if (columna >= 0) {
            this.columna = columna;
        } else {
            this.columna = 0;
        }
    }

    // Métodos alias para no romper código viejo del TP1 mientras refactorizamos
    public void setearNombre(String nombre) {
        setNombre(nombre);
    }

    public void setearEnergia(double energia) {
        setEnergia(energia);
    }

    public void setearEdad(int edad) {
        setEdad(edad);
    }

    public void setearViva(boolean viva) {
        setViva(viva);
    }
}
