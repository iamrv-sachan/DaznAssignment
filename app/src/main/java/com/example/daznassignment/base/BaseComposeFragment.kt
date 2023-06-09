package com.example.daznassignment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.daznassignment.ui.theme.DaznAssignmentTheme


abstract class BaseComposeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getExtras()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                DaznAssignmentTheme {
                    SetupUI()
                }
            }
        }
    }

    @Composable
    abstract fun SetupUI()

    open fun getExtras() {
    }
}
