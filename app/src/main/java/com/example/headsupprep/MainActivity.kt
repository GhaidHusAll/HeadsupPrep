package com.example.headsupprep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.headsupprep.apiModel.APIrequests
import com.example.headsupprep.apiModel.Celebrities
import com.example.headsupprep.apiModel.CelebritiesItem
import com.example.headsupprep.apiModel.Client
import com.example.headsupprep.databinding.ActivityMainBinding
import com.example.headsupprep.roomModel.CelebritiesDB
import com.example.headsupprep.roomModel.CelebritiesRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var celList : List<CelebritiesRoom>
    private lateinit var myAdapter: AdapterMain
    private val helper by lazy { DatabaseHelper(this) }
    private val dao by lazy { CelebritiesDB.getDB(this).dao() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //getCelebrities()
        //getLocalCelebrities()
        getLocalRoomCelebrities()
        binding.btnAdd.setOnClickListener {
            val toAddActivity = Intent(this,AddActivity::class.java)
            startActivity(toAddActivity)
        }

        adapter()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                var searchList: List<CelebritiesRoom> = celList.filter { s -> s.name == query }
                if (searchList.isEmpty()){
                    Toast.makeText(this@MainActivity,"there is no data matching the search $query",Toast.LENGTH_LONG).show()
                }else{
                    myAdapter.update(searchList)
                }
                return false
            }
        })

    }

    private fun getCelebrities(){
        celList = arrayListOf()
        val api = Client()?.requestClient()?.create(APIrequests::class.java)
        api?.fetchData()?.enqueue(object: Callback<Celebrities> {
            override fun onResponse(call: Call<Celebrities>, response: Response<Celebrities>) {
                try{
                   // celList = response.body()!!
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
    private fun getLocalCelebrities(){
//        celList = arrayListOf()
      //  celList = helper.read()
//        adapter()
    }
    private fun getLocalRoomCelebrities(){
        CoroutineScope(IO).launch {
            val returnData = async{dao.getCele()}.await()
            if (returnData.isNotEmpty()){
                celList = returnData as ArrayList<CelebritiesRoom>
                withContext(Main){
                    myAdapter.update(celList)
                }
            }
        }
    }


    fun adapter(){
        celList = listOf()
        myAdapter = AdapterMain(celList,this)
        binding.mainRV.adapter = myAdapter
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