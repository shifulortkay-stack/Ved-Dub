package com.veddub.app

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MainActivity : AppCompatActivity() {

    private var player: ExoPlayer? = null
    private var videoUri: Uri? = null

    private val pickVideo = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            videoUri = uri
            showVideo(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnAddVideo).setOnClickListener {
            pickVideo.launch("video/*")
        }

        val soon = View.OnClickListener {
            Toast.makeText(this, "Tez orada qo‘shiladi", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.btnRecord).setOnClickListener(soon)
        findViewById<Button>(R.id.btnSrt).setOnClickListener(soon)
        findViewById<Button>(R.id.btnRemoveSrt).setOnClickListener(soon)
    }

    private fun showVideo(uri: Uri) {
        val playerView = findViewById<PlayerView>(R.id.playerView)
        player?.release()
        player = ExoPlayer.Builder(this).build().also {
            playerView.player = it
            it.setMediaItem(MediaItem.fromUri(uri))
            it.prepare()
        }
        playerView.visibility = View.VISIBLE
        findViewById<View>(R.id.controls).visibility = View.VISIBLE
    }

    override fun onDestroy() {
        player?.release()
        super.onDestroy()
    }
}
