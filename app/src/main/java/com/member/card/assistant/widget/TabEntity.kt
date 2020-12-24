package com.member.card.assistant.widget

import com.flyco.tablayout.listener.CustomTabEntity

class TabEntity : CustomTabEntity {
    var title = ""
    var selectedIcon = 0
    var unSelectedIcon = 0

    constructor(title: String, selectedIcon: Int, unSelectedIcon: Int) {
        this.title = title
        this.selectedIcon = selectedIcon
        this.unSelectedIcon = unSelectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabTitle(): String {
        return title
    }

}
