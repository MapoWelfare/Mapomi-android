package io.mapomi.android.model.navigate

import io.mapomi.android.enums.AuthPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthNavigation @Inject constructor() {

    private val _topPage = MutableStateFlow(AuthPage.LOGIN)
    val topPage : StateFlow<AuthPage> get() = _topPage

    private val pageHistory = mutableListOf<AuthPage>()

    fun changePage(page: AuthPage)
    {
        if (_topPage.value!=page)
        {
            pageHistory.add(_topPage.value)
            _topPage.value = page
        }
    }

    fun revealHistory() : Boolean {
        if (pageHistory.isNotEmpty())
        {
            _topPage.value = pageHistory.last()
            pageHistory.removeLast()
            return true
        }
        return false
    }

    fun clearHistory() {
        pageHistory.clear()
    }
}