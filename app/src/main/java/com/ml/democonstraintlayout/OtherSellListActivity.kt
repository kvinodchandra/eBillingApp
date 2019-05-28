package com.ml.democonstraintlayout

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ml.democonstraintlayout.Adapter.SellListAdapter
import com.ml.democonstraintlayout.Adapter.SellerListAdapter
import com.ml.democonstraintlayout.helper.Helper
import com.ml.democonstraintlayout.helper.SellPOJO
import com.ml.democonstraintlayout.helper.SellerPOJO
import com.ml.democonstraintlayout.helper.UserSharedPref
import kotlinx.android.synthetic.main.activity_sell_list.*
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap

class OtherSellListActivity : AppCompatActivity() {

    lateinit var gsList: ArrayList<SellerPOJO>
    lateinit var adapter: SellerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_list)

        gsList = ArrayList()
        adapter = SellerListAdapter(this, gsList)
        val lm = LinearLayoutManager(this)
        rcv_sell_list.layoutManager = lm
        rcv_sell_list.adapter = adapter
        getSellList()
    }

    fun getSellList() {
        gsList.clear()
        val loader = ProgressDialog.show(this,"", "please wait")
        val request = object : StringRequest(Request.Method.POST, Helper().getSellerList, Response.Listener { response ->
            Log.e("getSellList", response)
            loader.dismiss()
            getSellListJSON(response)
        }, Response.ErrorListener {
            loader.dismiss()
        }) {
            override fun getParams(): Map<String, String> {
                val map = HashMap<String, String>()
                map["tbl_usr_id"] = UserSharedPref(applicationContext).getUserId()
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

            for (j in 0 until result_jsonarray.length()) {
                val jsonObject1 = result_jsonarray.getJSONObject(j)
                val tbl_user_id = jsonObject1.getString("tbl_user_id")
                val tbl_usr_name = jsonObject1.getString("tbl_usr_name")
                val tbl_total_amount = jsonObject1.getString("tbl_total_amount")
                val tbl_total_benefit = jsonObject1.getString("tbl_total_benefit")

                val om = SellerPOJO(tbl_user_id, tbl_total_amount, tbl_total_benefit, tbl_usr_name)
                gsList.add(om)
            }
            adapter.notifyDataSetChanged()
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
    }
}
