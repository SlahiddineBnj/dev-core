package com.rest.core.validation;

import com.rest.core.dto.authentication.SignupRequest;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    public static List<String> ValidateSignup(SignupRequest request) {
        List<String> errors_list = new ArrayList<>();
        String username = request.getUsername();
        if (username.length() < 8)
            errors_list.add("Username is too short !");
//        String email = request.getEmail();
//        if (!email.matches("^[a-zA-Z0-9]{3,}@^[a-zA-Z0-9]{3,}"))
//            errors_list.add(("Please check email address format , it should be " +
//                    "xyz@example.com !")) ;
        String password = request.getPassword();
        if (password.length() < 8)
            errors_list.add("Please choose a stronger password !");
        String firstName = request.getFirstName();
        if (firstName.length() < 3)
            errors_list.add("FirstName is too short ! ");
        String lastName = request.getLastName();
        if (lastName.length() < 3)
            errors_list.add("LastName is too short ! ");

        return errors_list;
    }

}