package com.example.mitchell.bible.service.data

import com.google.gson.annotations.SerializedName

/**
 * Created by mitchell on 10/27/16.
 */

data class TableOfContentsResponse(@SerializedName("books") var books: List<BookModel>?)

data class BookModel(@SerializedName("passage") var bookName: String?,
                        @SerializedName("chapters") var chapters: List<ChapterModel>?) {
}

data class ChapterModel(@SerializedName("passage") var chapterName: String?) {
}
