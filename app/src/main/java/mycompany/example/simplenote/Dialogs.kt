package mycompany.example.simplenote

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.DialogFragment


// ダイアログを追加
class ConfirmDialog(
    private val message: String,
    private val okLabel: String,
    private val okSelected: () -> Unit,
    private val cancelLabel: String,
    private val cancelSelected: () -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(message)
        builder.setPositiveButton(okLabel) { dialog, which ->
            okSelected()
        }
        builder.setNegativeButton(cancelLabel) { dialog, which ->
            cancelSelected()
        }
        return builder.create()
    }
    // アラートダイアログの背景変更
    override fun onStart() {
        super.onStart()
        val alertDialog = dialog as AlertDialog?
        if (alertDialog != null) {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GRAY)
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setBackgroundColor(resources.getColor(R.color.white))
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY)
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setBackgroundColor(resources.getColor(R.color.white))
        }
    }
}