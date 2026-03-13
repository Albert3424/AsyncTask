package com.example.asynctask

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private lateinit var percentTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var startButton: Button
    private lateinit var cancelButton: Button

    private var downloadTask: MyDownloadTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)
        percentTextView = findViewById(R.id.percentTextView)
        progressBar = findViewById(R.id.progressBar)
        startButton = findViewById(R.id.startButton)
        cancelButton = findViewById(R.id.cancelButton)

        startButton.setOnClickListener {
            if (downloadTask == null || downloadTask?.status == AsyncTask.Status.FINISHED) {
                downloadTask = MyDownloadTask()
                downloadTask?.execute()
            }
        }

        cancelButton.setOnClickListener {
            downloadTask?.cancel(true)
        }
    }

    @Suppress("StaticFieldLeak")
    private inner class MyDownloadTask : AsyncTask<Void, Int, Void>() {

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            statusTextView.text = getString(R.string.status_running)
            progressBar.progress = 0
            percentTextView.text = getString(R.string.initial_percent)
            startButton.isEnabled = false
        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): Void? {
            for (i in 1..100) {
                if (isCancelled) break
                try {
                    Thread.sleep(300)
                    publishProgress(i)
                } catch (_: InterruptedException) {
                    return null
                }
            }
            return null
        }

        @Deprecated("Deprecated in Java")
        override fun onProgressUpdate(vararg values: Int?) {
            val progress = values[0] ?: 0
            progressBar.progress = progress
            percentTextView.text = getString(R.string.percent_format, progress)
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Void?) {
            statusTextView.text = getString(R.string.status_finished)
            startButton.isEnabled = true
        }

        @Deprecated("Deprecated in Java")
        override fun onCancelled() {
            statusTextView.text = getString(R.string.status_cancelled)
            progressBar.progress = 0
            percentTextView.text = getString(R.string.initial_percent)
            startButton.isEnabled = true
        }
    }
}
