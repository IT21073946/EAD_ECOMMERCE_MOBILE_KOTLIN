package com.example.ecommerceapp

import android.app.Application

class AppContext : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: AppContext? = null

        fun get(): AppContext {
            return instance ?: throw IllegalStateException("AppContext has not been initialized")
        }
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize anything you need for your app here
    }
}
