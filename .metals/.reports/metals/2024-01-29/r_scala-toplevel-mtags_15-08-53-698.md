error id: file:///D:/karate%20gatling%20gitclone%20check/karate/karate-gatling/src/test/scala/mock/TestUserStimulation.scala:[21..27) in Input.VirtualFile("file:///D:/karate%20gatling%20gitclone%20check/karate/karate-gatling/src/test/scala/mock/TestUserStimulation.scala", "package examples.

import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef._
import scala.concurrent.duration._

class TestUserStimulation extends Simulation {

//   val httpConf = http.baseUrl("https://jsonplaceholder.typicode.com")

//   val karateProtocol = karateProtocol(
//     "/examples/users/users.features"
//   )

  val getUser = scenario( senarioName = "getcall").exec(karateFeature("classpath:mock/users.feature"))

  setUp(
   getUser.inject(rampUsers(10) during (5 seconds)),
  )

}")
file:///D:/karate%20gatling%20gitclone%20check/karate/karate-gatling/src/test/scala/mock/TestUserStimulation.scala
file:///D:/karate%20gatling%20gitclone%20check/karate/karate-gatling/src/test/scala/mock/TestUserStimulation.scala:3: error: expected identifier; obtained import
import com.intuit.karate.gatling.PreDef._
^
#### Short summary: 

expected identifier; obtained import