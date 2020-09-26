/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.dessertpusher

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.example.android.dessertpusher.databinding.ActivityMainBinding
import timber.log.Timber
import androidx.lifecycle.LifecycleObserver

const val KEY_REVENUE = "key_revenue"
const val KEY_DESSERTS_SOLD = "key_desserts_sold"
const val KEY_DESSERT_TIMER = "key_dessert_timer"

class MainActivity : AppCompatActivity(), LifecycleObserver {

    private var revenue = 0
    private var dessertsSold = 0
    private lateinit var dessertTimer: DessertTimer

    // Contains all the views
    private lateinit var binding: ActivityMainBinding

    /** Dessert Data **/

    /**
     * Simple data class that represents a dessert. Includes the resource id integer associated with
     * the image, the price it's sold for, and the startProductionAmount, which determines when
     * the dessert starts to be produced.
     */
    data class Dessert(val imageId: Int, val price: Int, val startProductionAmount: Int)

    // Create a list of all desserts, in order of when they start being produced
    private val allDesserts = listOf(
            Dessert(R.drawable.cupcake, 5, 0),
            Dessert(R.drawable.donut, 10, 1),
            Dessert(R.drawable.eclair, 15, 2),
            Dessert(R.drawable.froyo, 30, 3),
            Dessert(R.drawable.gingerbread, 50, 4),
            Dessert(R.drawable.honeycomb, 100, 5),
            Dessert(R.drawable.icecreamsandwich, 500, 6),
            Dessert(R.drawable.jellybean, 1000, 7),
            Dessert(R.drawable.kitkat, 2000, 8),
            Dessert(R.drawable.lollipop, 3000, 9),
            Dessert(R.drawable.marshmallow, 4000, 10),
            Dessert(R.drawable.nougat, 5000, 11),
            Dessert(R.drawable.oreo, 6000, 12)
    )
    private var currentDessert = allDesserts[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.e("onCreate called")

        dessertTimer = DessertTimer(this.lifecycle)

        if (savedInstanceState != null) {
            revenue = savedInstanceState.getInt(KEY_REVENUE)
            dessertsSold = savedInstanceState.getInt(KEY_DESSERTS_SOLD)
            dessertTimer.secondsCount = savedInstanceState.getInt(KEY_DESSERT_TIMER)
        }

        // Use Data Binding to get reference to the views
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.dessertButton.setOnClickListener {
            onDessertClicked()
        }

        // Set the TextViews to the right values
        binding.revenue = revenue
        binding.amountSold = dessertsSold

        // Make sure the correct dessert is showing
        binding.dessertButton.setImageResource(currentDessert.imageId)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_REVENUE, revenue)
        outState.putInt(KEY_DESSERTS_SOLD, dessertsSold)
        outState.putInt(KEY_DESSERT_TIMER, dessertTimer.secondsCount)
        Timber.e("onSaveInstanceState called")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.e("onRestoreInstanceState called")
    }

    override fun onStart() {
        super.onStart()
        Timber.e("onStart called")
    }

    override fun onResume() {
        super.onResume()
        Timber.e("onResume called")
    }

    override fun onPause() {
        super.onPause()
        Timber.e("onPause called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.e("onDestroy called")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.e("onRestart called")
    }

    override fun onStop() {
        super.onStop()
        Timber.e("OnStop called")
    }

    /**
     * Updates the score when the dessert is clicked. Possibly shows a new dessert.
     */
    private fun onDessertClicked() {

        // Update the score
        revenue += currentDessert.price
        dessertsSold++

        binding.revenue = revenue
        binding.amountSold = dessertsSold

        // Show the next dessert
        showCurrentDessert()
    }

    /**
     * Determine which dessert to show.
     */
    private fun showCurrentDessert() {
        var newDessert = allDesserts[0]
        for (dessert in allDesserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                newDessert = dessert
            }
            // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
            // you'll start producing more expensive desserts as determined by startProductionAmount
            // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
            // than the amount sold.
            else break
        }

        // If the new dessert is actually different than the current dessert, update the image
        if (newDessert != currentDessert) {
            currentDessert = newDessert
            binding.dessertButton.setImageResource(newDessert.imageId)
        }
    }

    /**
     * Menu methods
     */
    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
                .setText(getString(R.string.share_text, dessertsSold, revenue))
                .setType("text/plain")
                .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                    Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }
}
