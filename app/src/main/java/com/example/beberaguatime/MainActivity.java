package com.example.beberaguatime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //associar objetos da view a variaveis que podemos fazer as definicoes pelo codigo java
    private SharedPreferences preference;
    private Button btnNotify;
    private EditText editTime;
    private TimePicker timepicker;
    private boolean disabled;
    private int hours;
    private int minutes;
    private int interval;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNotify = findViewById(R.id.btn_notify);
        editTime = findViewById(R.id.edit_number_interval);
        timepicker = findViewById(R.id.time_picker);
        preference = getSharedPreferences("db", Context.MODE_PRIVATE);
        timepicker.setIs24HourView(true);
        disabled=preference.getBoolean("disabled",true);

        statusLayout();

        if(!disabled){
            minutes= preference.getInt("minutes", timepicker.getCurrentMinute());
            hours=preference.getInt("hours", timepicker.getCurrentHour());
            interval= preference.getInt("interval", 0);

            editTime.setText(String.valueOf(interval));
            timepicker.setCurrentHour(hours); ;
            timepicker.setCurrentMinute(minutes);
        }

    }

    public void OnClick(View view) {
        String sInterval = editTime.getText().toString();
        if (!sInterval.isEmpty()) {
            disabled=!disabled;
            minutes=timepicker.getCurrentMinute();
            hours=timepicker.getCurrentHour();
            interval=Integer.parseInt(sInterval);
            statusLayout();
            mSharedPreference();
            createLog();
        } else {
            Toast.makeText(this, "Digite o intervalo", Toast.LENGTH_SHORT).show();
        }
    }

    private void statusLayout() {
        if (!disabled) {
            btnNotify.setText(R.string.pause);
            int color = ContextCompat.getColor(this, R.color.vermelho);
            btnNotify.setBackgroundColor(color);
        } else {
            btnNotify.setText(R.string.notificar);
            int color = ContextCompat.getColor(this, R.color.verde);
            btnNotify.setBackgroundColor(color);
        }
    }


    private void mSharedPreference() {
        SharedPreferences.Editor editor = preference.edit();
        if (!disabled) {
            editor.putInt("minutes", minutes);
            editor.putInt("hours", hours);
            editor.putInt("interval", interval);
            editor.putBoolean("disabled",false);
        } else {
            editor.remove("minutes");
            editor.remove("hours");
            editor.remove("interval");
            editor.putBoolean("disabled",true);
        }
        editor.apply();
    }

    private void createLog() {
        Log.d("Teste", "Horas:" + hours + "  Minutos:" + minutes + "  Intervalo:" + interval);
    }

}