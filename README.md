# 💱 CurrencyConverterApp

## 🌟 Overview
CurrencyConverterApp is an Android application designed for currency conversion and displaying exchange rates based on selected currencies. It uses the Navigation Component for a smooth user experience and is based on Clean Architecture for clear separation of layers. The app employs Hilt for dependency injection and Retrofit for currency exchange API calls.

## 🛠️ Project Structure
The project is organized to efficiently manage currency conversion and data display:

### Main Components

#### `BaseActivity`
- **Acts as a fragment container and manages navigation configuration.**
- Uses the Navigation Component to manage navigation between app fragments.

#### `MainActivity`
- **Manages the main navigation of the app using an animated bottom navigation bar (`AnimatedBottomBar`).**
- Replaces the displayed fragment based on the selection from the bottom navigation bar.

#### `HomeFragment`
- **Main screen for currency conversion.**
- Allows users to input an amount and select the base currency and target currency for conversion.
- Uses a `Spinner` to select the base currency and displays converted results in a `RecyclerView`.
- Shows the conversion result and the current quote of the base currency against the target currency.

#### `SearchFragment`
- **Allows searching and displaying currency options.**
- Uses a `Spinner` to select the base currency and displays exchange rates in a `RecyclerView` for other currencies relative to the selected base currency.
- Dynamically updates the list of available currencies based on API response and allows users to view updated exchange rates.
- Navigates to `DetailsActivity` to display historical information about exchange rates.

#### `DetailsActivity`
- **Displays details of historical exchange rates for the selected currency.**
- Shows graphs and tables with historical exchange rates for different periods: 24 hours, 48 hours, 7 days, 30 days, 90 days, and 1 year.
- Uses the `DetailsViewModel` to fetch and display historical data based on the selected currency and chosen period.
- The `DetailsViewModel` calculates the date range based on the selected period and retrieves corresponding historical data from the API.

#### `IntroFragment`, `LoginFragment`, and `SignUpFragment`
- **Introduction, login, and sign-up screens.**
- Manage initial navigation and user authentication before accessing the main screen.

  ##### `LoginFragment`
  - **Login screen for user authentication.**
  - Allows users to enter their email and password to log in to the app.
  - **Input Validation**: Uses the `LoginViewModel` to validate the provided email and password. Displays appropriate error messages if fields are empty.
  - **Authentication**: The `LoginViewModel` uses the `LoginUseCase` to authenticate the user. The success of authentication is observed through `LiveData`, which updates the UI based on the login result.
  - **Error Messages**: If there are validation errors or authentication failures, these are passed to the UI via `LiveData`.

  ##### `SignUpFragment`
  - **Sign-up screen to create a new user account.**
  - Allows users to create a new account by entering a username, email, and password.
  - **Input Validation**: Uses the `SignupViewModel` to validate user-provided data. Checks if all fields are filled and if the password is at least 6 characters long. Displays error messages if any fields are missing or if the password does not meet minimum requirements.
  - **Registration Process**: The `SignupViewModel` uses the `SignupUseCase` to register the new user and observes the registration result through `LiveData`. Updates the UI based on the success or failure of registration, providing feedback to the user.
  - **Error Messages**: If there are validation errors, they are passed to the UI via `LiveData`.

### Data Layer

#### `IUserDAO`
- **DAO Interface for SQLite database operations.**
- **Methods**:
  - `insertUser(user: User): Long`: Inserts a user into the database.
  - `readUser(user: User): Boolean`: Checks if the user exists in the database.

### API Layer
- **Defines API endpoints for currency exchange and uses Retrofit to perform the calls.**
- All network services are injected via Hilt.

## 🚀 Implemented Features
- **Home Screen (HomeFragment)**:
  - **Currency Conversion**: Allows users to enter an amount and select the base currency and target currency for conversion. Displays the conversion result and the current quote of the base currency relative to the target currency.
  - **Quote Display**: Uses a `Spinner` to select the base currency and a `RecyclerView` to show conversions to other currencies.
  - **Data Updates**: Uses `LiveData` to update the UI with conversion results and quotes based on recent data obtained via the API.

