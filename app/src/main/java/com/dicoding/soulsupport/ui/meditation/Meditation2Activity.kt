package com.dicoding.soulsupport.ui.meditation

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.soulsupport.R
import com.dicoding.soulsupport.databinding.ActivityMeditation2Binding

@Suppress("DEPRECATION")
class Meditation2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMeditation2Binding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeditation2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this, R.raw.sleep)

        binding.seekbar.progress = 0
        binding.seekbar.max = mediaPlayer.duration

        binding.play.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                binding.play.setImageResource(R.drawable.ic_pause)
            } else {
                mediaPlayer.pause()
                binding.play.setImageResource(R.drawable.ic_play)
            }
        }

        binding.next.setOnClickListener {
            mediaPlayer.stop()
            showToastAndNavigate()
        }
        binding.previous.setOnClickListener {
            mediaPlayer.stop()
            showToastAndNavigate()
        }

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        runnable = Runnable {
            binding.seekbar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

        mediaPlayer.setOnCompletionListener {
            binding.seekbar.progress = 0
        }
    }

    private fun showToastAndNavigate() {
        Toast.makeText(this, "Better Sleep Finished", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MeditationActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
        handler.removeCallbacks(runnable)
    }
}