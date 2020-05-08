package com.mikhailgrigorev.geoquize

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val mQuestionBank = arrayOf(
        Question(R.string.question_australia, true, isAnswered = false),
        Question(R.string.question_oceans, true, isAnswered = false),
        Question(R.string.question_mideast, false, isAnswered = false),
        Question(R.string.question_africa, false, isAnswered = false),
        Question(R.string.question_americas, true, isAnswered = false),
        Question(R.string.question_asia, true, isAnswered = false)
    )

    private var mCurrentIndex = 0
    private var mCorrectCount = 0
    private var mVoted = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("QuizActivity", "onCreate(Bundle) called")

        if(savedInstanceState != null){
            mCurrentIndex=savedInstanceState.getInt("index", 0)
            mCorrectCount=savedInstanceState.getInt("count", 0)
            mVoted=savedInstanceState.getInt("voted", 0)
        }

        question_text_view.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }

        true_button.setOnClickListener {
            checkAnswer(true)
        }
        false_button.setOnClickListener {
            checkAnswer(false)
        }
        next_button.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }
        prev_button.setOnClickListener {
            mCurrentIndex = (mCurrentIndex - 1)
            if (mCurrentIndex == -1){
                mCurrentIndex = mQuestionBank.size - 1
            }
            updateQuestion()
        }
        updateQuestion()
    }

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].textResId
        question_text_view.setText(question)
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue
        val answered  = mQuestionBank[mCurrentIndex].isAnswered
        var messageResId = R.string.voted_toast
        if (!answered){
            if (userPressedTrue == answerIsTrue) {
                mQuestionBank[mCurrentIndex].isAnswered = true
                messageResId = R.string.correct_toast
                mCorrectCount += 1
                mVoted += 1
            } else {
                mQuestionBank[mCurrentIndex].isAnswered = true
                messageResId = R.string.incorrect_toast
                mVoted += 1
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

        if (mVoted == mQuestionBank.size){
            val result: String = (mCorrectCount.toDouble()/mVoted).toString().plus("%")
            Toast.makeText(this, result , Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("QuizActivity", "onStart called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i("QuizActivity", "onSaveInstanceState")
        outState.putInt("index", mCurrentIndex)
        outState.putInt("count", mCorrectCount)
        outState.putInt("voted", mCorrectCount)
    }

    override fun onResume() {
        super.onResume()
        Log.d("QuizActivity", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("QuizActivity", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("QuizActivity", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("QuizActivity", "onDestroy called")
    }

}