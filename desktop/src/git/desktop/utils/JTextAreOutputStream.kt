package git.desktop.utils

import java.io.IOException
import java.io.OutputStream
import javax.swing.JTextArea


class JTextAreOutputStream(private val textArea: JTextArea) : OutputStream() {
    @Throws(IOException::class)
    override fun write(b: Int) { // redirects data to the text area
        textArea.append(b.toChar().toString())
        textArea.caretPosition = textArea.document.length
    }

}