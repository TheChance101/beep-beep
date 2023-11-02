# beep-beep

We are at https://www.linkedin.com/company/thechance101/ mentorship starting the journey of building the largest full-stack open-source Kotlin project


## About the project

It is essentially a platform designed for food delivery and taxi ordering, intended to demonstrate the capabilities of Kotlin.
Using beep beep as a project name describes how fast you can build with Kotlin, and if it was a real service it should be fast also 😅


## Project components
 
* End User app (Built with Compose Multiplatform to serve both Android and iOS).
* Restaurant Management app (Built with Compose Multiplatform to serve both Android and iOS, supporting iPad and Android tablets).
* Taxi Driver app (Built with Compose Multiplatform to serve both Android and iOS).
* Food Delivery app (Built with Compose Multiplatform to serve both Android and iOS).
* Dashboard app (Built with Compose Multiplatform to run directly from your desktop, Mac, Linux, and Windows).
* Support app (Built with Compose Multiplatform to run directly from your desktop, Mac, Linux, and Windows).

* Identity Service (A microservice written in Kotlin using Ktor framework).
* Restaurant Service (A microservice written in Kotlin using Ktor framework).
* Taxi Service (A microservice written in Kotlin using Ktor framework).
* Location Service (A microservice written in Kotlin using Ktor framework).
* Notification Service (A microservice written in Kotlin using Ktor framework).
* Chat Service (A microservice written in Kotlin using Ktor framework).

# Structure
<img width="746" alt="Screenshot 2023-11-02 at 1 09 39 PM" src="https://github.com/TheChance101/beep-beep/assets/34461597/2e6f011e-6eb5-49c5-96c1-f8f76d5f4ee0">

<img width="940" alt="Screenshot 2023-11-02 at 1 14 40 PM" src="https://github.com/TheChance101/beep-beep/assets/34461597/d5d509e7-1adb-4fe5-9cf7-9dd550eb86f2">

# BeepBeep (End-User) App
> The Beep Beep end-user application is a Kmp mobile app for Android and iOS that allows users to order food and book taxis. It also has features for saving favorite restaurants and more.

## Features
- [x] Authentication (sign in and sign up)
- [x] Personalized recommendations: The app uses the user's preferences to recommend cuisines, restaurants, and meals that they are likely to enjoy.
- [x] Real-time order tracking: Users can track the status of their food orders and taxi rides in real-time.
- [x] Users can order food from restaurants with just a few taps.
- [x] Discavor restaurants/ meals: Users can browse all restaurants and their meals, and filter by cuisine to create their own order.
- [x] Search restaurants/ meals: Users can search for restaurants and/or meals.
- [x] Discounts and deals: users can see discounts and deals on restaurants.
- [x] Favorite restaurants and cuisines: Users can save their favorite restaurants and cuisines to make it easy to order again in the future.
- [x] Order history: Users can view their order history to keep track of their past orders and rides.
- [x] Cart: Users can add, remove, or edit meals in their cart and then checkout.
- [ ] Chat support: Users can contact customer support via chat for assistance with any issues they may have.
- [ ] Book Taxi: Users can book taxis and track them.
- [ ] Notifications: Users receive notifications about changes to their orders and taxi rides, as well as offers.

## Restaurant Management App

> Welcome to the Restaurant Management App, a versatile solution built with Compose Multiplatform for
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
> Introducing the Food Delivery app, a versatile platform that enables authorized delivery personnel
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

> Introducing the Taxi Driver app, a comprehensive platform tailored for licensed taxi drivers who
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

## Support App
> is a desktop application designed for support agents to efficiently manage and respond to user support requests. This app enables support agents to provide assistance, manage support tickets, and engage in real-time chat with users.**

### Features

- [x] User Authentication: Support agents can securely log in to their accounts.
- [x] Ticket Management: Support agents can view, prioritize, and address user support tickets.
- [ ] Real-time Chat: Support agents can engage in real-time chat sessions with users to address their issues.
- [ ] Ticket Closure: Support agents can mark tickets as completed when issues are resolved.
- [x] Logout: Support agents can securely log out of the application.


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
