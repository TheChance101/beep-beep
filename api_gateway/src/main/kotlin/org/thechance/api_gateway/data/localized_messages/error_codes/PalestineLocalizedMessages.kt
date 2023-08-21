package org.thechance.api_gateway.data.localized_messages.error_codes

import org.koin.core.annotation.Single

@Single
class PalestineLocalizedMessages : LocalizedMessages {
    override val invalidFullName: String = "اكتب اسمك كامل افتخر بنفسك"
    override val invalidUsername: String = "فين اسمك يا 56"
    override val passwordCannotBeLessThan8Characters: String = "خلي الباسوورد اكبر من 8 عشان متتسرقش وتعيط"
    override val usernameCannotBeBlank: String = "اييشش هااضض"
    override val passwordCannotBeBlank: String = "هتخلي الباسوورد شفاف كمان؟"
    override val invalidEmail: String = "دا مش ايميل يا 56"
    override val invalidCredentials: String = "امسك 56"
    override val notFound: String = "شفاف 404"
    override val invalidAddressLocation: String = "اللوكيشن في مثلث برمودا"
}