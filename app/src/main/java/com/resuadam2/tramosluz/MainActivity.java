package com.resuadam2.tramosluz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    EditText etTime;

    Button updateFromSystem, updateFromText;

    TextView tvTramo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTime = (EditText) findViewById(R.id.etTime);
        updateFromSystem = (Button) findViewById(R.id.updateFromSystem);
        updateFromText = (Button) findViewById(R.id.updateFromEditText);
        tvTramo = (TextView) findViewById(R.id.tvTramo);
        Calendar calendar = Calendar.getInstance();
        etTime.setText(new SimpleDateFormat("HH:mm").format(calendar.getTime()));
        tvTramo.setText(calcularTramo(calendar));
        tvTramo.setBackgroundColor(calcularColor(calendar));


        // TODO setear al edit text la hora actual y al tvTramo el color y el nombre correspondientes

        updateFromText.setOnClickListener(view -> updateFromText(etTime.getText().toString()));
        updateFromSystem.setOnClickListener(view -> updateFromSystem());
    }

    /**
     * Calcula el tramo en el que se encuentra la hora pasada por parámetro
     * @param instance
     * @return el tramo en el que se encuentra la hora pasada por parámetro
     */
    private String calcularTramo(Calendar instance) {
        int hora = instance.get(Calendar.HOUR_OF_DAY);
        if (hora >= TRAMO1 && hora < TRAMO2) {
            return "Tramo llano";
        } else if (hora >= TRAMO2 && hora < TRAMO3) {
            return "Tramo valle";
        } else {
            return "Tramo punta";
        }
    }

    /**
     * Calcula el color en el que se encuentra la hora pasada por parámetro
     * @param instance
     * @return el color en el que se encuentra la hora pasada por parámetro
     */
    private int calcularColor(Calendar instance) {
        int hora = instance.get(Calendar.HOUR_OF_DAY);
        if (hora >= TRAMO1 && hora < TRAMO2) {
            return COLOR_TRAMO1;
        } else if (hora >= TRAMO2 && hora < TRAMO3) {
            return COLOR_TRAMO2;
        } else {
            return COLOR_TRAMO3;
        }
    }



    private void updateFromSystem() {
    }

    private void updateFromText(String toString) {
    }
}