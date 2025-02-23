package me.bumiller.mol.ui.feed

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.bumiller.mol.model.aggregate.LawBookInvitationAggregate
import me.bumiller.mol.model.state.SimpleState
import me.bumiller.mol.ui.Res
import me.bumiller.mol.ui.components.LawBookInvitationListItem
import me.bumiller.mol.ui.invitation_list_empty_text
import me.bumiller.mol.ui.invitation_list_item_name
import org.jetbrains.compose.resources.stringResource

/**
 * List that displays a list of invitations.
 *
 * @param modifier The modifier
 * @param state The state with the invitations
 * @param onClick The callback for when an item is clicked
 */
@Composable
fun InvitationList(
    modifier: Modifier = Modifier,
    state: SimpleState<List<LawBookInvitationAggregate>>,
    onClick: (LawBookInvitationAggregate) -> Unit
) {
    val itemName = stringResource(Res.string.invitation_list_item_name)
    val emptyText = stringResource(Res.string.invitation_list_empty_text)

    Feed(
        modifier = modifier,
        state = state,
        itemName = itemName,
        emptyText = emptyText,
        isVertical = true
    ) { item, position ->
        LawBookInvitationListItem(
            modifier = Modifier,
            onClick = onClick,
            invitation = item
        )

        if (position.dividerRequired) {
            HorizontalDivider()
        }
    }
}