package com.example.mitchell.bible.service.data

import com.google.gson.annotations.SerializedName


data class VersionResponse(@SerializedName("bibles") var bibles: List<VersionModel>?)

data class VersionModel(@SerializedName("bible") var bible: String?,
                        @SerializedName("title") var title: String?,
                        @SerializedName("abbreviatedTitle") var abbreviatedTitle: String?,
                        @SerializedName("publicationDate") var publicationDate: String?,
                        @SerializedName("languages") var languages: List<String>?,
                        @SerializedName("publishers") var publishers: List<String>?,
                        @SerializedName("imageUrl") var imageUrl: String?,
                        @SerializedName("description") var description: String?,
                        @SerializedName("searchFields") var searchFields: List<String>?,
                        @SerializedName("copyright") var copyright: String?,
                        @SerializedName("extendedCopyright") var extendedCopyright: String?) {
}

