package com.example.instagramclone.Model

class Post {
    var posturl:String=""
    var caption:String=""
    constructor()
    constructor(posturl:String, caption:String) {
        this.posturl = posturl
        this.caption = caption
    }
}