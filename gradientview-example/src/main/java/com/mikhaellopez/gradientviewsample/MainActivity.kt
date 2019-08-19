package com.mikhaellopez.gradientviewsample

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.larswerkman.lobsterpicker.OnColorListener
import com.larswerkman.lobsterpicker.sliders.LobsterShadeSlider
import com.mikhaellopez.gradientview.GradientView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shadeSliderColorEnd.shadePosition = 2

        shadeSliderColorStart.onColorChanged { gradientView.start = it }
        seekBarAlphaStart.onProgressChanged { gradientView.alphaStart = it / 100f }
        shadeSliderColorEnd.onColorChanged { gradientView.end = it }
        seekBarAlphaEnd.onProgressChanged { gradientView.alphaEnd = it / 100f }

        btnChangeDirection.setOnClickListener {
            btnChangeDirection.rotation = btnChangeDirection.rotation - 90
            gradientView.direction = when (gradientView.direction) {
                GradientView.GradientDirection.LEFT_TO_RIGHT -> GradientView.GradientDirection.BOTTOM_TO_TOP
                GradientView.GradientDirection.BOTTOM_TO_TOP -> GradientView.GradientDirection.RIGHT_TO_LEFT
                GradientView.GradientDirection.RIGHT_TO_LEFT -> GradientView.GradientDirection.TOP_TO_BOTTOM
                GradientView.GradientDirection.TOP_TO_BOTTOM -> GradientView.GradientDirection.LEFT_TO_RIGHT
            }
        }
    }

    //region Extensions
    private fun SeekBar.onProgressChanged(onProgressChanged: (Int) -> Unit) {
        setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                onProgressChanged(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Nothing
            }
        })
    }

    private fun LobsterShadeSlider.onColorChanged(onColorChanged: (Int) -> Unit) {
        addOnColorListener(object : OnColorListener {
            override fun onColorChanged(color: Int) {
                onColorChanged(color)
            }

            override fun onColorSelected(color: Int) {
                // Nothing
            }
        })
    }
    //endregion

}