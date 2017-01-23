package fr.touriste.gendarme.pao.gendarme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*  On récupère le bouton gràce a l'identifiant de celui ci et on lui ajoute une action
            si l'on clique dessus. */
        Button emergencyButton = (Button) findViewById(R.id.emerButton);
        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri tel = Uri.parse("tel:112");
                Intent emergencyCall = new Intent(Intent.ACTION_DIAL, tel);
                startActivity(emergencyCall);
            }
        });
        Button samuButton = (Button) findViewById(R.id.samuButton);
        samuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri tel = Uri.parse("tel:15");
                Intent emergencyCall = new Intent(Intent.ACTION_DIAL, tel);
                startActivity(emergencyCall);
            }
        });

        Button firemanButton = (Button) findViewById(R.id.firemanButton);
        firemanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri tel = Uri.parse("tel:18");
                Intent emergencyCall = new Intent(Intent.ACTION_DIAL, tel);
                startActivity(emergencyCall);
            }
        });
        Button policeButton = (Button) findViewById(R.id.policeButton);
        policeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri tel = Uri.parse("tel:17");
                Intent emergencyCall = new Intent(Intent.ACTION_DIAL, tel);
                startActivity(emergencyCall);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.navHelp:
                startActivity(new Intent(this, HelpActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buttonCall(View v) {

        /* On instancie une chaine ou l'on va stocker le numéro à appeler. */
        String num = "";

        /* On récupère le bon numéro de téléphone à appeler */
        if(v.getId() == R.id.emerButton || v.getId() == R.id.emerLayout)
            num = "112";
        else if(v.getId() == R.id.samuButton || v.getId() == R.id.samuLayout)
            num = "15";
        else if(v.getId() == R.id.firemanButton || v.getId() == R.id.firemanLayout)
            num = "18";
        else if(v.getId() == R.id.policeButton || v.getId() == R.id.policeLayout)
            num = "17";

        /* On lance l'activité "ACTION_CALL" qui va lancer le téléphone avec le numéro */
        Uri tel = Uri.parse("tel:"+num);
        Intent emergencyCall = new Intent(Intent.ACTION_DIAL, tel);
        startActivity(emergencyCall);
    }
}
