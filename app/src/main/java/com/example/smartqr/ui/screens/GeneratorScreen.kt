package com.example.smartqr.ui.screens

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.smartqr.utils.QrUtils

@Composable
fun GeneratorScreen() {
    var inputText by remember { mutableStateOf("") }
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Enter Text, Link, or WiFi") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            singleLine = false,
            maxLines = 3
        )

        Button(
            onClick = {
                if (inputText.isNotBlank()) {
                    qrBitmap = QrUtils.generateQrBitmap(inputText)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate QR Code")
        }

        Spacer(modifier = Modifier.height(16.dp))

        qrBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Generated QR Code",
                modifier = Modifier
                    .size(250.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        Toast.makeText(context, "Saved to Gallery (Simulated)", Toast.LENGTH_SHORT).show()
                        // Actual file saving logic would go here
                    }
                ) {
                    Text("Save")
                }
                
                FilledTonalButton(
                    onClick = {
                         Toast.makeText(context, "Share Dialog (Simulated)", Toast.LENGTH_SHORT).show()
                         // Actual Intent share logic would go here
                    }
                ) {
                    Icon(Icons.Default.Share, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Share")
                }
            }
        }
    }
}