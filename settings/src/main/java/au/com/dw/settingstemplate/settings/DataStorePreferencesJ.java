package au.com.dw.settingstemplate.settings;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex.rxjava3.core.Flowable;

public class DataStorePreferencesJ {

    private RxDataStore<Preferences> dataStore;

    public DataStorePreferencesJ(RxDataStore<Preferences> dataStore) {
        this.dataStore = dataStore;
    }

    public Flowable<String> getPreference(Preferences.Key<String> key)
    {
        return dataStore.data().map(prefs -> prefs.get(key));
    }
}
