package com.jskako.core.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.jskako.core.domain.preferences.Preferences as AppPreferences
import com.jskako.core.domain.models.ActivityLevel
import com.jskako.core.domain.models.Gender
import com.jskako.core.domain.models.GoalType
import com.jskako.core.domain.models.UserInfo
import com.jskako.core.domain.preferences.Preferences.Companion.KEY_ACTIVITY_LEVEL
import com.jskako.core.domain.preferences.Preferences.Companion.KEY_AGE
import com.jskako.core.domain.preferences.Preferences.Companion.KEY_CARB_RATIO
import com.jskako.core.domain.preferences.Preferences.Companion.KEY_FAT_RATIO
import com.jskako.core.domain.preferences.Preferences.Companion.KEY_GENDER
import com.jskako.core.domain.preferences.Preferences.Companion.KEY_GOAL_TYPE
import com.jskako.core.domain.preferences.Preferences.Companion.KEY_HEIGHT
import com.jskako.core.domain.preferences.Preferences.Companion.KEY_PROTEIN_RATIO
import com.jskako.core.domain.preferences.Preferences.Companion.KEY_WEIGHT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DefaultPreferences(
    private val dataStore: DataStore<Preferences>
) : AppPreferences {
    override suspend fun saveGender(gender: Gender) {
        dataStore.edit { preferences ->
            preferences[KEY_GENDER] = gender.name
        }
    }

    override suspend fun saveAge(age: Int) {
        dataStore.edit { preferences ->
            preferences[KEY_AGE] = age
        }
    }

    override suspend fun saveWeight(weight: Float) {
        dataStore.edit { preferences ->
            preferences[KEY_WEIGHT] = weight
        }
    }

    override suspend fun saveHeight(height: Int) {
        dataStore.edit { preferences ->
            preferences[KEY_HEIGHT] = height
        }
    }

    override suspend fun saveActivityLevel(level: ActivityLevel) {
        dataStore.edit { preferences ->
            preferences[KEY_ACTIVITY_LEVEL] = level.name
        }
    }

    override suspend fun saveGoalType(type: GoalType) {
        dataStore.edit { preferences ->
            preferences[KEY_GOAL_TYPE] = type.name
        }
    }

    override suspend fun saveCarbRatio(ratio: Float) {
        dataStore.edit { preferences ->
            preferences[KEY_CARB_RATIO] = ratio
        }
    }

    override suspend fun saveProteinRatio(ratio: Float) {
        dataStore.edit { preferences ->
            preferences[KEY_PROTEIN_RATIO] = ratio
        }
    }

    override suspend fun saveFatRatio(ratio: Float) {
        dataStore.edit { preferences ->
            preferences[KEY_FAT_RATIO] = ratio
        }
    }

    override suspend fun loadUserInfo(): Flow<UserInfo> = dataStore.data.map { preferences ->
        val gender = Gender.fromString(preferences[KEY_GENDER] ?: "female")
        val age = preferences[KEY_AGE] ?: -1
        val weight = preferences[KEY_WEIGHT] ?: -1f
        val height = preferences[KEY_HEIGHT] ?: -1
        val activity = ActivityLevel.fromString(preferences[KEY_ACTIVITY_LEVEL] ?: "medium")
        val goalType = GoalType.fromString(preferences[KEY_GOAL_TYPE] ?: "keep_weight")
        val carbRatio = preferences[KEY_CARB_RATIO] ?: -1f
        val proteinRatio = preferences[KEY_PROTEIN_RATIO] ?: -1f
        val fatRatio = preferences[KEY_FAT_RATIO] ?: -1f

        UserInfo(
            gender,
            age,
            weight,
            height,
            activity,
            goalType,
            carbRatio,
            proteinRatio,
            fatRatio
        )
    }
}