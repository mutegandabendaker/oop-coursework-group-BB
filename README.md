Object-Oriented Programming Coursework
======================================

### Java Console Systems + Swing Desktop Application

This repository contains solutions for the **Object-Oriented Programming (OOP) coursework**.

The project includes:

*   Console-based systems (grading & auction)
    
*   A Swing desktop application (student registration form)
    
*   CSV export
    
*   MS Access database integration (UCanAccess JDBC)
    

📁 Project Structure
====================
```text
src/
└── oop/
    └── java/
        ├── GradingSystem.java
        ├── AuctionSystem.java
        ├── Vehicle.java
        ├── Bidder.java
        └── RegistrationForm.java

database/
└── students_template.accdb

nbproject/

build.xml
manifest.mf
.gitignore
README.md
```

✅ Question 1 – Console Applications
===================================

Part A – Grading System
-----------------------

**Main Class:** GradingSystem.java

Features:

*   Accepts student scores (0–100)
    
*   Calculates grade and remark using if–else logic
    
*   Processes multiple students
    
*   Displays grade summary
    

Run:
```text
java oop.java.GradingSystem
```

Part B – Auction System
-----------------------

**Main Class:** AuctionSystem.java**Supporting Classes:** Vehicle.java, Bidder.java

Features:

*   Accepts 3 bidders
    
*   Selects highest bidder
    
*   Tracks deposits and expenses
    
*   Calculates profit or loss
    

Run:
```text
java oop.java.AuctionSystem
```

✅ Question 2 – Desktop Application (Swing)
==========================================

Student Registration Form
-------------------------

**Main Class:** RegistrationForm.java

Features:

*   Form validation
    
*   Email & password checks
    
*   Age calculation
    
*   Gender & department selection
    
*   Generates student ID
    
*   Saves to CSV
    
*   Saves to MS Access database
    

Run (NetBeans recommended):
```
Run RegistrationForm.java
```

or
```
java oop.java.RegistrationForm
```

🗄 Database Setup (Important)
=============================

This project uses **MS Access (.accdb)** with **UCanAccess JDBC driver**.

To avoid committing runtime data, only a template database is provided.

### Steps:

1.  database/students\_template.accdb
    
2.  Copy it to project root
    
3.  students.accdb
    
4.  Run the application
    

The app connects using a relative path:

```
jdbc:ucanaccess://./students.accdb
```

⚙ Requirements
==============

*   JDK 17
    
*   NetBeans (recommended)
    
*   UCanAccess JDBC driver libraries
    
*   Microsoft Access (optional, to view database)
    

📌 Notes
========

*   Runtime files are ignored by Git:
    
    *   \*.accdb
        
    *   \*.csv
        
    *   build/
        
*   Only source code is tracked
    
*   Each system runs independently via its own main method
    

📋 Submission Checklist Alignment
=================================

✔ Java source files provided via GitHub✔ GUI screenshots (to be added in report)✔ Access database template included✔ Documentation provided (this README)

Author
======
Manthan Kumar
Student Coursework Submission – OOP Module
