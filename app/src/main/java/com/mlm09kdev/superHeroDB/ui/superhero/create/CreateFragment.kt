package com.mlm09kdev.superHeroDB.ui.superhero.create

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.mlm09kdev.superHeroDB.R
import com.mlm09kdev.superHeroDB.model.database.entity.*
import com.mlm09kdev.superHeroDB.ui.superhero.search.SearchFragmentDirections
import com.mlm09kdev.superHeroDB.utils.CallBackInterface
import com.mlm09kdev.superHeroDB.utils.InputFilterMinMax
import kotlinx.android.synthetic.main.create_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class CreateFragment : Fragment(), KodeinAware {

    //get the closest kodein from our superHeroApplication.kt
    override val kodein by closestKodein()

    private val viewModelFactory: CreateViewModelFactory by instance()
    private lateinit var viewModel: CreateViewModel
    private lateinit var callBackInterface: CallBackInterface


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callBackInterface.showActionAndNavBars()
        return inflater.inflate(R.layout.create_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateViewModel::class.java)
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val id = prefs.getInt("LastId", 731).plus(1)
        val editor = prefs.edit()
        editor.putInt("LastId", id)
        editor.apply() //important, otherwise it wouldn't save.

        bindSeekBarToTextView()

        button_add_to_favorites.setOnClickListener {
            if (checkNameNotEmpty()) {
                viewModel.createNewSuperHero(createSuperHeroEntity(id))
                val actionDetail =
                    CreateFragmentDirections.actionCreateFragmentToDetailsFragment(id)
                Navigation.findNavController(it).navigate(actionDetail)
            }
        }
    }

    private fun bindSeekBarToTextView() {
        textView_powerstat_power_seekbar_value.text = "0"
        textView_powerstat_intelligence_seekbar_value.text = "0"
        textView_powerstat_combat_seekbar_value.text = "0"
        textView_powerstat_speed_seekbar_value.text = "0"
        textView_powerstat_strength_seekbar_value.text = "0"
        textView_powerstat_durability_seekbar_value.text = "0"

        seekbar_powerStat_power.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                textView_powerstat_power_seekbar_value.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                textView_powerstat_power_seekbar_value.text = p0?.progress.toString()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                textView_powerstat_power_seekbar_value.text = p0?.progress.toString()
            }
        })
        seekbar_powerStat_intelligence.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                textView_powerstat_intelligence_seekbar_value.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                textView_powerstat_intelligence_seekbar_value.text = p0?.progress.toString()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                textView_powerstat_intelligence_seekbar_value.text = p0?.progress.toString()
            }
        })
        seekbar_powerStat_combat.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                textView_powerstat_combat_seekbar_value.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                textView_powerstat_combat_seekbar_value.text = p0?.progress.toString()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                textView_powerstat_combat_seekbar_value.text = p0?.progress.toString()
            }
        })
        seekbar_powerStat_speed.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                textView_powerstat_speed_seekbar_value.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                textView_powerstat_speed_seekbar_value.text = p0?.progress.toString()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                textView_powerstat_speed_seekbar_value.text = p0?.progress.toString()
            }
        })
        seekbar_powerStat_strength.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                textView_powerstat_strength_seekbar_value.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                textView_powerstat_strength_seekbar_value.text = p0?.progress.toString()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                textView_powerstat_strength_seekbar_value.text = p0?.progress.toString()
            }
        })
        seekbar_powerstat_durability.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                textView_powerstat_durability_seekbar_value.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                textView_powerstat_durability_seekbar_value.text = p0?.progress.toString()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                textView_powerstat_durability_seekbar_value.text = p0?.progress.toString()
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CallBackInterface)
            callBackInterface = context
        else
            throw RuntimeException("$context must implement CallBackInterface")
    }

    private fun checkNameNotEmpty(): Boolean {
        return if (editText_mainInfo_name_text.text.isNotBlank()) {
            true
        } else {
            editText_mainInfo_name_text.error = "Super Hero Name Required"
            editText_mainInfo_name_text.requestFocus()
            false
        }
    }


    private fun createSuperHeroEntity(id: Int): SuperHeroEntity {
        return SuperHeroEntity(
            name = editText_mainInfo_name_text.text.toString(),
            image = Image(url = editText_mainInfo_imageURL_text.text.toString()),
            appearance = Appearance(
                eyeColor = editText_appearance_eyeColor_text.text.toString(),
                gender = editText_appearance_gender_text.text.toString(),
                hairColor = editText_appearance_hairColor_text.text.toString(),
                height = emptyList(),
                race = editText_appearance_race_text.text.toString(),
                weight = emptyList()
            ),
            biography = Biography(
                aliases = emptyList(),
                alignment = editText_biography_alignment_text.text.toString(),
                alterEgos = editText_biography_alterEgos_text.text.toString(),
                firstAppearance = editText_biography_firstAppearance_text.text.toString(),
                fullName = editText_biography_fullName_text.text.toString(),
                placeOfBirth = editText_biography_placeOfBirth_text.text.toString(),
                publisher = editText_biography_publisher_text.text.toString()
            ),
            connections = Connections(
                groupAffiliation = editText_connections_groups_text.text.toString(),
                relatives = editText_connections_relatives_text.text.toString()
            ),
            isFavorite = 1,
            powerstats = Powerstats(
                intelligence = seekbar_powerStat_intelligence.progress.toString(),
                combat = seekbar_powerStat_combat.progress.toString(),
                durability = seekbar_powerstat_durability.progress.toString(),
                power = seekbar_powerStat_power.progress.toString(),
                speed = seekbar_powerStat_speed.progress.toString(),
                strength = seekbar_powerStat_strength.progress.toString()
            ),
            work = Work(
                base = editText_work_base_text.text.toString(),
                occupation = editText_work_occupation_text.text.toString()
            ), id = id
        )
    }


}
