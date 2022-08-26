package com.ttbzrs.gursha

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

//private const val TAG = "MainActivity"
private const val INISTAL_TIP_PERSENT = 10

private lateinit var etBaseAmount:EditText
private lateinit var seekBarTip:SeekBar
private lateinit var tvPersentLabel:TextView
private lateinit var tvTipAmount: TextView
private lateinit var tvTotalAmount: TextView
private lateinit var tvTipDiscription: TextView
private lateinit var tvAmount: TextView
private lateinit var numberOfPeople: TextView
private lateinit var ibAdd: ImageButton
private lateinit var ibSub: ImageButton

class MainActivity : AppCompatActivity() {
    private val TAG = this::class.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvPersentLabel = findViewById(R.id.tvPersentLable)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvTipDiscription = findViewById(R.id.tvTipDiscription)
        tvAmount = findViewById(R.id.tvAmount)
        numberOfPeople = findViewById(R.id.tvNumbOfPeople)
        tvAmount.text = "0.0" // computeBillSplit(tvTotalAmount.text.toString().toInt(), numberOfPeople.text.toString().toInt()).toString()
        ibAdd = findViewById(R.id.ibAdd)
        ibSub = findViewById(R.id.ibSubtract)

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

        ibAdd.setOnClickListener{

            if (numberOfPeople.text.toString().toInt() < 10){
                numberOfPeople.text = (numberOfPeople.text.toString().toInt() + 1).toString()
            }
           tvAmount.text = computeBillSplit(tvTotalAmount.text.toString().toDouble(), numberOfPeople.text.toString().toInt()).toString()
        }
        ibSub.setOnClickListener {
            if (numberOfPeople.text.toString().toInt() > 0)numberOfPeople.text = (numberOfPeople.text.toString().toInt() - 1).toString()
            tvAmount.text = computeBillSplit(tvTotalAmount.text.toString().toDouble(), numberOfPeople.text.toString().toInt()).toString()
        }


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
        val tipAmouny = baseAmount * tipPercent/100
        val totalAmount = baseAmount + tipAmouny
        tvTipAmount.text = "%.2f".format(tipAmouny)
        tvTotalAmount.text = "%.2f".format(totalAmount)
    }

    private fun computeBillSplit(total: Double, numberOfPeople: Int) :Double{
        if (total === 0.0 || numberOfPeople == 0){
            return 0.0
        }

            return total/numberOfPeople

    }
