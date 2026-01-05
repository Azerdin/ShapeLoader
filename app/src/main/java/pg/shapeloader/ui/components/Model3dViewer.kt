package pg.shapeloader

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.sceneview.Scene
import io.github.sceneview.math.Position
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelLoader
import pg.shapeloader.data.Model

@Composable
fun Model3DViewer(
    model: Model?,
    modifier: Modifier = Modifier
) {
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)

    var modelInstance by remember { mutableStateOf<ModelInstance?>(null) }

    LaunchedEffect(model) {
        if (model?.isPredefined == true) {
            model.uri?.path?.let {
                try {
                    modelInstance = modelLoader.createModelInstance(assetFileLocation = it)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            model?.file?.let {
                try {
                    modelInstance = modelLoader.createModelInstance(file = it)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    val childNodes = remember(modelInstance) {
        modelInstance?.let { instance ->
            listOf(
                ModelNode(
                    modelInstance = instance,
                    scaleToUnits = 1.0f,
                    centerOrigin = Position(x = 0f, y = 0f, z = 0f)
                )
            )
        } ?: emptyList()
    }
    Scene(
        modifier = modifier,
        engine = engine,
        modelLoader = modelLoader,
        childNodes = childNodes
    )
}