package co.edu.unipiloto.envios;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** Solicitud de envío de encomienda. */
public class Solicitud {

    private final int numero;
    private final String remitente;
    private final String destinatario;
    private final String direccionOrigen;
    private final String direccionDestino;
    private final String ciudadDestino;
    private final double pesoKg;
    private final String tipoPaquete;
    private final String descripcion;
    private final Date fecha;

    public Solicitud(int numero, String remitente, String destinatario, String direccionOrigen,
                     String direccionDestino, String ciudadDestino, double pesoKg,
                     String tipoPaquete, String descripcion) {
        this.numero = numero;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
        this.ciudadDestino = ciudadDestino;
        this.pesoKg = pesoKg;
        this.tipoPaquete = tipoPaquete;
        this.descripcion = descripcion;
        this.fecha = new Date();
    }

    public int getNumero() { return numero; }
    public String getRemitente() { return remitente; }
    public String getDestinatario() { return destinatario; }
    public String getCiudadDestino() { return ciudadDestino; }
    public String getTipoPaquete() { return tipoPaquete; }
    public double getPesoKg() { return pesoKg; }

    /** Texto corto para la lista de la actividad principal. */
    public String resumen() {
        return "#" + numero + "  " + remitente + " → " + destinatario
                + "\n" + ciudadDestino + " · " + tipoPaquete + " · " + pesoKg + " kg";
    }

    /** Texto completo para compartir o mostrar. */
    public String detalle() {
        String f = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(fecha);
        return "Solicitud de envío #" + numero + " (" + f + ")\n"
                + "Remitente: " + remitente + "\n"
                + "Recogida: " + direccionOrigen + "\n"
                + "Destinatario: " + destinatario + "\n"
                + "Entrega: " + direccionDestino + ", " + ciudadDestino + "\n"
                + "Tipo: " + tipoPaquete + " · Peso: " + pesoKg + " kg\n"
                + "Contenido: " + descripcion;
    }
}
