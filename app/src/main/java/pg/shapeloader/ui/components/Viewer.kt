package pg.shapeloader.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import pg.shapeloader.Model3DViewer

@Composable
fun Viewer(viewModel: ModelViewModel = viewModel()) {
    val selectedModel by viewModel.selectedModel.collectAsStateWithLifecycle()

    Column {
        selectedModel?.let { model ->
            if (model.dimension == pg.shapeloader.enums.Dimension.Model3D) {
                Model3DViewer(
                    model = model,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            } else {
                InteractiveImage2DViewer(
                    model = model,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}