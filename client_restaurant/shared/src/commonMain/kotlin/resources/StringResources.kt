package resources


data class StringResources(
    val beepBeep: String = "Beep Beep",
    val add: String = "Add",
    val name: String = "Name",
    val description: String = "Description",
    val price: String = "Price",
    val cuisines: String = "Cuisines",
    val chooseCuisine: String = "Choose cuisine",
    val save: String = "Save",
    val cancel: String = "Cancel",
    val addNewMeal: String = "Add New Meal",
    val editMeal: String = "Edit Meal",
    val allMeals: String = "All meals",
    val restaurantInfo: String = "Restaurant info",
    val ownerUsername: String = "Owner username",
    val address: String = "Address",
    val rating: String = "Rating",
    val priceLevel: String = "Price level",
    val restaurantName: String = "Restaurant name",
    val phoneNumber: String = "Phone number",
    val workingHours: String = "Working hours",
    val logout: String = "Logout",
    val restaurantNameErrorMessage: String = "restaurant name should be between 4 to 25 letters",
    val descriptionErrorMessage : String = "description shouldn't be more than 255 letter"
)
