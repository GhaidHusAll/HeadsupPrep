package com.example.headsupprep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.headsupprep.databinding.ActivityMainBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var celList : ArrayList<CelebritiesItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCelebrities()
        binding.btnAdd.setOnClickListener {
            val toAddActivity = Intent(this,AddActivity::class.java)
            startActivity(toAddActivity)
        }

    }

    private fun getCelebrities(){
        celList = arrayListOf()
        val api = Client()?.requestClient()?.create(APIrequests::class.java)
        api?.fetchData()?.enqueue(object: Callback<Celebrities> {
            override fun onResponse(call: Call<Celebrities>, response: Response<Celebrities>) {
                try{
                    celList = response.body()!!
                    adapter()
                }catch (e: Exception){
                    Log.d("Main","$e")
                }
            }

            override fun onFailure(call: Call<Celebrities>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Something went wrong while reading data",Toast.LENGTH_LONG).show()
            }

        })
    }
    fun adapter(){
        binding.mainRV.adapter = AdapterMain(celList,this)
        binding.mainRV.layoutManager = LinearLayoutManager(this)
    }

    fun toEditDeleteActivity(recipeIndex: Int){
        val toDisplayActivity = Intent(this, AddActivity::class.java)
        toDisplayActivity.putExtra("name" , celList[recipeIndex].name)
        toDisplayActivity.putExtra("1" , celList[recipeIndex].taboo1)
        toDisplayActivity.putExtra("2" , celList[recipeIndex].taboo2)
        toDisplayActivity.putExtra("3" , celList[recipeIndex].taboo3)
        toDisplayActivity.putExtra("id" , celList[recipeIndex].pk)


        startActivity(toDisplayActivity)
    }
}