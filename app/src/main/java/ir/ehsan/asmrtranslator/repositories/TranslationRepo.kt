package ir.ehsan.asmrtranslator.repositories

import ir.ehsan.asmrtranslator.models.BaseModel
import ir.ehsan.asmrtranslator.models.Translation

interface TranslationRepo {
    suspend fun translate(query:String,from:String,to:String):BaseModel<Translation>
}