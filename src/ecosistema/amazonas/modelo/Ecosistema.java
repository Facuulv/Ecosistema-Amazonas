package ecosistema.amazonas.modelo;

import java.util.ArrayList;
import java.util.List;

public class Ecosistema {

    private ArrayList<Planta> plantas;
    private ArrayList<Conejo> conejos;
    private ArrayList<Lobo> lobos;

    private Clima climaActual;
    private int turnoActual;
    private int turnosTotales;

    private int nacimientosPlantas;
    private int nacimientosConejos;

    private int muertesPlantas;
    private int muertesConejos;
    private int muertesLobos;

    private ArrayList<String> eventosTurno;
    private ArrayList<String> eventosTotales;

    private ArrayList<Integer> historialPlantas;
    private ArrayList<Integer> historialConejos;
    private ArrayList<Integer> historialLobos;

    private int turnoMayorActividad;
    private int cantidadEventosMayorActividad;

    private boolean finalizada;
    private String causaFinalizacion;

    public Ecosistema() {
        this.plantas = new ArrayList<>();
        this.conejos = new ArrayList<>();
        this.lobos = new ArrayList<>();

        this.climaActual = Clima.SOLEADO;
        this.turnoActual = 0;
        this.turnosTotales = 10;

        this.nacimientosPlantas = 0;
        this.nacimientosConejos = 0;

        this.muertesPlantas = 0;
        this.muertesConejos = 0;
        this.muertesLobos = 0;

        this.eventosTurno = new ArrayList<>();
        this.eventosTotales = new ArrayList<>();

        this.historialPlantas = new ArrayList<>();
        this.historialConejos = new ArrayList<>();
        this.historialLobos = new ArrayList<>();

        this.turnoMayorActividad = 0;
        this.cantidadEventosMayorActividad = 0;

        this.finalizada = false;
        this.causaFinalizacion = "";
    }

    public Ecosistema(Clima climaActual, int turnosTotales) {
        this();
        setClimaActual(climaActual);
        setTurnosTotales(turnosTotales);
    }

    public ArrayList<Planta> getPlantas() {
        return plantas;
    }

    public void setPlantas(ArrayList<Planta> plantas) {
        if (plantas != null) {
            this.plantas = plantas;
        } else {
            this.plantas = new ArrayList<>();
        }
    }

    public ArrayList<Conejo> getConejos() {
        return conejos;
    }

    public void setConejos(ArrayList<Conejo> conejos) {
        if (conejos != null) {
            this.conejos = conejos;
        } else {
            this.conejos = new ArrayList<>();
        }
    }

    public ArrayList<Lobo> getLobos() {
        return lobos;
    }

    public void setLobos(ArrayList<Lobo> lobos) {
        if (lobos != null) {
            this.lobos = lobos;
        } else {
            this.lobos = new ArrayList<>();
        }
    }

    public Clima getClimaActual() {
        return climaActual;
    }

