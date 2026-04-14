package com.codej.constants;

public final class ApiConstants {

    private ApiConstants() {}

    public static final String API_BASE = "/api";
    public static final String USER_BASE = API_BASE + "/users";
    public static final String TEACHER_BASE = API_BASE + "/teachers";
    public static final String TEACHER_ASSIGNMENTS  = API_BASE + "/teachers/assignments";
    public static final String TEACHER_ATTENDANCES  = API_BASE + "/teachers/attendances";
    public static final String STUDENT_BASE = API_BASE + "/students";
    public static final String PARENT_BASE = API_BASE + "/parents";
    public static final String ROLE_BASE = API_BASE + "/roles";
    public static final String SETTING_BASE = API_BASE + "/settings";
    public static final String MANAGEMENT_BASE = API_BASE + "/managements";
    public static final String EDUCATION_LEVEL_BASE =API_BASE+ "/education-levels";
    public static final String REGISTRATION_BASE = API_BASE + "/registrations";
    public static final String PAYMENT_BASE = API_BASE + "/payments";
    public static final String DEGREE_BASE = API_BASE + "/degrees";
    public static final String COURSE_BASE = API_BASE + "/courses";
    public static final String STUDENT_GRADE =API_BASE+  "/student-grades";
    public static final String WORKSHOP_BASE = API_BASE + "/workshops";
    public static final String SERVICE_BASE = API_BASE + "/services";

    public static final String ID_IN_PATH = "/{id}";

}
