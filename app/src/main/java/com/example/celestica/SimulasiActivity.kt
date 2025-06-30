package com.example.celestica

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class SimulasiActivity : AppCompatActivity() {

    private lateinit var inputTemp: EditText
    private lateinit var inputLum: EditText
    private lateinit var inputRadius: EditText
    private lateinit var inputMv: EditText
    private lateinit var spinnerColor: Spinner
    private lateinit var spinnerClass: Spinner
    private lateinit var btnPredict: Button
    private lateinit var tvResult: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var interpreter: Interpreter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulasi)

        // Inisialisasi view
        inputTemp = findViewById(R.id.inputTemp)
        inputLum = findViewById(R.id.inputLum)
        inputRadius = findViewById(R.id.inputRadius)
        inputMv = findViewById(R.id.inputMv)
        spinnerColor = findViewById(R.id.spinnerColor)
        spinnerClass = findViewById(R.id.spinnerClass)
        btnPredict = findViewById(R.id.btnPredict)
        tvResult = findViewById(R.id.tvResult)
        btnBack = findViewById(R.id.buttonBack)

        // Data spinner
        val colors = arrayOf(
            "Blue", "Blue-white", "White", "Whitish", "yellow-white",
            "Yellowish White", "yellowish", "Pale yellow orange", "Orange", "Red"
        )
        val spectralClasses = arrayOf("O", "B", "A", "F", "G", "K", "M")

        spinnerColor.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, colors)
        spinnerClass.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spectralClasses)

        // Load model
        interpreter = Interpreter(loadModelFile())

        // Tombol kembali
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Tombol prediksi
        btnPredict.setOnClickListener {
            val temp = inputTemp.text.toString().toFloatOrNull()
            val lum = inputLum.text.toString().toFloatOrNull()
            val radius = inputRadius.text.toString().toFloatOrNull()
            val mv = inputMv.text.toString().toFloatOrNull()
            val color = spinnerColor.selectedItemPosition.toFloat()
            val spectral = spinnerClass.selectedItemPosition.toFloat()

            // Validasi input
            if (temp == null || lum == null || radius == null || mv == null) {
                Toast.makeText(this, "Isi semua input dengan benar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi logika domain (kecuali magnitude yang bisa negatif)
            if (temp < 0 || lum < 0 || radius < 0) {
                Toast.makeText(this, "Nilai Temperature, Luminosity, dan Radius tidak boleh negatif", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi spinner (optional)
            if (spinnerColor.selectedItemPosition == AdapterView.INVALID_POSITION ||
                spinnerClass.selectedItemPosition == AdapterView.INVALID_POSITION) {
                Toast.makeText(this, "Pilih warna dan kelas spektral", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputArray = floatArrayOf(temp, lum, radius, mv, color, spectral)

            try {
                val resultIndex = doPrediction(inputArray)

                val labels = arrayOf(
                    "Red Dwarf",     // 0
                    "Brown Dwarf",   // 1
                    "White Dwarf",   // 2
                    "Main Sequence", // 3
                    "Supergiant",    // 4
                    "Hypergiant"     // 5
                )

                tvResult.text = if (resultIndex in labels.indices) {
                    "Star Type: ${labels[resultIndex]}"
                } else {
                    "Tidak dapat memprediksi Star Type"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Terjadi kesalahan saat prediksi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi untuk load model dari assets
    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = assets.openFd("startype.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    // Fungsi untuk prediksi
    private fun doPrediction(inputs: FloatArray): Int {
        val inputBuffer = ByteBuffer.allocateDirect(4 * inputs.size).order(ByteOrder.nativeOrder())
        for (value in inputs) {
            inputBuffer.putFloat(value)
        }

        val outputBuffer = ByteBuffer.allocateDirect(4 * 6).order(ByteOrder.nativeOrder())
        interpreter.run(inputBuffer, outputBuffer)
        outputBuffer.rewind()

        val outputs = FloatArray(6)
        outputBuffer.asFloatBuffer().get(outputs)

        return outputs.indices.maxByOrNull { outputs[it] } ?: -1
    }

    override fun onDestroy() {
        interpreter.close()
        super.onDestroy()
    }
}
