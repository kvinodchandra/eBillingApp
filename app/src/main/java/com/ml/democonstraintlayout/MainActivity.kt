package com.ml.democonstraintlayout

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ml.democonstraintlayout.helper.UserSharedPref
import kotlinx.android.synthetic.main.activity_main.*
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usrSharedPref = UserSharedPref(this)

        if (usrSharedPref.getLoginCheck()){
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun onClickProfile(view: View){
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    fun getSummeryReport() {
        val loading = ProgressDialog.show(this, "", "Please wait...", false, false)
        val stringRequestgroup = object : StringRequest(Request.Method.POST, "",
                Response.Listener { response ->
                    Log.e("booth_summary", response)
                    loading.dismiss()
                },
                Response.ErrorListener { loading.dismiss() }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                //params["tbl_police_station_id"] = appPreferences.getKeyOfficerOfficeId()
                return params
            }
        }

        val requestQueuegroup = Volley.newRequestQueue(this)
        requestQueuegroup.add(stringRequestgroup)
    }
}
