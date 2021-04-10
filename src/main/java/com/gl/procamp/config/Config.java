package com.gl.procamp.config;

public enum Config {
    CONFIG();

    private static String BASE_URL;

    public static String getAuthBearerToken() {
        return AUTH_BEARER_TOKEN;
    }

    public static void setAuthBearerToken(String authBearerToken) {
        AUTH_BEARER_TOKEN = authBearerToken;
    }

    private static String AUTH_BEARER_TOKEN;
    private static String GET_POSTS_PATH;
    private static String USER_PATH;

    Config() {
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public static String getUserPath() {
        return USER_PATH;
    }

    public static void setUserPath(String userPath) {
        USER_PATH = userPath;
    }

    public static String getGetPostsPath() {
        return GET_POSTS_PATH;
    }

    public static void setGetPostsPath(String getPostsPath) {
        GET_POSTS_PATH = getPostsPath;
    }
}
