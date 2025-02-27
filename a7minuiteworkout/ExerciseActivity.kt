package com.example.a7minuiteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.a7minuiteworkout.databinding.ActivityExcerciseBinding

import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View

import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuiteworkout.databinding.DialogCustomBackConfirmationBinding

class  ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var restTimer: CountDownTimer? =null
         // Variable for Rest Timer and later on we will initialize it.
    private var restProgress =0
         // Variable for timer progress. As initial value the rest progress is set to 0. As we are about to start.
    //END


    // Adding a variables for the 30 seconds Exercise timer
    // START
    private var exerciseTimer: CountDownTimer? = null // Variable for Exercise Timer and later on we will initialize it.
    private var exerciseProgress = 0 // Variable for the exercise timer progress. As initial value the exercise progress is set to 0. As we are about to start.
    // END
    private var exerciseTimerDuration:Long = 1
    // TODO(Step 6 - The Variable for the exercise list and current position of exercise here it is -1 as the list starting element is 0.)
    // START
    private var exerciseList: ArrayList<ExerciseModel>? = null // We will initialize the list later.
    private var currentExercisePosition = -1 // Current Position of Exercise.
    // END

    private var tts: TextToSpeech? = null

    // create a binding variable
    private var binding:ActivityExcerciseBinding? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null
    private var player: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val navController = findNavController(R.id.nav_host_fragment_content_excercise)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }
        exerciseList = Constants.defaultExerciseList()
        tts = TextToSpeech(this, this)

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressedDispatcher.addCallback(this /* lifecycle owner */, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Back is pressed... Finishing the activity
                    finish()
                }
            })

        }
        // TODO(Step 7 - Initializing and Assigning a default exercise list to our list variable.)
        // START
       // exerciseList = Constants.defaultExerciseList()
        // END
        setupRestView()
        setupExerciseStatusRecyclerView()
    }
    // END

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_excercise)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

//    override fun onBackPressed()
//    {
//        customDialogForBackButton()
//        super.onBackPressed()
//
//    }

   private fun customDialogForBackButton(){

        var customDialog = Dialog(this)
       var dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
    customDialog.setContentView(dialogBinding.root)
       customDialog.setCanceledOnTouchOutside(false)
       dialogBinding.tvYes.setOnClickListener{
               this@ExerciseActivity.finish()
           customDialog.dismiss()
           }
       dialogBinding.tvNo.setOnClickListener {
        customDialog.dismiss()
       }
       customDialog.show()
    }
private fun setupExerciseStatusRecyclerView(){
    binding?.rvExerciseStatus?.layoutManager=
    LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
    binding?.rvExerciseStatus?.adapter = exerciseAdapter

}
    private fun setupRestView() {
        try {
            val soundURI = Uri.parse("android.resource://com.example.a7minuiteworkout/"+ R.raw.press_start)
        player = MediaPlayer.create(applicationContext, soundURI )
       player?.isLooping = false
            player?.start()
        } catch (e: Exception){
        e.printStackTrace()
    }



        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvUpComingExerciseName?.visibility = View.VISIBLE
        binding?.upcomingLabel?.visibility = View.VISIBLE

        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        /**
         * Here firstly we will check if the timer is running the and it is not null then cancel the running timer and start the new one.
         * And set the progress to initial which is 0.
         */
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }



//binding?.tvExcerciseName?.text = excerciseList!![currrentExercisePosition +1].getName()
        binding?.tvUpComingExerciseName?.text = exerciseList!![currentExercisePosition + 1].getName()
        setRestProgressBar()
    }
    // END

    // Setting up the 10 seconds timer for rest view and updating it continuously.
    //START
    /**
     * Function is used to set the progress of timer using the progress
     */
    private fun setRestProgressBar() {

        binding?.progressBar?.progress = restProgress // Sets the current progress to the specified value.

        /**
         * @param millisInFuture The number of millis in the future from the call
         *   to {#start()} until the countdown is done and {#onFinish()}
         *   is called.
         * @param countDownInterval The interval along the way to receive
         *   {#onTick(long)} callbacks.
         */
        // Here we have started a timer of 10 seconds so the 10000 is milliseconds is 10 seconds and the countdown interval is 1 second so it 1000.
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++ // It is increased by 1
                binding?.progressBar?.progress = 10 - restProgress // Indicates progress bar progress
                binding?.tvTimer?.text =
                    (10 - restProgress).toString()  // Current progress is set to text view in terms of seconds.
            }

            override fun onFinish() {
                // TODO(Step 8 - Increasing the current position of the exercise after rest view.)
                // START
                currentExercisePosition++
            exerciseList!![currentExercisePosition].setIsSelected(true)
              //  exerciseList!![curremtExercisePosition].setisCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()
               setupExerciseView()
            }
        }.start()
    }
    //END


    // Setting up the Exercise View with a 30 seconds timer
    // START
    /**
     * Function is used to set the progress of the timer using the progress for Exercise View.
     */
    private fun setupExerciseView() {

        // Here according to the view make it visible as this is Exercise View so exercise view is visible and rest view is not.
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpComingExerciseName?.visibility = View.INVISIBLE
        binding?.upcomingLabel?.visibility = View.INVISIBLE
        /**
         * Here firstly we will check if the timer is running and it is not null then cancel the running timer and start the new one.
         * And set the progress to the initial value which is 0.
         */
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        // TODO(Step 9 - Setting up the current exercise name and image to view to the UI element.)
        // START
        /**
         * Here current exercise name and image is set to exercise view.
         */
        speakOut(exerciseList!![currentExercisePosition].getName())
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()
        // END
        setExerciseProgressBar()

    }

    private fun setExerciseProgressBar() {

        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = exerciseTimerDuration.toInt() - exerciseProgress
                binding?.tvTimerExercise?.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {
                // TODO(Step 10 - Updating the view after completing the 30 seconds exercise.)
                // START
                if (currentExercisePosition < exerciseList?.size!! - 1) {

                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter?.notifyDataSetChanged()
                    setupRestView()
                } else {

                    finish()
                    val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
//                    Toast.makeText(
//                        this@ExerciseActivity,
//                        "Congratulations! You have completed the 7 minutes workout.",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
                // END
            }
        }.start()

    }
    // END

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }
    // Destroying the timer when closing the activity or app
    //START
    /**
     * Here in the Destroy function we will reset the rest timer if it is running.
     */
    public override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        super.onDestroy()


        if(player !=null){
            player!!.stop()
        }
        if(exerciseTimer !=null ){
            exerciseTimer!!.cancel()
            exerciseProgress=0;
        }

        if(tts !=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        binding = null

    }

    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null , "")
    }
}