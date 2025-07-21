## Naagarik Feedback - Java Swing Application 📝

---

### Overview 👀

Naagarik Feedback is a desktop Java Swing application for collecting and managing user feedback.  
Users can sign up with their details and submit feedback.  
Admins can view users, their feedback, and delete user data.

---

### Features 🚀

- User signup with Name, Age, Location, Phone, Password  
- Weather display for selected cities ☀️🌧️  
- Admin login authentication 🔒  
- User list table showing Name, Location, Phone, Feedback  
- Click "+ View Feedbacks" to open popup with all feedback from a user 📋  
- Right-click user to delete user and all feedback 🗑️  

---

### Database Schema 🗄️

**Table: people**  
- id INT AUTO_INCREMENT PRIMARY KEY  
- name VARCHAR(100)  
- age INT  
- location VARCHAR(100)  
- phone_number VARCHAR(15)  
- password VARCHAR(100)  

**Table: feedback**  
- feedback_id INT AUTO_INCREMENT PRIMARY KEY  
- person_id INT  
- message TEXT  
- submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  
- FOREIGN KEY (person_id) REFERENCES people(id) ON DELETE CASCADE  

---

### Setup Instructions ⚙️

1. Clone repo:  
   ```bash
   git clone https://github.com/SandeshKhatiwada05/Naagarik-Feedback-Java-Swing.git
   cd Naagarik-Feedback-Java-Swing
   ```
## 🛠️ Setup MySQL Database

- Create database: `naagarikfeedbackswing`
- Create tables using the above schema
- Update `DBConnection` class with your DB credentials

---

## 🏃‍♂️ Build & Run

- Open project in IDE (**IntelliJ IDEA recommended**)
- Build project and run:
  - `org.JSP.User.SignUp` → to register users
  - `org.JSP.Admin.AdminLoginDialog` → to login as admin

---

## 🧑‍💻 Usage

- Users signup and submit feedback
- Admin logs in to view users in a table
- Click `+ View Feedbacks` to see user feedback in a popup
- Right-click a user row and select **Delete User** to remove user and feedback

---

## ⚙️ Technologies

- Java Swing GUI  
- MySQL with JDBC  
- Maven for dependency management

---

## Screenshots
<img width="1918" height="1078" alt="initial page" src="https://github.com/user-attachments/assets/3d8d2dd0-f1ed-428a-baff-935a69e3fd6d" />
<img width="777" height="780" alt="Signup" src="https://github.com/user-attachments/assets/dc79608a-6632-4624-b318-893d99f138d0" />


---

## 📬 Contact

For questions or support, open GitHub issues or contact **Sandesh Khatiwada**:  
[LinkedIn Profile](https://www.linkedin.com/in/sandesh-khatiwada-523b4626a/)

---

## 🔗 Repo

[GitHub Repository](https://github.com/SandeshKhatiwada05/Naagarik-Feedback-Java-Swing)

