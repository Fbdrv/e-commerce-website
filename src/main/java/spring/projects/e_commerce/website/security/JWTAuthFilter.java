package spring.projects.e_commerce.website.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.projects.e_commerce.website.dto.CustomerDto;
import spring.projects.e_commerce.website.entity.Customer;
import spring.projects.e_commerce.website.repository.CustomerRepository;
import spring.projects.e_commerce.website.service.CustomerService;
import spring.projects.e_commerce.website.service.JWTService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
    private final CustomerService customerService;
    private JWTService jwtService;
    private CustomerRepository customerRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            String username = jwtService.extractUsername(token);

            Optional<Customer> user = customerRepository.findByUsernameIgnoreCase(username);
            if (user.isPresent()) {
                CustomerDto customerDto = customerService.entityToDto(user.get());
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(customerDto, null, new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
