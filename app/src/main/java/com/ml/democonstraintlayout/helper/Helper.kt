package com.ml.democonstraintlayout.helper

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Helper {

    fun validMobile(number: String): Boolean {
        var check = true

        if (!TextUtils.isDigitsOnly(number)) {
            check = false
        } else if (number.length != 10) {
            check = false
        }
        return check
    }


    fun validPassword(password: String): Boolean {
        var check = true

        if (password.isEmpty()) {
            check = false
        } else if (password.length <6) {
            check = false
        }
        return check
    }

    var main_url = "http://skynetservices.in/billing_app/"

    var login_user = main_url+"login_user.php"
    var add_amount_details = main_url+"add_amount_details.php"
    var getSellList = main_url+"getSellList.php"
    var getSellerList = main_url+"getSellerList.php"

    fun  convertDate(date: String): String {
        var date = date
        var spf = SimpleDateFormat("yyyy-MM-dd")
        var newDate: Date? = null
        try {
            newDate = spf.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        spf = SimpleDateFormat("dd MMM yyyy")
        date = "" + spf.format(newDate)
        return date
    }
}
