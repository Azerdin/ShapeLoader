package pg.shapeloader.ui.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.io.File

@Composable
fun FilePickerButton(viewModel: ModelViewModel, context: Context) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        fun getFileName(context: Context, uri: Uri): String? {
            var name: String? = null
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIndex =
                        cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1) name = cursor.getString(nameIndex)
                }
            }
            return name
        }

        uri?.let { contentUri ->
            try {
                val originalName = getFileName(context, contentUri) ?: "model.glb"
                val allowed = listOf("jpg", "jpeg", "png", "glb", "gltf")
                val extension = originalName.substringAfterLast('.', "").lowercase()
                if (extension !in allowed) return@let

                context.contentResolver.openInputStream(contentUri)?.use { inputStream ->
                    val tempFile = File(context.cacheDir, originalName)

                    tempFile.outputStream().use { output ->
                        inputStream.copyTo(output)
                    }

                    viewModel.addModel(tempFile.absolutePath)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Column() {
        Button(
            onClick = {
                launcher.launch(
                    arrayOf(
                        "image/jpeg",          // .jpg
                        "image/png",           // .png
                        "model/gltf-binary",   // .glb
                        "model/gltf+json",     // .gltf
                        "application/octet-stream" // fallback dla niektórych menedżerów
                    )
                )
            }
        ) {
            Text("Wybierz plik")
        }
    }
}
