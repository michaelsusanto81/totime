package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.covid.saved

import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase

interface CovidSavedCommunicator {
    fun clickedItem(covidSaved: CovidCase)
}