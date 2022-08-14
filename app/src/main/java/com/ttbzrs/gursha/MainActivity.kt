package com.ttbzrs.gursha

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val INISTAL_TIP_PERSENT = 15

private lateinit var etBaseAmount:EditText
private lateinit var seekBarTip:SeekBar
private lateinit var tvPersentLabel:TextView
private lateinit var tvTipAmount: TextView
private lateinit var tvTotalAmount: TextView
private lateinit var tvTipDiscription: TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvPersentLabel = findViewById(R.id.tvPersentLable)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvTipDiscription = findViewById(R.id.tvTipDiscription)

        seekBarTip.progress = INISTAL_TIP_PERSENT
        tvPersentLabel.text = INISTAL_TIP_PERSENT.toString()
        updateTipDiscription(INISTAL_TIP_PERSENT)
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvPersentLabel.text = "$progress"
                computeTipAndTotal()
                updateTipDiscription(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {           }

            override fun onStopTrackingTouch(p0: SeekBar?) {            }
        })

        etBaseAmount.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG,"after text changed $p0")
                computeTipAndTotal()
            }

        })
    }

    private fun updateTipDiscription(tipPercent: Int) {
        var tipDiscription = when(tipPercent){
            in 0..9 -> "ምነው? ምነው?!"
            in 10..14 -> "ይሁና"
            in 15..19 -> "ጎሽ!"
            in 20..24 -> "አሰይ! አሰይ!"
            else -> "ዪብል የሚያስብል ነው!!!"
        }
        tvTipDiscription.text = tipDiscription

        val color = ArgbEvaluator().evaluate(
            tipPercent/ seekBarTip.max.toFloat(),
            ContextCompat.getColor(this, R.color.Red),
            ContextCompat.getColor(this, R.color.Green)
        )as Int
        tvTipDiscription.setTextColor(color)
        }
    }

    private fun computeTipAndTotal() {
        if (etBaseAmount.text.isEmpty()){
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
            return
        }
        val baseAmount = etBaseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress
        val tipAmount = baseAmount * tipPercent/100
        val totalAmount = baseAmount + tipAmount
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text = "%.2f".format(totalAmount)
    }
