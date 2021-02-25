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
package com.example.androiddevchallenge.data

import com.example.androiddevchallenge.components.getRandomNameForGender
import java.util.UUID

private const val NUM_OF_ADDITIONAL_KITTENS = 0

// from catipsum.com
private const val CAT_IPSUM = "I love cats i am one wake up scratch humans leg for food then purr then i have a and relax. Run in circles ooooh feather moving feather!, yet floof tum, tickle bum, jellybean footies curly toes and the fat cat sat on the mat bat away with paws eat a plant, kill a hand, human is in bath tub, emergency! drowning! meooowww! yet if it fits, i sits. Missing until dinner time making sure that fluff gets into the owner's eyes for i like to spend my days sleeping and eating fishes that my human fished for me we live on a luxurious yacht, sailing proudly under the sun, i like to walk on the deck, watching the horizon, dreaming of a good bowl of milk i like to spend my days sleeping and eating fishes that my human fished for me we live on a luxurious yacht, sailing proudly under the sun, i like to walk on the deck, watching the horizon, dreaming of a good bowl of milk. Pushes butt to face meow loudly just to annoy owners so meow to be let out carefully drink from water glass and then spill it everywhere and proceed to lick the puddle. X leave fur on owners clothes unwrap toilet paper stare at the wall, play with food and get confused by dust, or make muffins, or pet me pet me pet me pet me, bite, scratch, why are you petting me found somthing move i bite it tail. You call this cat food meow and purr meow go back to sleep owner brings food and water tries to pet on head, so scratch get sprayed by water because bad cat check cat door for ambush 10 times before coming in chirp at birds. Blow up sofa in 3 seconds swipe at owner's legs eat fish on floor for jumps off balcony gives owner dead mouse at present then poops in litter box snatches yarn and fights with dog cat chases laser then plays in grass finds tiny spot in cupboard and sleeps all day jumps in bathtub and meows when owner fills food dish the cat knocks over the food dish cat slides down the water slide and into pool and swims even though it does not like water, enslave the hooman and i like cats because they are fat and fluffy. Do i like standing on litter cuz i sits when i have spaces, my cat buddies have no litter i live in luxury cat life gnaw the corn cob, so lick butt. Instantly break out into full speed gallop across the house for no reason peer out window, chatter at birds, lure them to mouth if it fits i sits for i want to go outside let me go outside nevermind inside is better. Have a lot of grump in yourself because you can't forget to be grumpy and not be like king grumpy cat human is washing you why halp oh the horror flee scratch hiss bite, eats owners hair then claws head purrr purr littel cat, little cat purr purr i like big cats and i can not lie, need to chase tail, yet you call this cat food. Lick left leg for ninety minutes, still dirty murf pratt ungow ungow yet cat not kitten around for find something else more interesting. Cat milk copy park pee walk owner escape bored tired cage droppings sick vet vomit soft kitty warm kitty little ball of furr nap all day purr when being pet. I like fish stare at ceiling, for licks paws terrorize the hundred-and-twenty-pound rottweiler and steal his bed, not sorry so pushes butt to face for scratch at fleas, meow until belly rubs, hide behind curtain when vacuum cleaner is on scratch strangers and poo on owners food. Cats are a queer kind of folk. I heard this rumor where the humans are our owners, pfft, what do they know?! hit you unexpectedly who's the baby, so stare at imaginary bug eat grass, throw it back up. Pushed the mug off the table kitty loves pigs. Roll on the floor purring your whiskers off paw at beetle and eat it before it gets away for climb leg, or spot something, big eyes, big eyes, crouch, shake butt, prepare to pounce fat baby cat best buddy little guy, but destroy couch sitting in a box. Meow tweeting a baseball lick plastic bags stinky cat scratch the furniture."

// hardcoded stuff. of course this would usually include repo/db/network fun
object KittensProvider {

    fun getAllKittens(): List<Kitten> = kittens

    fun getKittenById(id: String): Kitten? = kittens.find { it.id == id }

    @OptIn(ExperimentalStdlibApi::class)
    private val kittens = buildList {

        add(
            Kitten(
                id = randomUUID(),
                name = "Toni",
                gender = Gender.MALE,
                age = 6,
                breed = "European Shorthair",
                description = "Toni is the best cat in the world!\nHe's not actually available for adoption!\n\nHis hobbies are sleeping, eating, cuddling, pooping, eating and eating. He also likes to be annoying while I'm trying to get more familiar with Compose here. Time to take a break and give him some treats!",
                images = listOf(
                    "https://i.imgur.com/VQBotmU.jpg",
                    "https://i.imgur.com/wX5hTvA.jpg",
                    "https://i.imgur.com/Ie4UPEh.jpg",
                    "https://i.imgur.com/f85wqhA.jpg",
                    "https://i.imgur.com/TkVVrSw.jpg",
                )
            )
        )

        add(
            Kitten(
                id = randomUUID(),
                name = "Mafalda",
                gender = Gender.FEMALE,
                age = 8,
                breed = "Black cat with yellow eyes",
                description = "Mafalda is also the best cat in the world! And no, you also cannot have her!",
                images = listOf(
                    "https://i.imgur.com/qs2UG0V.jpg",
                )
            )
        )

        repeat(NUM_OF_ADDITIONAL_KITTENS) {

            val id = randomUUID()
            val gender = randomGender()
            val name = getRandomNameForGender(gender)

            add(
                Kitten(
                    id = id,
                    name = name,
                    gender = gender,
                    age = (1..20).random(),
                    breed = "Unknown breed",
                    description = CAT_IPSUM,
                    images = listOf(
                        "https://cataas.com/cat?${id}1",
                        "https://cataas.com/cat?${id}2",
                        "https://cataas.com/cat?${id}3",
                        "https://cataas.com/cat?${id}4",
                        "https://cataas.com/cat?${id}5",
                    )
                )
            )
        }
    }

    private fun randomUUID(): String = UUID.randomUUID().toString()

    private fun randomGender(): Gender = Gender.values().random()
}
