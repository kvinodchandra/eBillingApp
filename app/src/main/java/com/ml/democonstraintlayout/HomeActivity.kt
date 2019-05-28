package com.ml.democonstraintlayout

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ml.democonstraintlayout.helper.Helper
import com.ml.democonstraintlayout.helper.SellPOJO
import com.ml.democonstraintlayout.helper.UserSharedPref
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_add_sell.view.*
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.HashMap

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        getSellAmt()

        if (UserSharedPref(applicationContext).getUserType().equals("admin")){
            btn_add_seller.visibility = View.VISIBLE
            btn_othr_sell_list.visibility = View.VISIBLE
        }else{
            btn_add_seller.visibility = View.GONE
            btn_othr_sell_list.visibility = View.GONE
        }
    }

    fun onClickAddSell(view: View){
        addSellDialog()
    }

    fun onClickMySellList(view: View){
        val intent = Intent(applicationContext, SellListActivity::class.java)
        intent.putExtra("seller_id", UserSharedPref(applicationContext).getUserId())
        startActivity(intent)
    }

    fun onClickOtherSellerSellList(view: View){
        val intent = Intent(applicationContext, OtherSellListActivity::class.java)
        startActivity(intent)
    }

    fun onClickAddSeller(view: View){

    }

    fun onClickLogout(view: View){
        UserSharedPref(applicationContext).logout()
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


    fun addSellDialog(){
        val dialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_add_sell, null)
        dialog.setView(view)
        val ad = dialog.create()
        ad.show()

        view.dia_btn_submit.setOnClickListener {
            var str_ttl_sell = view.tv_ttl_sell.text.toString()
            var str_benefit = view.tv_ttl_profit.text.toString()
            submitSell(str_ttl_sell, str_benefit, ad)
        }
    }

    fun submitSell(ttl_sell: String, ttl_benefit: String, ad: AlertDialog){
        // Instantiate the RequestQueue.
        val url = Helper().add_amount_details
        val loading = ProgressDialog.show(this, "", "Please wait...", false, false)
        val stringRequestgroup = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    Log.e("submitSell", response)
                    loading.dismiss()
                    val jsonObj = JSONObject(response)
                    var status = jsonObj.getString("status")
                    if (status.equals("success")){
                        ad.dismiss()
                        getSellAmt()
                        Toast.makeText(applicationContext, "Added Successfully", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(applicationContext, "Not Added Successfully", Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener {
                    loading.dismiss()
                    Log.e("error", "error")
                }){
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["tbl_total_amount"] = ttl_sell
                params["tbl_total_benefit"] = ttl_benefit
                params["tbl_usr_id"] = UserSharedPref(applicationContext).getUserId()
                return params
            }
        }
        val requestQueuegroup = Volley.newRequestQueue(this)
        requestQueuegroup.add(stringRequestgroup)
    }

    fun getSellAmt() {
        //val loader = ProgressDialog.show(this,"", "please wait")
        val request = object : StringRequest(Method.POST, Helper().getSellList, Response.Listener { response ->
            Log.e("getSellList", response)
            //loader.dismiss()
            getSellListJSON(response)
        }, Response.ErrorListener {
            //loader.dismiss()
        }) {
            override fun getParams(): Map<String, String> {
                val map = HashMap<String, String>()
                map["tbl_usr_id"] = UserSharedPref(applicationContext).getUserId()
                map["filter_tag"] = "today_sell"
                return map
            }

        }

        val queue = Volley.newRequestQueue(applicationContext)
        queue.add(request)
    }

    private fun getSellListJSON(response: String) {
        try {
            val jsonObject = JSONObject(response)
            val result_jsonarray = jsonObject.getJSONArray("result")
            var tbl_total_amount=0.0
            var tbl_total_benefit=0.0
            for (j in 0 until result_jsonarray.length()) {
                val jsonObject1 = result_jsonarray.getJSONObject(j)
                tbl_total_benefit = tbl_total_benefit + jsonObject1.getString("tbl_total_benefit").toFloat()
                tbl_total_amount = tbl_total_amount + jsonObject1.getString("tbl_total_amount").toFloat()
            }
            val df = DecimalFormat("##.##")
            //df.roundingMode = RoundingMode.CEILING
            tv_today_sell_profit.setText("\u20B9 "+df.format(tbl_total_benefit))
            tv_today_sell_amt.setText("\u20B9 "+"%.2f".format(tbl_total_amount))
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
    }
}
