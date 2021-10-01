package com.voss.seekbar_pc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SeekBarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(SeekBarViewModel::class.java)
        viewModel.percent.observe(this, Observer { percent ->
            percentRate_Text.setText("折扣金額($percent)%")
            percent_edittext.setText("${percent}%")
            seekBar.setProgress(percent)
        })
        viewModel.price.observe(this, Observer { price ->
            orgPrice_text.setText("$${price}")
            price_edittext.setText("$${price}")
        })
        viewModel.discount.observe(this, Observer {
            discountTotal_text.setText(String.format("%.2f$", it))
        })
        viewModel.total.observe(this, Observer { total ->
            discountPrice_text.setText(String.format("%.2f$", total))

        })
        setSeekbarView()
        clear_but.setOnClickListener {
            clearEditText()
        }

        price_edittext.setOnEditorActionListener { textView, actionid, keyEvent ->
            viewModel.price.value =
                price_edittext.text.toString().removePrefix("$").toIntOrNull() ?: 0
            if (actionid == EditorInfo.IME_ACTION_NEXT) {
                calculateDiscount()

            }
            price_edittext.clearFocus()
            false
            // 2021/09/28 筆記
            // 輸入完price 後 無法跑動 折扣金額 跟 折扣後金額  但在PercentEdit 卻可以執行
            // 差別在有實作seekBar的設定 啟動了 onProgressChanged 跟著變動折扣
        }
        percent_edittext.setOnEditorActionListener { textView, actionid, keyEvent ->
            if (actionid == EditorInfo.IME_ACTION_DONE) {
                viewModel.percent.value =
                    percent_edittext.text.toString().removePrefix("%").toIntOrNull() ?: 0
                calculateDiscount()
            }else if (actionid == EditorInfo.IME_ACTION_NEXT) {
                viewModel.percent.value =
                    percent_edittext.text.toString().removePrefix("%").toIntOrNull() ?: 0
                calculateDiscount()
            }
            percent_edittext.clearFocus()
            false
        }

    }

    private fun setSeekbarView() {
        val seekbar = findViewById<SeekBar>(R.id.seekBar)
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, postion: Int, p2: Boolean) {
                viewModel.value.value =
                    price_edittext.text.toString().removePrefix("$").toFloatOrNull() ?: 0f
                percentRate_Text.text = "折扣金額($postion)%"
                percent_edittext.setText("${postion}%")
                calculateDiscount()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

    }

    private fun clearEditText() {
        seekBar.progress = 0
        viewModel.price.value = 0
        viewModel.value.value = 0f
        calculateDiscount()
    }


    private fun calculateDiscount() {
        viewModel.discount.value = seekBar.progress * viewModel.value.value!! / 100
        viewModel.total.value = viewModel.value.value!! - viewModel.discount.value!!
    }
}