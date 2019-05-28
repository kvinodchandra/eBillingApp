package com.ml.democonstraintlayout.Adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ml.democonstraintlayout.R
import com.ml.democonstraintlayout.helper.Helper
import com.ml.democonstraintlayout.helper.SellPOJO
import com.ml.democonstraintlayout.helper.UserPOJO
import kotlinx.android.synthetic.main.sell_list_item.view.*

open class SellListAdapter(activity: Activity, arrayList: ArrayList<SellPOJO>): RecyclerView.Adapter<SellListAdapter.SellList>() {

    var _activity = activity
    var _arrayList = arrayList

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SellList {
        val view = LayoutInflater.from(p0.context)
                .inflate(R.layout.sell_list_item, p0, false)
        return SellList(view)
    }

    override fun getItemCount(): Int {
        return _arrayList.size
    }

    override fun onBindViewHolder(p0: SellList, p1: Int) {
        var gs = _arrayList.get(p1)

        p0.tv_ttl_profit.setText("\u20B9 "+gs._ttl_profit)
        p0.tv_ttl_sell.setText("\u20B9 " +gs._ttl_sell)
        if (gs._sell_date.equals("")){
            p0.tv_sell_date.visibility=View.GONE
        }else {
            p0.tv_sell_date.setText(Helper().convertDate(gs._sell_date))
        }

        Log.e("gsad_ttl_profit", gs._ttl_profit)
        Log.e("gsad_ttl_sell", gs._ttl_sell)
    }

    inner class SellList(view: View): RecyclerView.ViewHolder(view) {
        var tv_ttl_sell:TextView = view.tv_ttl_sell
        var tv_ttl_profit: TextView = view.tv_ttl_profit
        var tv_sell_date: TextView = view.tv_sell_date
    }

}