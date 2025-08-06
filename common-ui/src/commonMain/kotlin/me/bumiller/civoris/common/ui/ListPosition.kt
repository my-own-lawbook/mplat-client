package me.bumiller.civoris.common.ui

/**
 * Models the different positions that an item can have in a list of items.
 */
enum class ListPosition(

    /**
     * Whether a divider should appended after this item.
     */
    val dividerRequired: Boolean

) {

    /**
     * The item is a singular item.
     */
    Single(false),

    /**
     * The item is the first item.
     */
    First(true),

    /**
     * The item is surrounded by items.
     */
    Middle(true),

    /**
     * The item is the last item.
     */
    Last(false);

    companion object {

        /**
         * Returns the position of a specified item in the list.
         *
         * @param item The item to get the position of
         * @return The list position. If the item is not in the list, returns [Middle]
         */
        fun <T> List<T>.positionOf(item: T): ListPosition =
            if (size == 1) Single
            else indexOf(item).let {
                when (it) {
                    size - 1 -> Last
                    0 -> First
                    else -> Middle
                }
            }

    }

}