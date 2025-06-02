package trixo.api.trixo_api;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.stripe.Stripe;

@SpringBootApplication
public class TrixoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrixoApiApplication.class, args);
		initializeFirebase();
        initializeStripe();
	}

	private static void initializeStripe() {
        Stripe.apiKey = "sk_test_51RVWvGQtk4e56mvxuAaLOq7cxs32PrEPTyEuV12ZGARsIJMVAFPP0ZcB5owZlHfCCz74wcO6a69kGSGJqCgGq78i00bcSmTMXf";
    }

    private static void initializeFirebase() {
		try {
            FileInputStream serviceAccount = new FileInputStream("/home/ubuntu/API/firebase-service.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("trixo-1eacc.firebasestorage.app")
				.build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            System.err.println("Failed to initialize Firebase: " + e.getMessage());
        }
	}

}
