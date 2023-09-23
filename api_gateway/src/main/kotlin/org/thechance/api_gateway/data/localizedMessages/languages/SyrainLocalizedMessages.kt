package org.thechance.api_gateway.data.localizedMessages.languages

import org.koin.core.annotation.Single

@Single
class SyrianArabicLocalizedMessages() : LocalizedMessages {

    // region identity
    override val invalidRequestParameter: String = "بيانات الطلب مالا صحيحة"
    override val invalidAddressLocation: String = "مكان العنوان مالو صحيح"
    override val userAlreadyExist: String = "المستخدم موجود بالفعل"
    override val invalidInformation: String = "المعلومات مالا صحيحة"
    override val invalidFullName: String = "الإسم الكامل مالو صحيح"
    override val invalidUsername: String = "اسم المستخدم مالو صحيح"
    override val passwordCannotBeLessThan8Characters: String = "الرقم السري ما يقل عن ٨ حروف"
    override val usernameCannotBeBlank: String = "اسم المستخدم ما يقدر يكون فاضي"
    override val passwordCannotBeBlank: String = "الرقم السري ما يقدر يكون فاضي"
    override val invalidEmail: String = "الإيميل مالو صحيح"
    override val notFound: String = "ما لقيناه"
    override val invalidCredentials: String = "بيانات الاعتماد مالا صحيحة"
    override val userCreatedSuccessfully: String = "المستخدم صار عنا 🎉"
    override val unknownError: String = "خطأ مالو معروف `¯\\_(ツ)_/¯`"
    override val userNotFound: String = "المستخدم ما لقيناه"
    override val invalidPermission: String = "صلاحية مالا صحيحة"
    override val alreadyInFavorite: String= "بالفعل موجود في المفضله"

    // endregion

    // region taxi
    override val taxiCreatedSuccessfully: String = "سيارة التاكسي صارت عنا 🎉"
    override val tripCreatedSuccessfully: String = "تم انشاء الرحلة"
    override val tripApproved: String = "تم قبول الرحلة"
    override val tripCanceled: String = "تم الغاء الرحلة"
    override val tripFinished: String = "تم إنهاء الرحلة بنجاح"
    override val taxiUpdateSuccessfully: String = "سيارة التاكسي صارت محدثة 🎉"
    override val taxiDeleteSuccessfully: String = "سيارة التاكسي صارت محذوفة"
    override val invalidId: String= "الرقم مالو صحيح"
    override val invalidPlate: String= "رقم اللوحة مالو صحيح"
    override val invalidColor: String= "لون السيارة مالو صحيح"
    override val invalidCarType: String= "نوع السيارة مالو صحيح"
    override val seatOutOfRange: String= "عدد المقاعد مالو صحيح"
    override val invalidLocation: String=  "المكان اللي دخلته مالو صح!"
    override val invalidRate: String= "التقييم مالو صحيح"
    override val invalidDate: String= "التاريخ مالو صحيح"
    override val invalidPrice: String= "السعر مالو صحيح"
    override val alreadyExist: String= "هاد التاكسي موجود من قبل يا جار!"
    override val requiredQuery: String ="في معلومات ناقصة يا جار!"
    // endregion

    //region restaurant
    override val restaurantCreatedSuccessfully: String = "ضيفنالك المطعم"
    override val restaurantUpdateSuccessfully: String = "حدثنالك المطعم"
    override val restaurantDeleteSuccessfully: String = "حذفنالك المطعم"
    override val restaurantInvalidId: String = "الآي دي تبعك مو صحيح "
    override val restaurantInvalidName: String = "اسمك مو صحيح"
    override val restaurantInvalidLocation: String = "العنوان تبعك مو صحيح"
    override val restaurantInvalidDescription: String = "وصف المطعم تبعك مو صحيح"
    override val restaurantInvalidPriceLevel: String = "تسعيراتك للمطعم مو منيحة"
    override val restaurantInvalidRate: String = "تقييم مطعمك مو صحيح"
    override val restaurantInvalidPhone: String = "رقم تليفونك مو صحيح"
    override val restaurantInvalidTime: String = "الوقت تبعك مو صحيح"
    override val restaurantInvalidPage: String = "رقم الصفحة مو موجود"
    override val restaurantInvalidPageLimit: String = "الحد الأقصى للصفحات مو مناسب"
    override val restaurantInvalidOneOrMoreIds: String = "آي دي واحد أو اكتر مو صحيح"
    override val restaurantInvalidPermissionUpdateLocation: String = "إذن تحدييث المطعم مو صحيح"
    override val restaurantInvalidUpdateParameter: String = "البيانات تبعك مو صحيحة"
    override val restaurantInvalidPropertyRights: String = "حقوق ملكيتك مو مناسبة"
    override val restaurantInvalidPrice: String = "سعرك مو تمام"
    override val restaurantInvalidCuisineLimit: String = "الحد الأقصى للمطابخ مو مناسب"
    override val restaurantInvalidAddress: String = "عنوانك مو صحيح"
    override val restaurantInvalidEmail: String = "إيميلك مو صحيح"
    override val restaurantInvalidRequestParameter: String = "بيانات طلبك مو صحيحة"
    override val restaurantErrorAdd: String = "في مشكلة بالإضافة"
    override val restaurantClosed: String = "المطعم مسكر"
    override val restaurantInsertOrderError: String = "في مشكلة بإضافة الأوردر تبعك"
    override val restaurantInvalidReceivedOrders: String = "الأوردر يلي وصلنا مو صحيح "
    override val restaurantNotFound: String = "عذرًا، ما لقينا هالمطعم"
    override val deletedSuccessfully: String = "حذفنالك ياجار"
    override val cuisineNameAlreadyExisted: String = "المطبخ موجود قبل هيك"


    override val missingParameter: String = "في معلومات ناقصة يا جار!"
    override val tokensNotFound: String = "التوكن مالو موجود"
    override val tokenNotRegister: String = "التوكن مالو مسجل"
    //endregion
}
