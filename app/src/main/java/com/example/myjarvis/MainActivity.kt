package com.example.myjarvis

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.myjarvis.ui.theme.MyJarvisTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    lateinit var startForResult: ActivityResultLauncher<Intent>
    var speakText by
        mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startForResult=
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                if (it.resultCode== RESULT_OK && it.data !=null){
                    var resultData : Intent?= it.data
                    val resultArray= resultData?.getStringArrayExtra(RecognizerIntent.EXTRA_RESULTS)
                    speakText=resultArray?.get(0).toString()
                }
        }
        enableEdgeToEdge()
        setContent {
            MyJarvisTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SpeechToText(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun SpeechToText(modifier: Modifier = Modifier) {

        var context= LocalContext.current
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                IconButton(onClick = {
                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                    intent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                    )
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak Now")
                    startForResult.launch(intent)
                })
                {
                    Icon(imageVector = Icons.Rounded.Mic, contentDescription = null)
                }
                Text(text = speakText)
            }
        }
    }

}

@Preview
@Composable
private fun SpeechToTextPreview() {
    SpeechToTextPreview()
    
}