    public void setClimaActual(Clima climaActual) {
        if (climaActual != null) {
            this.climaActual = climaActual;
        } else {
            this.climaActual = Clima.SOLEADO;
        }
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    // hace el setting del turno actual
    public void setTurnoActual(int turnoActual) {
        if (turnoActual >= 0) {
            this.turnoActual = turnoActual;
        } else {
            this.turnoActual = 0;
        }
    }

    public int getTurnosTotales() {
        return turnosTotales;
    }

    public void setTurnosTotales(int turnosTotales) {
        if (turnosTotales >= 10 && turnosTotales <= 50) {
            this.turnosTotales = turnosTotales;
        } else if (turnosTotales < 10) {
            this.turnosTotales = 10;
        } else {
            this.turnosTotales = 50;
        }
    }

    public int getNacimientosPlantas() {
        return nacimientosPlantas;
    }

    public void setNacimientosPlantas(int nacimientosPlantas) {
        if (nacimientosPlantas >= 0) {
            this.nacimientosPlantas = nacimientosPlantas;
        } else {
            this.nacimientosPlantas = 0;
        }
    }

    public int getNacimientosConejos() {
        return nacimientosConejos;
    }

    public void setNacimientosConejos(int nacimientosConejos) {
        if (nacimientosConejos >= 0) {
            this.nacimientosConejos = nacimientosConejos;
        } else {
            this.nacimientosConejos = 0;
        }
    }

    public int getMuertesPlantas() {
        return muertesPlantas;
    }

    public void setMuertesPlantas(int muertesPlantas) {
        if (muertesPlantas >= 0) {
            this.muertesPlantas = muertesPlantas;
        } else {
            this.muertesPlantas = 0;
        }
    }

    public int getMuertesConejos() {
        return muertesConejos;
    }

    public void setMuertesConejos(int muertesConejos) {
        if (muertesConejos >= 0) {
            this.muertesConejos = muertesConejos;
        } else {
            this.muertesConejos = 0;
        }
    }

    public int getMuertesLobos() {
        return muertesLobos;
    }

    public void setMuertesLobos(int muertesLobos) {
        if (muertesLobos >= 0) {
            this.muertesLobos = muertesLobos;
        } else {
            this.muertesLobos = 0;
        }
    }

    public ArrayList<String> getEventosTurno() {
        return eventosTurno;
    }

    public void setEventosTurno(ArrayList<String> eventosTurno) {
        if (eventosTurno != null) {
            this.eventosTurno = eventosTurno;
        } else {
            this.eventosTurno = new ArrayList<>();
        }
    }

    public ArrayList<String> getEventosTotales() {
        return eventosTotales;
    }

    public void setEventosTotales(ArrayList<String> eventosTotales) {
        if (eventosTotales != null) {
            this.eventosTotales = eventosTotales;
        } else {
            this.eventosTotales = new ArrayList<>();
        }
    }

    public ArrayList<Integer> getHistorialPlantas() {
        return historialPlantas;
    }

    public void setHistorialPlantas(ArrayList<Integer> historialPlantas) {
        if (historialPlantas != null) {
            this.historialPlantas = historialPlantas;
        } else {
            this.historialPlantas = new ArrayList<>();
        }
    }

    public ArrayList<Integer> getHistorialConejos() {
        return historialConejos;
    }

    public void setHistorialConejos(ArrayList<Integer> historialConejos) {
        if (historialConejos != null) {
            this.historialConejos = historialConejos;
        } else {
            this.historialConejos = new ArrayList<>();
        }
    }

    public ArrayList<Integer> getHistorialLobos() {
        return historialLobos;
    }

    public void setHistorialLobos(ArrayList<Integer> historialLobos) {
        if (historialLobos != null) {
            this.historialLobos = historialLobos;
        } else {
            this.historialLobos = new ArrayList<>();
        }
    }

    public int getTurnoMayorActividad() {
        return turnoMayorActividad;
    }

    public void setTurnoMayorActividad(int turnoMayorActividad) {
        if (turnoMayorActividad >= 0) {
            this.turnoMayorActividad = turnoMayorActividad;
        } else {
            this.turnoMayorActividad = 0;
        }
    }

    public int getCantidadEventosMayorActividad() {
        return cantidadEventosMayorActividad;
    }

    public void setCantidadEventosMayorActividad(int cantidadEventosMayorActividad) {
        if (cantidadEventosMayorActividad >= 0) {
            this.cantidadEventosMayorActividad = cantidadEventosMayorActividad;
        } else {
            this.cantidadEventosMayorActividad = 0;
        }
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public boolean getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public String getCausaFinalizacion() {
        return causaFinalizacion;
    }

    public void setCausaFinalizacion(String causaFinalizacion) {
        if (causaFinalizacion != null) {
            this.causaFinalizacion = causaFinalizacion;
        } else {
            this.causaFinalizacion = "";
        }
    }

    public List<Entidad> getEntidadesVivas() {
        ArrayList<Entidad> entidadesVivas = new ArrayList<>();

        for (Planta planta : plantas) {
            if (planta.getViva()) {
                entidadesVivas.add(planta);
            }
        }

        for (Conejo conejo : conejos) {
            if (conejo.getViva()) {
                entidadesVivas.add(conejo);
            }
        }

        for (Lobo lobo : lobos) {
            if (lobo.getViva()) {
                entidadesVivas.add(lobo);
            }
        }

        return entidadesVivas;
    }
}