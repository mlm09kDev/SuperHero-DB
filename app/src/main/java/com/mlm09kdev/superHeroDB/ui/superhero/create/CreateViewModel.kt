package com.mlm09kdev.superHeroDB.ui.superhero.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlm09kdev.superHeroDB.model.database.entity.*
import com.mlm09kdev.superHeroDB.model.repository.SuperHeroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CreateViewModel(private val superHeroRepository: SuperHeroRepository) : ViewModel() {
    // TODO: Implement the ViewModel


    fun createNewSuperHero(id: Int) {
        val test = SuperHeroEntity(
            name = "test",
            image = Image(url = ""),
            appearance = Appearance(
                eyeColor = "",
                gender = "",
                hairColor = "",
                height = emptyList(),
                race = "",
                weight = emptyList()
            ),
            biography = Biography(
                aliases = emptyList(),
                alignment = "",
                alterEgos = "",
                firstAppearance = "",
                fullName = "",
                placeOfBirth = "",
                publisher = ""
            ),
            connections = Connections(groupAffiliation = "", relatives = ""), isFavorite = 1,
            powerstats = Powerstats(
                intelligence = "",
                combat = "",
                durability = "",
                power = "",
                speed = "",
                strength = ""
            ),
            work = Work(base = "", occupation = ""), id = id
        )
        viewModelScope.launch(Dispatchers.IO) {
            superHeroRepository.createSuperHero(test)
        }
    }

}
