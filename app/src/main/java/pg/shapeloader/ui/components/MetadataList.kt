package pg.shapeloader.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MetadataList(
    viewModel: ModelViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val selectedModel by viewModel.selectedModel.collectAsStateWithLifecycle()
    val metadata = selectedModel?.metadata;
    metadata?.let {
        val mimeType = it.mimeType;
        val extension = it.extension;
        val name = it.name;
        val size = it.size;
        val lastModified = it.lastModified;
        LazyColumn(modifier = modifier) {
            item {
                Metadata(
                    label = "Typ",
                    value = mimeType
                )
            }
            item { HorizontalDivider() }
            item {
                Metadata(
                    label = "Rozszerzenie",
                    value = extension
                )
            }
            item { HorizontalDivider() }
            item {
                Metadata(
                    label = "Nazwa",
                    value = name
                )
            }
            item { HorizontalDivider() }
            item {
                Metadata(
                    label = "Rozmiar",
                    value = size.toString(),
                )
            }
            item { HorizontalDivider() }
            item {
                Metadata(
                    label = "Ostatnia modyfikacja",
                    value = lastModified?.toString() ?: ""
                )
            }
            item { Divider() }
        }
    }


}