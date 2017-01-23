package fr.touriste.gendarme.pao.gendarme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;


public class ReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        setTitle(R.string.advice);

        Intent intent = getIntent();
        int itemName = intent.getIntExtra(AdviceActivity.KEY, 0);

        /* Initialisation du WebView */
        WebView htmlWebView = (WebView)findViewById(R.id.webView);

        /* Initialisation des options du webView */
        WebSettings webSettings = htmlWebView.getSettings();
        webSettings.setBuiltInZoomControls(true);   /*Activation du zoom*/
        webSettings.setJavaScriptEnabled(true);     /*Activation du JavaScript*/
        webSettings.setDisplayZoomControls(false);  /*Désactivation du menu de zoom*/

        /* Récupération de la langue */
        SharedPreferences languageSP = getApplicationContext().getSharedPreferences("language", MODE_PRIVATE);
        String lang = languageSP.getString("language", null);

        /* Récupération du chemin d'accès au fichier html à afficher */
        String file = lang+"/advices/"+"advice_"+itemName+"_"+lang.toUpperCase()+".html";

        /* Récupération des assets*/
        AssetManager mgr = getBaseContext().getAssets();
        try {
            InputStream in = mgr.open(file, AssetManager.ACCESS_BUFFER);
            String htmlContentInStringFormat = StreamToString(in);
            in.close();
            htmlWebView.loadDataWithBaseURL(null, htmlContentInStringFormat, "text/html", "utf-8", null);

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), R.string.problemFile, Toast.LENGTH_LONG).show();

        }
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
                this.finish();
                return true;
            case R.id.navHelp:
                startActivity(new Intent(this, HelpActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Méthode qui permet de transcrire un flux en Chaine de caractère
     *
     * */
    public static String StreamToString(InputStream in) throws IOException {
        if(in == null) {
            return "";
        }
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        }catch(Exception e){
            Log.e("SteamToString",e.getMessage());
        }
        return writer.toString();
    }
}
