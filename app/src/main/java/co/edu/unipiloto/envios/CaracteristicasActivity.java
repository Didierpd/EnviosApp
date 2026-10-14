package co.edu.unipiloto.envios;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/** Muestra las características del servicio de envío. */
public class CaracteristicasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caracteristicas);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView txt = findViewById(R.id.txt_caracteristicas);
        txt.setText(
                "Tipos de paquete\n"
              + "• Documento: hasta 0,5 kg\n"
              + "• Paquete pequeño: hasta 5 kg\n"
              + "• Paquete mediano: hasta 15 kg\n"
              + "• Paquete grande: hasta 30 kg\n"
              + "• Frágil: manejo especial, cualquier peso\n\n"
              + "Tiempos de entrega\n"
              + "• Misma ciudad: 1 día hábil\n"
              + "• Nacional: 2 a 4 días hábiles\n\n"
              + "Condiciones\n"
              + "• El remitente debe indicar el contenido del paquete.\n"
              + "• No se transportan líquidos inflamables ni objetos prohibidos.\n"
              + "• Cada solicitud recibe un número para su seguimiento.");
    }
}
