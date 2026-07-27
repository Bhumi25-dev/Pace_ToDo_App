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
val connection : ImageVector
  get() {
    if (_person_2 != null) {
      return _person_2!!
    }
    _person_2 =
      ImageVector.Builder(
          name = "person_2",
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
            moveTo(9.78f, 12f)
            quadToRelative(-0.9f, 0f, -1.5f, -0.68f)
            reflectiveQuadTo(7.8f, 9.75f)
            lineTo(8.13f, 7.3f)
            quadTo(8.33f, 5.88f, 9.43f, 4.94f)
            reflectiveQuadTo(12f, 4f)
            reflectiveQuadToRelative(2.58f, 0.94f)
            reflectiveQuadToRelative(1.3f, 2.36f)
            lineTo(16.2f, 9.75f)
            quadToRelative(0.13f, 0.9f, -0.47f, 1.57f)
            reflectiveQuadTo(14.23f, 12f)
            horizontalLineTo(9.78f)
            close()
            moveToRelative(0f, -2f)
            horizontalLineToRelative(4.45f)
            lineTo(13.9f, 7.6f)
            quadTo(13.8f, 6.9f, 13.26f, 6.45f)
            reflectiveQuadTo(12f, 6f)
            quadTo(11.28f, 6f, 10.74f, 6.45f)
            reflectiveQuadTo(10.1f, 7.6f)
            lineTo(9.78f, 10f)
            close()
            moveTo(12f, 10f)
            close()
            moveTo(4f, 20f)
            verticalLineTo(17.2f)
            quadTo(4f, 16.35f, 4.44f, 15.64f)
            quadTo(4.88f, 14.93f, 5.6f, 14.55f)
            quadTo(7.15f, 13.77f, 8.75f, 13.39f)
            reflectiveQuadTo(12f, 13f)
            reflectiveQuadToRelative(3.25f, 0.39f)
            reflectiveQuadToRelative(3.15f, 1.16f)
            quadToRelative(0.72f, 0.38f, 1.16f, 1.09f)
            reflectiveQuadTo(20f, 17.2f)
            verticalLineTo(20f)
            horizontalLineTo(4f)
            close()
            moveTo(6f, 18f)
            horizontalLineTo(18f)
            verticalLineTo(17.2f)
            quadToRelative(0f, -0.27f, -0.14f, -0.5f)
            quadTo(17.73f, 16.48f, 17.5f, 16.35f)
            quadTo(16.15f, 15.68f, 14.78f, 15.34f)
            reflectiveQuadTo(12f, 15f)
            reflectiveQuadTo(9.23f, 15.34f)
            reflectiveQuadTo(6.5f, 16.35f)
            quadTo(6.28f, 16.48f, 6.14f, 16.7f)
            quadTo(6f, 16.93f, 6f, 17.2f)
            verticalLineTo(18f)
            close()
            moveToRelative(6f, 0f)
            close()
          }
        }
        .build()
    return _person_2!!
  }

private var _person_2: ImageVector? = null
