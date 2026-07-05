package com.sicjac.backend.config;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public final class RenderDatabaseUrlConfig {

    private static final int POSTGRESQL_DEFAULT_PORT = 5432;

    private RenderDatabaseUrlConfig() {
    }

    public static void apply() {
        String renderDatabaseUrl = System.getenv("DATABASE_URL");
        String databaseUrl = firstPresent(
                System.getenv("SPRING_DATASOURCE_URL"),
                renderDatabaseUrl,
                System.getenv("DB_URL"));

        if (isBlank(databaseUrl) || !isPostgresUrl(databaseUrl)) {
            return;
        }

        URI uri = toUri(databaseUrl);
        System.setProperty("spring.datasource.url", toJdbcUrl(uri));

        String[] credentials = parseCredentials(uri.getRawUserInfo());
        String[] renderCredentials = parseCredentialsFromUrl(renderDatabaseUrl);
        String username = firstPresent(
                System.getenv("SPRING_DATASOURCE_USERNAME"),
                credentials[0],
                renderCredentials[0],
                System.getenv("DB_USER"));
        String password = firstPresent(
                System.getenv("SPRING_DATASOURCE_PASSWORD"),
                credentials[1],
                renderCredentials[1],
                System.getenv("DB_PASSWORD"));

        if (!isBlank(username)) {
            System.setProperty("spring.datasource.username", username);
        }
        if (!isBlank(password)) {
            System.setProperty("spring.datasource.password", password);
        }

        validateRequiredConfiguration();
    }

    private static String toJdbcUrl(URI uri) {
        StringBuilder jdbcUrl = new StringBuilder("jdbc:postgresql://")
                .append(uri.getHost());

        int port = uri.getPort() != -1 ? uri.getPort() : POSTGRESQL_DEFAULT_PORT;
        jdbcUrl.append(':').append(port);

        jdbcUrl.append(uri.getRawPath());

        if (isBlank(uri.getRawQuery())) {
            jdbcUrl.append("?sslmode=require");
        } else if (uri.getRawQuery().contains("sslmode=")) {
            jdbcUrl.append('?').append(uri.getRawQuery());
        } else {
            jdbcUrl.append('?').append(uri.getRawQuery()).append("&sslmode=require");
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

    private static String[] parseCredentialsFromUrl(String databaseUrl) {
        if (isBlank(databaseUrl) || !isPostgresUrl(databaseUrl)) {
            return new String[] { null, null };
        }

        return parseCredentials(toUri(databaseUrl).getRawUserInfo());
    }

    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private static URI toUri(String databaseUrl) {
        return URI.create(databaseUrl
                .replaceFirst("^jdbc:postgresql://", "postgresql://")
                .replaceFirst("^postgres://", "postgresql://"));
    }

    private static boolean isPostgresUrl(String value) {
        return value.startsWith("jdbc:postgresql://")
                || value.startsWith("postgresql://")
                || value.startsWith("postgres://");
    }

    private static String firstPresent(String... values) {
        for (String value : values) {
            if (!isBlank(value)) {
                return value;
            }
        }
        return null;
    }

    private static void validateRequiredConfiguration() {
        if (isBlank(System.getProperty("spring.datasource.username"))
                || isBlank(System.getProperty("spring.datasource.password"))) {
            throw new IllegalStateException(
                    "PostgreSQL credentials are missing. Configure DATABASE_URL with user/password, "
                            + "or set SPRING_DATASOURCE_USERNAME and SPRING_DATASOURCE_PASSWORD in Render.");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
