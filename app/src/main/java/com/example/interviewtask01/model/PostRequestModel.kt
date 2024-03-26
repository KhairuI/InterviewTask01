package com.example.interviewtask01.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostRequestModel(
    @Expose @SerializedName("limit") internal val limit: Int? = null,
    @Expose @SerializedName("skip") internal val skip: Int? = null,
    @Expose @SerializedName("select") internal val select: String? = null
)