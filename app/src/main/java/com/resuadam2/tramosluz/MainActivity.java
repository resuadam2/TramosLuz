package com.resuadam2.tramosluz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO hacer las vistas atributos de la clase para poder acceder a ellas desde los demás métodos
        EditText etTime = (EditText) findViewById(R.id.etTime);
        Button updateFromSystem = (Button) findViewById(R.id.updateFromSystem);
        Button updateFromText = (Button) findViewById(R.id.updateFromEditText);
        TextView tvTramo = (TextView) findViewById(R.id.tvTramo);

        // TODO setear al edit text la hora actual y al tvTramo el color y el nombre correspondientes

        updateFromText.setOnClickListener(view -> updateFromText(etTime.getText().toString()));
        updateFromSystem.setOnClickListener(view -> updateFromSystem());
    }

    private void updateFromSystem() {
    }

    private void updateFromText(String toString) {
    }
}