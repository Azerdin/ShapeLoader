package pg.shapeloader.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun FileScreen(viewModel: ModelViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current

    FilePickerButton(
        context = context,
        viewModel = viewModel
    )
}