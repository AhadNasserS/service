package com.example.service

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.service.ui.theme.ServiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPostNotificationPermission()
        setContent {
            ServiceTheme {
                StartService( this)
            }
        }
    }
private fun checkPostNotificationPermission(){
    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU)
    ActivityCompat.requestPermissions(
        this,
        arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
        1
    )
}
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ServiceTheme {
        Greeting("Android")
    }
}

@Composable
fun StartService(context: Context){
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        val serviceIntent = Intent(context, TestService::class.java)

        Button(onClick = {
                context.startService(serviceIntent)
            }){
                Text(text = "Start Service")
            }



        Button(onClick = {
            serviceIntent.action = STOP_SERVICE
            context.stopService(serviceIntent)
        }){
            Text(text = "Stop Service")
        }


    }
}