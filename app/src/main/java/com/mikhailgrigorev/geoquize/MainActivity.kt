package com.mikhailgrigorev.geoquize

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val mQuestionBank = arrayOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var mCurrentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        question_text_view.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size;
            updateQuestion();
        }

        true_button.setOnClickListener {
            checkAnswer(true);
        }
        false_button.setOnClickListener {
            checkAnswer(false);
        }
        next_button.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size;
            updateQuestion();
        }
        prev_button.setOnClickListener {
            mCurrentIndex = (mCurrentIndex - 1);
            if (mCurrentIndex == -1){
                mCurrentIndex = mQuestionBank.size - 1
            }
            updateQuestion();
        }
        updateQuestion();
    }

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].getTextResId()
        question_text_view.setText(question)
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue
        var messageResId = 0
        messageResId = if (userPressedTrue == answerIsTrue) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }


}