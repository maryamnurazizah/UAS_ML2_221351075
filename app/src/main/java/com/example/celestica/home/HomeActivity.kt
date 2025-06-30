package com.example.celestica.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.celestica.AboutActivity
import com.example.celestica.FiturActivity
import com.example.celestica.ModelActivity
import com.example.celestica.R
import com.example.celestica.SimulasiActivity
import com.example.celestica.dataset.DatasetActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewPager = findViewById(R.id.viewPager)

        val starList = listOf(
            StarType(R.drawable.red_dwarf, "Red Dwarf", "Bintang kecil dan dingin yang membakar hidrogen dengan sangat lambat."),
            StarType(R.drawable.brown_dwarf, "Brown Dwarf", "Objek di antara planet dan bintang; terlalu kecil untuk fusi hidrogen."),
            StarType(R.drawable.white_dwarf, "White Dwarf", "Inti bintang padat sisa dari bintang kecil yang telah mati."),
            StarType(R.drawable.main_sequence, "Main Sequence", "Bintang stabil yang membakar hidrogen menjadi helium selama sebagian besar hidupnya."),
            StarType(R.drawable.supergiant, "Supergiant", "Bintang sangat besar dan terang, berada pada tahap akhir evolusinya."),
            StarType(R.drawable.hypergiant, "Hypergiant", "Bintang paling masif dan paling terang yang dikenal, sangat tidak stabil.")
        )

        val adapter = CardAdapter(starList)
        viewPager.adapter = adapter

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_dataset -> {
                    startActivity(Intent(this, DatasetActivity::class.java))
                    true
                }
                R.id.nav_fitur -> {
                    startActivity(Intent(this, FiturActivity::class.java))
                    true
                }
                R.id.nav_model -> {
                    startActivity(Intent(this, ModelActivity::class.java))
                    true
                }
                R.id.nav_simulasi -> {
                    startActivity(Intent(this, SimulasiActivity::class.java))
                    true
                }
                else -> false
            }
        }

        bottomNav.selectedItemId = R.id.nav_home
    }

    // Klik pada ikon About di pojok atas
    fun openAbout(view: android.view.View) {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }
}
