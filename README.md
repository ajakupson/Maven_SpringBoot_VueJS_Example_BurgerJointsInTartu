# Maven_SpringBoot_VueJS_Example_BurgerJointsInTartu
## Foursquare API, HERE maps, OpenCage Geocoder
### Webapp that shows all burger joints in Tartu with their latest pictures of a burgers

#### Application is deployed on [Heroku](https://burger-joints-in-tartu.herokuapp.com/)


```
src/main/
├── java/
|   └── BurgerJointsInTartu/ -- server side
|       ├── SpringBootMavenExampleBurgerJointsInTartuApplication.java
|       ├── Config/
│       |   └── WebConfiguration.java
│       ├── Controllers/
│       |   ├── EntryPointController.java
│       |   └── BurgerJointsController.java
│       ├── Models/
│       |   ├── CompactVenueMod.java
|       |   ├── PhotoGroupWrapper.java
│       |   └── MapMarker.java
│       ├── Services/
│       |   ├── FoursquareService.java
│       |   └── aws/
|       |       ├── JsonApiGatewayCaller.java
|       |       ├── ApiGatewayResponse.java
|       |       └── ApiGatewayException.java
|       └──Utils/
|         └── xConstants.java
|
└── resources/ -- everything related to client side
.   ├── static/front-end
.   |    ├── vuejs-app/
.   |    |   ├── app.js -- VueJS app
    |    |   ├── build/
    |    |   |   └── main.bundle.js -- compiled JavaScript used in index.html
    |    |   ├── components/
    |    |   |   └── VLink.js
    |    |   ├── layouts/
    |    |   |   └── Main.js
    |    |   └── pages/
    |    |       ├── Home.js
    |    |       ├── BurgerJoints.js
    |    |       └── NotFound.js
    |    └──assets/ -- third party libs, custom styles, images and JS utils
    |       ├── css/
    |       │   └── ...
    |       ├── img/
    |       │   └── ...
    |       └── js/
    |            └── ...
    |
    ├── templates/
    |   └── index.html
    |
    └── application.properties
...
```
