package com.codej.constants;

public final class ApiConstants {

    private ApiConstants() {}

    public static final String API_BASE = "/api";

    public static final class Post {
        public static final String BASE = API_BASE + "/post";
        public static final String PAGE = "/page/{page}";
        public  static final String IN_ID_PATH = "/{id}";
    }

    public static final class Tag {
        public static final String BASE = API_BASE + "/tag";
        public  static final String IN_ID_PATH = "/{id}";
    }

    public static final class User {
        public static final String BASE = API_BASE + "/user";
        public static final String LOGIN = "/login";
    }

}
