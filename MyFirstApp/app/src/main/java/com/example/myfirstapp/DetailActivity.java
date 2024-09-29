package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;




public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Cargar los componentes de la vista
        Button btnBack = findViewById(R.id.btnBack);
        TextView tvHello = findViewById(R.id.tvHello);

        btnBack.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(DetailActivity.this, MainActivity.class);

                startActivity(intentBack);
            }
        });


        // Necesito recuperar el Intent que ha iniciado esta activity
        Intent intentMainActivity = getIntent();

        String name = intentMainActivity.getStringExtra("name");
        String surname = intentMainActivity.getStringExtra("surname");

        tvHello.setText("Hola " + name + " " + surname);

    }
}