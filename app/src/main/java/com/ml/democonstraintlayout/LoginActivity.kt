package com.ml.democonstraintlayout

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.ml.democonstraintlayout.helper.Helper
import kotlinx.android.synthetic.main.activity_login.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ml.democonstraintlayout.helper.UserSharedPref
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val usrSharedPref = UserSharedPref(this)

        if (usrSharedPref.getLoginCheck()){
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun onClickLogin(view: View){
        var mobile = et_mobile.text.toString()
        var password = et_password.text.toString()

        var helper = Helper();

        if (!helper.validMobile(mobile)){
            Toast.makeText(applicationContext, "Invalid mobile number", Toast.LENGTH_LONG
            ).show()
        }else if (!helper.validPassword(password)){
            Toast.makeText(applicationContext, "Invalid password number", Toast.LENGTH_LONG
            ).show()
        }else{
            loginUser(mobile, password)
        }
    }

    fun loginUser(mobile: String, password: String){
        // Instantiate the RequestQueue.
        var h = Helper()
        val url = h.login_user
        val loading = ProgressDialog.show(this, "", "Please wait...", false, false)
        val stringRequestgroup = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    Log.e("loginUser", response)
                    userJosnDecode(response);
                    loading.dismiss()
                },
                Response.ErrorListener {
                    loading.dismiss()
                    Log.e("error", "error")
                }){
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["tbl_usr_mobile"] = mobile
                params["tbl_usr_password"] = password
                return params
            }
        }
        val requestQueuegroup = Volley.newRequestQueue(this)
        requestQueuegroup.add(stringRequestgroup)
    }

    private fun userJosnDecode(response: String) {
        val jsonObj = JSONObject(response)
        val jsonAray = jsonObj.getJSONArray("result")

        var status = jsonObj.getString("status")

        if (status.equals("success")) {
            for (item in 0..(jsonAray.length() - 1)) {
                val value = jsonAray.getJSONObject(item)

                var usr_id = value.getString("tbl_usr_id")
                var usr_mobile = value.getString("tbl_usr_mobile")
                var usr_password = value.getString("tbl_usr_password")
                var usr_type = value.getString("tbl_usr_type")
                var usr_status = value.getString("tbl_usr_status")

                val usrSharedPref = UserSharedPref(this)

                if (usr_status.equals("Active")){
                    usrSharedPref.setLoginCheck(true)
                    usrSharedPref.setUserId(usr_id)
                    usrSharedPref.setMobileNum(usr_mobile)
                    usrSharedPref.setUserPassword(usr_password)
                    usrSharedPref.setUserType(usr_type)
                    usrSharedPref.setUserStatus(usr_status)

                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }else {
                    Toast.makeText(applicationContext, "Your account is blocked.", Toast.LENGTH_LONG).show()
                }
            }
        }else {
            Toast.makeText(applicationContext, "Incorrect mobile number and password", Toast.LENGTH_LONG).show()
        }
    }
}
