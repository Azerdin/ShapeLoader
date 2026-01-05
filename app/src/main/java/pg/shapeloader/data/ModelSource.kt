package pg.shapeloader.data

import java.io.File

sealed class ModelSource {
    data class Asset(val path: String) : ModelSource()
    data class Disk(val file: File) : ModelSource()
}
