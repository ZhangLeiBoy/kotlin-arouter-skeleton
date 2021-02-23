package com.member.card.assistant.home.model

/**
 * @author 张雷
 * @date 2/23/21
 * @brief
 */
data class HomeItemModel(
    val name: String,
    val icon: Int,
    val brief: String = "",
    val outLink: String
)