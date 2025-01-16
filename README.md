# medilabo

## Insert initial data into MongoDB database
To insert the initial data into the MongoDB database:
For patients collection: 
 - enable the 'DataLoader' class located in the package 'com.medilabo.MedilaboSolutions.config'
 - uncomment the 'loadData' method
 - restart the application
 
For notes collection: 
 - enable the 'DataLoader' class located in the package 'com.medilabo.notes.config'
 - uncomment the 'loadData' method
 - restart the application

Once the data has been inserted, you can comment out the method again to avoid reinserting data on every application startup