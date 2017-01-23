package fr.touriste.gendarme.pao.gendarme.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by florian on 07/04/16.
 */
public class DataArrayAdapter extends ArrayAdapter<String> {

    public DataArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    public DataArrayAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public DataArrayAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public DataArrayAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    public DataArrayAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    public DataArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public void loadDataFromPref(SharedPreferences sharedPrefs, String DATA) {
        String gList =sharedPrefs.getString(DATA, "");
        if(!"".equals(gList) && ""!=gList){
            String [] str = gList.split(",");
            for (String item : str) {
                this.add(item);
            }
        }
    }

    @Override
    public String toString(){
        String data = "";
        for (int i = 0; i < getCount(); i++) {
            data += getItem(i).toString() ;
            if( i + 1 < getCount()){
                data += ",";
            }
        }
        return  data;
    }
}
