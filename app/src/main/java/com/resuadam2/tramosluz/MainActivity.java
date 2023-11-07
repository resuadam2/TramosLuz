package com.resuadam2.tramosluz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    protected static final int TRAMO1 = 24; // 0 a 7
    protected static final int COLOR_TRAMO1 = Color.GREEN; // 0 a 7

    protected static final int TRAMO2 = 8; // 8 a 18
    protected static final int COLOR_TRAMO2 = Color.YELLOW; // 8 a 18

    protected static final int TRAMO3 = 19; // 19 a 23
    protected static final int COLOR_TRAMO3 = Color.RED; // 19 a 23

    TextView tvTime; // TextView de la hora

    Button updateFromSystem, updateFromText; // Botones para actualizar la hora

    TextView tvTramo, tvTramoNext, tvSiguienteTramo, tvCuentaAtras; // Tramo actual, tramo siguiente y cuenta atrás

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = (TextView) findViewById(R.id.tvTime);
        updateFromSystem = (Button) findViewById(R.id.updateFromSystem);
        updateFromText = (Button) findViewById(R.id.updateFromEditText);
        tvTramo = (TextView) findViewById(R.id.tvTramo);
        tvSiguienteTramo = (TextView) findViewById(R.id.tvSiguienteTramo);
        tvTramoNext = (TextView) findViewById(R.id.tvTramoNext);
        tvCuentaAtras = (TextView) findViewById(R.id.tvCuentaAtras);

        updateFromSystem(); // Actualiza la hora con la hora del sistema


        updateFromText.setOnClickListener(view -> updateFromPicker());
        updateFromSystem.setOnClickListener(view -> updateFromSystem());
    }


    /**
     * Calcula el tramo en el que se encuentra la hora pasada por parámetro
     * actualiza el TextView del tramo, su texto y el color de fondo
     * @param instance Hora actual
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
        tvSiguienteTramo.setText(this.getString(R.string.tramoSiguiente) + " " + calcularCuentaAtras(instance));
        startCountDown(instance);
    }


    /**
     * Actualiza el TextView de la hora con la hora actual del sistema
     * y calcula el tramo en el que se encuentra
     */
    private void updateFromSystem() {
        Calendar calendar = Calendar.getInstance();
        Toast.makeText(this, "Hora actual " +
                new SimpleDateFormat("HH:mm").format(calendar.getTime()), Toast.LENGTH_SHORT).show();
        tvTime.setText(new SimpleDateFormat("HH:mm").format(calendar.getTime()));
        calcularTramo(calendar);
    }

    /**
     * Actualiza el TextView de la hora con la hora seleccionada en el TimePicker
     */
    private void updateFromPicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            tvTime.setText(new SimpleDateFormat("HH:mm").format(calendar.getTime()));
            calcularTramo(calendar);
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    /**
     * Método que calcula cuánto tiempo falta para el siguiente tramo
     * @param instance Hora actual
     * @return long con el tiempo restante para el siguiente tramo
     */
    private long diferenciaTramos(Calendar instance) {
        int hora = instance.get(Calendar.HOUR_OF_DAY);

        int horaSiguiente = 0;

        if (hora >= TRAMO1 && hora < TRAMO2) {
            horaSiguiente = TRAMO2;
        } else if (hora >= TRAMO2 && hora < TRAMO3) {
            horaSiguiente = TRAMO3;
        } else {
            horaSiguiente = TRAMO1;
        }

        int minutoSiguiente = 0;
        int segundoSiguiente = 0;
        int milisegundoSiguiente = 0;

        Calendar calendar = Calendar.getInstance();
        // TODO Aqui hay un posible problema, si ponemos el -1 hace bien la diferencia pero
        //  si no lo ponemos no inicia la cuenta atras
        calendar.set(Calendar.HOUR_OF_DAY, horaSiguiente - 1);
        calendar.set(Calendar.MINUTE, minutoSiguiente);
        calendar.set(Calendar.SECOND, segundoSiguiente);
        calendar.set(Calendar.MILLISECOND, milisegundoSiguiente);

        long diferencia = calendar.getTimeInMillis() - instance.getTimeInMillis();
        return diferencia;
    }

    /**
     * Método que calcula cuánto tiempo falta para el siguiente tramo y lo devuelve en formato String
     * @param instance Hora actual
     * @return String con el tiempo restante para el siguiente tramo
     */
    private String calcularCuentaAtras(Calendar instance) {
        Date date = new Date(diferenciaTramos(instance));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    CountDown countDown;
    /**
     * Método que inicia la cuenta atrás y actualiza el TextView de la cuenta atrás
     * la cuenta atrás se actualiza cada segundo
     * @param instance Hora actual
     */
    private void startCountDown(Calendar instance) {
        if(countDown != null) stopCountDown();
        countDown = new CountDown(diferenciaTramos(instance), 1000);
        countDown.start();
    }

    /**
     * Método que para la cuenta atrás y actualiza el TextView de la cuenta atrás
     */
    private void stopCountDown() {
        countDown.cancel();
        tvCuentaAtras.setText("00:00:00");
    }

    /**
     * Clase que extiende de CountDownTimer y se encarga de la cuenta atrás
     */
    public class CountDown  extends CountDownTimer {
        /**
         * Constructor de la clase
         * @param millisInFuture Tiempo total de la cuenta atrás
         * @param countDownInterval Intervalo de la cuenta atrás
         */
        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        /**
         * Método que se ejecuta cada segundo y actualiza el editText de la cuenta atrás
         * @param millisUntilFinished Tiempo restante de la cuenta atrás
         */
        @Override
        public void onTick(long millisUntilFinished) {
            Date date = new Date(millisUntilFinished);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            tvCuentaAtras.setText(simpleDateFormat.format(date));
        }

        /**
         * Método que se ejecuta cuando la cuenta atrás termina
         * Lanza un Toast y para la cuenta atrás
         */
        @Override
        public void onFinish() {
            Toast.makeText(MainActivity.this, "Cuenta atrás terminada", Toast.LENGTH_SHORT).show();
            stopCountDown();
        }
    }
}