# beep-beep

We are at [The Chance](https://www.linkedin.com/company/thechance101/) mentorship starting the journey of building
the largest full-stack open-source Kotlin project.

## About the project

It is essentially a platform designed for food delivery and taxi ordering, intended to demonstrate
the capabilities of Kotlin.
Using beep beep as a project name describes how fast you can build with Kotlin, and if it was a real
service it should be fast also 😅

## Project components

* End User app (Built with Compose Multiplatform to serve both Android and iOS).
* Restaurant Management app (Built with Compose Multiplatform to serve both Android and iOS,
  supporting iPad and Android tablets).
* Taxi Driver app (Built with Compose Multiplatform to serve both Android and iOS).
* Food Delivery app (Built with Compose Multiplatform to serve both Android and iOS).
* Dashboard app (Built with Compose Multiplatform to run directly from your desktop, Mac, Linux, and
  Windows).
* Support app (Built with Compose Multiplatform to run directly from your desktop, Mac, Linux, and
  Windows).

* Identity Service (A microservice written in Kotlin using Ktor framework).
* Restaurant Service (A microservice written in Kotlin using Ktor framework).
* Taxi Service (A microservice written in Kotlin using Ktor framework).
* Location Service (A microservice written in Kotlin using Ktor framework).
* Notification Service (A microservice written in Kotlin using Ktor framework).
* Chat Service (A microservice written in Kotlin using Ktor framework).

# Structure

<img width="746" alt="Screenshot 2023-11-02 at 1 09 39 PM" src="https://github.com/TheChance101/beep-beep/assets/34461597/2e6f011e-6eb5-49c5-96c1-f8f76d5f4ee0">

<img width="940" alt="Screenshot 2023-11-02 at 1 14 40 PM" src="https://github.com/TheChance101/beep-beep/assets/34461597/d5d509e7-1adb-4fe5-9cf7-9dd550eb86f2">

## Technologies
- [compose-multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Ktor (Client - Server)](https://ktor.io/docs/welcome.html)
- [Koin dependency injection](https://insert-koin.io/)
- [voyager ](https://github.com/adrielcafe/voyager)
- [realm db](https://github.com/realm/realm-kotlin)
- [Firebase Messaging](https://firebase.google.com/docs/cloud-messaging)
- [AAY Chart](https://github.com/TheChance101/AAY-chart)
- [paging with compose multiplateform](https://developer.android.com/jetpack/androidx/releases/paging)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [Compose ImageLoader](https://github.com/qdsfdhvh/compose-imageloader)
- [Compose Multiplatform File Picker](https://github.com/Wavesonics/compose-multiplatform-file-picker)
- [Webview](https://github.com/google/accompanist/tree/main/web)
- [kotlin datetime](https://github.com/Kotlin/kotlinx-datetime)
- [moko-resources](https://github.com/icerockdev/moko-resources)


## BeepBeep (End-User) App

> The Beep Beep end-user application is a Kmp mobile app for Android and iOS that allows users to
> order food and book taxis. It also has features for saving favorite restaurants and more.

![Dribble show 56 (2)](https://github.com/TheChance101/beep-beep/assets/34461597/32a1437c-a1dd-4285-932c-e8df0fd065aa)

![Dribble show 56 (3)](https://github.com/TheChance101/beep-beep/assets/34461597/11457208-569d-4781-9d68-1ef69077c2e8)

### Features

- [x] Authentication (sign in and sign up)
- [x] Personalized recommendations: The app uses the user's preferences to recommend cuisines,
  restaurants, and meals that they are likely to enjoy.
- [x] Real-time order tracking: Users can track the status of their food orders and taxi rides in
  real-time.
- [x] Users can order food from restaurants with just a few taps.
- [x] Discover restaurants/ meals: Users can browse all restaurants and their meals, and filter by
  cuisine to create their own order.
- [x] Search restaurants/ meals: Users can search for restaurants and/or meals.
- [x] Discounts and deals: users can see discounts and deals on restaurants.
- [x] Favorite restaurants and cuisines: Users can save their favorite restaurants and cuisines to
  make it easy to order again in the future.
- [x] Order history: Users can view their order history to keep track of their past orders and
  rides.
- [x] Cart: Users can add, remove, or edit meals in their cart and then checkout.
- [ ] Chat support: Users can contact customer support via chat for assistance with any issues they
  may have.
- [ ] Book Taxi: Users can book taxis and track them.
- [ ] Notifications: Users receive notifications about changes to their orders and taxi rides, as
  well as offers.

## Admin Dashboard App

> A desktop App that provides a user-friendly interface for
> effortless management of restaurants, users, and taxi services.

### Features

- [x] Authentication (sign in)
- [x]  Add, modify, or delete restaurant details.
- [x]  Manage offers and cuisines for easy searching.
- [x]   Search by restaurant name, price level, or rating.
- [x]  Add, edit, or delete taxi services.
- [x]  Search by user name, car color, seat count, or driver availability.
- [x]  Define user permissions and roles.
- [x]  Disable user accounts when needed.
- [x]  Search for users using specific filters.
- [x] Supports Dark and Light Themes for UI customization.
- [x] Multilingual support for Arabic and English,including regional dialects.
- [ ] Provides statistics on registered restaurants,taxi services and users.

## Restaurant Management App

> An App empowers restaurant owners to streamline their menus and efficiently manage customer orders. With
> its user-friendly interface.

![Dribble show 56 (1)](https://github.com/TheChance101/beep-beep/assets/34461597/987f4fc5-09af-4ec0-90d4-b18f5638dd6f)


### Features

- [x] Create a new restaurant with details and owner info.
- [x] Authenticate as a restaurant admin with necessary permissions.
- [x] Manage multiple restaurants under a single account.
- [x] Easily switch between different restaurants within the same account.
- [x] Adjust opening and closing times for the restaurant.
- [x] Edit restaurant information (name, working hours, phone number, image/logo, description).
- [x] Full control over meal details (name, description, price, cuisines).
- [x] View categories and their associated meals.
- [x] Monitor restaurant revenue in the last 7 days.
- [x] Track order history in the last 7 days (completed and canceled).
- [ ] Receive incoming orders in real-time using websockets.
- [ ] Get real-time notifications for new orders.
- [ ] Change order status (approve or cancel).

## Food Delivery App

> An app that enables authorized delivery personnel
> to efficiently transport orders from restaurants to eager clients.

![Dribble show 40 (2)](https://github.com/TheChance101/beep-beep/assets/34461597/9d4be2c1-e84c-42a1-85c0-96d2db7d5844)


### Features

- [x] Submit a request with form data to access the delivery app.
- [x] Authenticate in the delivery app with necessary permissions.
- [ ] Receive new restaurant orders in real-time using websockets.
- [ ] Display the path on the map to the order after accepting it.

## Taxi App

> An app tailored for licensed taxi drivers who
> have the authority to transport clients to their desired destinations.

![Dribble show 42](https://github.com/TheChance101/beep-beep/assets/34461597/19ce5bd8-d800-42ba-85f3-513cf207bca5)


### Features

- [x] Request permission for a Taxi driver with the required information.
- [x] Authenticate in the Taxi app with necessary permissions.
- [ ] Listen to incoming nearby ride requests from clients in real-time.
- [ ] Display the location of the client and their destination on the map.

## Support App

> A desktop application designed for support agents to efficiently manage and respond to user
> support requests.

### Features

- [x] User Authentication: Support agents can securely log in to their accounts.
- [ ] Ticket Management: Support agents can view, prioritize, and address user support tickets.
- [ ] Real-time Chat: Support agents can engage in real-time chat sessions with users to address their issues.


## Contributors

<a href="https://github.com/TheChance101/beep-beep/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=TheChance101/beep-beep" />
</a>

## License:

    Copyright 2023 The Chance

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
