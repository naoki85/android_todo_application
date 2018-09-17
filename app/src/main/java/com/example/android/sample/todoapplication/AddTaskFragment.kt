package com.example.android.sample.todoapplication

import android.app.AlertDialog
import android.support.v4.app.DialogFragment
import android.content.DialogInterface
import android.app.Dialog
import android.os.Bundle
import android.app.Activity


class AddTaskFragment : DialogFragment() {
    interface AddTaskListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    private lateinit var mListener : AddTaskListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        builder.setView(inflater?.inflate(R.layout.add_task_fragment, null))
                .setPositiveButton(getString(R.string.add_task_dialog_positive_button),
                        DialogInterface.OnClickListener { dialog, _ ->
                            mListener.onDialogPositiveClick(this@AddTaskFragment);
                })
                .setNegativeButton(getString(R.string.add_task_dialog_negative_button),
                        DialogInterface.OnClickListener { dialog, _ ->
                            mListener.onDialogNegativeClick(this@AddTaskFragment);
                })
        // Create the AlertDialog object and return it
        return builder.create()
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        try {
            mListener = activity as AddTaskListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity!!.toString() + " must implement NoticeDialogListener")
        }

    }
}