- **Currency Search (SearchFragment)**:
  - **Currency List**: Displays a list of available currencies for selection using a `Spinner`.
  - **Currency Quotes**: Shows current exchange rates of various currencies relative to the selected base currency in a `RecyclerView`.
  - **Dynamic Updates**: Updates the currency list and quotes based on API response, allowing users to see the latest exchange rates.
  - **Navigation**: Allows navigation to `DetailsActivity` for historical exchange rate details.

- **Currency Details (DetailsActivity)**:
  - **Historical Information**: Displays graphs and tables with historical exchange rates for various periods: 24 hours, 48 hours, 7 days, 30 days, 90 days, and 1 year.
  - **Historical Queries**: Uses the `DetailsViewModel` to fetch historical data based on the selected currency and chosen period. The ViewModel calculates the date range and makes an API call to retrieve the historical exchange rates.

- **Login Screen (LoginFragment)**:
  - **User Authentication**: Allows users to log in by entering their email and password.
  - **Input Validation**: Uses the `LoginViewModel` to validate the user's input. Displays error messages if fields are empty.
  - **Login Process**: The `LoginViewModel` uses the `LoginUseCase` to authenticate the user and updates the UI based on the login result, informing whether authentication was successful or not.

- **Sign-Up Screen (SignUpFragment)**:
  - **New User Registration**: Allows users to create a new account by entering a username, email, and password.
  - **Input Validation**: Uses the `SignupViewModel` to validate the user's input. Checks if all fields are filled and if the password is at least 6 characters long. Displays error messages if fields do not meet requirements.
  - **Registration Process**: The `SignupViewModel` uses the `SignupUseCase` to register the new user and observes the registration result through `LiveData`. Updates the UI based on the registration success or failure, providing feedback to the user.

- **Introduction and Registration Screens**:
  - **User Management**: Manages navigation between introduction, login, and registration screens for user authentication and registration.

- **Clean Architecture**: Separates data, domain, and presentation layers for easier testing and maintenance.
- **SQLite Database and DAO**: Utilizes an SQLite database with DAO for better responsibility separation and data persistence.

## 📷 Screenshots
<img src="https://github.com/user-attachments/assets/68d9904f-efa2-4d9e-b229-3c9f1969c401" width="200"/>
<img src="https://github.com/user-attachments/assets/e08d196f-3551-4871-96a0-9e209dee6817" width="200"/>
<img src="https://github.com/user-attachments/assets/af3ca557-24af-4751-bf2e-225457e04b5f" width="200"/>
</p>
<img src="https://github.com/user-attachments/assets/a9ef627c-c3c5-4dc0-a072-9981a96e5840" width="200"/>
<img src="https://github.com/user-attachments/assets/b569fdd2-4dea-461f-afcd-f94f5890cdcd" width="200"/>
<img src="https://github.com/user-attachments/assets/fe2293d6-05ad-4798-9de4-d4eb82b8a2ec" width="200"/>

## 🛠️ Dependencies
- `com.squareup.retrofit2:retrofit:2.9.0`: For making network calls to the exchange API.
- `com.squareup.retrofit2:converter-gson:2.9.0`: For converting JSON responses to Kotlin objects.
- `androidx.fragment:fragment-ktx:1.8.2`: For loading `ViewModel`s in Activities and Fragments.
- `androidx.lifecycle:lifecycle-livedata-ktx:2.8.4`: For observing data changes and updating the UI.
- `androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4`: For managing data and UI state.
- `com.google.dagger:hilt-android:2.48.1`: For dependency injection with Hilt.
- `com.google.dagger:hilt-android-compiler:2.48.1`: For annotation processing with KSP.
- `nl.joery.animatedbottombar:library:1.1.0`: For creating an animated bottom navigation bar.

## 📌 License
This project is open-source under the MIT License.
