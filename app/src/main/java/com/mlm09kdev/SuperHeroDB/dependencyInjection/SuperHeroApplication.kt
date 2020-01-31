package com.mlm09kdev.SuperHeroDB.dependencyInjection

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mlm09kdev.SuperHeroDB.model.database.SuperHeroDatabase
import com.mlm09kdev.SuperHeroDB.model.network.*
import com.mlm09kdev.SuperHeroDB.model.repository.SuperHeroRepository
import com.mlm09kdev.SuperHeroDB.model.repository.SuperHeroRepositoryImpl
import com.mlm09kdev.SuperHeroDB.ui.superHero.superHero.SuperHeroViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/**
 * Created by Manuel Montes de Oca on 1/29/2020.
 */

class SuperHeroApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@SuperHeroApplication))

        bind() from singleton { SuperHeroDatabase(instance()) }
        bind() from singleton { instance<SuperHeroDatabase>().superHeroDao()}
        bind<ConnectionInterceptor>()with singleton { ConnectionInterceptorImpl(instance()) }
        bind() from singleton { SuperHeroAPIService(instance()) }
        bind<NetworkDataSource>()with singleton { NetworkDataSourceImpl(instance()) }
        bind<SuperHeroRepository>()with singleton { SuperHeroRepositoryImpl(instance(),instance()) }
        bind() from provider { SuperHeroViewModelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}