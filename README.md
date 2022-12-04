# crud-vuejs-spring-boot


## Develop

### DataBase
```
cd backend

#run db
mvn docker:run -Ddocker_host=localhost

#stop db
mvn docker:stop -Ddocker_host=localhost
```

### Backend
```
cd backend

#run backend
mvn spring-boot:run

#run tests
mvn clean verify -Ddocker_host=localhost
```

### Frontend
```
cd frontend

#run frontend
npm run serve
```