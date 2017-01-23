package fr.touriste.gendarme.pao.gendarme;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class LocalisationActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localisation);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.navHelp:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return true;
    }

    public void buttonLocation(View v) {

        /* Initialisation de la requête en fonction du bouton cliqué */
        String query = "geo:0,0?q=";

        /* On affecte la bonne chaine de caractère à la  requête */
        if(v.getId() == R.id.consulateLocationButton || v.getId() == R.id.consulateLocationLayout)
            query += getResources().getString(R.string.queryConsulate);
        else if(v.getId() == R.id.policeLocationButton || v.getId() == R.id.policeLocationLayout)
            query += getResources().getString(R.string.queryPolice);
        else if(v.getId() == R.id.hospitalLocationButton || v.getId() == R.id.hospitalLocationLayout)
            query += getResources().getString(R.string.queryHospital);

        Uri uri = Uri.parse(query);

        /* Initialisation de l'action pour la redirection sur Google Maps */
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

        try {
            /* On lance l'activité avec le nom de classe "MapsActivity de Google Maps */
            startActivity(intent);
        }
        catch(ActivityNotFoundException ex) {
            /* Si on ne trouve pas l'activité */
            try {
                /* On essaye de lancer sans contrainte */
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(unrestrictedIntent);
            }

            catch(ActivityNotFoundException innerEx) {/* Sinon, on affiche un message d'erreur */
                Toast.makeText(getApplicationContext(),R.string.mapsInstallText, Toast.LENGTH_LONG).show();
            }
        }
    }

}
