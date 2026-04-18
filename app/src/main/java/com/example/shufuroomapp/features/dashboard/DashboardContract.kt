package com.example.shufuroomapp.features.dashboard

import androidx.fragment.app.Fragment

interface DashboardContract {

    interface View {
        fun displayFragment(fragment: Fragment)
    }

    interface Presenter {
        fun handleNavigation(itemID: Int): Boolean
        fun onDestroy()
    }

}