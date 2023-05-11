package io.mapomi.android.model

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalValueModel @Inject constructor(@ApplicationContext val context: Context) {

    fun getString(resourceId : Int) = context.getString(resourceId)
}