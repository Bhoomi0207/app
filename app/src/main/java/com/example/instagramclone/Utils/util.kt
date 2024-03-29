package com.example.instagramclone.Utils

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import javax.security.auth.callback.Callback

fun uploadimage(uri:Uri,folderName:String,callback:(String?)->Unit){
    var imageUrl:String?=null
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageUrl=it.toString()
                callback(imageUrl)
            }
        }


}
