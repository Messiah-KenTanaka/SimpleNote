package mycompany.example.simplenote

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyNoteApplication : Application() {
    // データベースの設定処理
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true).build()
        Realm.setDefaultConfiguration(config)
    }
}