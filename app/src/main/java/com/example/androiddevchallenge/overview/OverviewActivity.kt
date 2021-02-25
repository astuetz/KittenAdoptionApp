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
package com.example.androiddevchallenge.overview

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.components.KittenGenderIcon
import com.example.androiddevchallenge.data.Kitten
import com.example.androiddevchallenge.data.KittensProvider
import com.example.androiddevchallenge.detail.DetailActivity
import com.example.androiddevchallenge.ui.theme.AdoptMeowTheme
import dev.chrisbanes.accompanist.glide.GlideImage

typealias OnKittenClicked = (Kitten) -> Unit

class OverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AdoptMeowTheme {
                AdoptMeowOverviewScaffold { openDetails(it) }
            }
        }
    }

    private fun openDetails(kitten: Kitten) {
        startActivity(DetailActivity.getIntent(this, kitten.id))
    }
}

@Composable
fun AdoptMeowOverviewScaffold(
    onClick: OnKittenClicked,
) {

    val columnCount = integerResource(id = R.integer.column_count)

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) },
        content = {
            KittensGrid(KittensProvider.getAllKittens(), columnCount, onClick)
        }
    )
}

@Composable
fun KittensGrid(
    kittens: List<Kitten>,
    columnCount: Int,
    onClick: OnKittenClicked,
) {

    val kittenGroups = kittens.chunked(columnCount)

    // docs tell me to use LazyColumn + Row for now instead of LazyVerticalGrid, so here we go
    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {

        itemsIndexed(kittenGroups) { index, kittenGroup ->

            // putting spacings is no fun :/
            Box(
                modifier = Modifier.padding(
                    top = if (index == 0) 0.dp else 16.dp
                )
            ) {
                KittenRow(kittenGroup, columnCount, onClick)
            }
        }
    }
}

@Composable
fun KittenRow(
    kittenGroup: List<Kitten>,
    columnCount: Int,
    onClick: OnKittenClicked,
) {

    Row {

        kittenGroup.forEachIndexed { index, kitten ->
            Box(
                modifier = Modifier
                    .weight(1f, true)
                    .padding(
                        start = if (index == 0) 0.dp else 12.dp,
                        end = if (index == columnCount - 1) 0.dp else 12.dp,
                    ),
            ) {
                KittenCard(
                    kitten = kitten,
                    onClick = onClick,
                )
            }
        }

        // add empty items to fill the row in case we don't have enough items
        if (kittenGroup.size < columnCount) {
            repeat(columnCount - kittenGroup.size) {
                Box(modifier = Modifier.weight(1f, true))
            }
        }
    }
}

@Composable
fun KittenCard(
    kitten: Kitten,
    modifier: Modifier = Modifier,
    onClick: OnKittenClicked,
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.clickable { onClick(kitten) }
        ) {
            GlideImage(
                data = kitten.images.first(),
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
                    .height(120.dp)
                    .fillMaxWidth(),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${kitten.name}, ${kitten.age}",
                )
                KittenGenderIcon(
                    gender = kitten.gender,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}
