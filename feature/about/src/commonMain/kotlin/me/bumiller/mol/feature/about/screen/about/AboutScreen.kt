package me.bumiller.mol.feature.about.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import me.bumiller.mol.about.Res
import me.bumiller.mol.about.about_screen_title
import me.bumiller.mol.about.app_github_button_label
import me.bumiller.mol.about.app_github_link
import me.bumiller.mol.about.app_name
import me.bumiller.mol.about.app_version
import me.bumiller.mol.about.license_link
import me.bumiller.mol.about.license_text
import me.bumiller.mol.about.license_text_post
import me.bumiller.mol.about.license_text_pre
import me.bumiller.mol.about.logo
import me.bumiller.mol.about.maintainer
import me.bumiller.mol.about.maintainer_link
import me.bumiller.mol.about.maintainer_post
import me.bumiller.mol.about.maintainer_pre
import me.bumiller.mol.about.notice_button_label
import me.bumiller.mol.common.ui.style.disabledColor
import me.bumiller.mol.ui.components.MultiStyleText
import me.bumiller.mol.ui.components.TextStyleWithCallback
import me.bumiller.mol.ui.layout.AppBarLayout
import me.bumiller.mol.ui.layout.AppBarLayoutType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Screen that displays information about the app.
 *
 * @param onShowNotice Callback for when the user clicked on the notice button
 */
@Composable
internal fun AboutScreen(
    onShowNotice: () -> Unit
) {
    val githubLink = stringResource(Res.string.app_github_link)
    val licenseLink = stringResource(Res.string.license_link)
    val maintainerLink = stringResource(Res.string.maintainer_link)
    val uriHandler = LocalUriHandler.current

    AppBarLayout(
        modifier = Modifier
            .fillMaxSize(),
        title = {
            Text(stringResource(Res.string.about_screen_title))
        },
        layoutType = AppBarLayoutType.SizeAware(
            verticalFirstWeightRange = 0.4F..0.5F,
            horizontalFirstWeightRange = 0.4F..0.5F
        ),
        firstContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .padding(bottom = 16.dp, top = 8.dp),
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = stringResource(Res.string.app_name)
                )

                Text(
                    text = stringResource(Res.string.app_name),
                    style = MaterialTheme.typography.displayMedium
                )

                Text(
                    text = stringResource(Res.string.app_version),
                    style = MaterialTheme.typography.bodyMedium.disabledColor()
                )
            }
        },
        secondContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val normalTextStyle = MaterialTheme.typography.bodyMedium.disabledColor()
                val linkTextStyle =
                    MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.Underline)

                Column(
                    verticalArrangement = Arrangement
                        .spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            uriHandler.openUri(githubLink)
                        }
                    ) {
                        Text(stringResource(Res.string.app_github_button_label))
                    }

                    OutlinedButton(
                        onClick = onShowNotice
                    ) {
                        Text(stringResource(Res.string.notice_button_label))
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MultiStyleText(
                        modifier = Modifier,
                        style = normalTextStyle,
                        stringResource(Res.string.license_text_pre) to TextStyleWithCallback(
                            normalTextStyle
                        ),
                        stringResource(Res.string.license_text) to TextStyleWithCallback(
                            linkTextStyle
                        ) {
                            uriHandler.openUri(
                                licenseLink
                            )
                        },
                        stringResource(Res.string.license_text_post) to TextStyleWithCallback(
                            normalTextStyle
                        )
                    )

                    MultiStyleText(
                        modifier = Modifier,
                        style = normalTextStyle,
                        stringResource(Res.string.maintainer_pre) to TextStyleWithCallback(
                            normalTextStyle
                        ),
                        stringResource(Res.string.maintainer) to TextStyleWithCallback(linkTextStyle) {
                            uriHandler.openUri(
                                maintainerLink
                            )
                        },
                        stringResource(Res.string.maintainer_post) to TextStyleWithCallback(
                            normalTextStyle
                        )
                    )
                }
            }
        }
    )
}