## Restaurant Management App

Welcome to the Restaurant Management App, a versatile solution built with Compose Multiplatform for
both Android and iOS users, including iPad and Android tablets. This powerful management tool
empowers restaurant owners to streamline their menus and efficiently manage customer orders. With
its user-friendly interface, it's the ideal choice for enhancing restaurant operations and ensuring
a seamless experience for both restaurant staff and customers. Say goodbye to manual tasks and hello
to a streamlined approach for managing meals and orders, all within a single app.

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

Introducing the Food Delivery app, a versatile platform that enables authorized delivery personnel
to efficiently transport orders from restaurants to eager clients. Built with Compose Multiplatform,
this app ensures a smooth and unified experience for both Android and iOS users.

### Features

- [x] Submit a request with form data to access the delivery app.
- [x] Authenticate in the delivery app with necessary permissions.
- [x] Receive new restaurant orders in real-time using websockets.
- [x] View order details, including location and restaurant name.
- [x] Accept or reject orders.
- [ ] Provide estimated time and distance to the client.
- [ ] Track order location on the map.
- [ ] Display the path on the map to the order after accepting it.
- [ ] Update order status after acceptance in real-time.

## Taxi App

Introducing the Taxi Driver app, a comprehensive platform tailored for licensed taxi drivers who
have the authority to transport clients to their desired destinations. This app, compatible with
both Android and iOS, equips drivers with the essential tools and capabilities needed to offer safe
and reliable transportation services to passengers.

### Features

- [x] Request permission for a Taxi driver with the required information.
- [x] Authenticate in the Taxi app with necessary permissions.
- [x] Listen to incoming nearby ride requests from clients in real-time.
- [ ] Display the location of the client and their destination on the map.
- [ ] Accept or reject ride requests shown on the map.
- [ ] Show ride information and the live location of the taxi on the map.

## Support App is a desktop application designed for support agents to efficiently manage and respond to user support requests. This app enables support agents to provide assistance, manage support tickets, and engage in real-time chat with users.**

### Features

- [x] User Authentication**: Support agents can securely log in to their accounts.
- [x] Ticket Management**: Support agents can view, prioritize, and address user support tickets.
- [ ] Real-time Chat**: Support agents can engage in real-time chat sessions with users to address their issues.
- [ ] Ticket Closure**: Support agents can mark tickets as completed when issues are resolved.
- [x] Logout**: Support agents can securely log out of the application.


## Admin Dashboard App

The Admin Dashboard, powered by Compose Multiplatform, provides a user-friendly interface for 
effortless management of restaurants, users, and taxi services. This versatile application enables 
administrators to easily handle orders, activate or deactivate user accounts, restaurants, and taxi
drivers based on customer requests. It runs directly from your desktop, compatible with Mac, Linux,
and Windows, ensuring convenient access across platforms.

### Features
- [x] Authentication (sign in)
- Overview Interface:
    - [ ] Provides statistics on registered restaurants, taxi services, and users
- Restaurant Management:
    - [x]  Add, modify, or delete restaurant details.
    - [x]  Manage offers and cuisines for easy searching.
    - [x]   Search by restaurant name, price level, or rating.

- Taxi Management:
    - [x]  Add, edit, or delete taxi services.
    - [x]  Search by user name, car color, seat count, or driver availability.
- User Management:
    - [x]  Define user permissions and roles.
    - [x]  Disable user accounts when needed.
    - [x]  Search for users using specific filters.
- Advanced Features:
    - [x] Supports Dark and Light Themes for UI customization.
    - [x] Multilingual support for Arabic and English, including regional dialects.

### Tech stack
- [voyager ](https://github.com/adrielcafe/voyager)
- [realm](https://github.com/realm/realm-kotlin)
- [kotlin datetime](https://github.com/Kotlin/kotlinx-datetime)
- [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)
- [AAY Chart](https://github.com/TheChance101/AAY-chart)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [Compose ImageLoader](https://github.com/qdsfdhvh/compose-imageloader)
- [Compose Multiplatform File Picker](https://github.com/Wavesonics/compose-multiplatform-file-picker)
- [Webview](https://github.com/google/accompanist/tree/main/web)

