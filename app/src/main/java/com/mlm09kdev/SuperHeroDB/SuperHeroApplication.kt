package com.mlm09kdev.SuperHeroDB

import android.app.Application
import com.mlm09kdev.SuperHeroDB.model.database.SuperHeroDatabase
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * Created by Manuel Montes de Oca on 1/29/2020.
 */

class SuperHeroApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@SuperHeroApplication))

        bind() from singleton { SuperHeroDatabase(instance()) }
    }


}