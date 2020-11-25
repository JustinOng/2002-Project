# MySTARS

This is a replica of MySTARS built for CE2002's assignment.

An exported `mystars.jar` can be found at `build/`. To adhere strictly to the requirement that this functions as a console-based application, the `.jar` has to be executed in a unix shell (eg WSL or a VM).

## Configuring Email
1. Copy `app.properties.sample` to `app.properties`
2. Replace the values for `username` and `password`, changing the SMTP configuration if necessary

## Command-Line Arguments
- `--config=/path/to/file`: Specifies path to app configuration. If not specified, the app tries to load this file from the current working directory
  - This file is expected to contain data in the following format:
    ```
    username=changeme@gmail.com
    password=password here
    smtpHost=smtp.gmail.com
    smtpPort=587
    maxAUs=21
    ```
- `--load-indexes=/path/to/file`: Specifies path to file to load courses, indexes and lessons from. A copy of the file with the expected format has been uploaded into the repository, using `--load-indexes=courseinfo.txt` will load it.
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
    lesson:|index number|type|day|location|group number|weeks|start period|end period
    ```
      - `weeks`: thirteen 1s or 0s separated by a comma, 1 if lesson is held on that week else 0
      - `start period`, `end period`: integers where `0` represents `0800` and `31` represents `2330`.
- `--create-admin`: Creates admin account with username: `admin`, password: `admin`
- `--create-students`: Creates test student accounts with username: `student1`, password: `1` and username: `student2`, password: `2`

## Running

The application can be started with `java -jar build/mystars.jar --create-admins --create-students --load-indexes=courseinfo.txt` from the root of this repository. It is assumed that `app.properties` has been setup correctly.

The application will then be started with the following accounts:
- `admin`:`admin`
- `student1`:`1`
- `student2`:`2`
