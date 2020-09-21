# Conversion

The UI uses a maven plugin called frontend-maven-plugin, to install node and run npm commands. It does
not work on a linux OS, due to all the dependencies not being pulled. 

Just install node, and npm then run 
```shell script
npm install
```
On windows the maven build works.
```shell script
mvn clean install
```


## Rest Controller

Convert Kelvin to Celsuis
```http request
GET /conversions/ktoc/{{kelvin}}
```

Convert Celsuis to Kelvin
```http request
GET /conversions/ctok/{{celsius}}
```

Convert Miles to Kilometers
```http request
GET /conversions/mtok/{{miles}}
```

Convert Kilometers to Miles
```http request
GET /conversions/ktom/{{kilometers}}
```

List all Available Conversions in JSON
```http request
GET /conversions/
```

Specify conversion
```http request
GET /conversions/convert/{{selected}}/{{value}}
```


## UI Project
The UI project compile and are copied to the resource folder of the service.
Use npm tasks:
     "compile": to compile locally in the ui project
     "serve": to run a local dev server
     "run-dev": runs compile then serve
     "production": compile and copy the ui project to the service
     
## The service 
   You can run the application main from the IDE. Then go to http://localhost:8080 for the ui.
   There is a single unit test for the controller
