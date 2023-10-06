package com.atinytot.vegsp_v_1.models

import android.os.Bundle
import androidx.lifecycle.ViewModel

class FragmentStateViewModel: ViewModel() {

    private val fragmentStates: MutableMap<String, Bundle> = mutableMapOf()

    fun saveFragmentState(fragmentTag: String, state: Bundle) {
        fragmentStates[fragmentTag] = state
    }

    fun getFragmentState(fragmentTag: String): Bundle? {
        return fragmentStates[fragmentTag]
    }
}