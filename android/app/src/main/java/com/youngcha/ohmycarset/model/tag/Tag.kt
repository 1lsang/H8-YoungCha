package com.youngcha.ohmycarset.model.tag

data class Tag (
    val tagType:String, //age, gender, keyword(strength,importance, uses)
    val name:String,
    var isSelected:Boolean
){
}