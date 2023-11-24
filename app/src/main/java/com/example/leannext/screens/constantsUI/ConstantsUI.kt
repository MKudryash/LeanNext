package com.example.leannext.screens.constantsUI

import com.example.leannext.R
import com.example.leannext.screens.modelsUI.ConnectionWithUs

object ConstantsUI {
    var  connectionWithUs = listOf<ConnectionWithUs>(
        ConnectionWithUs(R.drawable.telegram,"Telegram","https://t.me/JuKudryash"),
        ConnectionWithUs(R.drawable.phone,"Позвонить","tel:0123456789"),
        ConnectionWithUs(R.drawable.email,"Написать","mailto:1@mail.ru"),
        ConnectionWithUs(R.drawable.book,"Дополнительные материалы","https://docs.google.com/spreadsheets/d/1I8IqfNwkBWRvGYt0vyCRecDyRi2gFte-gICgqs2BsPQ/edit#gid=0"),
        )
}