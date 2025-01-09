import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import site.yourevents.auth.api.AuthController
import site.yourevents.auth.dto.request.LoginRequest
import site.yourevents.auth.dto.response.LoginResponse
import site.yourevents.auth.facade.AuthFacade
import site.yourevents.type.SuccessCode
import java.util.UUID

class AuthControllerTest : DescribeSpec({
    val authFacade = mockk<AuthFacade>()
    val authController = AuthController(authFacade)
    val mockMvc = MockMvcBuilders.standaloneSetup(authController).build()

    describe("AuthController") {
        context("로그인 API 호출 시") {
            it("로그인에 성공하면 SuccessCode와 LoginResponse를 반환한다") {
                val accessToken = "eyQqklsdfIUedkslslk-1NLQI"
                val loginRequest = LoginRequest(
                    code = "thisIsSomeKindOfAuthorizationCode"
                )
                val loginResponse = LoginResponse(
                    userId = UUID.randomUUID(),
                    socialId = "12345678",
                    nickname = "yes!",
                    accessToken = accessToken
                )

                every { authFacade.login(loginRequest) } returns loginResponse

                mockMvc.post("/login") {
                    contentType = MediaType.APPLICATION_JSON
                    content = ObjectMapper().writeValueAsString(loginRequest)
                }.andExpect {
                    status { isOk() }
                    jsonPath("$.code") { value(SuccessCode.LOGIN_SUCCESS.code) }
                    jsonPath("$.result.accessToken") { value(accessToken) }
                }
            }
        }
    }
})
