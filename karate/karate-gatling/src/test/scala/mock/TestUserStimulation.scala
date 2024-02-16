package examples.performance

import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef._
import scala.concurrent.duration._

class TestUserStimulation extends Simulation {

//   val httpConf = http.baseUrl("https://jsonplaceholder.typicode.com")

//   val karateProtocol = karateProtocol(
//     "/examples/users/users.features"
//   )

  val getUser = scenario("GET method should return 200").exec(karateFeature("classpath:mock/users.feature"))

  setUp(
   getUser.inject(rampUsers(10) during (5 seconds)),
  )

}