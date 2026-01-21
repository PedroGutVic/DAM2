package com.pedrogv.camisetas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrogv.camisetas.controller.Controller
import com.pedrogv.camisetas.databinding.ActivityMainBinding
import com.pedrogv.camisetas.ui.CamisetaFormFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var controller: Controller

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        controller = Controller(this, binding)

        binding.myRecyclerView.layoutManager = LinearLayoutManager(this)
        controller.setAdapter()

        binding.floatingActionButton3.setOnClickListener {
            showFormFragment()
        }
    }

    fun showFormFragment(camisetaId: Int = -1) {
        val fragment = CamisetaFormFragment.newInstance(camisetaId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, fragment)
            .addToBackStack(null)
            .commit()
    }
}