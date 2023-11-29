package com.example.leannext.screens.constantsUI

import com.example.leannext.R
import com.example.leannext.screens.modelsUI.ConnectionWithUs

object ConstantsUI {
    var  connectionWithUs = listOf<ConnectionWithUs>(
        ConnectionWithUs(R.drawable.telegram,"Telegram","https://t.me/SergeiKorchagin"),
        ConnectionWithUs(R.drawable.phone,"Позвонить (Иван)","tel:+79307014004"),
        ConnectionWithUs(R.drawable.book,"Что почитать","https://ridero.ru/books/catalog/economics-finance-business-management/?q=Корчагин%20Сергей&offset=0"),
        )
}