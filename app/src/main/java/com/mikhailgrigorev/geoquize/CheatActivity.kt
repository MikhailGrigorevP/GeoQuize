package com.mikhailgrigorev.geoquize

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cheat.*

class CheatActivity : AppCompatActivity() {

    fun wasAnswerShown(result: Intent): Boolean {
        return result.getBooleanExtra("isTrue", false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        val mAnswerIsTrue: Boolean = intent.getBooleanExtra("isTrue", false)

        show_answer_button.setOnClickListener{
            if(mAnswerIsTrue){
                answer_text_view.setText(R.string.true_button)
            }
            else{
                answer_text_view.setText(R.string.false_button)
            }
            setAnswerShownResult(true)
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent()
        data.putExtra("Shown", isAnswerShown)
        setResult(Activity.RESULT_OK, data)
    }


}
