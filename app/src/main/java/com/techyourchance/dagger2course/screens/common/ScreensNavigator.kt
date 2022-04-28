package com.techyourchance.dagger2course.screens.common

import android.app.Activity
import android.content.Context
import com.techyourchance.dagger2course.screens.questiondetails.QuestionDetailsActivity

class ScreensNavigator(private val activity: Activity) {

    fun navigateBack(){
        activity.onBackPressed()
    }
    fun navigateToQuestionDetail(questionID:String){
        QuestionDetailsActivity.start(activity, questionID)

    }

}