package ir.ehsan.asmrtranslator

import android.app.Application
import ir.ehsan.asmrtranslator.network.TranslationApi
import ir.ehsan.asmrtranslator.repositories.TranslationRepo
import ir.ehsan.asmrtranslator.repositories.TranslationRepoImpl
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KoinApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(module {
                single {
                    Retrofit
                        .Builder()
                        .baseUrl("https://api.mymemory.translated.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                single {
                    val retrofit:Retrofit = get()
                    retrofit.create(TranslationApi::class.java)
                }
                single {
                    TranslationRepoImpl(api = get())
                } bind TranslationRepo::class
            })
        }
    }
}