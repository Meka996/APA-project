Fitness Trainer Management System

A web-based fitness management application built using Java and Spring Boot. The system allows trainees and trainers to interact through workout plans, progress tracking, and profile management.

Features
Authentication System
User registration and login
Trainer and trainee role support
Session handling using cookies
Logout functionality
Trainee Features
View available trainers
Choose a personal trainer
View assigned workouts
Track workout progress
Update fitness information and profile
Trainer Features
View assigned trainees
Create workout plans
Monitor trainee workout history
Track calories burned and workout duration
Workout Management
Assign workouts with:
Title
Duration
Calories target
Instructions
Assigned date
Track workout completion
Store trainee notes and progress
Technologies Used
Java
Spring Boot
Spring MVC
Spring Data JPA
Hibernate
MySQL
HTML / CSS / Thymeleaf
Maven
Project Structure
Controllers

Handle application routes and user requests.

AuthController
DashboardController
ProfileController
TrainerController
TraineeController
WorkoutController
Models

Represent database entities.

User
Trainer
TrainerWorkout
Repositories

Handle database operations.

UserRepository
TrainerRepository
TrainerWorkoutRepository
Services

Contain the business logic.

UserService
TrainerService
TrainerWorkoutService
Database Design

The project uses relational database tables for:

Users
Trainer profiles
Assigned workouts

Relationships include:

One trainer can have multiple trainees
One trainee can have one selected trainer
Trainers can assign multiple workouts
Security Notes
Login sessions are handled using cookies
Role-based access is implemented
Global exception handling is included
Future Improvements
Password encryption
JWT authentication
Responsive UI improvements
Admin dashboard
Workout analytics charts
Email notifications



Authors:

Mohamed Adel
Ahmed Ehab
Basel Ashraf
Project Overview

This project demonstrates the use of Spring Boot MVC architecture with Controllers, Services, Repositories, and Models. It focuses 
on fitness training management where trainers can manage trainees and assign workout plans while trainees can track their progress 
and communicate their workout results through the system.
