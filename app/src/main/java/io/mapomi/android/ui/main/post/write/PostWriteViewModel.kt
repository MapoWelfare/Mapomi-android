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

    private val regex = Regex("[^0-9]")

    val adapter = PostDateAdapter(this).apply {
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

        /**
         * 글 업로드 성공했을 때
         */
        useFlag(postModel.flagUploadSuccess){
            navigation.revealHistory()
        }

    }

    private fun initDataByMode(buildRequest: PostBuildRequest)
    {

        buildRequest.let {

            val schedule = TimeUtil.splitSchedule(it.schedule)
            title.value = it.title
            timeState.value = if(schedule[1] as Boolean) AFTERNOON else MORNING
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

    fun typeHH(cs : CharSequence, editText: EditText) {
        hh.value = filterOnlyNumber(cs,editText)
    }

    fun typeMM(cs : CharSequence, editText: EditText) {
        mm.value = filterOnlyNumber(cs,editText)
    }

    fun typeDuration(cs : CharSequence, editText: EditText) {
        duration.value = filterOnlyNumber(cs,editText)
    }

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
        adapter.updateSelectedItem()
    }

    fun onSubmit()
    {
        if (!isTimeValidate()) return
        val request = PostBuildRequest(
            title = title.value,
            schedule = TimeUtil.makeRequestSchedule(adapter.getSelectedDate(),timeState.value,hh.value,mm.value),
            duration = duration.value,
            departure = departure.value,
            destination = destination.value,
            content = content.value
        )
        postModel.requestUploadPost(request)
    }

    /*******************************************
     **** 유효성을 검사합니다
     ******************************************/

    /**
     * 숫자 외 다른 문자는 받지 않습니다
     */
    private fun filterOnlyNumber(text: CharSequence, editText: EditText) : String {
        val filteredText = text.toString().replace(regex,"")
        if (filteredText != text.toString()) {
            editText.setText(filteredText)
            editText.setSelection(filteredText.length)
        }
        return filteredText
    }

    /**
     * 시간이 유효한지 검사합니다
     */
    private fun isTimeValidate() : Boolean
    {
        if (hh.value.isEmpty()&&mm.value.isNotEmpty()){
            uiModel.showToast("시간을 입력해주세요")
            return false
        }

        if (hh.value.toInt() >= 12) {
            uiModel.showToast("0~11 사이의 시간을 입력해주세요")
            return false
        }

        if (mm.value.toInt() >= 60){
            uiModel.showToast("0~59 사이의 분을 입력해주세요")
            return false
        }

        if (adapter.isTodaySelected() && TimeUtil.isTimeBeforeCurrent(hh.value,mm.value,timeState.value)) {
            uiModel.showToast("지난 시간은 입력할 수 없습니다")
            return false
        }

        return true
    }


    companion object {
        const val  MORNING = false
        const val AFTERNOON = true
    }
}