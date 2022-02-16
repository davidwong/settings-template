package au.com.dw.settingstemplate.settings

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.rxjava3.RxDataStore
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single


class DataStorePreferences(val dataStore: RxDataStore<Preferences>) {

//    fun getPreference(key: Preferences.Key<String>, default: String): @NonNull Single<String>
//    {
//        val flow =  getPreferenceFlow(key)
//        return flow.first(default)
//    }

    fun getPreferenceString(key: Preferences.Key<String>, default: String): Flowable<String>
    {
        return getPreferenceFlow(key, default)
    }

    fun <T> getPreferenceFlow(key: Preferences.Key<T>, default: T): @NonNull Flowable<T>
    {
        return dataStore.data().map{ prefs -> prefs.get(key) ?: default}
    }

    fun getPreferenceFlowString(key: Preferences.Key<String>, default: String): @NonNull Flowable<String>
    {
        return dataStore.data().map{ pref -> useDefault(pref, key, default)}
//        return dataStore.data().map{ prefs -> prefs.get(key) ?: default}
    }

    fun useDefault(pref: Preferences, key: Preferences.Key<String>, default: String): String
    {
        val result = pref.get(key)
        if (result == null)
        {
            return default
        }
        else
        {
            return result
        }
    }

    fun <T> editPreference(key: Preferences.Key<T>, newValue: T): Single<Preferences>  {
        val updateResult = dataStore.updateDataAsync { prefsIn: Preferences ->
            val mutablePreferences = prefsIn.toMutablePreferences()
            mutablePreferences.set(key, newValue)
            Single.just(mutablePreferences)
        }
        return updateResult
    }
}