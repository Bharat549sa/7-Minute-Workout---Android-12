package com.example.a7minuiteworkout

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

import com.example.a7minuiteworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
   private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

       // setSupportActionBar(binding.toolbar)
        setSupportActionBar(binding?.toolbarFinishActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarFinishActivity?.setNavigationOnClickListener{

            onBackPressedDispatcher.addCallback(this /* lifecycle owner */, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Back is pressed... Finishing the activity
                    finish()
                }
            })

        }
        binding?.btnFinish?.setOnClickListener{
            finish()
        }

        val Dao = (application as WorkOutApp).db.historyDao()

        addDateToDatabase(Dao)

        val navController = findNavController(R.id.nav_host_fragment_content_finish)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.fab.setOnClickListener { view ->
    //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
       //         .setAction("Action", null).show()
      //  }
    }
private fun addDateToDatabase(historyDao: HistoryDao){

    val c = Calendar.getInstance();
    val dataTime = c.time
    Log.e("date", "" + dataTime)
    val sdf = SimpleDateFormat("dd MMMM YY", Locale.getDefault())
    val date = sdf.format(dataTime)
    lifecycleScope.launch{
        historyDao.insert(HistoryEntity(date))
        Log.e("Date:", "Added...")
    }
}
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_finish)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}