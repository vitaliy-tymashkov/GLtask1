package com.gl.procamp.repository;

public class UserUtility {
    private final static String name = "Tenali Ramakrishna";
    private final static String namePatched = "Tenali Ramakrishna_patched";
    private final static String email = "tenali.ramakrishna2@15ce.com";
    private final static String gender = "Male";
    private final static String status = "Active";

    private UserUtility() {
        throw new UnsupportedOperationException();
    }

    public static String getUserJson() {
        return "{\n" +
                "\"name\":\"" + name + "\"," +
                "\"gender\":\"" + gender + "\", " +
                "\"email\":\"" + email + "\", " +
                "\"status\":\"" + status + "\" \n}";
    }

    public static String getUserPatchedJson(String id) {
        return "{\n" +
                "\"id\":\"" + id + "\"," +
                "\"name\":\"" + namePatched + "\"," +
                "\"gender\":\"" + gender + "\", " +
                "\"email\":\"" + email + "\", " +
                "\"status\":\"" + status + "\" \n}";
    }

    public static String getName() {
        return name;
    }

    public static String getNamePatched() {
        return namePatched;
    }

    public static String getEmail() {
        return email;
    }

    public static String getGender() {
        return gender;
    }

    public static String getStatus() {
        return status;
    }
}
