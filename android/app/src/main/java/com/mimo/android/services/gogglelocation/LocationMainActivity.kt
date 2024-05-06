package com.mimo.android.services.gogglelocation

import android.annotation.SuppressLint
import android.location.Location
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mimo.android.MainActivity

@SuppressLint("MissingPermission")
fun MainActivity.getLastLocation(){
    val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(this)

    fusedLocationProviderClient.lastLocation
        .addOnSuccessListener { success: Location? ->
            success?.let { location ->
                Toast.makeText(
                    this,
                    "${location.latitude}, ${location.longitude}",
                    Toast.LENGTH_SHORT
                ).show()
                println("가장 마지막 갱신된 위치 : ${location.latitude}, ${location.longitude}")
            }
        }
        .addOnFailureListener { fail ->
            Toast.makeText(
                this,
                fail.localizedMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
}

@SuppressLint("MissingPermission")
fun MainActivity.getCurrentLocation(){
    val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(this)

    fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
        .addOnSuccessListener { success: Location? ->
            success?.let { location ->
                Toast.makeText(
                    this,
                    "${location.latitude}, ${location.longitude}",
                    Toast.LENGTH_SHORT
                ).show()
                println("현재 위치 : ${location.latitude}, ${location.longitude}")
            }
        }
        .addOnFailureListener { fail ->
            Toast.makeText(
                this,
                fail.localizedMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
}