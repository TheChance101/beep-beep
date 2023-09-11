package org.thechance.api_gateway.data.localizedMessages.languages

import org.koin.core.annotation.Single

@Single
class IraqiArabicLocalizedMessages() : LocalizedMessages {

    // region identity
    override val invalidRequestParameter: String = "بيانات الطلب كلش غلط"
    override val invalidAddressLocation: String = "مكان العنوان مو صحيح"
    override val userAlreadyExist: String = "المستخدم موجود بالفعل"
    override val invalidInformation: String = "المعلومات كلش غلط"
    override val invalidFullName: String = "الإسم الكامل مو صحيح"
    override val invalidUsername: String = "اسم المستخدم مو صحيح"
    override val passwordCannotBeLessThan8Characters: String = "الرقم السري ما يقل عن ٨ حروف"
    override val usernameCannotBeBlank: String = "اسم المستخدم ميصير يكون فارغ"
    override val passwordCannotBeBlank: String = "الرقم السري ميصير يكون فارغ"
    override val invalidEmail: String = "الإيميل مو صحيح"
    override val notFound: String = "ما موجود"
    override val invalidCredentials: String = "بيانات الاعتماد كلش غلط"
    override val userCreatedSuccessfully: String = "المستخدم صار عندنا بنجاح 🎉"
    override val unknownError: String = "خطأ مو معروف `¯\\_(ツ)_/¯`"
    override val userNotFound: String = "المستخدم  ما موجود"
    override val invalidPermission: String = "صلاحية كلش غلط"
    override val alreadyInFavorite: String= "بالفعل موجود في المفضله"
    // endregion

    // region taxi
    override val taxiCreatedSuccessfully: String = "سيارة التاكسي صارت عندنا بنجاح 🎉"
    override val taxiUpdateSuccessfully: String = "سيارة التاكسي صارت محدثة بنجاح 🎉"
    override val taxiDeleteSuccessfully: String = "سيارة التاكسي صارت محذوفة بنجاح 🎉"
    override val invalidId: String = "الايدي مالتك مو صحيح"
    override val invalidPlate: String = "رقم اللوحة مو صحيح"
    override val invalidColor: String = "لون السيارة مو صحيح"
    override val invalidCarType: String = "نوع السيارة مو صحيح"
    override val seatOutOfRange: String = "عدد المقاعد مو صحيح"
    override val invalidLocation: String = "الموقع   مو صحيح"
    override val invalidRate: String = "التقييم مو صحيح"
    override val invalidDate: String = "التاريخ مو صحيح"
    override val invalidPrice: String = "السعر مو صحيح"
    override val alreadyExist: String = " موجود اصلا"
    override val requiredQuery: String= " عندك نقص بالمعلومات حجي"
    // endregion

    //region restaurant
    override val restaurantCreatedSuccessfully: String = "صار عندك مطعم الحين"
    override val restaurantUpdateSuccessfully: String = "حدثنا بيانات المطعم مالتك"
    override val restaurantDeleteSuccessfully: String = "حذفنا المطعم مالتك"
    override val restaurantInvalidId: String = "ماكو آي دي هيج"
    override val restaurantInvalidName: String = "اسمك مو صحيح"
    override val restaurantInvalidLocation: String = "العنوان مالتك مو صحيح"
    override val restaurantInvalidDescription: String = "الوصف مال المطعم مو مناسب"
    override val restaurantInvalidPriceLevel: String = "تسعير المطعم مالتك مو مناسب"
    override val restaurantInvalidRate: String = "تقييم المطعم مالتك مو مناسب"
    override val restaurantInvalidPhone: String = "رقم جوالك مو صحيح"
    override val restaurantInvalidTime: String = "الوقت يلي دزيته مو صحيح"
    override val restaurantInvalidPage: String = "رقم الصفحة يلي دزيتها مو صحيح"
    override val restaurantInvalidPageLimit: String = "عدد الصفحات يمك مو مناسبة"
    override val restaurantInvalidOneOrMoreIds: String = "آي دي أو أكتر مو صحيح"
    override val restaurantInvalidPermissionUpdateLocation: String = "إذن تحديث الموقع مالتك مو مناسب"
    override val restaurantInvalidUpdateParameter: String = "البيانات يلي دزيتها مو صحيحة"
    override val restaurantInvalidPropertyRights: String = "حقوق الملكية مالتك مو ماسبة"
    override val restaurantInvalidPrice: String = "السعر يلي دزيته مو مناسب"
    override val restaurantInvalidCuisineLimit: String = "عدد المطابخ يلي دزيتها مو مناسب"
    override val restaurantInvalidAddress: String = "العنوان مالتك مو صحيح"
    override val restaurantInvalidEmail: String = "الإيميل يلي دزيته مو صحيح"
    override val restaurantInvalidRequestParameter: String = "بيانات الطلب مالتك مو صحيح"
    override val restaurantErrorAdd: String = "واجهتنا مشكلة بالإضافة"
    override val restaurantClosed: String = "المطعم مقفل"
    override val restaurantInsertOrderError: String = "واجهتنا مشكلة بإضافة الطلب مالتك"
    override val restaurantInvalidReceivedOrders: String = "الطلب يلي دزيته النا مو مناسب"
    override val restaurantNotFound: String = "عيني مطعم لتريده ما موجود"
    override val deletedSuccessfully: String = "حذفناه حجي"
    override val cuisineNameAlreadyExisted: String = "مو ضايف هذا المطبخ من قبل !!"
    //endregion
}
