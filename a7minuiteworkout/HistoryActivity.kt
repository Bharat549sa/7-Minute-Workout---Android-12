package com.example.a7minuiteworkout

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.a7minuiteworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class HistoryActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

      //  setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_history)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        setSupportActionBar(binding?.toolbarHistoryActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="HISTORY"
        }

        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackInvokedCallback()
        }
        val dao = (application as WorkOutApp).db.historyDao()
        getAllCompletedDates(dao)


    }
private fun getAllCompletedDates(historyDao: HistoryDao) {

    lifecycleScope.launch {

        historyDao.fetchAllDates().collect { getAllCompletedDatesList ->
            for (i in getAllCompletedDatesList) {
                Log.e("Date:", "" + i)
            }

        }
    }
}


    override fun onDestroy() {
        super.onDestroy()
      //  binding = null
    }
}
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_history)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
//}