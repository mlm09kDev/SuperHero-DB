package com.mlm09kdev.superHeroDB.ui.superhero.details

import android.content.Context
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
import com.mlm09kdev.superHeroDB.utils.CallBackInterface
import com.mlm09kdev.superHeroDB.utils.glide.GlideApp
import kotlinx.android.synthetic.main.appearance_card_layout.*
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

    private lateinit var callBackInterface: CallBackInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callBackInterface.showActionAndNavBars()
        return inflater.inflate(R.layout.details_fragment_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { DetailsFragmentArgs.fromBundle(it) }
        val id = safeArgs?.id
        viewModel = ViewModelProvider(
            this,
            viewModelFactoryInstanceFactory(id!!)
        ).get(DetailsViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val superHero = viewModel.superHero.await()
        superHero.observe(viewLifecycleOwner, Observer {
            if (it == null)
                return@Observer

            group_details_loading.visibility = View.GONE
            (activity as? AppCompatActivity)?.supportActionBar?.title = it.name
            bindPowerStats(it.powerstats)
            bindBiography(it.biography)
            bindAppearance(it.appearance)
            bindWork(it.work)
            bindConnections(it.connections)
            GlideApp.with(this@DetailsFragment).load(it.image.url).centerCrop()
                .error(R.drawable.ic_broken_image).into(imageView_details_image)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is CallBackInterface)
            callBackInterface = context
        else
            throw RuntimeException("$context must implement CallBackInterface")
    }

    private fun bindConnections(connections: Connections) {

        textView_connections_groups_text.text =
            if (connections.groupAffiliation == "null")
                "-"
            else
                connections.groupAffiliation
        textView_connections_relatives_text.text =
            if (connections.relatives == "null")
                "-"
            else
                connections.relatives
    }

    private fun bindWork(work: Work) {
        textView_work_occupation_text.text =
            if (work.occupation == "null")
                "-"
            else work.occupation
        textView_work_base_text.text =
            if (work.base == "null")
                "-"
            else work.base
    }

    private fun bindAppearance(appearance: Appearance) {

        textView_appearance_gender_text.text =
            if (appearance.gender == "null")
                "-"
            else appearance.gender
        textView_appearance_race_text.text =
            if (appearance.race == "null")
                "-"
            else appearance.race
        textView_appearance_eyeColor_text.text =
            if (appearance.eyeColor == "null")
                "-"
            else appearance.eyeColor
        textView_appearance_hairColor_text.text =
            if (appearance.hairColor == "null")
                "-"
            else appearance.hairColor
        textView_appearance_height_text.text =
            if (appearance.hairColor == "null")
                "-"
            else "temp"
        textView_appearance_weight_text.text =
            if (appearance.hairColor == "null")
                "-"
            else "temp"
    }

    private fun bindBiography(biography: Biography) {
        textView_biography_fullName_text.text =
            if (biography.fullName == "null")
                "-"
            else biography.fullName
        textView_biography_placeOfBirth_text.text =
            if (biography.placeOfBirth == "null")
                "-"
            else biography.placeOfBirth
        textView_biography_publisher_text.text =
            if (biography.publisher == "null")
                "-"
            else biography.publisher
        textView_biography_firstAppearance_text.text =
            if (biography.firstAppearance == "null")
                "-"
            else biography.firstAppearance
        textView_biography_alterEgos_text.text =
            if (biography.alterEgos == "null")
                "-"
            else biography.alterEgos
        textView_biography_alignment_text.text =
            if (biography.alignment == "null")
                "-"
            else biography.alignment
        textView_biography_aliases_text.text =
            if (biography.alignment == "null")
                "-"
            else
                "-"
    }

    private fun bindPowerStats(powerstats: Powerstats) {

        progressBar_powerStat_power.progress =
            if (powerstats.power == "null")
                0
            else
                powerstats.power.toInt()
        textView_powerstat_power.text =
            if (powerstats.power == "null")
                "-"
            else
                powerstats.power
        progressBar_powerStat_intelligence.progress =
            if (powerstats.intelligence == "null")
                0
            else
                powerstats.intelligence.toInt()
        textView_powerstat_intelligence.text =
            if (powerstats.intelligence == "null")
                "-"
            else
                powerstats.intelligence
        progressBar_powerStat_combat.progress =
            if (powerstats.combat == "null")
                0
            else
                powerstats.combat.toInt()
        textView_powerstat_combat.text =
            if (powerstats.combat == "null")
                "-"
            else
                powerstats.combat
        progressBar_powerStat_speed.progress =
            if (powerstats.speed == "null")
                0
            else
                powerstats.speed.toInt()
        textView_powerstat_speed.text =
            if (powerstats.speed == "null")
                "-"
            else
                powerstats.speed
        progressBar_powerStat_strength.progress =
            if (powerstats.strength == "null")
                0
            else
                powerstats.strength.toInt()
        textView_powerstat_strength.text =
            if (powerstats.strength == "null")
                "-"
            else
                powerstats.strength
        progressBar_powerStat_durability.progress =
            if (powerstats.durability == "null")
                0
            else
                powerstats.durability.toInt()
        textView_powerstat_durability.text =
            if (powerstats.durability == "null")
                "-"
            else
                powerstats.durability
    }

}
