package com.bahram.weather

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather.adapter.BriefAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    lateinit var frameMain: FrameLayout
    lateinit var rlMain: RelativeLayout
    lateinit var etGetCityName: EditText
    lateinit var rvBrief: RecyclerView

    var newList: ArrayList<DataForRecycleView1?>? = null
    lateinit var briefAdapter: BriefAdapter

    //    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)

        frameMain = findViewById(R.id.frame_main)
        rlMain = findViewById(R.id.rl_main)
        etGetCityName = findViewById(R.id.et_get_city_name)
        rvBrief = findViewById(R.id.rv_brief)

        loadData()
        rvBrief.visibility = View.VISIBLE
        briefAdapter = BriefAdapter(this, newList)
        rvBrief.adapter = briefAdapter
        rvBrief.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//            briefAdapter.notifyDataSetChanged()


//        if (newList == null) {
//            newList = ArrayList()
//        }

        //        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//        actionBar?.hide()
        val cityName1 = intent.getStringExtra("cityName")
        val currentTemp1 = intent.getStringExtra("currentTemp")
        val description1 = intent.getStringExtra("description")
        val tempMax1 = intent.getStringExtra("tempMax")
        val tempMin1 = intent.getStringExtra("tempMin")

//        val myList = listOf(cityName1, currentTemp1, description1, tempMax1, tempMin1)


        newList?.add(DataForRecycleView1(cityName1, currentTemp1, description1, tempMax1, tempMin1))
        Log.i("newli", "$newList")
        Log.i("new", "$cityName1")
        saveData(newList)


        briefAdapter.notifyDataSetChanged()

        etGetCityName.setOnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                val view = this.currentFocus
                if (view != null) {
                    val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    etGetCityName.clearFocus()

                }

                supportActionBar?.setDisplayShowTitleEnabled(false)
                supportActionBar?.hide();

                rlMain.visibility = View.INVISIBLE
                frameMain.visibility = View.VISIBLE

                val bundle = Bundle()
                bundle.putString("YourKey2", etGetCityName.text.toString())

                val fragment = FragmentSplash()
                fragment.arguments = bundle
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_main, fragment, fragment.toString())
                fragmentTransaction.commit()

                true
            } else false
        }
        Log.i("final list","$newList")



    }


    fun saveData(data: ArrayList<DataForRecycleView1?>?) {
        val editor = sharedPreferences.edit()
        val json: String = Gson().toJson(data)
        editor.putString("list", json)
        editor.apply()
        Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_LONG)
            .show()
    }

    fun loadData(): ArrayList<DataForRecycleView1?>? {
        val json = sharedPreferences.getString("list", null)
        if (json == "") return null
        val type = object : TypeToken<ArrayList<DataForRecycleView1>>() {}.type

        newList = Gson().fromJson<Any>(json, type) as ArrayList<DataForRecycleView1?>?
        if (newList == null) {
            newList = ArrayList()
        }
        Log.i("wer", "$newList")
        return newList
    }

    data class DataForRecycleView1(
        val cityName: String?,
        val currentTemp: String?,
        val description: String?,
        val tempMin: String?,
        val tempMax: String?,
    )

    fun navigate(fragment: FragmentSplash) {
        var ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_main, fragment)
        ft.commit()
    }


}



