package me.bumiller.mol.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.datetime.Clock
import me.bumiller.mol.model.aggregate.LawBookInvitationAggregate
import me.bumiller.civoris.ui.Res
import me.bumiller.civoris.ui.cd_invitation_list_item_trailing
import me.bumiller.mol.ui.format.formatFullName
import me.bumiller.civoris.ui.invitation_list_item_header
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration.Companion.days

/**
 * List items that shows a non-detail information about an invitation.
 *
 * @param modifier The modifier
 * @param onClick The callback for when the list item is clicked
 * @param invitation The invitation of which to display the properties
 * @param author The author of the invitation
 * @param target THe target book of the invitation
 */
@Composable
fun LawBookInvitationListItem(
    modifier: Modifier = Modifier,
    onClick: (LawBookInvitationAggregate) -> Unit = {},
    invitation: LawBookInvitationAggregate
) {
    val showTrailingIcon = invitation.invitation.expiredTimestamp?.let {
        Clock.System.now().minus(it) < 5.days
    } ?: false

    ListItem(
        modifier = modifier
            .clickable { onClick(invitation) },
        headlineContent = {
            Text(
                text = stringResource(
                    Res.string.invitation_list_item_header,
                    invitation.author.formatFullName(),
                    invitation.target.name
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        supportingContent = invitation.invitation.message?.let {
            {
                Text(
                    text = it,
                )
            }
        },
        trailingContent = if (showTrailingIcon) {
            {
                Icon(
                    imageVector = Icons.Outlined.Alarm,
                    contentDescription = stringResource(Res.string.cd_invitation_list_item_trailing),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        } else null
    )
}