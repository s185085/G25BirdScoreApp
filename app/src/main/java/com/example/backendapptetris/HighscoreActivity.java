package com.example.backendapptetris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HighscoreActivity extends AppCompatActivity {

    private static final String TAG = "TetrisActivity";
    private static final String KEY_NAVN = "navn";
    private static final String KEY_SCORE = "score";

    private Button playAgain, saveScore;
    private TextView gameOver, playerScore;
    private String studienr, userFirstName;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        playAgain = findViewById(R.id.playAgainButton);
        saveScore = findViewById(R.id.saveScoreButton);
        gameOver = findViewById(R.id.gameOverText);
        playerScore = findViewById(R.id.playerScoreText);

        // Henter spiller data fra intents, skal bruge studienr og navn til databasen
        Intent intent = getIntent();
        studienr = intent.getStringExtra("studienr");
        userFirstName = intent.getStringExtra("navn");


    }

    public void saveScore(View v) {
        Map<String, Object> score = new HashMap<>();
        score.put(KEY_NAVN, userFirstName);
        score.put(KEY_SCORE, "1234");
        db.collection("Highscores").document(studienr).set(score)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(HighscoreActivity.this, "Score gemt.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HighscoreActivity.this, "Fejl", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }
    public void playAgain(View v){
        Intent intent = new Intent(this, TetrisActivity.class);
        intent.putExtra("studienr", studienr);
        intent.putExtra("navn", userFirstName);
        startActivity(intent);
        finish();
    }
}
