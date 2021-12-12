package mycompany.example.simplenote

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Note : RealmObject() {
    // データを格納するモデルNoteクラス
    @PrimaryKey
    var id: Long = 0
    var date: Date = Date()
    var title: String = ""
    var detail: String = ""
}