package pg.shapeloader.data

data class ModelMetadata(
    val name: String,
    val extension: String,
    val size: Long,
    val mimeType: String?,
    val lastModified: Long?
)