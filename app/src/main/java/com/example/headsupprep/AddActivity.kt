package com.example.headsupprep

import android.R
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.headsupprep.databinding.ActivityAddBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.DialogInterface






class AddActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddBinding
    private lateinit var name : String
    private  var id = -1
    private lateinit var tobo1 : String
    private lateinit var tobo2 : String
    private lateinit var tobo3 : String

    private val helper by lazy { DatabaseHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        name = intent.getStringExtra("name").toString()
        tobo1 = intent.getStringExtra("1").toString()
        tobo2 = intent.getStringExtra("2").toString()
        tobo3 = intent.getStringExtra("3").toString()
        id = intent.getIntExtra("id",-1)
        if ( id > 0){
            binding.btnAdd.isVisible = false
            binding.btnDelete.isVisible = true
            binding.btnEdit.isVisible = true
            setInformation()
        }
        binding.btnAdd.setOnClickListener {
            if(binding.etName.text.isEmpty() || binding.et1.text.isEmpty() || binding.et2.text.isEmpty() || binding.et3.text.isEmpty()){
                //fill
                Toast.makeText(this,"please fill all the fields",Toast.LENGTH_LONG).show()
            }else {
                alert()
            }
        }
        binding.btnEdit.setOnClickListener {
            editData()
        }
        binding.btnDelete.setOnClickListener {
            deleteData()
        }

    }
    private fun setInformation(){
        binding.etName.setText(name)
        binding.et1.setText(tobo1)
        binding.et2.setText(tobo2)
        binding.et3.setText(tobo3)
    }
    private fun addNewCelebrities(){
        val myAPI = Client().requestClient()?.create(APIrequests::class.java)

            myAPI?.addData(CelebritiesItem(binding.etName.text.toString(),0,
                binding.et1.text.toString(),binding.et2.text.toString(),
                binding.et3.text.toString()))?.enqueue(object: Callback<CelebritiesItem>{
                override fun onResponse(call: Call<CelebritiesItem>, response: Response<CelebritiesItem>) {
                    Toast.makeText(this@AddActivity,"Celebrities added successfully global",Toast.LENGTH_LONG).show()
                    toMainActivity()
                }

                override fun onFailure(call: Call<CelebritiesItem>, t: Throwable) {
                    Toast.makeText(this@AddActivity,"something went wrong",Toast.LENGTH_LONG).show()

                }

            })


    }
    private fun deleteData(){
        val api = Client().requestClient()?.create(APIrequests::class.java)
        api?.deleteData(id)?.enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@AddActivity,"delete celebrities successfully",Toast.LENGTH_LONG).show()
                toMainActivity()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@AddActivity,"something went wrong not able to delete celebrities",Toast.LENGTH_LONG).show()

            }

        } )
    }
    private fun editData(){
        val api = Client().requestClient()?.create(APIrequests::class.java)
        api?.updateData(id , CelebritiesItem(binding.etName.text.toString(),id,
            binding.et1.text.toString(),binding.et2.text.toString(),
            binding.et3.text.toString()))?.enqueue(object: Callback<CelebritiesItem>{
            override fun onResponse(call: Call<CelebritiesItem>, response: Response<CelebritiesItem>) {
                Toast.makeText(this@AddActivity,"edited celebrities successfully",Toast.LENGTH_LONG).show()
                toMainActivity()

            }

            override fun onFailure(call: Call<CelebritiesItem>, t: Throwable) {
                Toast.makeText(this@AddActivity,"something went wrong not able to edit celebrities",Toast.LENGTH_LONG).show()
            }

        } )
    }

    private fun alert(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Celebrity")
        builder.setMessage("Add new Celebrity to?")
        builder.setNegativeButton("Local") { dialog, which ->
            helper.saveData(
                binding.etName.text.toString(), binding.et1.text.toString(),
                binding.et2.text.toString(), binding.et3.text.toString())
            Toast.makeText(this@AddActivity, "Celebrities added successfully local ", Toast.LENGTH_LONG).show()
            toMainActivity()
        }

        builder.setNeutralButton("Global") { dialog, which ->
            addNewCelebrities()
        }
        builder.show()
    }

    fun toMainActivity(){
        val toMainActivity = Intent(this,MainActivity::class.java)
        startActivity(toMainActivity)
    }

}