package fr.touriste.gendarme.pao.gendarme;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.Locale;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button emergencyButton = null;
    private Button adviceButton = null;
    private Button contactButton = null;
    private Button localisationButton = null;
    private Button profileButton = null;
    private Locale myLocale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Permet de sélectionner la langue */
        setLocale();

        setContentView(R.layout.activity_main);


        /* Toolbar au top de l'appli avec bouton pour ouvrir le menu + titre */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Permet de placer le bouton dans la toolbar qui permet d'ouvrir le menu */
        DrawerLayout menuDrawer = (DrawerLayout) findViewById(R.id.menuDrawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, menuDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        menuDrawer.setDrawerListener(toggle);
        toggle.syncState();

        /* Listener pour les items dans le menu drawer */
        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        /* Gestion des différents boutons de l'accueil */
        emergencyButton = (Button) findViewById(R.id.emergencyButton);
        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri tel = Uri.parse("tel:112");
                Intent emergencyCall = new Intent(Intent.ACTION_DIAL, tel);
                startActivity(emergencyCall);
            }
        });

        adviceButton = (Button) findViewById(R.id.adviceButton);
        adviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AdviceActivity.class);
                startActivity(intent);
            }
        });

        contactButton = (Button) findViewById(R.id.contactButton);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        localisationButton = (Button) findViewById(R.id.localisationButton);
        localisationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocalisationActivity.class);
                startActivity(intent);
            }
        });

        profileButton = (Button) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Gestion de la localisation
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        // Si le GPS n'est pas activé
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);

            // Setting Dialog Title
            alertBuilder.setTitle(getResources().getString(R.string.askLocationTitle));

            // Setting Dialog Message
            alertBuilder.setMessage(getResources().getString(R.string.askLocationText));

            // Setting Icon to Dialog
            alertBuilder.setIcon(R.drawable.error);

            // Setting OK Button
            alertBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });

            // Showing Alert Message
            AlertDialog alert = alertBuilder.create();
            alert.show();
        }

    }


    /**
     * Fonction qui est appelé lorsque l'on touche le bouton retour du téléphone
     * */
    @Override
    public void onBackPressed() {
        DrawerLayout menuDrawer = (DrawerLayout) findViewById(R.id.menuDrawerLayout);
        //Si le menu drawer est ouvert, alors on le ferme
        if (menuDrawer.isDrawerOpen(GravityCompat.START)) {
            menuDrawer.closeDrawer(GravityCompat.START);
        } else {
            //Sinon le bouton reprend sa fonction d'origine (depuis l'accueil ça quitte l'application).
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }
    /**
     * Fonction qui est appelé lors d'un clic sur un bouton du menu du haut
     * (à droite de l'ecran)
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.navHelp:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case android.R.id.home:
                break;
        }
        return true;
    }
    /**
     * Fonction qui est appelé lors d'un clic sur un bouton du menu du haut
     * (à gauche de l'ecran)
     * */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch(item.getItemId()) {
            case R.id.navLanguage:
                Intent I = new Intent(this, LanguageActivity.class);
                I.putExtra("source","main");
                startActivity(I);
                break;
        }

        DrawerLayout menuDrawer = (DrawerLayout) findViewById(R.id.menuDrawerLayout);
        menuDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Fonction qui change la langue de l'application.
     * */
    public void setLocale() {

        /* On récupère la langue qui a été stockée */
        SharedPreferences languageSP = getApplicationContext().getSharedPreferences("language", MODE_PRIVATE);
        String lang = languageSP.getString("language",null);

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}