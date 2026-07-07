package com.brokenchess

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import org.jetbrains.skia.Image

object UiUtils {
    private const val BOARD_SCALE = 0.82f

    @JvmStatic
    fun calculateBoardSize(maxWidth: Float, maxHeight: Float): Float {
        val availableSpace = minOf(maxWidth, maxHeight)
        return availableSpace * BOARD_SCALE
    }

    fun loadImageBitmap(assetPath: String): ImageBitmap? {
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
                val bufferedImage: BufferedImage = ImageIO.read(file)
                val output = ByteArrayOutputStream()
                ImageIO.write(bufferedImage, "png", output)
                output.toByteArray()
            }

            Image.makeFromEncoded(bytes).toComposeImageBitmap()
        } catch (_: Exception) {
            null
        }
    }
}
