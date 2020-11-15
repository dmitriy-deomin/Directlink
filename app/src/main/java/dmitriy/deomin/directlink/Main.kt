package dmitriy.deomin.directlink

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pole = (findViewById<TextInputEditText>(R.id.text_input))

        // Get the clipboard system service
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


        // Paste clipboard text to edit text
        val clipData: ClipData? = clipboard.primaryClip
        clipData?.apply {
            val textToPaste: String = this.getItemAt(0).text.toString().trim()
            (findViewById<TextInputEditText>(R.id.text_input)).setText(textToPaste)
        }

        //btn_create
        (findViewById<Button>(R.id.btn_create)).setOnClickListener {

            var link = pole.text.toString()

            //проверим если там строка нужного формата
            if (link.contains("//www")) {
                //обрезаем до ?
                link = link.substringBefore('?')

                //заменяем www на dl
                link = link.replace("//www", "//dl")

                //меняем в поле
                (findViewById<TextInputEditText>(R.id.text_input)).setText(link)
            } else {
                Toast.makeText(this, R.string.text_error_nepodhodit_link, Toast.LENGTH_SHORT).show()
            }


        }
        //btn_copy
        (findViewById<Button>(R.id.btn_copy)).setOnClickListener {
            val clip = ClipData.newPlainText(
                "direct link dropbox", pole.text.toString()
            )
            clipboard.setPrimaryClip(clip)
        }
        //btn_share
        (findViewById<Button>(R.id.btn_share)).setOnClickListener {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_abaut -> {
                val abaut = DialogWindow(this, R.layout.abaut)
                (abaut.view().findViewById<Button>(R.id.donat)).setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://money.yandex.ru/to/41001566605499"))
                    startActivity(browserIntent) }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}