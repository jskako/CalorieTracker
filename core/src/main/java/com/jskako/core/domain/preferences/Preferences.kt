package com.jskako.core.domain.preferences

import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jskako.core.domain.models.ActivityLevel
import com.jskako.core.domain.models.Gender
import com.jskako.core.domain.models.GoalType
import com.jskako.core.domain.models.UserInfo
import kotlinx.coroutines.flow.Flow

interface Preferences {
    suspend fun saveGender(gender: Gender)
    suspend fun saveAge(age: Int)
    suspend fun saveWeight(weight: Float)
    suspend fun saveHeight(height: Int)
    suspend fun saveActivityLevel(level: ActivityLevel)
    suspend fun saveGoalType(type: GoalType)
    suspend fun saveCarbRatio(ratio: Float)
    suspend fun saveProteinRatio(ratio: Float)
    suspend fun saveFatRatio(ratio: Float)

    suspend fun loadUserInfo(): Flow<UserInfo>

    companion object {
        val KEY_GENDER = stringPreferencesKey("gender")
        val KEY_AGE = intPreferencesKey("age")
        val KEY_WEIGHT = floatPreferencesKey("weight")
        val KEY_HEIGHT = intPreferencesKey("height")
        val KEY_ACTIVITY_LEVEL = stringPreferencesKey("activity")
        val KEY_GOAL_TYPE = stringPreferencesKey("goal_type")
        val KEY_CARB_RATIO = floatPreferencesKey("carb_ratio")
        val KEY_PROTEIN_RATIO = floatPreferencesKey("protein_ratio")
        val KEY_FAT_RATIO = floatPreferencesKey("fat_ratio")
    }
}