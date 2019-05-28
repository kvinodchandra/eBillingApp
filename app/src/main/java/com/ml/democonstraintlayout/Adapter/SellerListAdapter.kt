package com.ml.democonstraintlayout.Adapter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.ml.democonstraintlayout.R
import com.ml.democonstraintlayout.SellListActivity
import com.ml.democonstraintlayout.helper.SellPOJO
import com.ml.democonstraintlayout.helper.SellerPOJO
import com.ml.democonstraintlayout.helper.UserPOJO
import com.ml.democonstraintlayout.helper.UserSharedPref
import kotlinx.android.synthetic.main.sell_list_item.view.*
import kotlinx.android.synthetic.main.seller_list_item.view.*

open class SellerListAdapter(activity: Activity, arrayList: ArrayList<SellerPOJO>): RecyclerView.Adapter<SellerListAdapter.SellList>() {

    var _activity = activity
    var _arrayList = arrayList

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SellList {
        val view = LayoutInflater.from(p0.context)
                .inflate(R.layout.seller_list_item, p0, false)
        return SellList(view)
    }

    override fun getItemCount(): Int {
        return _arrayList.size
    }

    override fun onBindViewHolder(p0: SellList, p1: Int) {
        var gs = _arrayList.get(p1)

        if (gs._ttl_profit.equals("null")){
            p0.tv_ttl_profit.setText("Sell-: \u20B9 " + "00.00")
            p0.tv_ttl_sell.setText("Profit-: \u20B9 " + "00.00")

        }else {
            p0.tv_ttl_profit.setText("Sell-: \u20B9 " + gs._ttl_profit)
            p0.tv_ttl_sell.setText("Profit-: \u20B9 " + gs._ttl_sell)
        }
        p0.tv_seller_name.setText(gs._usr_name)

        p0.img_details_list.setOnClickListener {
            val intent = Intent(_activity, SellListActivity::class.java)
            intent.putExtra("seller_id", gs._user_id)
            _activity.startActivity(intent)
        }
    }

    inner class SellList(view: View): RecyclerView.ViewHolder(view) {
        var tv_ttl_sell:TextView = view.tv_sell
        var tv_ttl_profit: TextView = view.tv_profit
        var tv_seller_name: TextView = view.tv_seller_name
        var img_details_list: ImageView = view.img_details_list
    }

}