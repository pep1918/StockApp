# StockApp: Mobile Stock Management App 📱📊

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![GitHub issues](https://img.shields.io/github/issues/your-username/your-repo)](https://github.com/your-username/your-repo/issues)
[![GitHub stars](https://img.shields.io/github/stars/your-username/your-repo)](https://github.com/your-username/your-repo/stargazers)

Welcome to StockApp, your go-to solution for mobile stock management! 🚀 This Android app, developed using Java and Android Studio, empowers users to effortlessly manage their stock, keeping everything organized and easily accessible on the go.

## 📸 Screenshots

![App Screenshots](Screenshot1.png)
![App Screenshots](Screenshot2.png)
![App Screenshots](Screenshot3.png)
![App Screenshots](Screenshot4.png)
![App Screenshots](Screenshot5.png)
![App Screenshots](Screenshot6.png)
![App Screenshots](Screenshot7.png)
![App Screenshots](Screenshot8.png)


## 🚀 Features

- **CRUD Application:** You can Add, Delete, Update, and View items
- **Report:** Can get the reports
- **Billing:** Billing System
- **Data Export:** Firebase database

## 🛠 Installation

1. Clone the repository: `[git clone https://github.com/your-username/your-repo.git](https://github.com/kavindee/StockApp.git)`
2. Open the project in Android Studio.
3. Build and run the app on your Android device or emulator.

## 🛠 Firebase setup
In your Firebase console, go to the storage tab on the left. Click on the rules tab and set your rule to public. Use the following code to set it to public.

```firebase
service firebase.storage {
  match /b/{bucket}/o {
    match /{allPaths=**} {
      allow read, write;
    }
  }
}

```
This is good for testing purposes, however, you should secure your DB connection using authenticate users.


## 🤝 Contributing

Contributions are welcome! Follow these steps to contribute:

1. Fork the repository.
2. Create a branch: `git checkout -b feature/new-feature`.
3. Commit your changes: `git commit -m 'Add new feature'`.
4. Push to the branch: `git push origin feature/new-feature`.
5. Open a pull request.

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📧 Contact

Have questions, suggestions, or just want to chat? Reach out to us at [wmohankavinda@gmail.com](mailto:wmohankavinda@gmail.com).


---
Happy coding! 🚀✨
