package id.firman.placeholder_test.di

import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import id.firman.placeholder_test.data.local.db.PostDb
import id.firman.placeholder_test.data.remote.ApiService
import id.firman.placeholder_test.data.repository.Repository
import id.firman.placeholder_test.utils.Const
import id.firman.placeholder_test.views.SharedViewModel
import id.firman.placeholder_test.views.detail.DetailPostViewModel
import id.firman.placeholder_test.views.main.PostListViewModel
import id.firman.placeholder_test.views.search.SearchViewModel
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { SharedViewModel() }
}


val mainVMModule = module {
    viewModel {
        PostListViewModel(
            get()
        )
    }
}

val searchVMModule = module {
    viewModel {
        SearchViewModel(
            get()
        )
    }
}


val detailVMModule = module {
    viewModel { DetailPostViewModel( get() ) }
}
val dataModule = module {
    single { Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(Const.URL_BASE)
        .client(createOkHttpClient())
        .build()
    }

    single { Room.databaseBuilder(get(), PostDb::class.java, Const.dbName).build() }
    single { get<PostDb>().postdao }

    single { get<Retrofit>().create(ApiService::class.java) }

    single { Repository(get(), get(), get() ) }
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()
}

