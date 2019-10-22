package com.example.calculadorasubneteo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText A, B, C, D;
    private TextView lblMask, lblNetAdd, lblMaxHosts, lblWildCard, lblPrefijo;
    private Button btnAtras, btnAdelante;

    int prefijo;

    int[] int_enteros = {128, 64, 32, 16, 8, 4, 2, 1};

    String[] subnetMask = {
            "128.0.0.0", "192.0.0.0", "224.0.0.0", "240.0.0.0", "248.0.0.0", "252.0.0.0", "254.0.0.0", "255.0.0.0",
            "255.128.0.0", "255.192.0.0", "255.224.0.0", "255.240.0.0", "255.248.0.0", "255.252.0.0", "255.254.0.0", "255.255.0.0",
            "255.255.128.0", "255.255.192.0", "255.255.224.0", "255.255.240.0", "255.255.248.0", "255.255.252.0", "255.255.254.0", "255.255.255.0",
            "255.255.255.128", "255.255.255.192", "255.255.255.224", "255.255.255.240", "255.255.255.248", "255.255.255.252"};

    String[] wildCard = {
            "127.255.255.255", "63.255.255.255", "31.255.255.255", "15.255.255.255", "7.255.255.255", "3.255.255.255", "1.255.255.255", "0.255.255.255",
            "0.127.255.255", "0.63.255.255", "0.31.255.255", "0.15.255.255", "0.7.255.255", "0.3.255.255", "0.1.255.255", "0.0.255.255",
            "0.0.127.255", "0.0.63.255", "0.0.31.255", "0.0.15.255", "0.0.7.255", "0.0.3.255", "0.0.1.255", "0.0.0.255",
            "0.0.0.127", "0.0.0.63", "0.0.0.31", "0.0.0.15", "0.0.0.7", "0.0.0.3"};

    String[] hostsNumber = {
            "2147483646", "1073741822", "536870910", "268435454", "134217726", "67108862", "33554430", "16777214", "8388606", "4194302",
            "2097150", "1048574", "524286", "262142", "131070", "65534", "32766", "16382", "8190",
            "4094", "2046", "1022", "510", "254", "126", "62", "30", "14", "6", "2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        A = (EditText) findViewById(R.id.A);
        B = (EditText) findViewById(R.id.B);
        C = (EditText) findViewById(R.id.C);
        D = (EditText) findViewById(R.id.D);
        lblMask = (TextView) findViewById(R.id.lblMask);
        lblNetAdd = (TextView) findViewById(R.id.lblNetAddre);
        lblMaxHosts = (TextView) findViewById(R.id.lblHosts);
        lblWildCard = (TextView) findViewById(R.id.lblWildCard);
        lblPrefijo = (TextView) findViewById(R.id.lblPrefijo);
        btnAdelante = (Button) findViewById(R.id.btnAdelante);
        btnAtras = (Button) findViewById(R.id.btnAtras);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prefijo != 1) {
                    prefijo--;
                    lblPrefijo.setText("/" + prefijo);
                    refrescar();
                }
            }
        });

        btnAdelante.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               if (prefijo < 30) {
                                                   prefijo++;
                                                   lblPrefijo.setText("/" + prefijo);


                                                   int str_A = Integer.parseInt(A.getText().toString());
                                                   int str_B = Integer.parseInt(B.getText().toString());
                                                   int str_C = Integer.parseInt(C.getText().toString());
                                                   int str_D = Integer.parseInt(D.getText().toString());

                                                   String octetoA = Integer.toBinaryString(str_A);
                                                   String octetoB = Integer.toBinaryString(str_B);
                                                   String octetoC = Integer.toBinaryString(str_C);
                                                   String octetoD = Integer.toBinaryString(str_D);

                                                   octetoA = AgregarCero(octetoA);
                                                   octetoB = AgregarCero(octetoB);
                                                   octetoC = AgregarCero(octetoC);
                                                   octetoD = AgregarCero(octetoD);

                                                   // int prefijoUltimoOcteto = 0;

                                                   if (prefijo > 24) {
                                                       lblNetAdd.setText(Binario(octetoA, 8)
                                                               + "." + Binario(octetoB, 8)
                                                               + "." + Binario(octetoC, 8)
                                                               + "." + Binario(octetoD, validarUltimoOcteto(octetoD, 24)));
                                                   } else if (prefijo > 16) {
                                                       lblNetAdd.setText(Binario(octetoA, 8)
                                                               + "." + Binario(octetoB, 8)
                                                               + "." + Binario(octetoC, validarUltimoOcteto(octetoC, 16))
                                                               + ".0");
                                                   } else if (prefijo > 8) {
                                                       lblNetAdd.setText(Binario(octetoA, 8)
                                                               + "." + Binario(octetoB, validarUltimoOcteto(octetoB, 8))
                                                               + ".0.0");
                                                   } else {
                                                       lblNetAdd.setText(Binario(octetoA, 8)
                                                               + ".0.0.0");
                                                   }

                                                   refrescar();
                                               }
                                           }
                                       }
        );

        prefijo = 1;

        refrescar();
    }

    public String AgregarCero(String octeto) {
        if (octeto.length() < 8) {
            int numero = 8 - octeto.length();
            String numeros = "";

            for (int i = 0; i < numero; i++) {
                numeros += "0";
            }

            octeto = numeros + octeto;
        }
        return octeto;
    }



    public int Binario(String octeto, int prefijoOcteto) {
        int suma = 0;
        char[] fromString = octeto.toCharArray();
        for (int i = 0; i < prefijoOcteto; i++) {
            if (fromString[i] == '1') {
                suma = suma + int_enteros[i];
            }
        }
        return suma;
    }

    public int validarUltimoOcteto(String ultimoOcteto, int octeto) {
        int prefijoUltimoOcteto = prefijo;

        prefijoUltimoOcteto = prefijo - octeto;
        return prefijoUltimoOcteto;
    }

    public void refrescar() {
        lblMask.setText(subnetMask[prefijo - 1]);
        lblMaxHosts.setText(hostsNumber[prefijo - 1]);
        lblWildCard.setText(wildCard[prefijo - 1]);
    }

    //Construyendo el boton
}


