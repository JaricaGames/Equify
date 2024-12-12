package com.jarica.compartirgastos.presentation.newGroup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jarica.compartirgastos.presentation.ui.next

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGroupScreen(navigateToInitial: () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color.Cyan,
                ),
                navigationIcon = {
                    IconButton(onClick = {navigateToInitial()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    Text(next, modifier = Modifier.padding(horizontal = 16.dp))
                },
                title = {
                }
            )
        }
    ) { paddingValues ->
        MainView(paddingValues)
    }
}

@Composable
fun MainView(paddingValues: PaddingValues) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Cyan)) {
           // TextField()

    }
}

