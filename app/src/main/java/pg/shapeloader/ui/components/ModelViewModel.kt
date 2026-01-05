package pg.shapeloader.ui.components

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pg.shapeloader.data.Model
import pg.shapeloader.data.ModelFactory
import java.io.File
import java.io.IOException

class ModelViewModel(application: Application) : AndroidViewModel(application) {

    private val MODELS_DIR = "models"

    private val _models = MutableStateFlow<List<Model>>(emptyList())

    private val _selectedModel = MutableStateFlow<Model?>(null);

    private val _currentFile = MutableStateFlow<File?>(null);
    val models = _models.asStateFlow()
    val selectedModel = _selectedModel.asStateFlow();

    init {
        loadModelsFromAssets();
        viewModelScope.launch {
            _selectedModel.collect {
                removeFileFromCache();
            }
        }
    }

    private fun loadModelsFromAssets() {
        val context = getApplication<Application>()
        try {
            val files = context.assets.list(MODELS_DIR) ?: emptyArray()
            val fullPaths = files.map { fileName -> "$MODELS_DIR/$fileName" }

            val modelList = fullPaths.map { fullPath ->
                ModelFactory.fromAsset(context, fullPath)
            }

            _models.value = modelList
        } catch (e: IOException) {
            _models.value = emptyList()
        }
    }

    fun addModel(absolutePath: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = File(absolutePath)

                if (!file.exists()) {
                    Log.e("ModelViewModel", "Plik nie istnieje pod ścieżką: $absolutePath")
                    return@launch
                }
                val model = ModelFactory.fromFile(file)
                withContext(Dispatchers.Main) {
                    _selectedModel.value = model;
                }

            } catch (e: Exception) {
                Log.e("ModelViewModel", "Błąd ładowania modelu: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun removeFileFromCache() {
        _currentFile.value?.let { file ->
            if (file.exists()) {
                val deleted = file.delete()
                if (deleted) {
                    Log.d("ModelViewModel", "Plik usunięty pomyślnie")
                } else {
                    Log.e("ModelViewModel", "Nie udało się usunąć pliku")
                }
            }
            _currentFile.value = null
        }
    }


    fun changeSelectedModel(model: Model) {
        _selectedModel.value = model;
    }
}