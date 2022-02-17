package com.example.yogiyo.constants

class Constants {
    companion object {
        const val BseUrl = "https://api.github.com"

        const val SEARCH_SORT_BEST_MATCH = "best match"
        const val SEARCH_SORT_FOLLOWERS = "followers"
        const val SEARCH_SORT_REPOSITORIES = "repositories"
        const val SEARCH_SORT_JOINED = "joined"
        const val SEARCH_SORT_DEFAULT = SEARCH_SORT_BEST_MATCH

        const val SEARCH_ORDER_ASC = "asc"
        const val SEARCH_ORDER_DESC = "desc"
        const val SEARCH_ORDER_DEFAULT = SEARCH_ORDER_DESC

        const val SEARCH_PER_PAGE = 30
    }
}