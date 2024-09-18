package com.example.lab8

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab8.ui.theme.Lab8Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URL

val imageUrl = URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNWvJP4EOKpyn2ADnGHLPJIfJeDSiZj039Ecc8fFUvuVYzPYmhmUul39oWUeVjn8_n4fA&usqp=CAU")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab8Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ImageFromUrl(
                        url = imageUrl,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ImageFromUrl(url: URL, modifier: Modifier = Modifier) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Download the image asynchronously
    LaunchedEffect(url) {
        val bmp = withContext(Dispatchers.IO) {
            try {
                val inputStream = url.openStream()
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                null
            }
        }
        bitmap = bmp
    }

    // Display the image or a loading text
    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = null,
            modifier = modifier
        )
    } else {
        Text("Loading...", modifier = modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun ImagePreview() {
    Lab8Theme {
        ImageFromUrl(imageUrl)
    }
}