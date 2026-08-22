package bg.tuvarna.sit.project.ps.internshipmanagement.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final String secret;
    private final long expirationMs;

    public JwtService(
            @Value("${app.jwt.secret:change-me-to-a-long-secret-key-at-least-32-chars}") String secret,
            @Value("${app.jwt.expiration-ms:86400000}") long expirationMs
    ) {
        this.secret = secret;
        this.expirationMs = expirationMs;
    }

    public String generateToken(Long userId, String email, String role) {

        String header = base64(
                "{\"alg\":\"HS256\",\"typ\":\"JWT\"}"
        );

        long now = Instant.now().getEpochSecond();
        long expiration = now + expirationMs / 1000;

        String payload = base64(
                "{\"sub\":\"" + userId +
                        "\",\"email\":\"" + escape(email) +
                        "\",\"role\":\"" + role +
                        "\",\"iat\":" + now +
                        ",\"exp\":" + expiration +
                        "}"
        );

        String data = header + "." + payload;

        return data + "." + sign(data);
    }

    public boolean isValid(String token) {

        try {
            String[] parts = token.split("\\.");

            if (parts.length != 3) {
                return false;
            }

            String data = parts[0] + "." + parts[1];

            if (!MessageDigest.isEqual(
                    sign(data).getBytes(StandardCharsets.UTF_8),
                    parts[2].getBytes(StandardCharsets.UTF_8)
            )) {
                return false;
            }

            String payload = new String(
                    Base64.getUrlDecoder().decode(parts[1]),
                    StandardCharsets.UTF_8
            );

            long expiration = Long.parseLong(
                    payload.replaceAll(
                            ".*\\\"exp\\\":(\\d+).*",
                            "$1"
                    )
            );

            return expiration > Instant.now().getEpochSecond();

        } catch (Exception e) {
            return false;
        }
    }

    public String getEmail(String token) {
        return claim(token, "email");
    }

    public String getRole(String token) {
        return claim(token, "role");
    }

    public Long getUserId(String token) {
        return Long.valueOf(claim(token, "sub"));
    }

    private String claim(String token, String claimName) {

        String payload = new String(
                Base64.getUrlDecoder().decode(token.split("\\.")[1]),
                StandardCharsets.UTF_8
        );

        return payload.replaceAll(
                ".*\\\"" + claimName + "\\\":\\\"([^\\\"]+)\\\".*",
                "$1"
        );
    }

    private String base64(String value) {

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(
                        value.getBytes(StandardCharsets.UTF_8)
                );
    }

    private String sign(String data) {

        try {

            Mac mac = Mac.getInstance("HmacSHA256");

            SecretKeySpec secretKey = new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            );

            mac.init(secretKey);

            byte[] signature = mac.doFinal(
                    data.getBytes(StandardCharsets.UTF_8)
            );

            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(signature);

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Could not create JWT signature",
                    e
            );
        }
    }

    private String escape(String value) {

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}