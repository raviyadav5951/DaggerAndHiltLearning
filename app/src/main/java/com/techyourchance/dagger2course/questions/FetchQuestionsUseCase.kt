package com.techyourchance.dagger2course.questions

import com.techyourchance.dagger2course.Constants
import com.techyourchance.dagger2course.networking.StackoverflowApi
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Naming convention comes from clean code architecture
class FetchQuestionsUseCase {

    sealed class Result(){
        class Success(val questions: List<Question>) :Result()
        class SuccessBody(val questionBody: String) :Result()
        object Failure:Result()
    }

    // init retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val stackoverflowApi: StackoverflowApi = retrofit.create(StackoverflowApi::class.java)


    suspend fun fetchLatestQuestions():Result {
      return  withContext(Dispatchers.IO) {
            try {
                val response = stackoverflowApi.lastActiveQuestions(20)
                if (response.isSuccessful && response.body() != null) {
                   return@withContext Result.Success(response.body()!!.questions)
                } else {
                    return@withContext Result.Failure
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext Result.Failure
                }
                else{
                    throw t
                }
            }
        }
    }

    suspend fun fetchQuestionDetails(questionId:String):Result{

        return withContext(Dispatchers.IO) {

            try {
                val response = stackoverflowApi.questionDetails(questionId)
                if (response.isSuccessful && response.body() != null) {
                    val questionBody = response.body()!!.question.body
                    return@withContext Result.SuccessBody(questionBody)
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