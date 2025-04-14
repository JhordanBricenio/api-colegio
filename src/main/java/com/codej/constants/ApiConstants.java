package com.codej.constants;

public final class ApiConstants {

    private ApiConstants() {}

    public static final String API_BASE = "/api";
    public static final String USER_BASE = API_BASE + "/users";
    public static final String TAG_BASE = API_BASE + "/tags";
    public static final String POST_BASE = API_BASE + "/posts";
    public static final String COURSE_BASE = API_BASE + "/courses";
    public static final String DEGREE_BASE = API_BASE + "/degrees";
    public static final String ASSIGN_COURSE_TO_DEGREE = "/assign-course/{idDegree}";
    public static final String WORKSHOP_BASE = API_BASE + "/workshops";
    public static final String SERVICE_BASE = API_BASE + "/services";

    public static final String ID_IN_PATH = "/{id}";



}
