package com.example.quizonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizonline.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var quizModelist:MutableList<QuizModel>
    lateinit var adapter:QuizListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModelist= mutableListOf()
        getDataFromFirebase()
    }
    private fun getDataFromFirebase(){
      //  dummy data

//        val listQuestionModel= mutableListOf<QuestionModel>()
//        listQuestionModel.add(QuestionModel("what is android ?", mutableListOf("Language","OS","Product","None"),"OS"))
//        listQuestionModel.add(QuestionModel("who own android ? ?", mutableListOf("Apple","Google","samsung","Microsoft"),"Google"))
//        listQuestionModel.add(QuestionModel("which assistant android uses ?", mutableListOf("Siri","Cortana","Google Assistant","Alexa"),"Google Assistant"))
//
//        quizModelist.add(QuizModel("1","Programming","All the besic programming","10",listQuestionModel))
//        quizModelist.add(QuizModel("2","Computer","All the computer question","20"))
//        quizModelist.add(QuizModel("3","Geography","Boost your geographic knowledge","10"))

//        Getting data from firebase to json file
        binding.progressBar.visibility=View.VISIBLE
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener {dataSnapshot->
                if (dataSnapshot.exists()){
                    for (snapShot in dataSnapshot.children){
                        val quizModel=snapShot.getValue(QuizModel::class.java)
                        if (quizModel != null) {
                            quizModelist.add(quizModel)
                        }
                    }
                }
                setupRecyclerView()

            }
    }

    private fun setupRecyclerView() {
        binding.progressBar.visibility=View.GONE
        adapter= QuizListAdapter(quizModelist)
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.adapter=adapter
    }
}
