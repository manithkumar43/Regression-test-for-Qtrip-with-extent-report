QTrip Automation Testing Framework 🚀

    

📌 Project Overview

This repository contains an end-to-end automation testing framework for the QTrip web application, built using Selenium WebDriver with Java and TestNG. The framework automates complete user journeys such as Registration, Login, Booking Adventures, Counting Adventures, Removing and Cancelling Reservations.

This project follows industry-standard automation practices and is designed to be scalable, maintainable, and interview-ready.


---

🛠️ Tech Stack & Tools

Language: Java

Automation Tool: Selenium WebDriver

Test Framework: TestNG

Build Tool: Gradle

Reporting: Extent Reports (XML customized)

Design Patterns: Page Object Model (POM), Singleton Pattern

Data Driven Testing: TestNG DataProvider + Excel (Apache POI)

Version Control: Git & GitHub



---

🧱 Framework Architecture

✅ Page Object Model (POM)

Each application page is represented by a separate class to improve readability and maintainability.

✅ Singleton Pattern

DriverSingleton → Ensures a single WebDriver instance

ReportSingleton → Ensures a single ExtentReport instance


✅ Data Driven Testing

Test data stored in DatasetsforQTrip.xlsx

Data supplied to tests using TestNG DataProviders


✅ Custom Selenium Wrapper

Safe click operations

Clear-and-type sendKeys

Retry mechanism for element finding

Navigation validations


✅ Reporting

Extent Reports with custom XML configuration

Step-level logging and execution status



---

🧪 Test Scenarios Covered (End-to-End)

The automation suite is logically grouped into 4 major test cases:

🔹 Test Case 01 – User Registration

Register a new user

Validate successful registration


🔹 Test Case 02 – User Login

Login using valid credentials

Validate successful authentication


🔹 Test Case 03 – Booking & Counting Adventures

Search city

Select and book multiple adventures

Count total booked adventures


🔹 Test Case 04 – Manage Reservations

Remove booked adventures

Cancel reservations

Validate updates in reservation history


📋 Test Case Mapping

Test Case	Class Name	Feature Covered	Validation

TC-01	testCase_01	User Registration	Successful account creation
TC-02	testCase_02	User Login	Valid login session
TC-03	testCase_03	Adventure Booking	Booking & count verification
TC-04	testCase_04	Reservation Management	Cancel/remove confirmation



---

📂 Project Structure

.
├── CRIO
│   └── metadata.json
├── app
│   ├── bin                     # Compiled class files
│   ├── build.gradle             # Gradle build configuration
│   ├── extent_customization_configs.xml  # Extent report config
│   └── src
│       ├── main/java/qtriptest
│       │   └── App.java
│       └── test
│           ├── java/qtriptest
│           │   ├── DriverSingleton.java
│           │   ├── ReportSingleton.java
│           │   ├── SeleniumWrapper.java
│           │   ├── DP.java
│           │   ├── pages               # Page Object classes
│           │   └── tests               # TestNG test cases
│           └── resources
│               └── DatasetsforQTrip.xlsx
├── gradle/wrapper
├── gradlew
├── settings.gradle
└── test-results.xml


---

▶️ How to Run the Tests

1️⃣ Clone the repository

git clone <your-github-repo-url>
cd manithkumar43-ME_QTRIP_QA_V2

2️⃣ Execute tests using Gradle

./gradlew test

3️⃣ Run via TestNG (optional)

Use testng.xml inside app/src/test/java/qtriptest/



---

📊 Extent Report

The framework uses Extent Reports for rich HTML reporting with a custom XML configuration.

🔹 How Extent Reports Are Implemented

ReportSingleton.java creates and manages a single ExtentReports instance across the test run

The report configuration is loaded from:

app/extent_customization_configs.xml


Reports are flushed once execution is completed to avoid duplication


🔹 What the Report Captures

Test case name and execution status

Step-level logs

Failure stack traces

Execution timeline


🔹 Report Output

HTML report is generated after execution

Location: test-output/



---

🌟 Key Highlights

Real-world end-to-end automation project

Clean framework design using POM & Singleton

Data-driven testing with Excel

Custom Selenium wrapper utilities

Professional Extent Report integration



---

🧠 Framework Explanation 

This automation framework is designed to validate complete end-to-end user journeys of the QTrip application. It uses Page Object Model to separate UI logic from test logic, Singleton pattern to manage WebDriver and reporting instances efficiently, and TestNG DataProviders to support data-driven testing from Excel files.

The framework also includes a custom Selenium wrapper to handle synchronization issues, retries, and safe element interactions. Execution results are captured using Extent Reports, customized through an external XML configuration for better readability and professional reporting.


---

📄 Project Description

QTrip Automation Testing Framework

Built an end-to-end Selenium automation framework using Java, TestNG, and Gradle

Automated user flows including registration, login, booking, and cancellation

Implemented Page Object Model, Singleton WebDriver, Data-driven testing, and Extent Reports

Designed reusable Selenium wrapper utilities for stable and maintainable tests



---

👤 Author

Manith Kumar
QA Automation Engineer | Selenium | Java | TestNG


---

🚀 Future Enhancements

Parallel execution with TestNG

CI/CD integration (Jenkins / GitHub Actions)

Cross-browser testing

Dockerized test execution



---

⭐ If you find this project useful, don’t forget to star the repository!
