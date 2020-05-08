package com.mikhailgrigorev.geoquize

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
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

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                val cx: Int = show_answer_button.width / 2
                val cy: Int = show_answer_button.height / 2
                val radius:Float = show_answer_button.width.toFloat()
                val anim = ViewAnimationUtils.createCircularReveal(show_answer_button, cx, cy, radius,
                    0F
                )
                anim.addListener(object: AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        show_answer_button.visibility = View.INVISIBLE
                    }
                })
                anim.start()
            }
            else{
                show_answer_button.visibility =  View.INVISIBLE
            }

            sdk_version.text = Build.VERSION.SDK_INT.toString()
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent()
        data.putExtra("Shown", isAnswerShown)
        setResult(Activity.RESULT_OK, data)
    }


}
