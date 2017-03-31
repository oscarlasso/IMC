package com.example.ingenieria.imc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {


    private EditText pes;
    private EditText est;
    private EditText res;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        est = (EditText) findViewById(R.id.estatura);
        res = (EditText) findViewById(R.id.resultado);
        pes = (EditText) findViewById(R.id.peso);
        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        pes.setText(prefe.getString("peso",""));
        est.setText(prefe.getString("estatura",""));
        res.setText(prefe.getString("resultado",""));

        String[] archivos = fileList();
        if (existe(archivos, "notas.txt"))
            try {
                InputStreamReader archivo = new InputStreamReader(openFileInput("notas.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String todo = "";
                while (linea != null) {
                    todo = todo + linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                archivo.close();
                res.setText(todo);
            } catch (IOException e) {
            }
        if (existe(archivos, "notas.txt"))
            try {
                InputStreamReader archivo = new InputStreamReader(
                        openFileInput("notas.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String todo = "";
                while (linea != null) {
                    todo = todo + linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                archivo.close();
                res.setText(todo);
            } catch (IOException e) {
            }
    }
    private boolean existe(String[] archivos, String archbusca) {
        for (int f = 0; f < archivos.length; f++)
            if (archbusca.equals(archivos[f]))
                return true;
        return false;
    }
    public void grabar(View v) {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("notas.txt", Activity.MODE_PRIVATE));
            archivo.write(res.getText().toString());
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
        }
        Toast t = Toast.makeText(this, "Los datos fueron grabados",Toast.LENGTH_SHORT);
        t.show();
    }

    public void calcular(View v) {

        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("peso", pes.getText().toString());
        editor.putString("estatura", est.getText().toString());
        editor.putString("resultado", res.getText().toString());
        editor.commit();


        if (pes.getText().toString().trim().length() == 0) {
            pes.requestFocus();

            res.setText("No te olvides de rellenar el peso");
        } else if (est.getText().toString().trim().length() == 0) {
            est.requestFocus();

            res.setText("No te olvides de rellenar la estatura");
        } else {

            float kg = Float.parseFloat(pes.getText().toString());
            float m = Float.parseFloat(est.getText().toString());
            float bmi = (kg / (m * m));

            if (bmi < 16.0) {
                DecimalFormat df = new DecimalFormat("####.####");
                res.setText(getString(Integer.parseInt(getString(R.string.tuimc))) + df.format(bmi) + " " + getString(R.string.delgadezsevera));
            } else if (bmi >= 40.0) {
                DecimalFormat df = new DecimalFormat("####.####");
                res.setText(getString(R.string.tres) + df.format(bmi) + " " + getString(R.string.obesidadmorbida));
            } else if (bmi >= 16.0 && bmi <= 16.99) {
                DecimalFormat df = new DecimalFormat("####.####");
                res.setText(getString(R.string.cuatro) + df.format(bmi) + " " + getString(R.string.delgadezmoderada));
            } else if (bmi >= 17.0 && bmi <= 18.49) {
                DecimalFormat df = new DecimalFormat("####.####");
                res.setText(getString(R.string.cinco) + df.format(bmi) + " " + getString(R.string.delgadezleve));
            } else if (bmi >= 18.5 && bmi <= 24.99) {
                DecimalFormat df = new DecimalFormat("####.####");
                res.setText(getString(R.string.dos) + df.format(bmi) + " " + getString(R.string.pesonormal));
            } else {
                if (bmi >= 25.0 && bmi <= 29.99) {
                    DecimalFormat df = new DecimalFormat("####.####");
                    res.setText(getString(R.string.seis) + df.format(bmi) + " " + getString(R.string.tienespreobesidad));
                } else {
                    if (bmi >= 30.0 && bmi <= 34.99) {
                        DecimalFormat df = new DecimalFormat("####.####");
                        res.setText(getString(R.string.siete) + df.format(bmi) + " " + getString(R.string.obesidadleve));
                    } else {
                        if (bmi >= 35.0 && bmi <= 39.99) {
                            DecimalFormat df = new DecimalFormat("####.####");
                            res.setText(getString(R.string.ocho) + df.format(bmi) + " " + getString(R.string.obesidadmedia));
                        }
                    }

                }

            }


        }

    }


    public void acercade(View view) {
        Intent i = new Intent(this, AcercaDe.class);
        startActivity(i);
    }

    public void historial(View view) {
        Intent i = new Intent(this,Historico.class);
        grabar(view);
        startActivity(i);

    }




}
