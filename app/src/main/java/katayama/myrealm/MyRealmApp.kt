package katayama.myrealm

import android.app.Application
import io.realm.Realm

/**
 * Created by katayama on 2017/11/23.
 */
class MyRealmApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}