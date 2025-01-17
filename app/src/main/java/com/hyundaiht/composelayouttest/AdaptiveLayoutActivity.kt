package com.hyundaiht.composelayouttest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.window.area.WindowAreaCapability
import androidx.window.area.WindowAreaController
import androidx.window.area.WindowAreaInfo
import androidx.window.area.WindowAreaSession
import androidx.window.core.ExperimentalWindowApi
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.hyundaiht.composelayouttest.ui.theme.ComposeUiTestTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class AdaptiveLayoutActivity : ComponentActivity() {
    private val tag = javaClass.simpleName
    private lateinit var windowAreaController: WindowAreaController
    private lateinit var displayExecutor: Executor
    private var windowAreaSession: WindowAreaSession? = null
    private var windowAreaInfo: WindowAreaInfo? = null
    private var capabilityStatus: WindowAreaCapability.Status =
        WindowAreaCapability.Status.WINDOW_AREA_STATUS_UNSUPPORTED

    private val dualScreenOperation = WindowAreaCapability.Operation.OPERATION_PRESENT_ON_AREA
    private val rearDisplayOperation = WindowAreaCapability.Operation.OPERATION_TRANSFER_ACTIVITY_TO_AREA

    @OptIn(ExperimentalWindowApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.Default).launch {
            WindowInfoTracker.getOrCreate(this@AdaptiveLayoutActivity)
                .windowLayoutInfo(this@AdaptiveLayoutActivity)
                .collect { newLayoutInfo ->
                    val foldingFeature = newLayoutInfo.displayFeatures
                        .filterIsInstance<FoldingFeature>()
                        .firstOrNull()
                    Log.d(tag, "WindowLayoutInfo foldingFeature = ${foldingFeature?.orientation}")
                    Log.d(tag, "WindowLayoutInfo foldingFeature = ${foldingFeature?.state}")
                    Log.d(tag, "WindowLayoutInfo foldingFeature = ${foldingFeature?.isSeparating}")
                    Log.d(tag, "WindowLayoutInfo foldingFeature = ${foldingFeature?.bounds}")
                    Log.d(tag, "WindowLayoutInfo foldingFeature = ${foldingFeature?.occlusionType}")

                    for(displayFeature in newLayoutInfo.displayFeatures){
                        Log.d(tag, "WindowLayoutInfo newLayoutInfo = ${newLayoutInfo.displayFeatures}")
                    }

                }
        }

        CoroutineScope(Dispatchers.Default).launch {
            displayExecutor = ContextCompat.getMainExecutor(this@AdaptiveLayoutActivity)
            windowAreaController = WindowAreaController.getOrCreate()
            Log.d(tag, "windowAreaInfos = ${windowAreaController.windowAreaInfos}")

            windowAreaController.windowAreaInfos
                .map { info ->
                    Log.d(tag, "windowAreaInfos map1 info = $info")
                    info.firstOrNull { it.type == WindowAreaInfo.Type.TYPE_REAR_FACING }
                }
                .onEach { info ->
                    Log.d(tag, "windowAreaInfos onEach info = $info")
                    windowAreaInfo = info
                }
                .map { info ->
                    Log.d(tag, "windowAreaInfos map2 it = $info")
                    info?.getCapability(WindowAreaCapability.Operation.OPERATION_PRESENT_ON_AREA)?.status ?: WindowAreaCapability.Status.WINDOW_AREA_STATUS_UNSUPPORTED
                }
                .distinctUntilChanged()
                .collect {
                    capabilityStatus = it
                    Log.d(tag, "windowAreaInfos capabilityStatus = $capabilityStatus")

                    when (capabilityStatus) {
                        WindowAreaCapability.Status.WINDOW_AREA_STATUS_UNSUPPORTED -> {
                            Log.d(tag, "windowAreaInfos The selected display mode is not supported on this device.")
                        }
                        WindowAreaCapability.Status.WINDOW_AREA_STATUS_UNAVAILABLE -> {
                            Log.d(tag, "windowAreaInfos The selected display mode is not available.")
                        }
                        WindowAreaCapability.Status.WINDOW_AREA_STATUS_AVAILABLE -> {
                            Log.d(tag, "windowAreaInfos The selected display mode is available and can be enabled")
                        }
                        WindowAreaCapability.Status.WINDOW_AREA_STATUS_ACTIVE -> {
                            Log.d(tag, "windowAreaInfos The selected display mode is already active.")
                        }
                        else -> {
                            Log.d(tag, "windowAreaInfos The selected display mode status is unknown.")
                        }
                    }
                }
        }

        enableEdgeToEdge()
        setContent {
            ComposeUiTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val state = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .verticalScroll(state)
                    ) {
                        val windowInfo = currentWindowAdaptiveInfo()
                        Log.d(tag, "windowInfo windowSizeClass = ${windowInfo.windowSizeClass}")
                        Log.d(tag, "windowInfo windowPosture = ${windowInfo.windowPosture}")
                    }
                }
            }
        }
    }

    /**
     * FoldingFeature 객체에 포함된 정보를 사용하여 앱은 휴대전화가 표면에 있고 힌지가 수평 위치에 있으며 폴더블 화면이 절반 정도 열려 있는 탁자 등의 상태를 지원할 수 있습니다.
     *
     * 탁자 모드는 사용자가 휴대전화를 손에 쥐고 있지 않은 상태에서 휴대전화를 조작할 수 있는 편리함을 제공합니다. 탁자 모드는 미디어를 보고, 사진을 찍고, 영상 통화를 할 때 적합합니다.
     *
     * FoldingFeature.State 및 FoldingFeature.Orientation를 사용하여 기기가 탁자 모드인지 확인할 수 있습니다.
     *
     * @param foldFeature
     * @return
     */
    @OptIn(ExperimentalContracts::class)
    fun isTableTopPosture(foldFeature : FoldingFeature?) : Boolean {
        contract { returns(true) implies (foldFeature != null) }
        return foldFeature?.state == FoldingFeature.State.HALF_OPENED &&
                foldFeature.orientation == FoldingFeature.Orientation.HORIZONTAL
    }

    /**
     * 또 다른 고유한 폴더블 기능은 기기가 반쯤 열려 있고 힌지가 수직인 책 모드입니다. 책 상태는 eBook을 읽을 때 좋습니다. 책 모드는 대형 폴더블 화면에 열린 2페이지 레이아웃을 제본된 책처럼 사용하여 실제 책을 읽는 듯한 경험을 제공합니다.
     *
     * 핸즈프리로 사진을 찍을 때 다른 가로세로 비율을 캡처하려는 경우 사진에도 사용할 수 있습니다.
     *
     * 탁자 상태에 사용된 것과 동일한 기법으로 책 상태를 구현합니다. 유일한 차이점은 코드에서 접기 기능 방향이 가로가 아닌 세로인지 확인해야 한다는 것입니다.
     *
     * @param foldFeature
     * @return
     */
    @OptIn(ExperimentalContracts::class)
    fun isBookPosture(foldFeature : FoldingFeature?) : Boolean {
        contract { returns(true) implies (foldFeature != null) }
        return foldFeature?.state == FoldingFeature.State.HALF_OPENED &&
                foldFeature.orientation == FoldingFeature.Orientation.VERTICAL
    }
}