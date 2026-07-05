package com.sicjac.backend.config;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public final class RenderDatabaseUrlConfig {

    private RenderDatabaseUrlConfig() {
    }

    public static void apply() {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (isBlank(databaseUrl) || hasSpringConfigured("spring.datasource.url", "SPRING_DATASOURCE_URL")) {
            return;
        }

        URI uri = URI.create(databaseUrl.replaceFirst("^postgres(?:ql)?://", "postgresql://"));
        String jdbcUrl = toJdbcUrl(uri);
        System.setProperty("spring.datasource.url", jdbcUrl);

        String[] credentials = parseCredentials(uri.getRawUserInfo());
        if (credentials[0] != null && !hasSpringConfigured("spring.datasource.username", "SPRING_DATASOURCE_USERNAME")) {
            System.setProperty("spring.datasource.username", credentials[0]);
        }
        if (credentials[1] != null && !hasSpringConfigured("spring.datasource.password", "SPRING_DATASOURCE_PASSWORD")) {
            System.setProperty("spring.datasource.password", credentials[1]);
        }
    }

    private static String toJdbcUrl(URI uri) {
        StringBuilder jdbcUrl = new StringBuilder("jdbc:postgresql://")
                .append(uri.getHost());

        if (uri.getPort() != -1) {
            jdbcUrl.append(':').append(uri.getPort());
        }

        jdbcUrl.append(uri.getRawPath());

        if (!isBlank(uri.getRawQuery())) {
            jdbcUrl.append('?').append(uri.getRawQuery());
        }

        return jdbcUrl.toString();
    }

    private static String[] parseCredentials(String userInfo) {
        if (isBlank(userInfo)) {
            return new String[] { null, null };
        }

        String[] parts = userInfo.split(":", 2);
        String username = decode(parts[0]);
        String password = parts.length > 1 ? decode(parts[1]) : null;
        return new String[] { username, password };
    }

    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private static boolean hasSpringConfigured(String systemProperty, String springEnv) {
        return !isBlank(System.getProperty(systemProperty))
                || !isBlank(System.getenv(springEnv));
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
