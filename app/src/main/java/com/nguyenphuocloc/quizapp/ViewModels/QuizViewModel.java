package com.nguyenphuocloc.quizapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nguyenphuocloc.quizapp.Models.QuestionList;
import com.nguyenphuocloc.quizapp.Repository.QuizRepository;

public class QuizViewModel extends ViewModel {

    QuizRepository repository =  new QuizRepository();

    LiveData<QuestionList> questionListLiveData;

    public QuizViewModel(){
        questionListLiveData = repository.getQuestionsFromApi();
    }

    public LiveData<QuestionList> getQuestionListLiveData(){
        return questionListLiveData;
    }



}
