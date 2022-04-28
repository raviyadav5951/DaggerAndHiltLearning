package com.techyourchance.dagger2course.questions

import com.techyourchance.dagger2course.networking.StackoverflowApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//Naming convention comes from clean code architecture
class FetchQuestionDetailUseCase(private val stackoverflowApi: StackoverflowApi) {

    sealed class Result(){
        class Success(val questionBody: String) :Result()
        object Failure:Result()
    }


    suspend fun fetchQuestionDetails(questionId:String):Result{

        return withContext(Dispatchers.IO) {

            try {
                val response = stackoverflowApi.questionDetails(questionId)
                if (response.isSuccessful && response.body() != null) {
                    val questionBody = response.body()!!.question.body
                    return@withContext Result.Success(questionBody)
                } else {
                    return@withContext Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext Result.Failure
                } else {
                    throw t
                }
            }
        }
    }
}