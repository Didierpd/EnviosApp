package co.edu.unipiloto.envios;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/** Actividad principal: gestiona el registro de solicitudes. */
public class MainActivity extends AppCompatActivity {

    private static final int REQ_NOTIFICACIONES = 100;

    private ArrayAdapter<String> adaptador;
    private final List<String> items = new ArrayList<>();
    private TextView txtVacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar como app bar de la actividad
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtVacio = findViewById(R.id.txt_vacio);
        ListView lista = findViewById(R.id.lista_solicitudes);
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lista.setAdapter(adaptador);
    }

    @Override
    protected void onResume() {
        super.onResume();
        items.clear();
        for (Solicitud s : RepositorioSolicitudes.todas()) {
            items.add(s.resumen());
        }
        adaptador.notifyDataSetChanged();
        txtVacio.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_nueva) {
            startActivity(new Intent(this, SolicitudActivity.class));
            return true;
        } else if (id == R.id.action_caracteristicas) {
            startActivity(new Intent(this, CaracteristicasActivity.class));
            return true;
        } else if (id == R.id.action_notificacion) {
            crearNotificacion();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Crea la notificación de la última solicitud registrada. */
    private void crearNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQ_NOTIFICACIONES);
            return;
        }

        Solicitud ultima = RepositorioSolicitudes.ultima();
        if (ultima == null) {
            Toast.makeText(this, "Registra una solicitud primero", Toast.LENGTH_SHORT).show();
            return;
        }
        NotificacionHelper.mostrar(this,
                "Solicitud #" + ultima.getNumero() + " registrada",
                "Envío de " + ultima.getRemitente() + " para " + ultima.getDestinatario()
                        + " en " + ultima.getCiudadDestino() + ". Total de solicitudes: "
                        + RepositorioSolicitudes.todas().size());
        Toast.makeText(this, "Notificación creada", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_NOTIFICACIONES) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                crearNotificacion();
            } else {
                Toast.makeText(this, "Sin permiso no se pueden mostrar notificaciones",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
