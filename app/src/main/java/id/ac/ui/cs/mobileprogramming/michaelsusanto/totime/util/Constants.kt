package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.util

import java.util.regex.Pattern

object Constants {
    val COVID_API_URL = "https://api.kawalcorona.com/"
    val VALID_EMAIL_ADDRESS_REGEX: Pattern =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
}