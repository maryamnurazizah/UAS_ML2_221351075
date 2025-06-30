package com.example.celestica.dataset

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.celestica.R
import java.io.BufferedReader
import java.io.InputStreamReader

class DatasetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dataset)

        val backButton = findViewById<ImageButton>(R.id.buttonBack)
        backButton.setOnClickListener {
            finish() // Menutup activity dan kembali ke sebelumnya
        }

        val columnContainer = findViewById<LinearLayout>(R.id.columnContainer)
        val csvData = readCSVFromAssets("6 class.csv")

        if (csvData.isNotEmpty()) {
            val headers = csvData[0]               // Baris pertama = judul kolom
            val columns = Array(headers.size) { mutableListOf<String>() }

            // Masukkan tiap isi ke kolom masing-masing
            for (i in 1 until csvData.size) {
                csvData[i].forEachIndexed { index, value ->
                    columns[index].add(value)
                }
            }

            // Tampilkan tiap kolom
            headers.forEachIndexed { index, header ->
                val columnView = createColumnView(header, columns[index])
                columnContainer.addView(columnView)
            }
        }
    }

    private fun readCSVFromAssets(fileName: String): List<List<String>> {
        val result = mutableListOf<List<String>>()
        val inputStream = assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.forEach { line ->
                val row = line.split(",")
                result.add(row)
            }
        }
        return result
    }

    private fun createColumnView(header: String, values: List<String>): LinearLayout {
        val context = this

        // Container kolom (card lengkap)
        val columnContainer = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            val params = LinearLayout.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.column_width),
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(
                resources.getDimensionPixelSize(R.dimen.column_margin),
                0,
                resources.getDimensionPixelSize(R.dimen.column_margin),
                0
            )
            layoutParams = params
        }

        // Header (title kolom)
        val headerView = TextView(context).apply {
            text = header
            setTypeface(null, Typeface.BOLD)
            setTextColor(Color.WHITE)
            textSize = 13f
            gravity = Gravity.CENTER
            background = ContextCompat.getDrawable(context, R.drawable.header_background)
            setPadding(8, 8, 8, 8)
        }

        // Container isi data (biar rounded bawahnya)
        val valueContainer = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            background = ContextCompat.getDrawable(context, R.drawable.body_background)
            setPadding(12, 12, 12, 12)
        }

        // Tambahkan isi data
        values.take(10).forEach { value ->
            val textView = TextView(context).apply {
                text = value
                textSize = 13f
                setTextColor(Color.parseColor("#DDDDDD"))
                gravity = Gravity.CENTER
                setPadding(4, 4, 4, 4)
            }
            valueContainer.addView(textView)
        }

        // Gabungkan semuanya
        columnContainer.addView(headerView)
        columnContainer.addView(valueContainer)

        return columnContainer
    }
}