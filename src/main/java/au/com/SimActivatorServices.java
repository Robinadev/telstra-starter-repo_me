import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/sim")
public class SimActivationController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationRequest request) {
        try {
            // Forward the ICCID to the actuator service
            String actuatorUrl = "http://localhost:8444/actuate";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Prepare JSON body for actuator
            String jsonPayload = String.format("{\"iccid\":\"%s\"}", request.getIccid());

            HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

            // Send POST request to the actuator
            ResponseEntity<ActuatorResponse> response = restTemplate.postForEntity(
                    actuatorUrl, entity, ActuatorResponse.class);

            // Check if activation was successful
            if (response.getBody() != null && response.getBody().isSuccess()) {
                System.out.println("SIM Activation Successful for ICCID: " + request.getIccid());
                return ResponseEntity.ok("SIM Activation Successful");
            } else {
                System.out.println("SIM Activation Failed for ICCID: " + request.getIccid());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SIM Activation Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during SIM activation");
        }
    }
}
