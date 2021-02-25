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
package com.example.androiddevchallenge.components

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.Gender

@Composable
fun KittenGenderIcon(
    gender: Gender,
    modifier: Modifier = Modifier,
) {

    val genderIconAndColor = when (gender) {
        Gender.FEMALE -> Icons.Default.Female to colorResource(id = R.color.gender_female)
        Gender.MALE -> Icons.Default.Male to colorResource(id = R.color.gender_male)
    }

    Icon(
        imageVector = genderIconAndColor.first,
        tint = genderIconAndColor.second,
        contentDescription = gender.toString(),
        modifier = modifier
    )
}
