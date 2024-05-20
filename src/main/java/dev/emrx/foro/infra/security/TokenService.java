package dev.emrx.foro.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.emrx.foro.domain.usuario.DatosTokenJwt;
import dev.emrx.foro.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    @Value("${api.security.issuer}")
    private String nameIssuer;

    public DatosTokenJwt generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            Instant fechaExpiracion = generarFechasExpiracion();
            String tokenJWT = JWT.create()
                    .withIssuer(nameIssuer)
                    .withSubject(usuario.getCorreo())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(fechaExpiracion)
                    .sign(algorithm);
            return new DatosTokenJwt(tokenJWT, fechaExpiracion.toString());
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {
        if(token == null || token == "") {
            throw new RuntimeException("El token no es valido");
        }

        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // validando la firma
            verifier = JWT.require(algorithm)
                    .withIssuer(nameIssuer)
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception){
            System.out.println(exception.toString());
        }

        if(verifier == null || verifier.getSubject() == null) {
            throw new RuntimeException("Verifier invalido");
        }

        return verifier.getSubject();
    }

    private Instant generarFechasExpiracion() {
        return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-05:00"));
    }
}
