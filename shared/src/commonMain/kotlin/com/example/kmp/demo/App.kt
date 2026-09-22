package com.example.kmp.demo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kmp_demo.shared.generated.resources.Res
import kmp_demo.shared.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Click me!",
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Red)
                    .padding(20.dp)
                    .size(100.dp)
                    .clickable {
                        showContent = !showContent
                    }

            )


            Text(
                text = "Click me!",
                modifier = Modifier
                    .background(Color.Blue)
                    .size(100.dp)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable {
                        showContent = !showContent
                    }
            )

            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                    Text("时区: ${currentTimeZoneId()}")
                }
            }

            LazyColumn {
                items(100) { index ->
                    if (index == 0) {
                        LazyRow {
                            items(10) { index ->
                                Text(
                                    text = "Item $index",
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "Item $index",
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        )
                    }

                }
            }
        }
    }
}