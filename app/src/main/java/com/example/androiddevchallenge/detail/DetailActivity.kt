/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.Gender
import com.example.androiddevchallenge.data.Kitten
import com.example.androiddevchallenge.data.KittensProvider
import com.example.androiddevchallenge.ui.theme.AdoptMeowTheme
import dev.chrisbanes.accompanist.glide.GlideImage

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val kittenId = intent.getStringExtra(EXTRA_KITTEN_ID) ?: ""
        val kitten = KittensProvider.getKittenById(kittenId)

        setContent {
            AdoptMeowTheme {
                AdoptMeowDetailScaffold(
                    onNavigationIconClicked = { finish() }
                ) {
                    if (kitten != null) {
                        KittenDetails(
                            kitten = kitten,
                            onImageClicked = {
                                // open fullscreen image
                            },
                            onAdoptClicked = {
                                // lucky cat!
                            }
                        )
                    } else {
                        KittenNotFoundMessage()
                    }
                }
            }
        }
    }

    companion object {

        private const val EXTRA_KITTEN_ID = "kittenId"

        fun getIntent(context: Context, kittenId: String) =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_KITTEN_ID, kittenId)
            }
    }
}

@Composable
fun AdoptMeowDetailScaffold(
    onNavigationIconClicked: () -> Unit,
    content: @Composable (PaddingValues) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = onNavigationIconClicked) {
                        Icon(Icons.Default.ArrowBack, "back")
                    }
                }
            )
        },
        content = content
    )
}

@Composable
fun KittenDetails(
    kitten: Kitten,
    onImageClicked: (String) -> Unit,
    onAdoptClicked: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxHeight()
    ) {

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            KittenHeader(kitten.name, kitten.age, kitten.gender, kitten.breed)
            Spacer(modifier = Modifier.size(16.dp))
            KittenImages(kitten.images, onImageClicked)
            KittenDescription(kitten.description)
            Spacer(modifier = Modifier.size(96.dp))
        }

        Button(
            onClick = onAdoptClicked,
            contentPadding = PaddingValues(
                start = 32.dp,
                end = 32.dp,
            ),
            modifier = Modifier
                .padding(
                    bottom = 24.dp,
                )
                .height(56.dp)
                .align(Alignment.BottomCenter)
        ) {
            Icon(Icons.Default.FavoriteBorder, "") // VolunteerActivism
            Text(
                text = "Adopt me now!",
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun KittenHeader(name: String, age: Int, gender: Gender, breed: String) {
    Column(
        modifier = Modifier.padding(start = 16.dp),
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.h4,
        )
        Text(
            text = "$age years old, $gender, $breed",
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
fun KittenImages(
    images: List<String>,
    onImageClicked: (String) -> Unit
) {

    val spacingStartEnd = 16.dp
    val spacingBetween = 4.dp

    LazyRow(
        modifier = Modifier.height(240.dp),
    ) {

        itemsIndexed(images) { index, imageUrl ->

            Surface(
                modifier = Modifier
                    .padding(
                        start = if (index == 0) spacingStartEnd else spacingBetween,
                        end = if (index == images.size - 1) spacingStartEnd else spacingBetween,
                        bottom = 24.dp,
                    ),
                shape = RoundedCornerShape(16.dp),
                elevation = 4.dp,
            ) {
                GlideImage(
                    data = imageUrl,
                    fadeIn = false,
                    loading = {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(Color(0xfffafafa)),
                        )
                    },
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(.8f)
                        .fillMaxHeight()
                        .clickable { onImageClicked(imageUrl) },
                )
            }
        }
    }
}

@Composable
fun KittenDescription(description: String) {

    Text(
        text = description,
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
        )
    )
}

@Composable
fun KittenNotFoundMessage() {
    Text(
        "Kitten not found :(\nTry to choose another one instead!",
        modifier = Modifier.padding(16.dp)
    )
}
