package com.procsin.API.Controller;

import com.procsin.API.Model.AuthToken;
import com.procsin.API.Model.LoginRequestModel;
import com.procsin.API.Model.UpdatePasswordRequestModel;
import com.procsin.API.Service.Interface.UserService;
import com.procsin.Configuration.TokenProvider;
import com.procsin.DB.Entity.UserManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody LoginRequestModel loginModel) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginModel.username,
                        loginModel.password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findOne(userDetails.getUsername());
        if (user.getActive()) {
            return ResponseEntity.ok(new AuthToken(token,user));
        }
        else {
            throw new IllegalArgumentException("Hatali");
        }
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public User saveUser(@RequestBody User user){
        return userService.save(user);
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public User updatePassword(@RequestBody UpdatePasswordRequestModel model) {
        return userService.updatePassword(model.username, model.oldPassword, model.newPassword);
    }
}
