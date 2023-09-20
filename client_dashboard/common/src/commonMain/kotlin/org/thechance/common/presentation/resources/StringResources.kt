package org.thechance.common.presentation.resources


data class StringResources(
        //region Login
    val login: String = "Login",
    val loginTitle: String = "Use Admin account to login",
    val loginUsername: String = "Username",
    val loginPassword: String = "Password",
    val loginButton: String = "Login",
    val loginKeepMeLoggedIn: String = "Keep me logged in",
        //endregion Login

        //region Restaurant
    val searchForRestaurants: String = "Search for restaurants",
    val export: String = "Export",
    val addCuisine: String = "Add cuisine",
    val newRestaurant: String = "New Restaurant",
    val restaurant: String = "restaurant",
    val save: String = "Save",
    val cancel: String = "Cancel",
    val priceLevel: String = "Price level",
    val rating: String = "Rating",
    val filter: String = "Filter",
    val restaurantName: String = "Restaurant name",
    val ownerUsername: String = "Owner username",
    val phoneNumber: String = "Phone number",
    val workingHours: String = "Working hours",
    val location: String = "Location",
    val create: String = "Create",
    val workStartHourHint: String = "1:00",
    val workEndHourHint: String = "24:00",
    val restaurants: String = "Restaurants",
    val cuisines: String = "Cuisines",
    val enterCuisineName: String = "Enter cuisine name",
    val add: String = "Add",
        //endregion Restaurant

        //region Taxi
    val searchForTaxis: String = "Search for Taxis",
    val newTaxi: String = "New Taxi",
    val taxi: String = "taxi",
    val downloadSuccessMessage: String = "Your file download was successful.",
    val seats: String = "Seats",
    val status: String = "Status",
    val carModel: String = "Car Model",
    val carColor: String = "Car Color",
    val driverUsername: String = "Driver Username",
    val taxiPlateNumber: String = "Taxi Plate Number",
    val createNewTaxi: String = "Create new Taxi",
    val taxis: String = "Taxis",
    val offline: String = "Offline",
    val online: String = "Online",
    val onRide: String = "On ride",
        //endregion Taxi

        //region User
    val user: String = "user",
    val permission: String = "Permission",
    val country: String = "Country",
    val searchForUsers: String = "Search for users",
    val edit: String = "Edit",
    val delete: String = "Delete",
    val disable: String = "Disable",
    val permissions: String = "Permissions",
    val users: String = "Users",
        //endregion User

        //region scaffold
    val logout: String = "Logout",
    val darkTheme: String = "Dark theme",
    val dropDownMenu: String = "DropDownMenu",
        //endregion scaffold

        //region table
    val outOf: String = "out of",
    val pluralLetter: String = "s",
        //endregion table

        //region overview
    val overview: String = "Overview",
    val revenueShare: String = "Revenue share",
    val viewMore: String = "View more",
    val taxiLabel: String = "Taxi",
    val restaurantLabel: String = "Restaurant",
    val restaurantPermission: String = "Restaurant Owner",
    val taxiPermission: String = "Taxi Driver",
    val endUserPermission: String = "End User",
    val supportPermission: String = "Support",
    val deliveryPermission: String = "Delivery",
    val adminPermission: String = "Admin",
    val revenue: String = "Revenue",
    val earnings: String = "Earnings",
    val trip: String = "Trip",
    val accepted: String = "Accepted",
    val pending: String = "Pending",
    val rejected: String = "Rejected",
    val canceled: String = "Canceled",
    val completed: String = "Completed",
    val inTheWay: String = "In the way",
    val orders: String = "Orders",

        // endregion overview

    val clearAll: String = "Clear all",
    val noMatchesFound: String = "Oops, No matches found",
    val invalidPlateNumber: String = "Invalid plate number",
    val invalidCarModel: String = "Invalid car model",

    val noInternet: String = "No internet connection!",
    val unKnownError: String = "Unknown error please retry again!",
)

val english: StringResources = StringResources()
val arabic: StringResources = StringResources(
        //region Login
        login = "تسجيل الدخول",
        loginTitle = "استخدم حساب المشرف لتسجيل الدخول",
        loginUsername = "اسم المستخدم",
        loginPassword = "كلمة السر",
        loginButton = "تسجيل الدخول",
        loginKeepMeLoggedIn = "البقاء قيد التسجيل",
//endregion Login

        //region Restaurant
        searchForRestaurants = "ابحث عن مطاعم",
        export = "تصدير",
        addCuisine = "اضف مطب",
        newRestaurant = "مطعم جديد",
        restaurant = "مطعم",
        save = "احفض",
        cancel = "الغي",
        priceLevel = "مستوى السعر",
        rating = "تقييم",
        filter = "تصفية",
        restaurantName = "اسم المطعم",
        ownerUsername = "اسم المالك",
        phoneNumber = "رقم الهاتف",
        workingHours = "ساعات العمل",
        location = "موقعك",
        create = "انشئ",
        workStartHourHint = "1:00",
        workEndHourHint = "24:00",
        restaurants = "مطاعم",
        cuisines = "مطابخ",
        enterCuisineName = "ادخل اسم المطبخ",
        add = "اضف",
//endregion Restaurant

        //region Taxi
        searchForTaxis = "ابحث عن سيارة اجرة",
        newTaxi = "سيارة اجرة جديدة",
        taxi = "سيارة اجرة",
        downloadSuccessMessage = "تم تحميل الملف بنجاح",
        seats = "مقاعد",
        status = "الحالة",
        carModel = "نوع السيارة",
        carColor = "لون السيارة",
        driverUsername = "اسم السائق",
        taxiPlateNumber = "رقم اللوحة",
        createNewTaxi = "انشئ سيارة اجرة جديدة",
        taxis = "سيارات اجرة",
        offline = "غير متصل",
        online = "متصل",
        onRide = "في رحلة",
//endregion Taxi

        //region User
        user = "مستخدم",
        permission = "صلاحية",
        country = "بلد",
        searchForUsers = "ابحث عن مستخدمين",
        edit = "عدل",
        delete = "احذف",
        disable = "تعطيل",
        permissions = "صلاحيات",
        users = "مستخدمين",
//endregion User

        //region scaffold
        logout = "خروج",
        darkTheme = "الوضع الليلي",
        dropDownMenu = "قائمة منسدلة",
//endregion scaffold

        //region table
        outOf = "من",
        pluralLetter = "s",
//endregion table

        //region overview
        overview = "نظرة عامة",
        revenueShare = "نسبة الارباح",
        viewMore = "عرض المزيد",
        taxiLabel = "سيارة اجرة",
        restaurantLabel = "مطعم",
        restaurantPermission = "مالك مطعم",
        taxiPermission = "سواق تكسي",
        endUserPermission = "مستخدم",
        supportPermission = "دعم",
        deliveryPermission = "توصيل",
        adminPermission = "مشرف",
        revenue = "ارباح",
        earnings = "الايرادات",
        trip = "رحله",
        accepted = "مقبول",
        pending = "قيد الانتظار",
        rejected = "مرفوض",
        canceled = "ملغي",
        completed = "مكتمل",
        inTheWay = "في الطريق",
        orders = "طلبات",

// endregion overview

        clearAll = "امسح الكل",
        noMatchesFound = "اوبس ماكو اتصال بالانترنيت",
        invalidPlateNumber = "رقم اللوحة غير صحيح",
        invalidCarModel = "موديل السيارة غير صحيح",

        noInternet = "ماكو اتصال بالانترنيت!",
        unKnownError = "خطأ غير معروف من فضلك حاول مرة اخرى!"
)

