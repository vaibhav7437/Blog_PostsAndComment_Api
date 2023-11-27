package com.myblog.Controller;

import com.myblog.Entity.Role;
import com.myblog.Entity.User;
import com.myblog.Payload.JWTAuthResponse;
import com.myblog.Payload.LoginDto;
import com.myblog.Payload.SignupDto;
import com.myblog.Repository.RoleRepository;
import com.myblog.Repository.UserRepository;
import com.myblog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashSet;

import java.util.Set;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return new ResponseEntity<>("User logged out successfully!", HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto
                                                                    loginDto){
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }



    @PostMapping("/signup")
   public ResponseEntity<?> CreateUser(@RequestBody SignupDto signupDto) {
        if (userRepository.existsByUsername(signupDto.getUsername())) {
            return new ResponseEntity<>("User Already Exist ", HttpStatus.BAD_REQUEST);

        }
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            return new ResponseEntity<>("Email is Already Exist ", HttpStatus.BAD_REQUEST);

        }
        User user = new User();
        user.setName(signupDto.getName());
        user.setEmail(signupDto.getEmail());
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));


        Role roles = roleRepository.findByName(signupDto.getRole()).get();
        Set<Role>role=new HashSet<>();
        role.add(roles);
        user.setRoles(role);


        User saveuser = userRepository.save(user);

        SignupDto dto = new SignupDto();
        dto.setName(saveuser.getName());
        dto.setUsername(saveuser.getUsername());
        dto.setEmail(saveuser.getEmail());

        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
}
