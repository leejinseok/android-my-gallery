package com.example.mygallery

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var granted = remember {
                mutableStateOf(false)
            }

            val launcher =
                rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                    granted.value = isGranted
                }

            val isAndroidVersionBeforeTiramisu =
                android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.TIRAMISU

            if (isAndroidVersionBeforeTiramisu) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    granted.value = true
                }
            } else {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.READ_MEDIA_IMAGES
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    granted.value = true
                }
            }

            if (granted.value) {


            } else {
                if (isAndroidVersionBeforeTiramisu) {
                    PermissionRequestScreen {
                        launcher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                } else {
                    PermissionRequestScreen {
                        launcher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                    }
                }
            }
        }
    }
}

@Composable
fun PermissionRequestScreen(onClickPermissionRequestButton: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "권한이 허용되지 않았습니다")
        Button(onClick = onClickPermissionRequestButton) {
            Text(text = "권한 요청")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(photoUris: List<Uri>) {
    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(pageCount = 3, modifier = Modifier.weight(1f)) { pageIndex ->
            Card {

            }
        }
    }
}

