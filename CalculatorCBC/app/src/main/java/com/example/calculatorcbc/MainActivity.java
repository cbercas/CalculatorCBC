package com.example.calculatorcbc;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView visualizacionNumeros;  //Es el campo que muestra los numeros y los resultados en la calculadora
    String operadorActual = ""; //Es una cadena qe almacena el signo para el calculo que se vaya a realizar
    double primerNumero = 0; // Almacena el primer numero ingresado antes de seleccionar el signo
    boolean nuevaOperacion = true; //indica si estamos comenzando una nueva operacion, para reiniciar la pantalla


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

        // Inicializacion de los botones
        Button buttonBorrar = findViewById(R.id.buttonBorrar);
        Button buttonDivision = findViewById(R.id.buttonDivision);
        Button buttonMultiplicacion = findViewById(R.id.buttonMultiplicacion);
        Button buttonResta = findViewById(R.id.buttonResta);
        Button buttonSuma = findViewById(R.id.buttonSuma);
        Button buttonIgual = findViewById(R.id.buttonIgual);
        Button buttonComa = findViewById(R.id.buttonComa);
        visualizacionNumeros = findViewById(R.id.visualizacionNumeros);


        // Configuracion de los botones numericos
        int [] idsBotonesNumericos = {
                R.id.button00, R.id.button01, R.id.button02, R.id.button03,
                R.id.button04, R.id.button05, R.id.button06, R.id.button07,
                R.id.button08, R.id.button09
        };

        // Se recorren los botones numericos y para cada boton se establece un onClick
        //cada vez que se presiona un boton se agrega el valor al textView
        for (int i = 0; i < idsBotonesNumericos.length; i++) {
            final int numero = i;
            Button buttonNumero = findViewById(idsBotonesNumericos[i]);
            buttonNumero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nuevaOperacion) {
                        visualizacionNumeros.setText("");
                        nuevaOperacion = false;
                    }
                    visualizacionNumeros.append(String.valueOf(numero));
                }
            });
        }

        // Configurar de los botones de las operaciones
        // Cuando se presiona un operador  se llama al método realizarOperacion
        // que guarda el primer número introducido y el operador actual.
        buttonDivision.setOnClickListener(v -> realizarOperacion("/"));
        buttonMultiplicacion.setOnClickListener(v -> realizarOperacion("*"));
        buttonResta.setOnClickListener(v -> realizarOperacion("-"));
        buttonSuma.setOnClickListener(v -> realizarOperacion("+"));

        //Boton Borrar.
        //cuando se presiona el boton borrar, la pantalla se vuelve a 0
        buttonBorrar.setOnClickListener(v -> {
            visualizacionNumeros.setText("0");
            operadorActual = "";
            primerNumero = 0;
            nuevaOperacion = true;
        });

        //Boton igual
        //cuando se presiona el boton igual, se realiza el cálculo y se muestra el resultado en pantalla
        buttonIgual.setOnClickListener(v -> {
            if (!operadorActual.isEmpty()) {
                double segundoNumero = Double.parseDouble(visualizacionNumeros.getText().toString());
                double resultado = calcular(primerNumero, segundoNumero, operadorActual);
                if(!visualizacionNumeros.getText().toString().equals("Error")){
                    visualizacionNumeros.setText(String.valueOf(resultado));
                }
                operadorActual = "";
                nuevaOperacion = true;
            }
        });

        //Boton coma, si es una nueva operación, comenzará con 0.
        // Si ya hay números en pantalla añade un punto decimal
        buttonComa.setOnClickListener(v -> {
            if (nuevaOperacion) {
                visualizacionNumeros.setText("0.");
                nuevaOperacion = false;
            } else if (!visualizacionNumeros.getText().toString().contains(".")) {
                visualizacionNumeros.append(".");
            }
        });

    }

    //Metodo que guarda el primer numero y el signo, preparando a la calculadora para el segundo numero
    private void realizarOperacion(String operador) {
        primerNumero = Double.parseDouble(visualizacionNumeros.getText().toString());
        operadorActual = operador;
        nuevaOperacion = true;
    }

    //Metodo que realiza el calculo dependiendo de el signo que se haya pulsado y devuelve el resultado correspondiente
    private double calcular(double num1, double num2, String operador) {
        switch (operador) {
            case "+":
                return num1 + num2;

            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if(num2==0.0){
                    visualizacionNumeros.setText("Error");
                }else{
                    return num1 / num2;
                }
            default:
                return 0;
        }
    }


}