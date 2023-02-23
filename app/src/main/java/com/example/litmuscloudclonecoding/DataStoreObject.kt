package com.example.litmuscloudclonecoding

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

object DataStoreObject {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

}










































