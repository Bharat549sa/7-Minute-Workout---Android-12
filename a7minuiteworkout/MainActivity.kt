package com.example.a7minuiteworkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.a7minuiteworkout.databinding.ActivityMain2Binding
import android.content.Intent
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity2 : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


      //  setSupportActionBar(binding.toolbar)
//        tvTimer.text = "${(timerduration/1000).toString()}"
//        btnStart.setONClickListener{ StartTimerpauseOffset)

        binding?.flStart?.setOnClickListener {
            val intent = Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }


        binding?.flBMI?.setOnClickListener {
            // Launching the BMI Activity
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }


        val navController = findNavController(R.id.nav_host_fragment_content_main2)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

//    private fun startTimer(pauseOffsetL:Long){
//
//        countDownTimer = object: CountDownTimer(timerDuration -PauseOffsetl, countDowninterval)
//
//                override fun onTick(millisUntilFinished:Long) {
//            pauseOffset = timerDuration - millisUntilFinished
//            tvTimer.text = (millisUntilFinished / 1000).toString()
//        }
//        override fun onFinish(){
//
//            Toast.makeText(this@MainActivity2, "Timer is Finished", Toast.LENGTH_SHORT).show()
//        }
//    }.start()
//
//
//}
//private fun pausetimer() {
//    if (countDownTimer != null) {
//        countDownTiemr!!.cancel()
//        tvTimer.text = "${(timerDuration/1000).toString()}"
//        countDownTimer = null
//        pauseOffset = 0
//    }
//
//}

//    override fun onInit(status: Int) {
//
//        if (status == TextToSpeech.SUCCESS) {
//            // set US English as language for tts
//            val result = tts!!.setLanguage(Locale.US)
//
//            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.e("TTS", "The Language specified is not supported!")
//            }
//
//        } else {
//            Log.e("TTS", "Initialization Failed!")
//        }
//   }
    override fun onDestroy() {
        super.onDestroy()
       // binding = null
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main2)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}