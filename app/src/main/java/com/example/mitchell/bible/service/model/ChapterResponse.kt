package com.example.mitchell.bible.service.model

import com.google.gson.annotations.SerializedName

/**
 * Created by mitchell on 10/27/16.
 */
data class ChapterResponse(@SerializedName("text") var chapterText: String?) {
}
