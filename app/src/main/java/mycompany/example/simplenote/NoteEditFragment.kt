package mycompany.example.simplenote

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import mycompany.example.simplenote.databinding.FragmentNoteEditBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NoteEditFragment : Fragment() {
    // 保存ボタンタップ時の処理
    private var _binding: FragmentNoteEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 保存ボタンタップ時の処理
        realm = Realm.getDefaultInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //
        _binding = FragmentNoteEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 更新処理を実装する
    private val args: NoteEditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 更新処理を実装する
        if (args.note != -1L) {
            val note = realm.where<Note>()
                .equalTo("id", args.note).findFirst()
//            binding.dateEdit.setText(
//                android.text.format.DateFormat.format(
//                    "yyyy/MM/dd",
//                    note?.date
//                )
//            )
//            binding.timeEdit.setText(android.text.format.DateFormat.format("HH:mm", note?.date))
            binding.titleEdit.setText(note?.title)
            binding.detailEdit.setText(note?.detail)
            // ビューの表示制御(削除ボタンの表示・非表示)
            binding.delete.visibility = View.VISIBLE //表示
        } else {
            binding.delete.visibility = View.INVISIBLE // 非表示
        }

        // 更新処理を実装する
        (activity as? MainActivity)?.setFabVisible(View.INVISIBLE)
        binding.save.setOnClickListener {
            // 確認ダイアログを表示する(保存ボタン)
            val dialog = ConfirmDialog("保存しますか？",
                "保存", { saveNote(it) },
                "キャンセル", {
                    Snackbar.make(it, "キャンセルしました", Snackbar.LENGTH_SHORT)
                        .show()
                })
            dialog.show(parentFragmentManager, "save_dialog")
        }
        // 削除処理を実装
        binding.delete.setOnClickListener {
            // (削除ボタン)
            val dialog = ConfirmDialog(
                "削除しますか？",
                "削除", { deleteNote(it) },
                "キャンセル", {
                    Snackbar.make(it, "キャンセルしました", Snackbar.LENGTH_SHORT)
                        .show()
                })
            dialog.show(parentFragmentManager, "delete_dialog")
        }
    }

    private fun saveNote(view: View) {
        // 更新処理を実装する
        when (args.note) {
            -1L -> {
                realm.executeTransaction { db: Realm ->
                    val maxId = db.where<Note>().max("id")
                    val nextId = (maxId?.toLong() ?: 0L) + 1L
                    val note = db.createObject<Note>(nextId)
                    val date = /*"${binding.dateEdit.text} ${binding.timeEdit.text}"*/"".toDate()
                    if (date != null) note.date = date
                    note.title = binding.titleEdit.text.toString()
                    note.detail = binding.detailEdit.text.toString()
                }
                Snackbar.make(view, "追加しました", Snackbar.LENGTH_SHORT)
                    .setAction("戻る") { findNavController().popBackStack() }
                    .setActionTextColor(Color.YELLOW)
                    .show()
            }
            else -> {
                realm.executeTransaction { db: Realm ->
                    val note = db.where<Note>()
                        .equalTo("id", args.note).findFirst()
                    val date = (/*"${binding.dateEdit.text} "*/"" +
                            /*"${binding.timeEdit.text}"*/"").toDate()
                    if (date != null) note?.date = date
                    note?.title = binding.titleEdit.text.toString()
                    note?.detail = binding.detailEdit.text.toString()
                }
                Snackbar.make(view, "修正しました", Snackbar.LENGTH_SHORT)
                    .setAction("戻る") { findNavController().popBackStack() }
                    .setActionTextColor(Color.YELLOW)
                    .show()
            }
        }
    }

    // 削除処理を実装
    private fun deleteNote(view: View) {
        realm.executeTransaction { db: Realm ->
            db.where<Note>().equalTo("id", args.note)
                ?.findFirst()
                ?.deleteFromRealm()
        }
        Snackbar.make(view, "削除しました", Snackbar.LENGTH_SHORT)
            .setActionTextColor(Color.YELLOW)
            .show()

        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        realm.close()
    }

    private fun String.toDate(pattern: String = "yyyy/MM/dd HH:mm"): Date? {
        return try {
            SimpleDateFormat(pattern).parse(this)
        } catch (e: IllegalAccessException) {
            return null
        } catch (e: ParseException) {
            return null
        }
    }
}
