# Getting Started

### First-Time Setup

1) Install MySQL: `brew install mysql`
2) Start MySQL: `brew services start mysql`
3) Create the database: `mysql -u root -p -e "CREATE DATABASE inventory_db;"

### Testing Locally

1) Check if mysql is running by using ```ps aux | grep mysql```
2) If not started, set up your local database i.e ```brew services start mysql```
3) To run the application, in the /backend directory run ```./gradlew bootRun --args='--spring.profiles.active=local'```

### Other helpful commands

1) Stop: ```brew services stop mysql```
2) Restart: ```brew services restart mysql```
3) Check status: ```brew services info mysql```