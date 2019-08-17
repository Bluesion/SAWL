package com.charlie.sawl.timetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charlie.sawl.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class TimetableDialog: BottomSheetDialogFragment() {

    private lateinit var mDBHelper: TimetableDBHelper
    private val mTimetableList = ArrayList<TimetableDB>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.dialog_timetable, container, false)

        mDBHelper = TimetableDBHelper(activity!!)
        mTimetableList.addAll(mDBHelper.getAll)

        val cancelButton = rootView.findViewById<MaterialButton>(R.id.cancel)
        val saveButton = rootView.findViewById<MaterialButton>(R.id.save)

        val subject = rootView.findViewById<TextInputEditText>(R.id.subject)
        val teacher = rootView.findViewById<TextInputEditText>(R.id.teacher)

        val bundleSubject = arguments!!.getString("subject")
        val bundleTeacher = arguments!!.getString("teacher")
        val bundlePosition = arguments!!.getInt("position")

        if (bundleSubject != "") {
            subject.setText(bundleSubject)
        }

        if (bundleTeacher != "") {
            teacher.setText(bundleTeacher)
        }

        cancelButton.setOnClickListener {
            dismiss()
        }

        saveButton.setOnClickListener {
            update(subject.text.toString(), teacher.text.toString(), bundlePosition)
            dismiss()
        }

        return rootView
    }

    private fun update(subject: String, teacher: String, position: Int) {
        val db = mTimetableList[position]
        db.subject = subject
        db.teacher = teacher

        mDBHelper.update(db)

        mTimetableList[position] = db
    }

    companion object {
        fun newInstance(subject: String, teacher: String, position: Int): TimetableDialog {
            val f = TimetableDialog()

            val args = Bundle()
            args.putString("subject", subject)
            args.putString("teacher", teacher)
            args.putInt("position", position)
            f.arguments = args

            return f
        }
    }
}