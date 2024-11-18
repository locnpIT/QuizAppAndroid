package com.nguyenphuocloc.quizapp.Retrofit;

import com.nguyenphuocloc.quizapp.Models.QuestionList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuestionAPI {


    @GET("/api/questions")
    Call<QuestionList> getQuestions();


}
