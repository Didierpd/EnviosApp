package co.edu.unipiloto.envios;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/** Crea el canal y publica la notificación de la solicitud. */
public final class NotificacionHelper {

    private static final String CANAL_ID = "solicitudes_envio";

    private NotificacionHelper() { }

    private static void crearCanal(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(CANAL_ID,
                    ctx.getString(R.string.canal_nombre), NotificationManager.IMPORTANCE_DEFAULT);
            canal.setDescription(ctx.getString(R.string.canal_descripcion));
            ctx.getSystemService(NotificationManager.class).createNotificationChannel(canal);
        }
    }

    /** Muestra la notificación. El permiso se verifica antes en MainActivity. */
    @SuppressLint("MissingPermission")
    public static void mostrar(Context ctx, String titulo, String texto) {
        crearCanal(ctx);

        Intent abrir = new Intent(ctx, MainActivity.class);
        abrir.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = PendingIntent.getActivity(ctx, 0, abrir,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CANAL_ID)
                .setSmallIcon(R.drawable.ic_notificacion)
                .setContentTitle(titulo)
                .setContentText(texto)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(texto))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pi)
                .setAutoCancel(true);

        NotificationManagerCompat.from(ctx).notify((int) System.currentTimeMillis(), builder.build());
    }
}
