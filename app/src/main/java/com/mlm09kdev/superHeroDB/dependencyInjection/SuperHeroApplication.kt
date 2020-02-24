package com.mlm09kdev.superHeroDB.dependencyInjection

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mlm09kdev.superHeroDB.model.database.SuperHeroDatabase
import com.mlm09kdev.superHeroDB.model.network.*
import com.mlm09kdev.superHeroDB.model.repository.SuperHeroRepository
import com.mlm09kdev.superHeroDB.model.repository.SuperHeroRepositoryImpl
import com.mlm09kdev.superHeroDB.ui.superhero.create.CreateViewModelFactory
import com.mlm09kdev.superHeroDB.ui.superhero.details.DetailsViewModelFactory
import com.mlm09kdev.superHeroDB.ui.superhero.search.SearchViewModelFactory
import com.mlm09kdev.superHeroDB.ui.superhero.favorites.FavoritesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

/**
 * Created by Manuel Montes de Oca on 1/29/2020.
 */

class SuperHeroApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@SuperHeroApplication))

        bind() from singleton { SuperHeroDatabase(instance()) }
        bind() from singleton { instance<SuperHeroDatabase>().superHeroDao() }
        bind<ConnectionInterceptor>() with singleton { ConnectionInterceptorImpl(instance()) }
        bind() from singleton { SuperHeroAPIService(instance()) }
        bind<NetworkDataSource>() with singleton { NetworkDataSourceImpl(instance()) }
        bind<SuperHeroRepository>() with singleton {
            SuperHeroRepositoryImpl(
                instance(),
                instance()
            )
        }
        bind() from provider { SearchViewModelFactory(instance()) }
        bind() from provider { FavoritesViewModelFactory(instance()) }
        bind() from factory { id: String -> DetailsViewModelFactory(instance(), id) }
        bind() from provider { CreateViewModelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}