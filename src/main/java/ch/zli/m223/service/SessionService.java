package ch.zli.m223.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import ch.zli.m223.model.CwSUser;
import io.smallrye.jwt.build.Jwt;
import javax.ws.rs.core.NewCookie;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.HashSet;

@ApplicationScoped
public class SessionService {
    
    @Inject
    CwSUserService cwSUserService;

    public Response authenticate(CwSUser cwSUser) {
        Optional<CwSUser> principal = cwSUserService.findByEmail(cwSUser.getEmail());

        try {
            if (principal.isPresent() && principal.get().getPassword().equals(cwSUser.getPassword())) {
                String token = Jwt
                    .issuer("https://zli.example.com/")
                    .upn(cwSUser.getEmail())
                    .groups(new HashSet<>(Arrays.asList("User", "Admin")))
                    .expiresIn(Duration.ofHours(12))
                    .sign();
                return Response
                    .ok(principal.get())
                    .cookie(new NewCookie("coworkingspace", token))
                    .header("Authorization", "Bearer " + token)
                    .build();
            }
        } catch (Exception e) {
            System.err.println("Couldn't validate password.");
        }

        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
