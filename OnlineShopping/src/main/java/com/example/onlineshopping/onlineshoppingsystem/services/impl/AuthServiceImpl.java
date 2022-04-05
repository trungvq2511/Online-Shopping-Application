package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.onlineshopping.onlineshoppingsystem.dto.request.AccessTokenDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.request.UserRequestDTO;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.RefreshTokenDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.UserDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidTokenException;
import com.example.onlineshopping.onlineshoppingsystem.repositories.UserRepository;
import com.example.onlineshopping.onlineshoppingsystem.security.WebSecurityConfig;
import com.example.onlineshopping.onlineshoppingsystem.services.AuthService;
import com.example.onlineshopping.onlineshoppingsystem.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthServiceImpl(UserService userService,
                           UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void signUp(UserRequestDTO userRequestDTO) throws InvalidInputDataException {
        //check
        User userByEmail = userRepository.findUserByEmail(userRequestDTO.getEmail());
        if (userByEmail == null) {
            userService.saveNewUser(userRequestDTO);
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("email", "is taken");
            throw new InvalidInputDataException(errors);
        }
    }

    @Override
    public RefreshTokenDTOResponse refreshToken(AccessTokenDTORequest accessTokenDTORequest) throws InvalidTokenException {
        String accessTokenRequest = accessTokenDTORequest.getAccessToken();
        if (accessTokenRequest != null && accessTokenRequest.startsWith("Bearer ")) {
            //if token is valid
            try {
                String accessToken = accessTokenRequest.substring("Bearer ".length());
                //decode
                Algorithm algorithm = Algorithm.HMAC256(WebSecurityConfig.SECRET_KEY);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(accessToken);
                //authenticate
                String username = decodedJWT.getSubject();
                //check if user is exists
                UserDTOResponse user = userService.getUserByUsername(username);
                //create refresh token
                String refreshToken = JWT.create()
                        //subject
                        .withSubject(user.getEmail())
                        //issued
                        .withIssuedAt(new Date())
                        //expired date
                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 1000))
                        //roles
                        .withClaim("roles", user.getRole().stream().map(role -> role.getName().toString()).collect(Collectors.toList()))
                        .sign(algorithm);

                return new RefreshTokenDTOResponse(refreshToken);
            } catch (Exception e) {
                System.out.println("EXPIRED");
                throw new InvalidTokenException(e.getMessage());
            }
        } else {
            throw new InvalidTokenException("Token is invalid!");
        }
    }
}
