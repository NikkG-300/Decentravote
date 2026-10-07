# DecentraVote 🗳️

A secure and user-friendly online voting platform designed to streamline the election process while ensuring transparency, integrity, and accessibility.

## 📌 Overview

DecentraVote is a web-based voting system that enables administrators to create and manage elections while allowing registered voters to securely cast their votes online. The platform focuses on authentication, vote integrity, and real-time election management.

## 🚀 Features

### Admin Module
- Secure Admin Login
- Create and Manage Elections
- Add and Manage Candidates
- View Voting Results
- Monitor Election Status
- Manage Voter Records

### Voter Module
- Secure User Registration
- Login Authentication
- View Available Elections
- Cast Vote Securely
- One Vote Per Election
- View Election Information

### Security Features
- Password Hashing using bcrypt
- Authentication and Authorization
- Input Validation
- Secure Database Storage
- Prevention of Duplicate Voting

## 🛠️ Tech Stack

### Frontend
- HTML5
- CSS3
- JavaScript

### Backend
- Node.js
- Express.js

### Database
- MySQL 8.0

### Security
- bcrypt
- CORS

## 📂 Project Structure

```text
DecentraVote/
│
├── frontend/
│   ├── index.html
│   ├── login.html
│   ├── register.html
│   ├── admin.html
│   ├── css/
│   └── js/
│
├── backend/
│   ├── routes/
│   ├── controllers/
│   ├── config/
│   ├── middleware/
│   └── server.js
│
├── database/
│   └── schema.sql
│
└── README.md
```

## ⚙️ Installation

### Clone Repository

```bash
git clone https://github.com/NikkG-300/DecentraVote.git
cd DecentraVote
```

### Install Dependencies

```bash
npm install
```

### Configure Database

Create a MySQL database and update the database credentials in your configuration file.

```sql
CREATE DATABASE decentravote;
```

### Start Server

```bash
node server.js
```

Server runs on:

```text
http://localhost:5000
```

## 📸 Screenshots

Add screenshots of:
- Login Page
- Registration Page
- Admin Dashboard
- Voting Interface
- Election Results Page

## 🎯 Use Cases

- College Elections
- Club Elections
- Student Council Voting
- Organization Polls
- Internal Committee Elections

## 🔮 Future Enhancements

- Email Verification
- OTP Authentication
- Election Analytics Dashboard
- Multi-Factor Authentication
- Cloud Deployment
- Mobile Responsive UI

## 👨‍💻 Author

**Nikhil Ganesh**

- GitHub: https://github.com/NikkG-300
- LinkedIn: https://linkedin.com/in/nikhil-ganesh-a170ba324/

## 📜 License

This project is developed for educational and learning purposes.
