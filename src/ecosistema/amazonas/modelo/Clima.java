package ecosistema.amazonas.modelo;

public enum Clima {

    SOLEADO("Soleado", 1.5, 5, 0, 0.0),
    LLUVIOSO("Lluvioso", 2.0, 3, -5, 0.0),
    SEQUIA("Sequía", 0.5, -5, 0, 0.0),
    INVIERNO("Invierno", 0.0, -8, 0, 0.20);

    private final String nombreVisible;
    private final double multiplicadorReproduccionPlantas;
    private final int energiaExtraConejo;
    private final int energiaExtraLobo;
    private final double bonusCazaLobo;

    private Clima(String nombreVisible, double multiplicadorReproduccionPlantas, int energiaExtraConejo, int energiaExtraLobo, double bonusCazaLobo) {
        this.nombreVisible = nombreVisible;
        this.multiplicadorReproduccionPlantas = multiplicadorReproduccionPlantas;
        this.energiaExtraConejo = energiaExtraConejo;
        this.energiaExtraLobo = energiaExtraLobo;
        this.bonusCazaLobo = bonusCazaLobo;
    }

    public String getNombreVisible() {
        return nombreVisible;
    }

    public double getMultiplicadorReproduccionPlantas() {
        return multiplicadorReproduccionPlantas;
    }

    public int getEnergiaExtraConejo() {
        return energiaExtraConejo;
    }

    public int getEnergiaExtraLobo() {
        return energiaExtraLobo;
    }

    public double getBonusCazaLobo() {
        return bonusCazaLobo;
    }

    @Override
    public String toString() {
        return nombreVisible;
    }

    public static Clima desdeTexto(String texto) {
        if (texto == null) {
            return SOLEADO;
        }

        String valor = texto.trim().toUpperCase();

        switch (valor) {
            case "SOLEADO":
            case "SOL":
                return SOLEADO;

            case "LLUVIOSO":
            case "LLUVIA":
                return LLUVIOSO;

            case "SEQUIA":
            case "SEQUÍA":
                return SEQUIA;

            case "INVIERNO":
                return INVIERNO;

            default:
                return SOLEADO;
        }
    }
}