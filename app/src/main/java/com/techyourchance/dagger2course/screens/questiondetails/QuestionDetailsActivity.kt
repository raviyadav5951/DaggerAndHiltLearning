package com.techyourchance.dagger2course.screens.questiondetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.dagger2course.MyApplication
import com.techyourchance.dagger2course.questions.FetchQuestionDetailUseCase
import com.techyourchance.dagger2course.screens.common.ScreensNavigator
import com.techyourchance.dagger2course.screens.common.dialogs.DialogManager
import kotlinx.coroutines.*

class QuestionDetailsActivity : AppCompatActivity(), QuestionDetailMvc.DetailListener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)


    private lateinit var viewMvc: QuestionDetailMvc
    private lateinit var questionId: String

    private lateinit var fetchQuestionDetailUseCase: FetchQuestionDetailUseCase
    private lateinit var dialogManager: DialogManager

    private lateinit var screensNavigator: ScreensNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewMvc= QuestionDetailMvc(layoutInflater,null)
        setContentView(viewMvc.rootView)
        // retrieve question ID passed from outside
        questionId = intent.extras!!.getString(EXTRA_QUESTION_ID)!!

        fetchQuestionDetailUseCase= FetchQuestionDetailUseCase((application as MyApplication).stackoverflowApi)

        dialogManager= DialogManager(supportFragmentManager)

        screensNavigator= ScreensNavigator(this)

    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)

        fetchQuestionDetails()
    }

    override fun onStop() {
        super.onStop()
        viewMvc.unregisterListener(this)

        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchQuestionDetails() {
        coroutineScope.launch {
            viewMvc.showProgressIndication()
            try {
                val result=fetchQuestionDetailUseCase.fetchQuestionDetails(questionId)
                when(result)
                {
                    is FetchQuestionDetailUseCase.Result.Success->{
                        viewMvc.setQuestionBody(result.questionBody)

                    }

                    is FetchQuestionDetailUseCase.Result.Failure->{
                        onFetchFailed()
                    }
                }

            }  finally {
                viewMvc.hideProgressIndication()
            }

        }
    }

    private fun onFetchFailed() {
        dialogManager.showErrorDialogFragment()
    }



    companion object {
        const val EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID"
        fun start(context: Context, questionId: String) {
            val intent = Intent(context, QuestionDetailsActivity::class.java)
            intent.putExtra(EXTRA_QUESTION_ID, questionId)
            context.startActivity(intent)
        }
    }

    override fun onBackPressClicked() {
        screensNavigator.navigateBack()
    }
}