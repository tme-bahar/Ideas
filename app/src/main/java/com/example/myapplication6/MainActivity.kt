package com.example.myapplication6

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val url = URL("https://api.github.com/search/users?q=eyehunt")
        val url = URL("http://tmebahar.ir/uploads/data.txt")
        MyAsyncTask().execute(url)
    }
    // AsyncTask inner class
    inner class MyAsyncTask : AsyncTask<URL, Int, String>() {

        private var result: String = "";

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: URL?): String {



                val connect = params[0]?.openConnection() as HttpURLConnection

                    connect.readTimeout = 8000
                    connect.connectTimeout = 8000
                    connect.requestMethod = "GET"
                    connect.connect();

                    val responseCode: Int = connect.responseCode;
                    if (responseCode == 200) {
                        result = streamToString(connect.inputStream)
                    }

            return result
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressBar.visibility = View.GONE
            //set result in textView
            tv_result.text = result
        }
    }

    fun streamToString(inputStream: InputStream): String {

        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var result = ""

        try {
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    result += line
                }
            } while (line != null)
            inputStream.close()
        } catch (ex: Exception) {

        }
        return result
    }
}
