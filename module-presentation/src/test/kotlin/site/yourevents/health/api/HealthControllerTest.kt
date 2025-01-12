import io.kotest.core.spec.style.DescribeSpec
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import site.yourevents.health.api.HealthController
import site.yourevents.type.SuccessCode

class HealthControllerTest : DescribeSpec({
    val healthController = HealthController()
    val mockMvc = MockMvcBuilders.standaloneSetup(healthController).build()

    describe("HealthController") {
        it("healthCheck() 메서드를 호출하면 200 OK를 반환한다.") {
            mockMvc.get("/health-check") {
            }.andExpect {
                status { isOk() }
                jsonPath("$.code") { value(SuccessCode.REQUEST_OK.code) }
            }
        }
    }
})
