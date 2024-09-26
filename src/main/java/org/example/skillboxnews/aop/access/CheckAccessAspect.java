package org.example.skillboxnews.aop.access;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.skillboxnews.exception.ForbiddenException;
import org.example.skillboxnews.security.AppUserPrincipal;
import org.example.skillboxnews.service.CheckAccessService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class CheckAccessAspect {
    private final CheckAccessService checkAccessService;

    private static Long getId(HttpServletRequest request) {
        Map<String, String> attribute = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (attribute == null) {
            throw new IllegalArgumentException("URI template variables not found");
        }
        String stringId = attribute.get("id");
        if (stringId == null) {
            throw new IllegalArgumentException("Path variable 'id' is null");
        }
        long id;
        try {
            id = Long.parseLong(stringId);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Path variable 'id' is not a number");
        }
        return id;
    }

    private static AppUserPrincipal getPrincipal() {
        return (AppUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Before("@annotation(checkAccess)")
    public void checkAccess(final JoinPoint joinPoint, final CheckAccess checkAccess) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            throw new IllegalArgumentException("RequestAttributes is null");
        }

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Long id = getId(request);
        AppUserPrincipal principal = getPrincipal();
        if (!checkAccessService.accessible(checkAccess, id, principal)) {
            throw new ForbiddenException("Access denied");
        }
    }
}
