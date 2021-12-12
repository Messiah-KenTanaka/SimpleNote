package mycompany.example.simplenote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

// アダプターを作る(RealmRecyclerViewAdapterの継承)
class NoteAdapter(data: OrderedRealmCollection<Note>) :
    RealmRecyclerViewAdapter<Note, NoteAdapter.ViewHolder>(data, true) {

    //データを選択して詳細画面を開く(コールバック追加)
    private var listener: ((Long?) -> Unit)? = null

    fun setOnItemClickListener(listener: (Long?) -> Unit) {
        this.listener = listener
    }

    // 処理を実装する
    init {
        setHasStableIds(true)
    }

    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        val date: TextView = cell.findViewById(android.R.id.text2)
        val title: TextView = cell.findViewById(android.R.id.text1)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(android.R.layout.simple_expandable_list_item_2,
        parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
        val note: Note? = getItem(position)
        holder.date.text = android.text.format.DateFormat.format("yyyy/MM/dd HH:mm", note?.date)
        holder.title.text = note?.title
        //データを選択して詳細画面を開く(コールバック追加
        holder.itemView.setOnClickListener {
            listener?.invoke(note?.id)
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?: 0
    }


}