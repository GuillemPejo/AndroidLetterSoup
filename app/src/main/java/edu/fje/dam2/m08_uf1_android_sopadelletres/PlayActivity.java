package edu.fje.dam2.m08_uf1_android_sopadelletres;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.text.InputType;
import android.util.Log;
import java.time.Instant;
import java.time.Duration;
import java.util.ArrayList;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.net.Uri;



public class PlayActivity extends AppCompatActivity {

    // Input player name inserted on an AlertDialog
    public String username;
    public int score;
    public Instant startGame;
    public Instant finishGame;
    public long gameDurationSeconds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        //Asking for username to the user
        // Input username on start a game
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Nom del jugador");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialog.setView(input);

        // Set up the buttons
        alertDialog.setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                username = input.getText().toString();
                gameSopaDeLletres();
            }
        });

        alertDialog.setNegativeButton("Cancel·lar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    public void gameSopaDeLletres(){
        // Get time when starts the game
        startGame = Instant.now();

        try{
            // Getting 8 contacts' name into an ArrayList<String> (max length of the name => 7)
            ArrayList<String> contacts = new ArrayList<String>();
            int numContacts = 0;
            Cursor cursorContacts = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
            while (cursorContacts.moveToNext()){
                String name = cursorContacts.getString(cursorContacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                if(numContacts != 8){
                    if(name.length() <= 7){
                        String[] nameSplit = name.split(" ");
                        contacts.add(nameSplit[0].toUpperCase());
                        numContacts++;
                    }
                }
            }

            cursorContacts.close();

        } catch (SecurityException e){
            // AlertDialog asking for access permissions to contacts
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage(R.string.permissionsContactsDialog);

            // Set up the buttons
            alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    finish();
                }
            });

            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            alertDialog.setCancelable(false);
            alertDialog.show();
        }

        /* Game code here */

        // Create a matrix
        //int matrix[][] = new int[10][10];


        // Fill it with numbers
        /*for(int rows = 0; rows < matrix.length; rows++){
            for(int columns = 0; columns < matrix[rows].length; columns++){
                matrix[rows][columns] = 1 + (int) (Math.random() * 100);
            }
        }

        ArrayList<String> ar = new ArrayList<String>();
        for(int i = 0; i < 100; i++){
            ar.add("A");
        }

        GridView gridViewGame = findViewById(R.id.gridViewGame);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, ar);

        gridViewGame.setAdapter(adapter);*/




        // When all words are found
        // Get time when finishes the game
        finishGame = Instant.now();

        // Calculating the duration of the game in seconds
        gameDurationSeconds = Duration.between(startGame, finishGame).getSeconds();
        Log.i("PlayActivity", "Duration: " + gameDurationSeconds);

        // Call function goToAfterGameScoreActivity() to show the final score and go to AfterGameScoreActivity
        //goToAfterGameScoreActivity();

    }

    public void goToAfterGameScoreActivity(){
        // Passing username, score and game duration to AfterGameScoreActivity
        Intent intent = new Intent(PlayActivity.this, AfterGameScoreActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("score", score);
        intent.putExtra("gameDuration", gameDurationSeconds);
        startActivity(intent);
        finish();
    }


}