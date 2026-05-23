package com.example.shufuroomapp.features.dashboard

import com.example.shufuroomapp.features.bookings.BookingsFragment
import com.example.shufuroomapp.features.dashboard.fragments.HomeFragment
import com.example.shufuroomapp.features.profile.fragments.ProfileFragment

class DashboardPresenter(
    private var view: DashboardContract.View?
) : DashboardContract.Presenter {
    override fun handleNavigation(itemID: Int): Boolean {

        val fragment = when (itemID) {
            com.example.shufuroomapp.R.id.nav_home -> HomeFragment()
            com.example.shufuroomapp.R.id.nav_bookings -> BookingsFragment() // ADD THIS
            com.example.shufuroomapp.R.id.nav_profile -> ProfileFragment()
            else -> null
        }

        return if (fragment != null) {
            view?.displayFragment(fragment)
            true
        } else {
            false
        }
    }

    override fun onDestroy() {
        view = null
    }
}