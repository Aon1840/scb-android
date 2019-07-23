package com.codemobiles.cmscb

import android.content.Context
import android.widget.Toast

// ขยาย context
fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}