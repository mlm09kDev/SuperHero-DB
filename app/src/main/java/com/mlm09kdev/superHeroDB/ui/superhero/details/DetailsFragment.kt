package com.mlm09kdev.superHeroDB.ui.superhero.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.model.database.entity.*
import com.mlm09kdev.superHeroDB.ui.ScopedFragment
import com.mlm09kdev.superHeroDB.utils.glide.GlideApp
import kotlinx.android.synthetic.main.appearance_card_layout.*
import kotlinx.android.synthetic.main.appearance_card_layout.textView_appearance_race_text
import kotlinx.android.synthetic.main.biography_card_layout.*
import kotlinx.android.synthetic.main.connections_card_layout.*
import kotlinx.android.synthetic.main.details_fragment_layout.*
import kotlinx.android.synthetic.main.image_card_layout.*
import kotlinx.android.synthetic.main.powerstat_card_layout.*
import kotlinx.android.synthetic.main.work_card_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory

class DetailsFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactoryInstanceFactory: ((String) -> DetailsViewModelFactory) by factory()

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { DetailsFragmentArgs.fromBundle(it) }
        val id = safeArgs?.id

        viewModel = ViewModelProvider(this, viewModelFactoryInstanceFactory(id!!)).get(DetailsViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val superHero = viewModel.superHero.await()
        superHero.observe(viewLifecycleOwner, Observer {
            if (it == null)
                return@Observer

            group_details_loading.visibility = View.GONE
            (activity as? AppCompatActivity)?.supportActionBar?.title =it.name
            bindPowerStats(it.powerstats)
            bindBiography(it.biography)
            bindAppearance(it.appearance)
            bindWork(it.work)
            bindConnections(it.connections)
            GlideApp.with(this@DetailsFragment).load(it.image.url).centerCrop().error(R.drawable.ic_broken_image).into(imageView_details_image)
        })
    }

    private fun bindConnections(connections: Connections) {

        textView_connections_groups_text.text =connections.groupAffiliation
        textView_connections_relatives_text.text =connections.relatives
    }

    private fun bindWork(work: Work) {
        textView_work_occupation_text.text = work.occupation
        textView_work_base_text.text = work.base
    }

    private fun bindAppearance(appearance: Appearance) {

        textView_appearance_gender_text.text = appearance.gender
        textView_appearance_race_text.text = appearance.race
        textView_appearance_eyeColor_text.text = appearance.eyeColor
        textView_appearance_hairColor_text.text = appearance.hairColor
        textView_appearance_height_text.text = "temp"
        textView_appearance_weight_text.text = "temp"
    }

    private fun bindBiography(biography: Biography) {
        textView_biography_fullName_text.text = biography.fullName
        textView_biography_placeOfBirth_text.text = biography.placeOfBirth
        textView_biography_publisher_text.text = biography.publisher
        textView_biography_firstAppearance_text.text = biography.firstAppearance
        textView_biography_alterEgos_text.text = biography.alterEgos
        textView_biography_alignment_text.text = biography.alignment
        textView_biography_aliases_text.text = "test for now"

    }

    private fun bindPowerStats(powerstats: Powerstats){
        progressBar_powerStat_power.progress = powerstats.power.toInt()
        textView_powerstat_power.text = powerstats.power
        progressBar_powerStat_intelligence.progress = powerstats.intelligence.toInt()
        textView_powerstat_intelligence.text = powerstats.intelligence
        progressBar_powerStat_combat.progress = powerstats.combat.toInt()
        textView_powerstat_combat.text = powerstats.combat
        progressBar_powerStat_speed.progress = powerstats.speed.toInt()
        textView_powerstat_speed.text = powerstats.speed
        progressBar_powerStat_strength.progress = powerstats.strength.toInt()
        textView_powerstat_strength.text = powerstats.strength
        progressBar_powerStat_durability.progress = powerstats.durability.toInt()
        textView_powerstat_durability.text = powerstats.durability
    }

}
