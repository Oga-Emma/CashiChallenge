package com.example.cashichallenge.util

import android.icu.text.NumberFormat
import android.icu.util.Currency
import java.text.DecimalFormat

actual fun formatCurrency(amount: Double, currencyCode: String): String {
    try {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.setMaximumFractionDigits(2)
        format.currency = Currency.getInstance(currencyCode)

        return format.format(amount)
    }catch (e: Exception){
        return DecimalFormat("###,###,##0.00").format(amount) + " $currencyCode"
    }
}
