package com.okay.paceto_doapp.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val equalizer: ImageVector
  get() {
    if (_equalizer != null) {
      return _equalizer!!
    }
    _equalizer =
      ImageVector.Builder(
          name = "equalizer",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(
            fill = SolidColor(Color.Black),
            fillAlpha = 1f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.Companion.NonZero,
          ) {
            moveTo(4f, 20f)
            verticalLineTo(12f)
            horizontalLineTo(8f)
            verticalLineToRelative(8f)
            horizontalLineTo(4f)
            close()
            moveToRelative(6f, 0f)
            verticalLineTo(4f)
            horizontalLineToRelative(4f)
            verticalLineTo(20f)
            horizontalLineTo(10f)
            close()
            moveToRelative(6f, 0f)
            verticalLineTo(9f)
            horizontalLineToRelative(4f)
            verticalLineTo(20f)
            horizontalLineTo(16f)
            close()
          }
        }
        .build()
    return _equalizer!!
  }

private var _equalizer: ImageVector? = null
