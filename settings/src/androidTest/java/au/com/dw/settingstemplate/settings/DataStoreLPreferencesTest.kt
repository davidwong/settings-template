package au.com.dw.settingstemplate.settings

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder
import androidx.datastore.rxjava3.RxDataStore
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry

import io.reactivex.rxjava3.subscribers.TestSubscriber
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DataStoreLPreferencesTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val dataStore : RxDataStore<Preferences> = RxPreferenceDataStoreBuilder(context, /*name=*/ "settings").build()
        val prefs = DataStorePreferences(dataStore)
        val testKey : Preferences.Key<String> = stringPreferencesKey("example_counter")

        val testSubscriber = TestSubscriber<String>()

        val update = prefs.editPreference(testKey, "testfirst")
        assertEquals("testfirst", update.blockingGet().get(testKey))

//        prefs.getPreference(testKey, "test").test().assertNoValues()
        prefs.getPreferenceFlowString(testKey, "test").subscribe(testSubscriber)
        testSubscriber.await(1, TimeUnit.SECONDS)
        testSubscriber.assertValue("testfirst")


    }

    @Test
    fun useAppContext2() {
        // Context of the app under test.
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val dataStore : RxDataStore<Preferences> = RxPreferenceDataStoreBuilder(context, /*name=*/ "settings").build()
        val prefs = DataStorePreferences(dataStore)
        val testKey : Preferences.Key<String> = stringPreferencesKey("example_counter")

//        val update = prefs.editPreference(testKey, "first")

//        prefs.getPreference(testKey, "test").test().assertNoValues()
//        val disposable = prefs.getPreferenceFlowString(testKey, "test")
//            .subscribeOn(Schedulers.trampoline())
//            .observeOn(Schedulers.trampoline())
//            .subscribe {
//                println(it)
//                assertEquals("test2weew", it)
//            }

        val disposable = prefs.getPreferenceFlowString(testKey, "test")
            .subscribe ({
                println(it)
                assertEquals("test2weew", it)
            },
                {
                    println(it.message)
                    fail("error")
                }
            )

        disposable.dispose()
    }

    @Test
    fun useAppContext3() {
        // Context of the app under test.
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val context = ApplicationProvider.getApplicationContext<Context>()

        val dataStore : RxDataStore<Preferences> = RxPreferenceDataStoreBuilder(context, /*name=*/ "settings").build()
        val prefs = DataStorePreferences(dataStore)
        val testKey : Preferences.Key<String> = stringPreferencesKey("example_counter")

//        val update = prefs.editPreference(testKey, "testfirst")
//        assertEquals("testfirst", update.blockingGet().get(testKey))

        assertEquals("testfirskjkjjt", prefs.getPreferenceFlowString(testKey, "test")
            .blockingFirst())
    }
}