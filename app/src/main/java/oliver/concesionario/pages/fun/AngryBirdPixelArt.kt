package oliver.concesionario.pages.`fun`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AngryBirdPixelArt(modifier: Modifier = Modifier) {
    val pixelArt = listOf(
        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr",
        "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr",
        "rrrrrrrBBBBBBBBBBBBBBrBBBBBBBBBBBBBrrrrrrr",
        "rrrrrrBBBBBBBBBBBBBBBrBBBBBBBBBBBBBBBrrrrr",
        "rrrrrBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBrrrr",
        "rrrrrBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBrrrr",
        "rrrrrBBwwwwwwBBBBBBBBBwwwwwwBBBBBBBBBrrrr",
        "rrrrrrBwwwwwwBBBBBBBBBwwwwwwBBBBBBBBBrrrrr",
        "rrrrrrBwwPPwwBBBBBBBBBwwPPwwBBBBBBBBBrrrrr",
        "rrrrrrBwwwwwwBBBBBBBBBwwwwwwBBBBBBBBBrrrrr",
        "rrrrrrBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBrrrrr",
        "rrrrrrrBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBrrrrrr",
        "rrrrrrrrrBBBBBBBBBBBBBBBBBBBBBBBBBBBrrrrrrr",
        "rrrrrrrrrrrrBBBBBBBBBBBBBBBBBBBBBrrrrrrrrr",
        "rrrrrrrrrrrrrrBBBBBBBBBBBBBBBBBrrrrrrrrrrr",
        "rrrrrrrrrrrrrrrrrrYYYYYYrrrrrrrrrrrrrrrrrr",
        "rrrrrrrrrrrrrrrrrYYYYYYYrrrrrrrrrrrrrrrrrr",
        "rrrrrrrrrrrrrrrrrYOOOOOYrrrrrrrrrrrrrrrrrr",
        "rrrrrrrrrrrrrrrrrrYOOOYrrrrrrrrrrrrrrrrrrr",
        "rrrrrrrrrrrrrrrrrrrYOYrrrrrrrrrrrrrrrrrrrr",
        "rrrrrrrrrrrrrrrrrrFFFFFrrrrrrrrrrrrrrrrrrr",
        "rrrrrrrrrrrrrrrrrFFFFFFFFrrrrrrrrrrrrrrrrr",
        "rrrrrrrrrrrrrrrrFFFFFFFFFFFrrrrrrrrrrrrrrr",
        "rrrrrrrrrrrrrrrFFFFFFFFFFFFFrrrrrrrrrrrrrr",
        "rrrrrrrrrrrrrrFFFFFFFFFFFFFFFrrrrrrrrrrrrr",
        "rrrrrrrrrrrrrFFFFFFFFFFFFFFFFFrrrrrrrrrrrr",
        "rrrrrrrrrrrrFFFFFFFFFFFFFFFFFFFrrrrrrrrrrr",
        "rrrrrrrrrrrFFFFFFFFFFFFFFFFFFFFFrrrrrrrrrr",
        "rrrrrrrrrrFFFFFFFFFFFFFFFFFFFFFFFrrrrrrrrr",
        "rrrrrrrrrFFFFFFFFFFFFFFFFFFFFFFFFFrrrrrrrr",
    )

    val colorMap = mapOf(
        'r' to Color(0xFFc32a2f), // red background
        'B' to Color(0xFF201a18), // black eyebrow
        'w' to Color(0xFFd4c5b4), // white eye
        'P' to Color(0xFF5f4739), // pupil
        'Y' to Color(0xFFf0a131), // beak yellow
        'O' to Color(0xFFd48129), // beak orange
        'F' to Color(0xFFe5d5c1), // belly feather
    )

    Canvas(modifier = modifier
        .fillMaxWidth()
        .aspectRatio(1f)) {
        val pixelSize = size.width / pixelArt[0].length
        pixelArt.forEachIndexed { y, row ->
            row.forEachIndexed { x, char ->
                colorMap[char]?.let { color ->
                    drawRect(
                        color = color,
                        topLeft = Offset(x * pixelSize, y * pixelSize),
                        size = Size(pixelSize + 1, pixelSize + 1) // +1 to avoid gaps
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AngryBirdPixelArtPreview() {
    AngryBirdPixelArt()
}