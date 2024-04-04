package com.example.excursionPlanning.securityconfig;

public class SecurityConstants {

    public static final String REST_SIGN_UP_URLS = "/auth/api/**";
    public static final String SIGN_UP_URLS = "/auth/**";

    public static final String LOG_OUT_URLS = "/logout";
    public static final String SECRET_KEY = "26452948404D6351655468576D5A7134743777217A25432A462D4A614E645267";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME = 600_000;

}
