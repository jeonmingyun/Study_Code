package com.example.yogiyo.vo

data class UserVo(
    var login: String = "",
    var id: Int = 0,
    var node_id: String = "",
    var avatar_url: String = "",
    var gravatar_id: String = "",
    var url: String = "",
    var html_url: String = "",
    var followers_url: String = "",
    var subscriptions_url: String = "",
    var organizations_url: String = "",
    var repos_url: String = "",
    var received_events_url: String = "",
    var type: String = "",
    var score: Int = 0,
    var following_url: String = "",
    var gists_url: String = "",
    var starred_url: String = "",
    var events_url: String = "",
    var site_admin: Boolean = false,
    var isChecked : Boolean = false
) {
    constructor(login: String, id: Int, node_id: String, avatar_url: String, html_url: String)
            : this(login, id, node_id, avatar_url, "", "", html_url)
}