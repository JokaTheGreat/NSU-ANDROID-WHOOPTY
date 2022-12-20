package com.example.whoopty.utils

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import com.example.whoopty.R

class ShaderFactory {
    companion object { //TODO: может заменить синглтоном? пробрасывать цвета вместо контекста?
        fun createRedGradientShader(activityContext: Context): LinearGradient {
            return LinearGradient(
                0.0f,
                0.0f,
                0.0f,
                110.0f,

                intArrayOf(
                    activityContext.getColor(R.color.red_gradient_start),
                    activityContext.getColor(R.color.red_gradient_end)
                ),
                null,
                Shader.TileMode.CLAMP
            )
        }
    }
}