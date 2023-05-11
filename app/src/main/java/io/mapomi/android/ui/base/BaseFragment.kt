package io.mapomi.android.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.mapomi.android.model.insets.SoftKeyModel
import io.mapomi.android.model.navigate.Navigation
import io.mapomi.android.system.LogDebug
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject lateinit var navigation : Navigation

    @Inject lateinit var ime : SoftKeyModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getFragmentRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogDebug(javaClass.name,"FRAGMENT VIEW CREATED")
        onFragmentCreated()
    }

    /**
     * DataBinding 으로 inflate 한 fragment 의 rootView 를 넘겨주기
     */
    abstract fun getFragmentRoot() : View

    /**
     * Fragment 가 생성
     */
    abstract fun onFragmentCreated()

    /**
     * MainActivity 에서 Bottom Navigation 을 보여줄지
     */
    abstract fun showBottomBar() : Boolean

    /**
     * OnBackPressed 에서 이동할 뷰
     */
    abstract fun navigationOnBackPressed()
}