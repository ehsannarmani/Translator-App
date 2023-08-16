package ir.ehsan.asmrtranslator.models

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("translatedText")
    val translatedText:String,
    @SerializedName("match")
    val match:Double
)

/*
"responseData": {
        "translatedText": "- Bu haftasonu ne yaptım.",
        "match": 1
    },
 */