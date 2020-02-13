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
        if (context is CallBackInterface)
            callBackInterface = context
        else
            throw RuntimeException("$context must implement CallBackInterface")
    }

    private fun bindConnections(connections: Connections) {
        textView_connections_groups_text.text = validateString(connections.groupAffiliation)
        textView_connections_relatives_text.text = validateString(connections.relatives)
    }

    private fun bindWork(work: Work) {
        textView_work_occupation_text.text = validateString(work.occupation)
        textView_work_base_text.text = validateString(work.base)
    }

    private fun bindAppearance(appearance: Appearance) {

        textView_appearance_gender_text.text = validateString(appearance.gender)
        textView_appearance_race_text.text = validateString(appearance.race)
        textView_appearance_eyeColor_text.text = validateString(appearance.eyeColor)
        textView_appearance_hairColor_text.text = validateString(appearance.hairColor)
        textView_appearance_height_text.text =
            validateString(appearance.height.toString()).replace("[", "").replace("]", "")
        textView_appearance_weight_text.text =
            validateString(appearance.weight.toString()).replace("[", "").replace("]", "")
    }

    private fun bindBiography(biography: Biography) {
        textView_biography_fullName_text.text = validateString(biography.fullName)
        textView_biography_placeOfBirth_text.text = validateString(biography.placeOfBirth)
        textView_biography_publisher_text.text = validateString(biography.publisher)
        textView_biography_firstAppearance_text.text = validateString(biography.firstAppearance)
        textView_biography_alterEgos_text.text = validateString(biography.alterEgos)
        textView_biography_alignment_text.text = validateString(biography.alignment)
        textView_biography_aliases_text.text =
            validateString(biography.aliases.toString()).replace("[", "").replace("]", "")
    }

    private fun bindPowerStats(powerstats: Powerstats) {

        progressBar_powerStat_power.progress = validateInt(powerstats.power)
        textView_powerstat_power.text = validateString(powerstats.power)
        progressBar_powerStat_intelligence.progress = validateInt(powerstats.intelligence)
        textView_powerstat_intelligence.text = validateString(powerstats.intelligence)
        progressBar_powerStat_combat.progress = validateInt(powerstats.combat)
        textView_powerstat_combat.text = validateString(powerstats.combat)
        progressBar_powerStat_speed.progress = validateInt(powerstats.speed)
        textView_powerstat_speed.text = validateString(powerstats.speed)
        progressBar_powerStat_strength.progress = validateInt(powerstats.strength)
        textView_powerstat_strength.text = validateString(powerstats.strength)
        progressBar_powerStat_durability.progress = validateInt(powerstats.durability)
        textView_powerstat_durability.text = validateString(powerstats.durability)
    }

    private fun validateString(string: String): String {
        return if (string.isEmpty() || string == "null") "-" else string.capitalize()
    }

    private fun validateInt(string: String): Int {
        return if (string.isEmpty() || string == "null") 0 else string.toInt()
    }

}
