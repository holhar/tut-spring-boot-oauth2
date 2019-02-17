package de.holhar.spring_oauth2.ui.web;

import de.holhar.spring_oauth2.ui.domain.User;
import de.holhar.spring_oauth2.ui.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class WebController {

    @Autowired
    private UserRepository users;

    @RequestMapping({"/user", "/me"})
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();

        Optional<User> userOptional = users.findByName(principal.getName());
        User loggedInUser;
        if (!userOptional.isPresent()) {
            User newUser = new User();
            newUser.setName(principal.getName());
            // TODO: Add a field in the User object to link to a unique identifier in the external provider.
            users.save(newUser);
            loggedInUser = newUser;
        } else {
            loggedInUser = userOptional.get();
        }

        map.put("name", loggedInUser.getName());
        return map;
    }
}
