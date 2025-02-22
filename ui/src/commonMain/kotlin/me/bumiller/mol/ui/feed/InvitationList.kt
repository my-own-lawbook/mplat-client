package me.bumiller.mol.ui.feed

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import me.bumiller.mol.model.aggregate.LawBookInvitationAggregate
import me.bumiller.mol.model.law.ForeignUser
import me.bumiller.mol.model.law.InvitationStatus
import me.bumiller.mol.model.law.LawBook
import me.bumiller.mol.model.law.LawBookInvitation
import me.bumiller.mol.model.law.MemberRole
import me.bumiller.mol.model.state.SimpleState
import me.bumiller.mol.model.user.Gender
import me.bumiller.mol.ui.Res
import me.bumiller.mol.ui.components.LawBookInvitationListItem
import me.bumiller.mol.ui.invitation_list_empty_text
import me.bumiller.mol.ui.invitation_list_item_name
import org.jetbrains.compose.resources.stringResource

private val DummyInvitation = LawBookInvitation(
    id = 1L,
    authorId = 1L,
    recipientId = 1L,
    targetId = 1L,
    role = MemberRole.Member,
    sentTimestamp = Clock.System.now(),
    usedTimestamp = null,
    expiredTimestamp = null,
    status = InvitationStatus.Open,
    message = "- ".repeat(200)
).let {
    LawBookInvitationAggregate(
        invitation = it,
        author = ForeignUser(1L, "", "John", "Doe", Gender.Male, LocalDate(2000, 1, 1)),
        target = LawBook(1L, "KEY", "BOOK_NAME", description = "", isFavourite = false)
    )
}

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
    onClick: (LawBookInvitation) -> Unit
) {
    val itemName = stringResource(Res.string.invitation_list_item_name)
    val emptyText = stringResource(Res.string.invitation_list_empty_text)

    LazyColumn(
        modifier = modifier
    ) {
        feed(
            state = state,
            itemName = itemName,
            emptyText = emptyText,
            true,
            fixedCrossAxisItem = DummyInvitation
        ) { item, position ->
            LawBookInvitationListItem(
                modifier = Modifier,
                onClick = onClick,
                invitation = item.invitation,
                author = item.author,
                target = item.target
            )

            if (position.dividerRequired) {
                HorizontalDivider()
            }
        }
    }
}