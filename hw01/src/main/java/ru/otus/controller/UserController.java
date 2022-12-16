package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.otus.model.User;
import ru.otus.repository.UserRepository;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.representations.AccessToken;

import javax.ws.rs.core.Response;

import org.keycloak.admin.client.resource.UserResource;


@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserRepository userRepository;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.auth-server-url}")
    private String authUrl;

    @GetMapping("{id}")
    User getUser(@PathVariable("id") String id) {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal principal=(KeycloakPrincipal)token.getPrincipal();
        if (!id.equals(principal.getName())) {
            throw new AccessDeniedException("denied");
        }
        return userRepository.findById(id).get();
    }

    @PostMapping
    User createUser(@RequestBody User user) {
        UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setUsername(user.getName());
        userRepresentation.setFirstName(user.getName());
        userRepresentation.setLastName(user.getName());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);

        Response response = getAdminKeycloakUser().realm(realm).users().create(userRepresentation);
        //If user is created successfully 200 is returned for response status.

        //Set password flow
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        String userId = CreatedResponseUtil.getCreatedId(response);
        passwordCred.setTemporary(false);
        passwordCred.setType("password");
        passwordCred.setValue(user.getPassword());
        UserResource userResource = getAdminKeycloakUser().realm(realm).users().get(userId);
        userResource.resetPassword(passwordCred);
        user.setId(userId);

        return userRepository.save(user);
    }

    @PutMapping("{id}")
    User updateUser(@PathVariable("id") String id, @RequestBody User user) {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal principal=(KeycloakPrincipal)token.getPrincipal();
        if (!id.equals(principal.getName())) {
            throw new AccessDeniedException("denied");
        }

        user.setId(id);
        return userRepository.save(user);
    }

    @DeleteMapping("{id}")
    void deleteUser(@PathVariable("id") String id) {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal principal=(KeycloakPrincipal)token.getPrincipal();
        if (!id.equals(principal.getName())) {
            throw new AccessDeniedException("denied");
        }

        userRepository.deleteById(id);
    }

    public Keycloak getAdminKeycloakUser() {
        return KeycloakBuilder.builder().serverUrl(authUrl)
                .grantType("password").realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(clientId)
                .password(clientId)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
    }
}
