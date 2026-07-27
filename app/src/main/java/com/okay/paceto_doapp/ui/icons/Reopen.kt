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
val reopen_window: ImageVector
  get() {
    if (_reopen_window != null) {
      return _reopen_window!!
    }
    _reopen_window =
      ImageVector.Builder(
          name = "reopen_window",
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
            pathFillType = PathFillType.NonZero,
          ) {
            moveTo(4f, 20f)
            quadTo(3.18f, 20f, 2.59f, 19.41f)
            reflectiveQuadTo(2f, 18f)
            verticalLineTo(6f)
            quadTo(2f, 5.18f, 2.59f, 4.59f)
            reflectiveQuadTo(4f, 4f)
            horizontalLineTo(20f)
            quadToRelative(0.83f, 0f, 1.41f, 0.59f)
            quadTo(22f, 5.18f, 22f, 6f)
            verticalLineToRelative(7f)
            horizontalLineTo(20f)
            verticalLineTo(8f)
            horizontalLineTo(4f)
            verticalLineTo(18f)
            horizontalLineToRelative(8f)
            verticalLineToRelative(2f)
            horizontalLineTo(4f)
            close()
            moveToRelative(15f, 4f)
            quadToRelative(-1.82f, 0f, -3.19f, -1.14f)
            reflectiveQuadTo(14.1f, 20f)
            horizontalLineToRelative(1.55f)
            quadToRelative(0.33f, 1.1f, 1.24f, 1.8f)
            reflectiveQuadTo(19f, 22.5f)
            quadToRelative(1.45f, 0f, 2.48f, -1.02f)
            reflectiveQuadTo(22.5f, 19f)
            reflectiveQuadTo(21.48f, 16.52f)
            reflectiveQuadTo(19f, 15.5f)
            quadToRelative(-0.72f, 0f, -1.35f, 0.26f)
            reflectiveQuadToRelative(-1.1f, 0.74f)
            horizontalLineTo(18f)
            verticalLineTo(18f)
            horizontalLineTo(14f)
            verticalLineTo(14f)
            horizontalLineToRelative(1.5f)
            verticalLineToRelative(1.42f)
            quadToRelative(0.68f, -0.65f, 1.57f, -1.04f)
            quadTo(17.98f, 14f, 19f, 14f)
            quadToRelative(2.08f, 0f, 3.54f, 1.46f)
            reflectiveQuadTo(24f, 19f)
            reflectiveQuadToRelative(-1.46f, 3.54f)
            reflectiveQuadTo(19f, 24f)
            close()
          }
        }
        .build()
    return _reopen_window!!
  }

private var _reopen_window: ImageVector? = null
