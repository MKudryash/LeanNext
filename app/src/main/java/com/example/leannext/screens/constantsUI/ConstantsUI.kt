package com.example.leannext.screens.constantsUI

import com.example.leannext.R
import com.example.leannext.screens.modelsUI.ConnectionWithUs
/**Константы для обратной связи*/
object ConstantsUI {
    var  connectionWithUs = listOf<ConnectionWithUs>(
        ConnectionWithUs(R.drawable.telegram,"Telegram","https://t.me/SergeiKorchagin"),
        ConnectionWithUs(R.drawable.phone,"Позвонить (Иван)","tel:+79307014004"),
        ConnectionWithUs(R.drawable.book,"Что почитать","https://ridero.ru/books/povyshenie_effektivnosti_proizvodstvennoi_sistemy_kompanii/"),
        )
}