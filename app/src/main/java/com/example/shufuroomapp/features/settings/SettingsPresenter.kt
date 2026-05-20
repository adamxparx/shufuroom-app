package com.example.shufuroomapp.features.settings

import com.example.shufuroomapp.core.api.RetrofitClient
import com.example.shufuroomapp.core.utils.PrefManager
import com.example.shufuroomapp.features.profile.data.ChangePasswordRequest
import com.example.shufuroomapp.features.profile.data.EditProfileRequest
import com.example.shufuroomapp.features.profile.data.MessageResponse
import com.example.shufuroomapp.features.profile.data.UpdateProfileResponse
import com.example.shufuroomapp.features.profile.data.UserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsPresenter(
    private var view: SettingsContract.View?,
    private var prefManager: PrefManager
) : SettingsContract.Presenter {

    private val token = "Bearer ${prefManager.getToken()}"

    override fun loadCurrentProfile() {
        view?.showLoading(true)
        RetrofitClient.instance.getMyProfile(token).enqueue(object : Callback<UserProfile> {
            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                view?.showLoading(false)
                if (response.isSuccessful) {
                    response.body()?.let {
                        view?.populateProfile(it.firstName, it.lastName)
                    }
                }
            }
            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                view?.showLoading(false)
                view?.showMessage("Failed to load profile info")
            }
        })
    }

    override fun updateProfile(firstName: String, lastName: String) {
        val request = EditProfileRequest(firstName, lastName)
        view?.showLoading(true)

        RetrofitClient.instance.updateProfile(token, request).enqueue(object : Callback<UpdateProfileResponse> {
            override fun onResponse(call: Call<UpdateProfileResponse>, response: Response<UpdateProfileResponse>) {
                view?.showLoading(false)
                if (response.isSuccessful) view?.showMessage("Profile Updated!")
            }
            override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                view?.showLoading(false)
                view?.showMessage("Update failed")
            }
        })
    }

    override fun changePassword(
        currentPass: String,
        newPass: String,
        confirmPass: String
    ) {
        if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            view?.showMessage("All fields are required")
            return
        }
        if (newPass != confirmPass) {
            view?.showMessage("New passwords do not match")
            return
        }

        view?.showLoading(true)
        val request = ChangePasswordRequest(currentPass, newPass)
        RetrofitClient.instance.changePassword(token, request).enqueue(object : Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                view?.showLoading(false)
                if (response.isSuccessful) {
                    view?.showMessage("Password Updated Successfully!")
                    view?.clearPasswordFields()
                } else if (response.code() == 401) {
                    view?.showMessage("Current password is incorrect")
                }
            }
            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                view?.showLoading(false)
                view?.showMessage("Network Error")
            }
        })
    }

    override fun logout() {
        prefManager.clear()
        view?.navigateToLogin()
    }

    override fun onDestroy() {
        view = null
    }


}