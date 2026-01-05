package pg.shapeloader.data

import android.net.Uri
import pg.shapeloader.enums.Dimension
import java.io.File

data class Model(
    val uri: Uri?,
    val file: File?,
    val source: ModelSource,
    val metadata: ModelMetadata,
    val dimension: Dimension,
    val isPredefined: Boolean = true,
)