package br.com.itau.api_transacao.infraestructure;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ResponseTimeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
        } finally {
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;

            String methodName = request.getMethod();
            String uri = request.getRequestURI();
            int statusCode = response.getStatus();

            System.out.println(
                    "Método Http: " + methodName +
                    "| URL: " + uri +
                    "| Status: " + statusCode +
                    "| Tempo de resposta: " + responseTime + " ms");
        }



    }
}
