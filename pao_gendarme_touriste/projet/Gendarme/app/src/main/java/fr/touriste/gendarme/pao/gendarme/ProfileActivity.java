package fr.touriste.gendarme.pao.gendarme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

import fr.touriste.gendarme.pao.gendarme.tools.*;

public class ProfileActivity extends AppCompatActivity {

    /* Constantes pour les préférences */
    private final String NAME = "name";
    private final String FIRSTNAME = "firstname";
    private final String BLOOD = "blood";
    private final String DATA = "data";
    private final String IMAGE = "image";

    private int PICK_IMAGE_REQUEST = 1;
    private CircularImageView profileImageView;
    private EditText nameEditText;
    private EditText firstnameEditText;
    private EditText diseaseEditText;
    private Spinner spinner;
    private ListView diseaseLV;
    private ArrayList<String> diseases;
    private DataArrayAdapter arrayAdapter;
    private static boolean addPressed = false;
    private static ViewSwitcher nameSwitcher;
    private static ViewSwitcher firstnameSwitcher;
    private static ViewSwitcher bloodSwitcher;
    private static TextView nameTextView;
    private static TextView firstnameTextView;
    private static TextView bloodTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Permet de récupérer les préférences de l'utilisateur afin de restituer ses informations */
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences data = getApplicationContext().getSharedPreferences(DATA, MODE_PRIVATE);
        SharedPreferences image = getApplicationContext().getSharedPreferences(IMAGE, MODE_PRIVATE);


        /* On récupère l'ensemble des containers */
        nameEditText        = (EditText) findViewById(R.id.nameEditText);
        firstnameEditText   = (EditText) findViewById(R.id.firstnameEditText);
        diseaseEditText     = (EditText) findViewById(R.id.diseaseEditText);

        profileImageView    = (CircularImageView) findViewById(R.id.profileImageView);
        spinner             = (Spinner) findViewById(R.id.bloodType);
        diseaseLV           = (ListView) findViewById(R.id.diseaseLV);

        nameSwitcher        = (ViewSwitcher) findViewById(R.id.nameSwitcher);
        firstnameSwitcher   = (ViewSwitcher) findViewById(R.id.firstnameSwitcher);
        bloodSwitcher       = (ViewSwitcher) findViewById(R.id.bloodSwitcher);

        nameTextView        = (TextView) nameSwitcher.findViewById(R.id.nameTextView);
        firstnameTextView   = (TextView) firstnameSwitcher.findViewById(R.id.firstnameTextView);
        bloodTextView       = (TextView) bloodSwitcher.findViewById(R.id.bloodTextView);

        /* On initialise les valeurs par défaut pour le nom, prénom et le Groupe sanguin */
        nameTextView.setText(pref.getString(NAME, getString(R.string.nameTextView)));
        firstnameTextView.setText(pref.getString(FIRSTNAME, getString(R.string.firstnameTextView)));
        bloodTextView.setText(pref.getString(BLOOD, "N/A"));

        /* Gestion de la liste des valeurs possibles pour le groupe sanguin */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bloodType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        /* Gestion de l'image */
        String imageUri = image.getString(IMAGE, ""); //On récupère l'URI de l'image de profil dans les préférences de l'utilisateur.

        /* Si la variable n'est pas nulle, alors on affiche l'image. */
        if(!imageUri.equals("")) {
            profileImageView.setImageURI(Uri.parse(imageUri));
        }

        /* On ajoute un listener qui va permettre de modifier la photo de profil lorsque l'on touche l'imageView. */
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*On créer un intent pour rediriger l'utilisateur dans la galerie*/
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* On démarre une nouvelle activité en attendant un retour de celle-ci lorsque l'on reviendra sur l'activité en cours */
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        /* Permet de refermer le clavier virtuel lorsqu'un champs perds le focus */
        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        firstnameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        diseaseEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        /* Construction de la liste des remarques médicales. */
        diseases = new ArrayList<>();
        arrayAdapter = new DataArrayAdapter(ProfileActivity.this,
                android.R.layout.simple_list_item_1, diseases);
        diseaseLV.setAdapter(arrayAdapter);
        registerForContextMenu(diseaseLV);

        /* Permet de construire la liste des remarques médicales à partir des préférences */
        arrayAdapter.loadDataFromPref(data, DATA);
        final SharedPreferences.Editor editor = data.edit();

        diseaseEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(!diseaseEditText.getText().toString().equals("")) { //Si la chaine entrée est non nulle.
                        diseases.add(diseaseEditText.getText().toString()); //On la place dans la liste.
                        arrayAdapter.notifyDataSetChanged();
                        /* On l'enregistre dans les préférences puis on les recharges. */
                        editor.clear();
                        editor.putString(DATA, arrayAdapter.toString());
                        editor.apply();
                    }
                    hideKeyboard(v); //On réduit le clavier lorsque l'utilisateur a terminé sa saisie.
                    diseaseEditText.setText(null); //On vide le champs permettant la saisie d'une remarques puisque l'utilisateur a fini.
                    return true;
                }
                return false;
            }
        });
    }

    /* Permet de réduire le clavier virtuel après avoir cliquer à côté d'un champs texte */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /* Permet de mettre en place la barre de navigation */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    /* Méthode de callback appelée après un startActivityForResult */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if (resultCode == RESULT_OK) {
                if (requestCode == PICK_IMAGE_REQUEST) { //Correspond au request code pour l'image de profil.
                    Uri selectedImageUri = data.getData(); //Permet de récupérer l'URI de l'image sélectionnée.
                    String selectedImagePath = getPath(selectedImageUri); //Permet de récupérer le path de cette image.
                    System.out.println("Image Path : " + selectedImagePath);
                    profileImageView.setImageURI(selectedImageUri); //On l'affiche.
                    /* On la sauvegarde dans les préférences de l'utilisateur */
                    SharedPreferences pref = getApplicationContext().getSharedPreferences(IMAGE, MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(IMAGE, selectedImageUri.toString());
                    editor.apply();
                }
            }
        }
        catch(Exception e){
            Log.e("imageTropGrosse",e.getMessage());
            Toast.makeText(getApplicationContext(), R.string.problemFile, Toast.LENGTH_LONG).show();
        }

    }

    /*Permet de récupérer le path de l'image de profil récupéré dans la galerie à partir de son uri*/
    public String getPath(Uri uri) {
        String res;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        res = cursor.getString(column_index);
        cursor.close();

        return res;
    }

    /* Permet de gérer les différents boutons situés dans la barre de navigation. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences pref;
        SharedPreferences.Editor editor;
        String nameTemp;
        String firstnameTemp;
        String bloodTemp;

        switch (item.getItemId()) {
            case R.id.navHelp: //Le bouton d'aide.
                startActivity(new Intent(this, HelpActivity.class)); //On démarre l'activité d'aide.
                break;
            case R.id.navAdd: //Bouton permettant la modification des champs dans le profil. (mode éditeur)
                if(addPressed) //Permet de gérer l'activation du mode éditeur du profil.
                    addPressed = false;
                else
                    addPressed = true;

                pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
                editor = pref.edit();

                /* Permet d'activer la possibilité d'éditer les champs texte. */
                nameSwitcher.showNext();
                firstnameSwitcher.showNext();
                bloodSwitcher.showNext();

                diseaseEditText = (EditText) findViewById(R.id.diseaseEditText);
                if(!diseaseEditText.isShown())
                    diseaseEditText.setVisibility(View.VISIBLE);
                else
                    diseaseEditText.setVisibility(View.INVISIBLE);

                nameTemp = nameEditText.getText().toString();
                firstnameTemp = firstnameEditText.getText().toString();
                bloodTemp = spinner.getSelectedItem().toString();

                /* Si rien n'est entré par l'utilisateur on place une valeur par défaut. */
                if (!nameTemp.equals("")) {
                    nameTextView.setText(nameTemp);
                    editor.putString(NAME, nameTemp);
                }

                if (!firstnameTemp.equals("")) {
                    firstnameTextView.setText(firstnameTemp);
                    editor.putString(FIRSTNAME, firstnameTemp);
                }

                bloodTextView.setText(bloodTemp);
                editor.putString(BLOOD, bloodTemp); //On sauvegarde les préférences.
                editor.apply();
                break;
            case android.R.id.home: //Bouton permettant de retourner sur l'accueil.
                if(addPressed) { //Sécurité au cas où on quitte l'activité en mode éditeur.
                    pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE); //sauvegarde des prefs.
                    editor = pref.edit();

                    nameTemp = nameEditText.getText().toString();
                    firstnameTemp = firstnameEditText.getText().toString();
                    bloodTemp = spinner.getSelectedItem().toString();

                    /* On sauvegarde dans les préférences seulement si la chaine entrée n'est pas vide. */
                    if (!nameTemp.equals("")) {
                        nameTextView.setText(nameTemp);
                        editor.putString(NAME, nameTemp);
                    }

                    if (!firstnameTemp.equals("")) {
                        firstnameTextView.setText(firstnameTemp);
                        editor.putString(FIRSTNAME, firstnameTemp);
                    }

                    bloodTextView.setText(bloodTemp);
                    editor.putString(BLOOD, bloodTemp);
                    editor.apply();
                }

                addPressed = false; //Avant de quitter l'activité on quitte le mode éditeur du profil.
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return true;
    }

    /* Création d'un contextMenu pour les remarques médicales */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() == R.id.diseaseLV) {
            menu.setHeaderTitle(getString(R.string.remarks));
            String[] menuItems = getResources().getStringArray(R.array.diseaseMenu);
            for(int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    /* Permet de construire un popup proposant de supprimer une remarque médicale si l'utilisateur appui longuement dessus. */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        SharedPreferences data = getApplicationContext().getSharedPreferences(DATA, MODE_PRIVATE);
        final SharedPreferences.Editor editor = data.edit();

        if(item.toString().equals(getString(R.string.delete))) { //Si on choisit de supprimer.
            arrayAdapter.remove(arrayAdapter.getItem(info.position)); //On supprime la remarque de la liste.
            editor.clear(); //On recharge les préférences pour rester cohérent.
            editor.putString(DATA, arrayAdapter.toString());
            editor.apply();
        }
        return true;
    }

    /*Action lorsque la touche back du téléphone est pressée*/
    @Override
    public void onBackPressed() {
        /* On sauvegarde les informations via les préférences avant de quitter l'application */
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        /* On récupère les infos entrées par l'utilisateur. */
        String nameTemp = nameEditText.getText().toString();
        String firstnameTemp = firstnameEditText.getText().toString();
        String bloodTemp = spinner.getSelectedItem().toString();

        /* Si rien n'est entrée on met une valeur par défaut*/
        if (!nameTemp.equals("")) {
            nameTextView.setText(nameTemp);
            editor.putString(NAME, nameTemp);
        }

        if (!firstnameTemp.equals("")) {
            firstnameTextView.setText(firstnameTemp);
            editor.putString(FIRSTNAME, firstnameTemp);
        }

        bloodTextView.setText(bloodTemp);
        editor.putString(BLOOD, bloodTemp);
        editor.apply();

        NavUtils.navigateUpFromSameTask(this); //On quitte l'activité.
    }
}
