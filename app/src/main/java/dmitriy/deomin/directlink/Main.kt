package dmitriy.deomin.directlink

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class Main : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pole = (findViewById<EditText>(R.id.text_input))

        // Get the clipboard system service
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


        // Paste clipboard text to edit text
        val clipData: ClipData? = clipboard.primaryClip
        clipData?.apply {
            val textToPaste: String = this.getItemAt(0).text.toString().trim()
            pole.setText(textToPaste)
        }


        (findViewById<Button>(R.id.btn_title)).setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.myalpha))
            DialogWindow(this, R.layout.abaut)
        }

        //btn_create
        (findViewById<Button>(R.id.btn_create)).setOnClickListener {

            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.myalpha))

            var link = pole.text.toString()

            //проверим если там строка нужного формата
            if (link.contains("//www")) {
                //обрезаем до ?
                link = link.substringBefore('?')

                //заменяем www на dl
                link = link.replace("//www", "//dl")

                //меняем в поле
                pole.setText(link)
            } else {
                Toast.makeText(this, R.string.text_error_nepodhodit_link, Toast.LENGTH_SHORT).show()
            }


        }
        //btn_copy
        (findViewById<Button>(R.id.btn_copy)).setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.myalpha))

            val clip = ClipData.newPlainText(
                "direct link dropbox", pole.text.toString()
            )
            clipboard.setPrimaryClip(clip)
        }
        //btn_share
        (findViewById<Button>(R.id.btn_share)).setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.myalpha))

            try {
                val intent2 = Intent()
                intent2.action = Intent.ACTION_SEND
                intent2.type = "text/plain"
                intent2.putExtra(Intent.EXTRA_TEXT, pole.text.toString())
                startActivity(Intent.createChooser(intent2, "direct link dropbox"))
            } catch (e: Exception) {
                Toast.makeText(this, "error" + e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }
}
