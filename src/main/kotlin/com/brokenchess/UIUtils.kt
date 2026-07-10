package com.brokenchess

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import org.jetbrains.skia.Image

object UiUtils {
    private const val BOARD_SCALE = 0.82f

    // board square colors, sampled from the original ChessBoard.jpg
    private const val SQUARES_PER_SIDE = 8
    private val LIGHT_SQUARE_COLOR = Color(0xFF9CA4E3)
    private val DARK_SQUARE_COLOR = Color(0xFF353E81)

    // draws the 8x8 board by filling one colored rectangle per square
    @Composable
    fun ChessBoard(modifier: Modifier = Modifier) {
        Canvas(modifier = modifier) {
            val squareSize = size.width / SQUARES_PER_SIDE
            for (row in 0 until SQUARES_PER_SIDE) {
                for (column in 0 until SQUARES_PER_SIDE) {
                    val isLightSquare = (row + column) % 2 == 0
                    drawRect(
                        color = if (isLightSquare) LIGHT_SQUARE_COLOR else DARK_SQUARE_COLOR,
                        topLeft = Offset(column * squareSize, row * squareSize),
                        size = Size(squareSize, squareSize)
                    )
                }
            }
        }
    }

    /*Composables call loadImageBitmap on every recomposition, and the render loop
    recomposes without a cache each frame re-reads every asset from
    disk*/
    private val imageCache = ConcurrentHashMap<String, ImageBitmap>()

    @JvmStatic
    fun calculateBoardSize(maxWidth: Float, maxHeight: Float): Float {
        val availableSpace = minOf(maxWidth, maxHeight)
        return availableSpace * BOARD_SCALE
    }

    fun loadImageBitmap(assetPath: String): ImageBitmap? {
        imageCache[assetPath]?.let { return it }

        val file = File(assetPath)
        if (!file.exists()) {
            return null
        }

        return try {
            val bytes = if (file.extension.equals("svg", ignoreCase = true)) {
                val transcoder = PNGTranscoder()
                val input = TranscoderInput(file.toURI().toURL().toString())
                val output = ByteArrayOutputStream()
                transcoder.transcode(input, TranscoderOutput(output))
                output.toByteArray()
            } else {
                // Skia decodes PNG/JPEG natively; no need to round-trip through ImageIO.
                file.readBytes()
            }

            Image.makeFromEncoded(bytes).toComposeImageBitmap().also { imageCache[assetPath] = it }
        } catch (_: Exception) {
            null
        }
    }
}
