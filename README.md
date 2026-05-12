# Sydney Metro Live

A real-time Sydney Metro tracker built with Spring Boot, deployable to Render via Docker.
Consumes the [Transport NSW GTFS-Realtime](https://opendata.transport.nsw.gov.au) vehicle-position feed and presents three views:

- **Map** — animated SVG diagram of the full Tallawong → Sydenham corridor with live train positions
- **Table** — sortable data table with speed, direction, nearest station, and distance per train
- **Track** — horizontal progress visualisation showing each train's position along the route in real time

---

## Tech stack

| Layer | Choice |
|---|---|
| Backend | Spring Boot 3.3 / Java 21 |
| GTFS-RT | `protobuf-java 3.25.3` + `protoc-jar-maven-plugin` (compiles `.proto` at build time) |
| Frontend | Vanilla JS + SVG (no framework) |
| Deployment | Multi-stage Dockerfile → Render |
| Data | Transport NSW GTFS-Realtime v2 (`/v2/gtfs/vehiclepos/metro`) |

---

## Running locally

```bash
export TRANSPORT_NSW_API_KEY=<your-key>   # free at opendata.transport.nsw.gov.au
./mvnw spring-boot:run
# open http://localhost:8080
```

Get a free API key at <https://opendata.transport.nsw.gov.au>.

## Deploying to Render

1. Push this repo to GitHub.
2. Create a new **Web Service** on Render, select **Docker** as the environment.
3. Add the environment variable `TRANSPORT_NSW_API_KEY` in the Render dashboard.
4. Render builds and runs the container; the app reads `$PORT` automatically.

---

## Prompts used to build this app

This app was built entirely through conversation with [Claude Code](https://claude.ai/code).
Every feature below corresponds to a prompt in that session.

1. **Initial scaffold**
   > Build a spring boot app which can easily be deployed to render using a Dockerfile.

2. **Person lookup UI** *(later replaced)*
   > Make this app allow users to lookup a person based on name country and job title. The app should provide a web user interface. Remove the todo functionality.

3. **Real data source** *(later replaced)*
   > Use a real source of data.
   *(Selected: randomuser.me API)*

4. **Complete pivot — Sydney Metro tracker**
   > Delete this project and start from scratch. Create a new project which shows a diagram of the Sydney metro with animations showing where all the current metros are at any time using the transport nsw api. The new project should also be spring boot and have a Dockerfile to deploy to render.

5. **Map fixes — layout and projection**
   > Only shows trains between Tallawong and Sydenham. Shows the trains in a straight line. Remove all the blank space between the map and the top of the page.

6. **Remove Bankstown line**
   > Don't show those stations I mean. *(clarifying "Don't shows trains from Sydenham and Bankstown")*

7. **Station corrections + clickable trains**
   > Make the trains clickable and show more details if a train is selected. Castle Hill is between Cherrybrook and Hills Showground. Bella Vista is in the wrong spot.

8. **More station fixes**
   > Bella Vista is between Norwest and Kellyville. Central is missing.

9. **Visual polish**
   > Can you fix the formatting. It looks messy and the writing is blurred. Some stations too close to each other.

10. **Mobile scrollable map**
    > Looks better but on mobile can't see all the stations or scroll to see them either.

11. **Table view**
    > Also provide an alternative view where the user can see train information in a table with speed details of each train in a table that refreshes.

12. **Mobile toggle bug fix**
    > The menu up the top is not operating correctly on mobile. When selecting table both data is visible.

13. **Mobile header layout**
    > The menu up the top is all bunched up on mobile.

14. **Nearest station + distance column**
    > In the table also show which station the train is closest to and the distance from that station.

15. **Dead-reckoning animation**
    > Animate the trains as always moving based on their current speed and position and then correct to their actual positions when a refresh is done.

16. **Track view**
    > Show a third tab which shows the trains positions as a visualisation with Tallawong on the left and Sydenham on the right. Each train is shown in a row with its position from left to right indicating where its location is relative to Tallawong and Sydenham. The table should animate in realtime and accurately update after a refresh.

17. **Next station in Track view**
    > In the third tab show the next station the train is visiting in the table.

18. **Dockerfile fix**
    > dockerfile invalid: flag '--mount=type=cache,target=/root/.m2' is missing an id argument.

19. **This README**
    > Record all prompts used to build this app in the readme.
