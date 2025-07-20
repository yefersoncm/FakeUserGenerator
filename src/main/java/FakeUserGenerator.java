import com.github.javafaker.Faker;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

public class FakeUserGenerator {
    private static final String API_URL = "http://localhost:8081/api/v1/users";
    private static final Faker faker = new Faker();

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        // Creating 100 users
        for (int i = 0; i < 100; i++) {
            Map<String, Object> user = new HashMap<>();
            user.put("name", faker.name().fullName());
            user.put("email", faker.internet().emailAddress());
            user.put("age", faker.number().numberBetween(18, 100));
            user.put("password", "Password@123");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(user, headers);

            try {
                ResponseEntity<String> response = restTemplate.exchange(
                        API_URL, HttpMethod.POST, request, String.class
                );
                System.out.println("User " + (i + 1) + " created: " + response.getBody());
            } catch (Exception e) {
                System.err.println("Error creating user " + (i + 1) + ": " + e.getMessage());
            }
        }

        // Fetch and print all users
        fetchAndPrintAllUsers(restTemplate);
    }

    private static void fetchAndPrintAllUsers(RestTemplate restTemplate) {
        String url = API_URL + "/all"; // Ensure your API has a GET /all endpoint
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println("\n=== All Users ===");
            System.out.println(response.getBody());
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
    }
}
