package com.example.daznassignment.ui.theme.viewModel

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.daznassignment.ui.theme.DaznAssignmentTheme
import com.example.daznassignment.ui.theme.components.HomeScreenFragment
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ScreensViewModelTest{
    @get: Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            DaznAssignmentTheme {

            }
        }
    }
}