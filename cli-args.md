# Command-Line Arguments

- `--config=/path/to/file`: Specifies path to email configuration. See configuring-email.md for more information on how to configure email.
  - This file is expected to contain data in the following format:
    ```
    username=changeme@gmail.com
    password=password here
    smtpHost=smtp.gmail.com
    smtpPort=587
    ```
- `--load-indexes=/path/to/file`: Specifies path to file to load courses, indexes and lessons from.
  - This file is expected to contain data in the following format:
    ```
    course:|CE1105|DIGITAL LOGIC|CSE|3
    index:|10005
    lesson:|10005|Lecture|Tuesday|LT2A|CE1|1,1,1,1,1,1,1,1,1,1,1,1,1|11|13
    ```
    where lines are
    ```
    course:|course code|course name|school|AUs
    ```
    ```
    index:|index number
    ```
    ```
    lesson:|index number|type|day|location|group number|thirteen 1s or 0s, 1 if lesson is held on that week else 0|start period|end period
    ```
     where `start period` and `end period` are integers where `0` represents `0800` and `31` represents `2330`.
- `--create-admin`: Creates admin account with username: `admin`, password: `admin`
- `--create-students`: Creates test student accounts with username: `student1`, password: `1` and username: `student2`, password: `2`
