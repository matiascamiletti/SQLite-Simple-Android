package garin.artemiy.simple.app.sqlite.library.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Artemiy Garin
 * date: 23.12.2012
 */
public class SharedPreferencesUtil {

    private SharedPreferences.Editor sharedPreferencesEditor;
    private SharedPreferences sharedPreferences;

    public SharedPreferencesUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_DATABASE,
                Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public void clearAllPreferences() {
        sharedPreferencesEditor.remove(Constants.SHARED_PREFERENCES_LIST);
        sharedPreferencesEditor.remove(Constants.SHARED_PREFERENCES_INDEX);
        sharedPreferencesEditor.remove(Constants.SHARED_DATABASE_QUERIES);
        sharedPreferencesEditor.remove(Constants.SHARED_DATABASE_TABLES);
        sharedPreferencesEditor.commit();
    }


    private int getNextIndex(String place) {
        return getCurrentIndex(place) + 1;
    }

    private void putCurrentIndex(String place, int index) {
        sharedPreferencesEditor.putInt(String.format(Constants.SHARED_PREFERENCES_INDEX, place), index);
        sharedPreferencesEditor.commit();
    }

    private int getCurrentIndex(String place) {
        return sharedPreferences.getInt(String.format(Constants.SHARED_PREFERENCES_INDEX, place), 1);
    }

    public void putList(String place, List<String> entityList) {
        for (String entity : entityList) {
            int index = getNextIndex(place);
            sharedPreferencesEditor.putString(String.format(Constants.SHARED_PREFERENCES_LIST, place, index), entity);
            putCurrentIndex(place, index);
        }
    }

    public List<String> getList(String place) {
        List<String> resultList = new ArrayList<String>();

        for (int i = 1; i <= getCurrentIndex(place); i++) {
            String savedString =
                    sharedPreferences.getString(String.format(Constants.SHARED_PREFERENCES_LIST, place, i), null);
            if (savedString != null) {
                resultList.add(savedString);
            }
        }

        return resultList;
    }

    public void commit() {
        sharedPreferencesEditor.commit();
    }

    public void putDatabaseVersion(int databaseVersion) {
        sharedPreferencesEditor.putInt(Constants.SHARED_DATABASE_VERSION, databaseVersion);
    }

    public int getDatabaseVersion() {
        // if not found, return first version -> 1
        return sharedPreferences.getInt(Constants.SHARED_DATABASE_VERSION, Constants.FIRST_DATABASE_VERSION);
    }
}
