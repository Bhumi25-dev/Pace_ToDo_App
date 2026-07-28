package com.okay.paceto_doapp.notification

import com.okay.paceto_doapp.R
import com.okay.paceto_doapp.data.local.Category

fun getNotificationIcon(category: Category): Int {

    return when(category) {
        Category.FOCUS -> R.drawable.lightbulb_2_24px
        Category.HEALTH -> R.drawable.health_and_safety_24px
        Category.CONNECTIONS -> R.drawable.person_2_24px
        Category.ADMIN -> R.drawable.business_center_24px
    }

}