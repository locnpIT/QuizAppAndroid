package com.nguyenphuocloc.quizapp;

import static com.nguyenphuocloc.quizapp.R.layout.activity_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nguyenphuocloc.quizapp.Models.Question;
import com.nguyenphuocloc.quizapp.Models.QuestionList;
import com.nguyenphuocloc.quizapp.ViewModels.QuizViewModel;
import com.nguyenphuocloc.quizapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    QuizViewModel quizViewModel;
    List<Question> questionList;

    static int result = 0;
    static int totalQuestions = 0;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Data biding
        binding = DataBindingUtil.setContentView(
                this, activity_main
        );

        // Resetting the Scores;
        result = 0;
        totalQuestions = 0;

        //Creating an instance of "quizviewmodel"
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        // display the first question
        DisplayFirstQuestion();

        binding.btnNext.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DisplayNextQuestion();
                    }
                }
        );



    }


    public void DisplayFirstQuestion(){
        //observing livedata from a viewmodel
        quizViewModel.getQuestionListLiveData().observe(this, new Observer<QuestionList>() {
            @Override
            public void onChanged(QuestionList questions) {
                // Called when the data inside LIVEDATA change
                questionList = questions;

                binding.txtQuestion.setText("Question 1: " + questions.get(0).getQuestion());
                binding.radio1.setText(questions.get(0).getOption1());
                binding.radio2.setText(questions.get(0).getOption2());
                binding.radio3.setText(questions.get(0).getOption3());
                binding.radio4.setText(questions.get(0).getOption4());

            }
        });
    }

    public void DisplayNextQuestion(){

        //Direct the ser to the Result activity
        if(binding.btnNext.getText().equals("Finish")){
            Intent i = new Intent(MainActivity.this, ResultActivity.class);
            startActivity(i);
            finish();
        }


        //Display the question
        int selectedOption = binding.radioGroup.getCheckedRadioButtonId();
        if(selectedOption!= -1){
            RadioButton radioButton = findViewById(selectedOption);

            //More questions to display??
            if((questionList.size() - i) > 0) {
                //Getting the number of questions
                totalQuestions = questionList.size();
                //check if the chosen option is correct
                if(radioButton.getText().toString().equals(
                    questionList.get(i).getCorrectOption()
                )){
                    result++;
                    binding.txtResult.setText("Correct Answer " + result);
                }
                if(i == 0){
                    i++;
                }
                //Displaying the next question
                binding.txtQuestion.setText("Question " +(i+1) + " : " + questionList.get(i).getQuestion());

                binding.radio1.setText(questionList.get(i).getOption1());
                binding.radio2.setText(questionList.get(i).getOption2());
                binding.radio3.setText(questionList.get(i).getOption3());
                binding.radio4.setText(questionList.get(i).getOption4());

                //Check if it is the last question
                if(i == (questionList.size() - 1)){
                    binding.btnNext.setText("Finish");

                }
                binding.radioGroup.clearCheck();
                i++;

            }else{
                if(radioButton.getText().toString().equals(
                        questionList.get(i-1).getCorrectOption()
                )){
                    result++;
                    binding.txtResult.setText("Correct answers : " + result);
                }

            }

        }else{
            Toast.makeText(this, "You need make a selection", Toast.LENGTH_SHORT).show();
        }


    }
}