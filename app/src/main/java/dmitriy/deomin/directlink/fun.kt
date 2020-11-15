package dmitriy.deomin.directlink

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager

class DialogWindow(context: Context, loaut: Int, onTop: Boolean = false) {

    var full_skren = false

    private val alertDialog: AlertDialog
    private val content: View

    init {
        val builder = AlertDialog.Builder(context)
        content = LayoutInflater.from(context).inflate(loaut, null)
        builder.setView(content)

        this.alertDialog = builder.create()

        if(onTop){
            alertDialog.setCanceledOnTouchOutside(false)
        }else{
            alertDialog.setCanceledOnTouchOutside(true)
        }

        //сместим немногов низ окно
        val params = this.alertDialog.window?.attributes

        //https://it-giki.com/post/355.html
        params!!.y = 150

        //применяем правки
        this.alertDialog.window!!.attributes = params

        //показываем окно
        this.alertDialog.show()
    }

    fun view(): View {
        return content
    }

    fun close() {
        alertDialog.cancel()
    }

    fun full_screen(){
        full_skren = if(full_skren){
            alertDialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT)
            false
        }else{
            alertDialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
            true
        }
    }
}
