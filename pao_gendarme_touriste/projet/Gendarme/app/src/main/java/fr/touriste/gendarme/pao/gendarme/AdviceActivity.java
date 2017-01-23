package fr.touriste.gendarme.pao.gendarme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import fr.touriste.gendarme.pao.gendarme.tools.CustomListAdapter;

public class AdviceActivity extends AppCompatActivity {

    public final static String KEY = "ADVICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        /* On récupère la liste des conseils (dans le fichier values/string.xml) */
        final String[] suffixes = getResources().getStringArray(R.array.adviceLabel);

        /* On crée une liste */
        final Integer[] imageID = new Integer[suffixes.length];

        /* Pour chaque item de la liste, on y affecte la bonne image associé au texte. */
        for (int i = 0; i < suffixes.length; i++) {
            imageID[i] = getResources().getIdentifier("image_advice_" + i, "drawable", getPackageName());
        }

        /* On crée notre vue grâce à un outil. */
        CustomListAdapter adapter = new CustomListAdapter(this, suffixes, imageID);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        /* On ajouter une action lors du clic d'un item */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(AdviceActivity.this, ReaderActivity.class);
                intent.putExtra(KEY, position);
                startActivity(intent);
            }
        });
    }
}
