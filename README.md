Project Description: Hotel Reservation System

Overview:
The Hotel Reservation System is a full-stack application designed to facilitate hotel reservations for users. It provides functionality for users to browse hotels, make reservations, and manage their bookings. The system incorporates features for location-based filtering, allowing users to find hotels nearest to their location. Additionally, users can cancel their reservations with a minimum notice period of 2 hours. Staff and administrators have exclusive privileges to manage rooms and create new hotels within the system.

Key Features:

    User Authentication and Authorization:
        Users can register, login, and manage their accounts.
        Role-based access control ensures that only staff and administrators have access to administrative functionalities.

    Hotel Browsing and Reservation:
        Users can browse through a list of available hotels.
        Hotel listings display essential information such as location, amenities, and available rooms.
        Users can make reservations for desired dates and room types.

    Location-based Filtering:
        The system provides functionality to filter hotels based on the user's location.
        Users can input their location, and the system will display hotels nearest to them.

    Reservation Management:
        Users can view their upcoming reservations and manage them.
        Reservation details include booking dates, room type, and cancellation policies.
        Users can cancel reservations with a minimum notice period of 2 hours before the check-in time.

    Staff and Admin Privileges:
        Staff members and administrators have access to additional functionalities:
            Adding new rooms to existing hotels.
            Creating new hotels in the system.

Technology Stack:

    Java Spring Boot (Back-End):
        Spring Boot provides the foundation for building robust, scalable, and efficient back-end services.
        It offers features such as dependency injection, MVC architecture, and security configurations.
        Spring Data JPA facilitates database interactions and entity management.

    React JS (Front-End):
        React JS is used to create dynamic and interactive user interfaces.
        It offers component-based architecture for building reusable UI components.
        React Router enables client-side routing for seamless navigation within the application.

    MySQL Database:
        MySQL is utilized for data storage and management.
        It offers relational database capabilities, ensuring data integrity and consistency.
        MySQL provides scalability and performance for handling large volumes of hotel and reservation data.
