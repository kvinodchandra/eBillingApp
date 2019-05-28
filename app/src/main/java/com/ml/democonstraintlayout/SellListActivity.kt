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
import com.ml.democonstraintlayout.helper.Helper
import com.ml.democonstraintlayout.helper.SellPOJO
import com.ml.democonstraintlayout.helper.UserSharedPref
import kotlinx.android.synthetic.main.activity_sell_list.*
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap

class SellListActivity : AppCompatActivity() {

    lateinit var gsList: ArrayList<SellPOJO>
    lateinit var adapter: SellListAdapter
    var seller_id: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_list)

        gsList = ArrayList()
        adapter = SellListAdapter(this, gsList)
        val lm = LinearLayoutManager(this)
        rcv_sell_list.layoutManager = lm
        rcv_sell_list.adapter = adapter
        seller_id = intent.getStringExtra("seller_id")
        getSellList()
    }

    fun getSellList() {
        gsList.clear()
        val loader = ProgressDialog.show(this,"", "please wait")
        val request = object : StringRequest(Request.Method.POST, Helper().getSellList, Response.Listener { response ->
            Log.e("getSellList", response)
            loader.dismiss()
            getSellListJSON(response)
        }, Response.ErrorListener {
            loader.dismiss()
        }) {
            override fun getParams(): Map<String, String> {
                val map = HashMap<String, String>()
                map["tbl_usr_id"] = seller_id
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
            var date=""
            for (j in 0 until result_jsonarray.length()) {
                val jsonObject1 = result_jsonarray.getJSONObject(j)
                val tbl_total_amount = jsonObject1.getString("tbl_total_amount")
                val tbl_total_benefit = jsonObject1.getString("tbl_total_benefit")
                val tbl_add_date = jsonObject1.getString("tbl_add_date")

                if (!date.equals(tbl_add_date)){
                    date = tbl_add_date
                }else{
                    date = ""
                }
                val om = SellPOJO(tbl_total_amount, tbl_total_benefit, date)
                gsList.add(om)

                Log.e("tbl_total_amount", om._ttl_profit)
                Log.e("gs_total_amount", gsList.get(j)._ttl_profit)
            }
            adapter.notifyDataSetChanged()
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
    }
}
