package com.example.gameofpig;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    View viewPlayer1, viewPlayer2;
    TextView player1, player2, player1Score, player2Score, contadorTirada, winner;
    Button newGame, lanzar, turno;
    ImageView dado;

    int Player1Score = 0, Player2Score = 0;
    int tiradaActual = 0;
    boolean turnoPlayer1 = true;
    Random random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        viewPlayer1 = findViewById(R.id.viewPlayer1);
        viewPlayer2 = findViewById(R.id.viewPlayer2);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player1Score = findViewById(R.id.player1Score);
        player2Score = findViewById(R.id.player2Score);
        contadorTirada = findViewById(R.id.contadorTirada);
        newGame = findViewById(R.id.newGame);
        lanzar = findViewById(R.id.lanzar);
        turno = findViewById(R.id.turno);
        dado = findViewById(R.id.dado);
        winner = findViewById(R.id.winner);

        random = new Random();

        // Empieza Player 1
        player1.setTextColor(getResources().getColor(R.color.activo));
        player1.setTypeface(null, Typeface.BOLD);
        turnoPlayer1 = true;
        Toast.makeText(this, "Comienza PLAYER 1", Toast.LENGTH_SHORT).show();


        // Botón NEW!
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        // Botón LANZAR
        lanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarDado();
            }
        });

        // Botón TURNO
        turno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiaTurno();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Metodo para lanzar el dado
    void lanzarDado(){

        int numero = random.nextInt(6) + 1;

        // Mostrar animación del dado (gira 360 grados)
        dado.animate().rotationBy(360).setDuration(300).withEndAction(new Runnable() {
            @Override
            public void run() {
                // Actualizar la imagen del dado una vez que termina la animación
                updateImagenDado(numero);
            }
        });

        if (numero == 1) {
            Toast.makeText(this, "Pierdes tu turno", Toast.LENGTH_SHORT).show();
            tiradaActual = 0;
            cambiaTurno();
        } else {
            tiradaActual += numero;
            contadorTirada.setText(String.valueOf(tiradaActual));
        }
    }

    // Método para cambiar de turno
    void cambiaTurno(){
        if (turnoPlayer1) {
            // Actualiza estilo para jugador 1
            player1.setTextColor(getResources().getColor(R.color.inactivo));
            player1.setTypeface(null, Typeface.NORMAL);
            player2.setTextColor(getResources().getColor(R.color.activo));
            player2.setTypeface(null, Typeface.BOLD);
            viewPlayer1.setBackgroundColor(getResources().getColor(R.color.secundario));
            viewPlayer2.setBackgroundColor(getResources().getColor(R.color.primario));

            // Suma puntos a jugador 1 y cambia turno
            goodTurn(tiradaActual); //comprobar si es buena tirada
            Player1Score += tiradaActual;
            player1Score.setText(String.valueOf(Player1Score));
            tiradaActual = 0;
            turnoPlayer1 = false;

        } else {
            // Actualiza estilo para jugador 2
            player2.setTextColor(getResources().getColor(R.color.inactivo));
            player2.setTypeface(null, Typeface.NORMAL);
            player1.setTextColor(getResources().getColor(R.color.activo));
            player1.setTypeface(null, Typeface.BOLD);
            viewPlayer2.setBackgroundColor(getResources().getColor(R.color.secundario));
            viewPlayer1.setBackgroundColor(getResources().getColor(R.color.primario));

            // Suma puntos a jugador 2 y cambia turno
            goodTurn(tiradaActual);  //comprobar si es buena tirada
            Player2Score += tiradaActual;
            player2Score.setText(String.valueOf(Player2Score));
            tiradaActual = 0;
            turnoPlayer1 = true;
        }
        contadorTirada.setText(String.valueOf(tiradaActual));

        // Comprobar si alguno de los jugadores ha ganado
        if (Player1Score >= 100) {
            Toast.makeText(this, "Ganador Player 1!", Toast.LENGTH_SHORT).show();
            winner.setText("Ganador PLAYER 1!");
            resetGame();
        } else if (Player2Score >= 100) {
            Toast.makeText(this, "Ganador Player 2!", Toast.LENGTH_SHORT).show();
            winner.setText("Ganador PLAYER 2!");
            resetGame();
        }
    }

    // Método para reiniciar el juego
    void resetGame() {
        Player1Score = 0;
        Player2Score = 0;
        tiradaActual = 0;

        player1Score.setText(String.valueOf(Player1Score));
        player2Score.setText(String.valueOf(Player2Score));
        contadorTirada.setText(String.valueOf(tiradaActual));
        winner.setText("");
    }

    // Método para actualizar la imagen del dado
    void updateImagenDado(int numero) {
        int numTirada;
        switch (numero) {
            case 1:
                numTirada = R.drawable.dice_one;
                break;
            case 2:
                numTirada = R.drawable.dice_two;
                break;
            case 3:
                numTirada = R.drawable.dice_three;
                break;
            case 4:
                numTirada = R.drawable.dice_four;
                break;
            case 5:
                numTirada = R.drawable.dice_five;
                break;
            case 6:
                numTirada = R.drawable.dice_six;
                break;
            default:
                numTirada = R.drawable.dice_random;
                break;
        }
        dado.setImageResource(numTirada);
    }

    // Método para considerar una excelente tirada (+20 puntos)
    void goodTurn(int tiradaActual){
        if (tiradaActual >= 20){
            Toast.makeText(this, "Buena tirada!", Toast.LENGTH_SHORT).show();
        }
    }
}