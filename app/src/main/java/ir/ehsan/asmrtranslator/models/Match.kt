package ir.ehsan.asmrtranslator.models

import com.google.gson.annotations.SerializedName

data class Match(
    @SerializedName("id")
    val id:Long,
    @SerializedName("segment")
    val segment:String,
    @SerializedName("translation")
    val translation:String,
    @SerializedName("source")
    val source:String,
    @SerializedName("target")
    val target:String,
    @SerializedName("quality")
    val quality:Int,
    @SerializedName("reference")
    val reference:String?,
    @SerializedName("usage-count")
    val usageCount:Int,
    @SerializedName("subject")
    val subject:String,
    @SerializedName("created-by")
    val createdBy:String,
    @SerializedName("last-updated-by")
    val lastUpdatedBy:String,
    @SerializedName("create-date")
    val createdDate:String,
    @SerializedName("last-update-date")
    val lastUpdatedDate:String,
    @SerializedName("match")
    val match:Double
)
/*
 {
            "id": "441845863",
            "segment": "hello",
            "translation": "- Bu haftasonu ne yaptım.",
            "source": "en-GB",
            "target": "tr-TR",
            "quality": "0",
            "reference": null,
            "usage-count": 2,
            "subject": "All",
            "created-by": "MateCat",
            "last-updated-by": "MateCat",
            "create-date": "2019-01-22 18:18:15",
            "last-update-date": "2019-01-22 18:18:15",
            "match": 1
        },
 */