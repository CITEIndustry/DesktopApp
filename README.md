# DesktopApp


A. Database
The database will consist of a single User table with the attributes:
  - 1. Id: It is the numerical primary key that is autoincreased.
  - 2. Name: A varchar where we document the user's name.
  - 3. Password: A varchar where we store the user's password.
The database comes with a test user with id: 1, name: "user1" and password: 1234.
To get the database you must run the "createDatabase.java" program and you must have the "utilsSQLite.java" program in the same space to call functions.
Once "createDatabase.java" has been executed there will be in its same folder a document "databaseIndustrial.db", with the user mentioned above.

B. Server API that connects to the app
We have a "Server.java" class that upon receiving a message will compare whether we should send components or a commit. To send the confirmation we will receive a string from the application
of text coded in a certain way, the server will extract the necessary components and compare the credentials sent with the database, if it finds a user with these
credentials will make a broadcast that says that everything is fine to start the application, if not it will say that there is an error and will cut the connection.
