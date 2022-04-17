package com.techyourchance.dagger2course.screens.common.dialogs

import androidx.fragment.app.FragmentManager

class DialogManager(private val fragmentManager: FragmentManager) {


    fun showErrorDialogFragment(){
        fragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }
}