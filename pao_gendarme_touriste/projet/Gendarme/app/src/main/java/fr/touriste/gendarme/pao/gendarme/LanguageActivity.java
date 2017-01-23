package fr.touriste.gendarme.pao.gendarme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.StringTokenizer;


public class LanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);


        /* On récupère la liste des nationalités implémentées */
        final String[] items = getResources().getStringArray(R.array.nationalityList);
        Arrays.sort(items); /* On trie la liste par ordre alphabétique */
        String[] nationalities = getNationalityString(items);
        Arrays.sort(nationalities); /* On trie la liste par ordre alphabétique */

        /* On récupère la liste */
        ListView langageList = (ListView) findViewById(R.id.langageList);

        /* On lui affecte notre liste de nationalités */
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nationalities);
        langageList.setAdapter(adapter);

        /* On défini une action lors du clic d'un item de la liste généré */
        langageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                SharedPreferences languageSP = getApplicationContext().getSharedPreferences("language", MODE_PRIVATE);
                SharedPreferences.Editor editor = languageSP.edit();

                /*On récupère le code ISO de la nationalité choisie*/
                String isoCode = items[+position].split(":")[0];

                /*On change la valeur de la variable globale "language" */
                editor.putString("language", isoCode);
                editor.apply();

                /* permet de finir l'activité !*/
                Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(refresh);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Fonction qui va transformer la chaine de caracère pour n'avoir que le nom de la langue
     * La chaine de caractère étant composé de deux partie, on récupère seulement la seconde partie
     * */
    public String[] getNationalityString(String[] item) {
        String delim = ":";
        StringTokenizer st;
        String[] nationalities = new String[item.length];

        for(int i = 0; i < item.length; i++) {
            st = new StringTokenizer(item[i], delim);
            st.nextToken();
            nationalities[i] =  st.nextToken();
        }
        return nationalities;
    }
}
