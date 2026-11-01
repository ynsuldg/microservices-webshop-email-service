package se.iths.martin.emailserviceprojekt2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.martin.emailserviceprojekt2.controller.StatusController;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StatusController.class)
class StatusPageTests {
    @Autowired
    private MockMvc mockMvc;

    // Rotadressen och direktadressen ska fungera utan inloggning och visa rätt tjänst.
    @Test
    void statusPageIsPublic() throws Exception {
        for (String address : new String[] { "/", "/index.html" }) {
            mockMvc.perform(get(address))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith("text/html"))
                    .andExpect(content().string(containsString("Email Service")))
                    .andExpect(content().string(containsString("Service is running")));
        }
    }
}
