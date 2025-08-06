package me.bumiller.civoris.feature.about.screen.notice

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
import me.bumiller.civoris.about.Res
import me.bumiller.civoris.about.notice_screen_title
import me.bumiller.civoris.common.ui.color.hexString
import me.bumiller.civoris.ui.layout.AppBarLayout
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

private const val FileName = "files/license_report.html"

/**
 * Screen that hosts the web-view containing the generated notices file.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun NoticeScreen() {
    var htmlData by remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(Unit) {
        htmlData = Res.readBytes(FileName).decodeToString()
    }

    if (htmlData == null) {
        return
    }

    val webViewState = rememberWebViewStateWithHTMLData(processReport(htmlData!!))

    AppBarLayout(
        modifier = Modifier
            .fillMaxSize(),
        title = {
            Text(stringResource(Res.string.notice_screen_title))
        },
        contentPadding = PaddingValues(),
        firstContent = {
            WebView(
                modifier = Modifier
                    .fillMaxSize(),
                state = webViewState
            )
        },
        secondContent = {}
    )
}

private const val StyleLineIndex = 3

@Composable
private fun processReport(reportData: String): String {
    val lines = reportData.split(System.lineSeparator())

    val styleLine = buildStyleLine(
        backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        textColor = MaterialTheme.colorScheme.onSurface,
        linkColor = MaterialTheme.colorScheme.secondary,
        noticeColor = MaterialTheme.colorScheme.surfaceContainerLow
    )

    val list = mutableListOf<String>()
    list.addAll(lines.subList(0, StyleLineIndex))
    list.add(styleLine)
    list.addAll(lines.subList(StyleLineIndex + 1, lines.size))

    return list.joinToString(System.lineSeparator())
}

private fun buildStyleLine(
    backgroundColor: Color,
    textColor: Color,
    linkColor: Color,
    noticeColor: Color
) =
    "<style>body { font-family: sans-serif; background-color: #${backgroundColor.hexString}; color: #${textColor.hexString}; } a { color: #${linkColor.hexString}; } pre { background-color: #${noticeColor.hexString}; padding: 1em; white-space: pre-wrap; word-break: break-word; display: inline-block; }</style>"
