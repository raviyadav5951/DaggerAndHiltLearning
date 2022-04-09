package com.techyourchance.dagger2course.screens.questiondetails

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.techyourchance.dagger2course.R
import com.techyourchance.dagger2course.screens.common.toolbar.MyToolbar
import com.techyourchance.dagger2course.screens.questionslist.QuestionsListViewMvc

class QuestionDetailMvc(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) {

    private val toolbar: MyToolbar
    private val swipeRefresh: SwipeRefreshLayout
    private val txtQuestionBody: TextView

    var rootView: View? = null
    private val context: Context get() = rootView!!.context


    fun <T : View> findViewById(@IdRes id: Int): T {
        return rootView!!.findViewById<T>(id)
    }

    interface DetailListener {
        fun onBackPressClicked()
    }

    private val listeners = HashSet<DetailListener>()

    fun registerListener(listener: DetailListener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: DetailListener) {
        listeners.remove(listener)
    }


    init {

        rootView = layoutInflater.inflate(R.layout.layout_question_details, parent, false)
        txtQuestionBody = findViewById(R.id.txt_question_body)

        // init toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener {
            for (listener in listeners) {
                listener.onBackPressClicked()
            }
        }

        // init pull-down-to-refresh (used as a progress indicator)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false
    }

    fun setQuestionBody(questionBody: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtQuestionBody.text = Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            txtQuestionBody.text = Html.fromHtml(questionBody)
        }
    }

     fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

     fun hideProgressIndication() {
        swipeRefresh.isRefreshing = false
    }

}