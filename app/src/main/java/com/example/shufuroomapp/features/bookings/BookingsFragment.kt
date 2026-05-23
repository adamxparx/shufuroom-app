package com.example.shufuroomapp.features.bookings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shufuroomapp.R
import com.example.shufuroomapp.databinding.FragmentBookingsBinding
import com.example.shufuroomapp.features.bookings.adapter.BookingAdapter
import com.example.shufuroomapp.features.rooms.booking.data.BookingResponse

class BookingsFragment : Fragment(R.layout.fragment_bookings), BookingsContract.View {

    private var _binding: FragmentBookingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: BookingsContract.Presenter
    private lateinit var bookingAdapter: BookingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBookingsBinding.bind(view)

        bookingAdapter = BookingAdapter(emptyList())
        binding.rvBookings.adapter = bookingAdapter

        presenter = BookingsPresenter(this)
        presenter.fetchMyBookings()
    }

    override fun showLoading() {}
    override fun hideLoading() {}

    override fun showMyBookings(bookings: List<BookingResponse>) {
        bookingAdapter.updateData(bookings)
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
        _binding = null
    }
}