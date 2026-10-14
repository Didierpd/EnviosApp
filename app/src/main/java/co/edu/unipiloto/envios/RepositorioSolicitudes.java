package co.edu.unipiloto.envios;

import java.util.ArrayList;
import java.util.List;

/** Almacena en memoria las solicitudes registradas mientras la app está abierta. */
public final class RepositorioSolicitudes {

    private static final List<Solicitud> SOLICITUDES = new ArrayList<>();

    private RepositorioSolicitudes() { }

    public static int siguienteNumero() {
        return SOLICITUDES.size() + 1;
    }

    public static void agregar(Solicitud s) {
        SOLICITUDES.add(s);
    }

    public static List<Solicitud> todas() {
        return SOLICITUDES;
    }

    public static Solicitud ultima() {
        return SOLICITUDES.isEmpty() ? null : SOLICITUDES.get(SOLICITUDES.size() - 1);
    }
}
