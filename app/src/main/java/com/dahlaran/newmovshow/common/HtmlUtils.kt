package com.dahlaran.newmovshow.common

import android.text.Html
import android.text.Spanned

object HtmlUtils {

    /**
     * Return displayable styled text using html text
     */
    fun convertHtmlTextToShowText(htmlText: String?): Spanned {
        if (htmlText.isNullOrEmpty()) {
            return Html.fromHtml("")
        }
        // TODO: use fromHtml(String, int) instead with FROM_HTML_MODE_LEGACY as int
        return Html.fromHtml(htmlText)
    }
}