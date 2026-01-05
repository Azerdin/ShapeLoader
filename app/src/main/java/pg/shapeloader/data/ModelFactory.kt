package pg.shapeloader.data

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.net.toUri
import pg.shapeloader.enums.Dimension
import java.io.File

object ModelFactory {

    fun fromAsset(
        context: Context,
        assetPath: String
    ): Model {
        val ext = assetPath.substringAfterLast('.', "").lowercase()

        val metadata = ModelMetadata(
            name = assetPath.substringAfterLast('/'),
            extension = ext,
            size = context.assets.open(assetPath).available().toLong(),
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext),
            lastModified = null
        )

        return Model(
            uri = Uri.parse(assetPath),
            source = ModelSource.Asset(assetPath),
            metadata = metadata,
            file = File(assetPath),
            dimension = if (ext == "glb") Dimension.Model3D else Dimension.Model2D,
            isPredefined = true
        )
    }

    fun fromFile(file: File): Model {
        val ext = file.extension.lowercase()

        val metadata = ModelMetadata(
            name = file.name,
            extension = ext,
            size = file.length(),
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext),
            lastModified = file.lastModified()
        )

        return Model(
            uri = file.toUri(),
            file = file,
            source = ModelSource.Disk(file),
            metadata = metadata,
            dimension = if (ext == "glb" || ext == "gltf") Dimension.Model3D else Dimension.Model2D,
            isPredefined = false
        )
    }
}
