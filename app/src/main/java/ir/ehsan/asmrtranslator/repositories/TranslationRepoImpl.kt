package ir.ehsan.asmrtranslator.repositories

import ir.ehsan.asmrtranslator.models.BaseModel
import ir.ehsan.asmrtranslator.models.Translation
import ir.ehsan.asmrtranslator.network.TranslationApi
import java.lang.Exception

class TranslationRepoImpl(private val api:TranslationApi):TranslationRepo {
    override suspend fun translate(
        query: String,
        from: String,
        to: String
    ): BaseModel<Translation> {
        try {
            api.translate(
                query = query,
                langPair = "$from|$to"
            ).also {
                return if (it.isSuccessful){
                    BaseModel.Success(data = it.body()!!)
                }else{
                    BaseModel.Error(error = it.errorBody()?.string().toString())
                }
            }
        }catch (e:Exception){
            return BaseModel.Error(error = e.message.toString())
        }
    }
}