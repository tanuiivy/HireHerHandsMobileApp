**HireHerHands Mobile App (Version 3.2)**

**Overview**
HireHerHands is a mobile app designed to connect women skilled in manual labor (like plumbing, construction, electrical repairs, painting, etc.) with clients who need their services. It is inspired by TaskRabbit, but localized for Kenya and focused on women empowerment giving them fair job opportunities and visibility.

**Project**
Purpose Many women in hands-on professions struggle to find consistent, fairly paid work. 
HireHerHands aims to create a safe and easy-to-use platform where:

Clients (Customers) can find and hire skilled female workers.
Workers can create profiles, list their skills, and get job requests.
Admins can monitor and manage the entire system.
Navigation Flow (App Movement) The app will have three main dashboards → one for each user role.

**General Flow**
Launch Screen → checks if the user is logged in
If not logged in → goes to Login / Signup Screen
If logged in → goes to their dashboard (based on role)

**Customer Dashboard**
Post a new job
View available workers
See job history 

**Worker Dashboard**
View job requests
Accept or reject jobs
Manage profile and skills 

**Admin Dashboard**
View all jobs and users
Approve new worker accounts
See simple reports (e.g., total workers, active jobs)

**Data Model (What will be stored)**
The app will store data locally using Room Database. 
Here’s a simple explanation of what the data looks like:

***Users*** → Info about all users (name, email, phone, role, password)
***Jobs*** → Details of each job (title, description, customer, worker, date, status)
***Ratings*** → Feedback after jobs (score, comment)
***Skills*** → List of possible skills (e.g., electrician, plumber)
***Worker Profiles*** → Extra details about workers (bio, skills, average rating)
