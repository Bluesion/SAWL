package com.charlie.sawl.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.TextView
import com.charlie.sawl.R
import java.io.IOException
import java.io.InputStream

class AddEditActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val txtContent = findViewById<TextView>(R.id.txtContent)

        val assetManager = assets

        val input: InputStream
        try {
            input = assetManager.open("License.txt")

            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()

            val text = String(buffer)

            txtContent.text = text
        } catch (ignored: IOException) {}
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}