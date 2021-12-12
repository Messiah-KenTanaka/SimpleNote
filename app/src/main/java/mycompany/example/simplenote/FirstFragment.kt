package mycompany.example.simplenote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.kotlin.where
import mycompany.example.simplenote.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    // Realmオブジェクトを取得する
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()


    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Realmオブジェクトを取得する
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //RecyclerViewにアダプターとレイアウトマネージャを設定する
        binding.list.layoutManager = LinearLayoutManager(context)
        val notes = realm.where<Note>().findAll()
        val adapter = NoteAdapter(notes)
        binding.list.adapter = adapter

        // コールバックを実装し画面遷移を行う
        adapter.setOnItemClickListener { id ->
            id?.let {
                val action =
                    FirstFragmentDirections.actionToNoteEditFragment(it)
                findNavController().navigate(action)
            }
        }

        (activity as? MainActivity)?.setFabVisible(View.VISIBLE)
    }
    // Realmオブジェクトを取得する
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // Realmオブジェクトを取得する
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}