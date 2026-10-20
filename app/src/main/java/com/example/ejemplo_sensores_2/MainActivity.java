package com.example.ejemplo_sensores_2;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView txt_estado, txt_acelerometro, txt_aceleracion;
    Button boton_iniciar ;

    //Sensores
    SensorManager manager;
    Sensor acelerometro ;

    //Hilos
    boolean monitoreando = false;
    Handler mainHandler ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //cargar los elementos del xml
        txt_estado = findViewById(R.id.txt_estado);
        txt_acelerometro = findViewById(R.id.txt_acelerometro);
        txt_aceleracion = findViewById(R.id.txt_aceleracion);
        boton_iniciar = findViewById(R.id.boton_iniciar);

        //inicializar hilos
        mainHandler = new Handler(Looper.getMainLooper()); //el hilo principal que tiene toda la parte grafica

        //inicializar sensores
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (manager != null)
        {
            //si existe manager, obtener los sensores que necesitemos
            acelerometro = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        //cuando se haga click en el boton comenzar a tomar medidas del sensor
        boton_iniciar.setOnClickListener(v -> {
            if (monitoreando == true)
            {
                detenerSensor();
            }
            else
            {
                iniciarSensor();
            }
        });
    }

    public void iniciarSensor()
    {
        if (acelerometro != null)
        {
            //registrar el sensor
            manager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_UI);
            monitoreando = true;
            txt_estado.setText("estado : activo");
            boton_iniciar.setText("Detener Sensor");
        }
        else
        {
            txt_acelerometro.setText("No existe sensor....");
        }
    }

    public void detenerSensor()
    {
        manager.unregisterListener(this);
        monitoreando = false ;
        txt_estado.setText("estado : detenido");
        boton_iniciar.setText("Iniciar Sensor");
    }


}