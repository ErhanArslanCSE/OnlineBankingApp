***Online Banking Application***

**Overview**
This project is an Online Banking Application developed as part of the MSc Artificial Intelligence program for the SWE7102-Advanced Software Development module. The application provides a comprehensive software solution for various online banking activities such as user management, account management, payment processing, and transaction surveillance.

**Key Features**
User Management: Secure user authentication and profile management.
Account Management: Support for multiple account types including bank accounts, credit card accounts, investment accounts, and loan accounts.
Payment Processing: Handles different payment methods such as bank transfers, credit card payments, PayPal payments, bill payments, and salary payments.
Transaction Management: Detailed management of transactions, including categorization into income, expenses, and transfers.

**Project Structure**
Model: Contains entity classes and enums for the application's data.
account
authentication
payment
transactions
common
Factory: Factory classes for creating instances of models.
DAO (Data Access Object): Handles database interactions.
Service: Business logic and application services.
UI: User Interface components.


**Design Principles and Patterns**
The application follows object-oriented programming (OOP) principles and uses several design patterns to ensure maintainability and scalability:

Singleton Pattern: Used in the CurrentUser class.
Factory Pattern: Used for creating instances of models like Account, Payment, and User.
DAO Pattern: Separates data access logic from business logic.
Strategy Pattern: For different validation requirements.
Observer Pattern: To manage event handling and notifications.
Template Method Pattern: To define algorithm structures in the base class.
MVC Pattern: Separates the application into Model, View, and Controller for better organization.


**Setup and Installation**
*Prerequisites*
Java Development Kit (JDK) 8 or higher
Apache Maven
MySQL Database


*Steps to Run*

Clone the Repository

git clone https://github.com/your-username/online-banking-application.git
cd online-banking-application

*Configure Database*

Create a MySQL database named online_banking.
Update the application.properties file with your database credentials.

*Build the Project*
mvn clean install

*Run the Application*
java -jar target/online-banking-application.jar

**Usage**

User Authentication
Users can log in with their username and password.
Different user roles have specific permissions and access levels.

Account Management
Users can view, create, and manage their accounts.
Admin users can create and manage accounts for customers.

Payment Processing
Users can make various types of payments.
Payments are validated and securely processed.

Transaction Management
Users can view their transaction history.
Transactions can be categorized and managed.


Contribution
Contributions are welcome! Please fork the repository and create a pull request with your changes.

License
This project is licensed under the MIT License. See the LICENSE file for more details.

Contact
For any questions or support, please contact Erhan ARSLAN.
