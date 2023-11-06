package com.resuadam2.tramosluz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    protected static final int TRAMO1 = 0; // 0 a 7
    protected static final int COLOR_TRAMO1 = Color.GREEN; // 0 a 7

    protected static final int TRAMO2 = 8; // 8 a 18
    protected static final int COLOR_TRAMO2 = Color.YELLOW; // 8 a 18

    protected static final int TRAMO3 = 19; // 19 a 23
    protected static final int COLOR_TRAMO3 = Color.RED; // 19 a 23

    TextView tvTime;

    Button updateFromSystem, updateFromText;

    TextView tvTramo, tvTramoNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = (TextView) findViewById(R.id.tvTime);
        updateFromSystem = (Button) findViewById(R.id.updateFromSystem);
        updateFromText = (Button) findViewById(R.id.updateFromEditText);
        tvTramo = (TextView) findViewById(R.id.tvTramo);
        tvTramoNext = (TextView) findViewById(R.id.tvTramoNext);

        updateFromSystem();


        // TODO setear al edit text la hora actual y al tvTramo el color y el nombre correspondientes

        updateFromText.setOnClickListener(view -> updateFromText(tvTime.getText().toString()));
        updateFromSystem.setOnClickListener(view -> updateFromSystem());
    }

    /**
     * Calcula el tramo en el que se encuentra la hora pasada por parámetro
     * actualiza el TextView del tramo, su texto y el color de fondo
     * @param instance
     */
    private void calcularTramo(Calendar instance) {
        int hora = instance.get(Calendar.HOUR_OF_DAY);
        if (hora >= TRAMO1 && hora < TRAMO2) {
            tvTramo.setText("Tramo llano");
            tvTramo.setBackgroundColor(Color.GREEN);
            tvTramoNext.setText("Tramo valle");
            tvTramoNext.setBackgroundColor(Color.YELLOW);
        } else if (hora >= TRAMO2 && hora < TRAMO3) {
            tvTramo.setText("Tramo valle");
            tvTramo.setBackgroundColor(Color.YELLOW);
            tvTramoNext.setText("Tramo pico");
            tvTramoNext.setBackgroundColor(Color.RED);
        } else {
            tvTramo.setText("Tramo pico");
            tvTramo.setBackgroundColor(Color.RED);
            tvTramoNext.setText("Tramo llano");
            tvTramoNext.setBackgroundColor(Color.GREEN);
        }
    }



    private void updateFromSystem() {
        Calendar calendar = Calendar.getInstance();
        Toast.makeText(this, "Hora actual " +
                new SimpleDateFormat("HH:mm").format(calendar.getTime()), Toast.LENGTH_SHORT).show();
        tvTime.setText(new SimpleDateFormat("HH:mm").format(calendar.getTime()));
        calcularTramo(calendar);
    }

    private void updateFromText(String toString) {
    }
}