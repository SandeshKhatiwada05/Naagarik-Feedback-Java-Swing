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
