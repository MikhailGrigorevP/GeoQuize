package com.mikhailgrigorev.geoquize

import android.app.Activity
import android.content.ClipData.newIntent
import android.content.Intent
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
    private var mTips = 0
    private var REQUEST_CODE_CHEAT = 0
    private var mIsCheater: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("QuizActivity", "onCreate(Bundle) called")

        tips_text_view.text = (3 - mTips).toString()

        if(savedInstanceState != null){
            mCurrentIndex=savedInstanceState.getInt("index", 0)
            mCorrectCount=savedInstanceState.getInt("count", 0)
            mVoted=savedInstanceState.getInt("voted", 0)
            mVoted=savedInstanceState.getInt("tips", 0)
        }

        question_text_view.setOnClickListener {
            mIsCheater = false
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
            mIsCheater = false
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }
        cheat_button.setOnClickListener{
            if (mTips < 3){
                mTips += 1
                tips_text_view.text = (3 - mTips).toString()
                val answerIsTrue: Boolean = mQuestionBank[mCurrentIndex].isAnswerTrue
                val intent = Intent(this, CheatActivity::class.java)
                intent.putExtra("isTrue", answerIsTrue)
                startActivityForResult(intent, REQUEST_CODE_CHEAT)
            }
            else {
                Toast.makeText(this, "No tips left" , Toast.LENGTH_SHORT)
                    .show()
            }
        }
        prev_button.setOnClickListener {
            mCurrentIndex = (mCurrentIndex - 1)
            if (mCurrentIndex == -1) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK){
            return
        }
        if (requestCode == 0){
            if (data == null){
                return
            }
            mIsCheater = data.getBooleanExtra("Shown", false)
        }

    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue
        val answered  = mQuestionBank[mCurrentIndex].isAnswered
        var messageResId = R.string.voted_toast
        if (!answered){
            mQuestionBank[mCurrentIndex].isAnswered = true
            mVoted += 1
            if (mIsCheater){
                messageResId = R.string.judgment_toast
                mCorrectCount += 1
            }
            else{
                if (userPressedTrue == answerIsTrue) {
                    messageResId = R.string.correct_toast
                    mCorrectCount += 1
                } else {
                    messageResId = R.string.incorrect_toast
                }
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
        outState.putInt("voted", mVoted)
        outState.putInt("tips", mTips)
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