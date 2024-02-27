package network

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    val networkModule = module {

        single { "https://interview-task-api.mca.dev/" }

        single { createRetrofitClient(get()) }

        single { get<Retrofit>().create(ReceiptDetailsApi::class.java) }
    }

    private fun createRetrofitClient(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}