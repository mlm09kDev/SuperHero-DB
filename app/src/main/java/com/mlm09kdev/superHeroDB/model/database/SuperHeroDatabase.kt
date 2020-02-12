package com.mlm09kdev.superHeroDB.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity
import com.mlm09kdev.superHeroDB.model.database.entity.converters.StringListConverter

@Database(
    entities = [SuperHeroEntity::class], version = 1, exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class SuperHeroDatabase: RoomDatabase() {
    abstract fun superHeroDao(): SuperHeroDao

    companion object{
        @Volatile
        private var instance: SuperHeroDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context:Context) =instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext,
                SuperHeroDatabase::class.java,"superHero.db")
                .build()

    }
}