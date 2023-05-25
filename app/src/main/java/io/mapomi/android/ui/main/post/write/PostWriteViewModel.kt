package io.mapomi.android.ui.main.post.write

import android.widget.EditText
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.model.post.PostModel
import io.mapomi.android.remote.dataclass.request.post.PostBuildRequest
import io.mapomi.android.ui.base.BaseViewModel
import io.mapomi.android.ui.main.post.adapter.PostDateAdapter
import io.mapomi.android.utils.TimeUtil
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class PostWriteViewModel @Inject constructor(
    val postModel: PostModel
) : BaseViewModel() {

    val postType get() = postModel.postType
    val typeBoxVisible = MutableStateFlow(false)


    val title = MutableStateFlow("")
    val timeState = MutableStateFlow(MORNING)
    val hh = MutableStateFlow("")
    val mm = MutableStateFlow("")
    val departure = MutableStateFlow("")
    val destination = MutableStateFlow("")
    val duration = MutableStateFlow("")
    val location = MutableStateFlow("")
    val content = MutableStateFlow("")

    val adapter = PostDateAdapter().apply {
        TimeUtil.getPostDateList {
            setDateList(it)
        }
    }

    /*******************************************
     **** init 시 실행합니다
     ******************************************/
    init {

        /**
         * BUILD 모드일 때
         */
        invokeBooleanFlow(postModel.flagPrepareBuild) {
            initDataByMode(PostBuildRequest(title = "", schedule = TimeUtil.makeRequestSchedule()))
        }

        /**
         * EDIT 모드일 때
         */
        invokeBooleanFlow(postModel.flagPrepareEdit) {
            initDataByMode(postModel.getRequestForEdit())
        }

        useFlag(postModel.flagUploadSuccess){
            navigation.revealHistory()
        }

    }

    private fun initDataByMode(buildRequest: PostBuildRequest)
    {

        buildRequest.let {

            val schedule = TimeUtil.splitSchedule(it.schedule)

            title.value = it.title
            timeState.value = if(schedule[1].toString().toBoolean()) AFTERNOON else MORNING
            hh.value = schedule[2].toString()
            mm.value = schedule[3].toString()
            departure.value = it.departure ?: ""
            destination.value = it.destination ?: ""
            duration.value = it.duration ?: ""
            content.value = it.content ?: ""
        }
    }

    /*******************************************
     **** 입력을 받습니다
     ******************************************/

    fun typeTitle(cs : CharSequence, editText: EditText)
    {
        if (!isMaxLines(2, editText,title.value))
            type(title,cs)
    }

    fun typeHH(cs : CharSequence) = type(hh,cs)

    fun typeMM(cs : CharSequence) = type(mm,cs)

    fun typeDuration(cs : CharSequence) = type(duration,cs)

    fun typeDeparture(cs : CharSequence) = type(departure,cs)

    fun typeDestination(cs : CharSequence) = type(destination,cs)

    fun typeLocation(cs : CharSequence) = type(location,cs)

    fun typeContent(cs : CharSequence, editText: EditText)
    {
        if (!isMaxLines(4, editText,content.value))
            type(content,cs)
    }

    private fun type(value: MutableStateFlow<String>, cs : CharSequence)
    {
        cs.toString().let {
            if (value.value != it) value.value = it
        }
    }

    private fun isMaxLines(maxLine : Int, editText: EditText, value : String) : Boolean
    {
        if (editText.lineCount > maxLine){
            editText.setText(value)
            editText.setSelection(value.length)
            return true
        }

        return false
    }

    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    fun changeTimeState(state : Boolean)
    {
        timeState.value = state
    }

    fun openTypeBox()
    {
        typeBoxVisible.value = true
    }

    fun selectTypeBox(type : Boolean)
    {
        typeBoxVisible.value = false
        postModel.changePostType(type)
    }

    fun onSubmit()
    {
        val request = PostBuildRequest(
            title = title.value,
            schedule = TimeUtil.makeRequestSchedule(adapter.getSelectedDate(),timeState.value,hh.value,mm.value),
            duration = duration.value,
            departure = departure.value,
            destination = destination.value,
            content = content.value
        )
        uiModel.showToast(request.schedule)
/*        postModel.requestUploadPost(request)*/
    }


    companion object {
        const val  MORNING = false
        const val AFTERNOON = true
    }
}