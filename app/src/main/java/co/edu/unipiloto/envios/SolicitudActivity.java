package co.edu.unipiloto.envios;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/** Formulario de solicitud de envío de encomienda. */
public class SolicitudActivity extends AppCompatActivity {

    private EditText etRemitente, etDestinatario, etOrigen, etDestino, etCiudad, etPeso, etDescripcion;
    private Spinner spTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);

        // Toolbar con botón Up hacia MainActivity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        etRemitente = findViewById(R.id.et_remitente);
        etDestinatario = findViewById(R.id.et_destinatario);
        etOrigen = findViewById(R.id.et_origen);
        etDestino = findViewById(R.id.et_destino);
        etCiudad = findViewById(R.id.et_ciudad);
        etPeso = findViewById(R.id.et_peso);
        etDescripcion = findViewById(R.id.et_descripcion);
        spTipo = findViewById(R.id.sp_tipo);

        Button btnRegistrar = findViewById(R.id.btn_registrar);
        Button btnCompartir = findViewById(R.id.btn_compartir);
        btnRegistrar.setOnClickListener(v -> registrar());
        btnCompartir.setOnClickListener(v -> compartir());
    }

    /** Valida los campos obligatorios y arma la solicitud. Devuelve null si falta algo. */
    private Solicitud leerFormulario(int numero) {
        EditText[] obligatorios = {etRemitente, etDestinatario, etOrigen, etDestino, etCiudad, etPeso};
        for (EditText et : obligatorios) {
            if (TextUtils.isEmpty(et.getText().toString().trim())) {
                et.setError("Campo obligatorio");
                et.requestFocus();
                return null;
            }
        }
        double peso;
        try {
            peso = Double.parseDouble(etPeso.getText().toString().trim());
        } catch (NumberFormatException e) {
            etPeso.setError("Peso no válido");
            return null;
        }
        if (peso <= 0) {
            etPeso.setError("El peso debe ser mayor a 0");
            return null;
        }
        return new Solicitud(numero,
                texto(etRemitente), texto(etDestinatario), texto(etOrigen), texto(etDestino),
                texto(etCiudad), peso, spTipo.getSelectedItem().toString(), texto(etDescripcion));
    }

    private String texto(EditText et) {
        return et.getText().toString().trim();
    }

    private void registrar() {
        Solicitud s = leerFormulario(RepositorioSolicitudes.siguienteNumero());
        if (s == null) return;
        RepositorioSolicitudes.agregar(s);
        Toast.makeText(this, "Solicitud #" + s.getNumero() + " registrada", Toast.LENGTH_SHORT).show();
        finish();
    }

    /** Comparte el formulario con otra app usando un intent ACTION_SEND. */
    private void compartir() {
        Solicitud s = leerFormulario(RepositorioSolicitudes.siguienteNumero());
        if (s == null) return;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Solicitud de envío de encomienda");
        intent.putExtra(Intent.EXTRA_TEXT, s.detalle());
        startActivity(Intent.createChooser(intent, "Compartir solicitud con..."));
    }
}
