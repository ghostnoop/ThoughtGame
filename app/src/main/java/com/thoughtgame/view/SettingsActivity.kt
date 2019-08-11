package com.thoughtgame.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface.BUTTON_NEGATIVE
import android.content.DialogInterface.BUTTON_POSITIVE
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.thoughtgame.R
import androidx.appcompat.app.AlertDialog
import com.thoughtgame.storage.SharedPrefManager


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val listView = findViewById<ListView>(R.id.main_listview)
//        val redColor = Color.parseColor("#FF0000")
//        listView.setBackgroundColor(redColor)


        listView.adapter = MyCustomAdapter(this) // this needs to be my custom adapter telling my list what to render


    }


    private class MyCustomAdapter(context: Context) : BaseAdapter() {


        @SuppressLint("SetTextI18n")
        fun withItems(itemz: kotlin.Array<String>, rowMain: View, pos: Int) {


            val items = itemz
            val builder = AlertDialog.Builder(mContext)
            with(builder)
            {
                if (pos.equals(1)) {
                    setTitle("Time")
                    setItems(items) { dialog, which ->
                        val positionTextView = rowMain.findViewById<TextView>(R.id.position_textview)
                        positionTextView.text = items[which]
                        val clock = "${items[which][0]}${items[which][1]}"
                        SharedPrefManager.getInstance(mContext).setTime(clock.toInt())


                    }
                }
                if (pos.equals(2)) {
                    setTitle("Language")
                    setItems(items) { dialog, which ->
                        val positionTextView = rowMain.findViewById<TextView>(R.id.position_textview)
                        positionTextView.text = items[which]
                        SharedPrefManager.getInstance(mContext).setLang(items[which])

                    }
                }
                if (pos.equals(0)) {
                    val title = TextView(mContext)
                    title.text = "Number of questions"
                    title.gravity = Gravity.CENTER
                    title.setTextColor(Color.parseColor("#FF4081"));
                    title.textSize = 20f
                    title.setPadding(10, 20, 10, 20)
                    title.typeface = Typeface.DEFAULT_BOLD
                    setCustomTitle(title)


                    val dialogLayout = LayoutInflater.from(mContext).inflate(R.layout.alert_dialog_with_edittext, null)
                    val editer = dialogLayout.findViewById<EditText>(R.id.editText)
//                    val editer  = EditText(mContext)

                    editer.hint = "Change the number"
                    editer.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    editer.inputType = InputType.TYPE_CLASS_NUMBER

                    setView(dialogLayout)

                    val positionTextView = rowMain.findViewById<TextView>(R.id.position_textview)


                    setPositiveButton(
                        "Save"
                    ) { dialog, which ->

                        positionTextView.text = editer.text.toString()
                        val psize = editer.text.toString().toInt()
                        SharedPrefManager.getInstance(mContext).setCardsSize(psize)



                        positionTextView.text = "size is $psize"

                        dialog.dismiss()
                    }

//                    setNegativeButton(
//                        "Reset"
//                    ) { dialog, which -> dialog.dismiss() }

                    setNeutralButton(
                        "Reset"
                    ) { dialog, which ->
                        SharedPrefManager.getInstance(mContext).setCardsSize(-1)
                        positionTextView.text = "size is not defined"


                        dialog.dismiss()
                    }

                }

                show()

            }

        }


        private val mContext: Context

        private val names = arrayListOf<String>(
            "Number of questions", "Time", "Language", "Though game"
        )


        init {
            mContext = context

        }

        // responsible for how many rows in my list
        override fun getCount(): Int {
            return names.size
        }

        // you can also ignore this
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        // you can ignore this for now
        override fun getItem(position: Int): Any {
            print("@N22")
            return "TEST STRING"
        }

        // responsible for rendering out each row
        @SuppressLint("ViewHolder", "SetTextI18n")
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)

            val rowMain = layoutInflater.inflate(R.layout.row_main, viewGroup, false)

//            listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->
//
//                toast(position.toString())
//            }
            rowMain.setOnClickListener {
                val lang_arr = mContext.resources.getStringArray(R.array.lang_array)
                val time_arr = mContext.resources.getStringArray(R.array.time_array)

                if (position.equals(0))
                    withItems(time_arr, rowMain, 0)

                if (position.equals(1))
                    withItems(time_arr, rowMain, 1)
                if (position.equals(2))
                    withItems(lang_arr, rowMain, 2)

//                Toast.makeText(mContext,names[position],Toast.LENGTH_SHORT).show()

            }

            val nameTextView = rowMain.findViewById<TextView>(R.id.name_textView)
            nameTextView.text = names.get(position)
            val list_id = rowMain.findViewById<LinearLayout>(R.id.list_id)
//            list_id.setBackgroundColor(Color.parseColor("#A77044"))

            val positionTextView = rowMain.findViewById<TextView>(R.id.position_textview)
//            positionTextView.text = "Row number: $position"

            val time = SharedPrefManager.getInstance(mContext).getTime
            val lang = SharedPrefManager.getInstance(mContext).getLang
            val vers = SharedPrefManager.getInstance(mContext).getVers
            val size = SharedPrefManager.getInstance(mContext).getCardsSize


            when (position) {
                0 -> {
                    if (size < 0)
                        positionTextView.text = "size is not defined"
                    else
                        positionTextView.text = "size is $size"
                }
                1 -> {
                    positionTextView.text = "$time sec";
                }
                2 -> {
                    positionTextView.text = lang
                }
                3 -> {
                    positionTextView.text = "Version $vers"
                }

            }



            return rowMain
//            val textView = TextView(mContext)
//            textView.text = "HERE is my ROW for my LISTVIEW"
//            return textView
        }


    }

}

