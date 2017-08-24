package com.example.registration.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Alex on 20.08.2017.
 */

@ControllerAdvice
public class CustomAuthenticationFailureHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
//    @Override
//    protected ModelAndView doResolveException
//            (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        try {
//            if (ex instanceof DisabledException) {
////                return handleIllegalArgument((DisabledException) ex, response, handler);
//            }
//
//        } catch (Exception handlerException) {
//            logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
//        }
//        return null;
//    }

//    private ModelAndView handleIllegalArgument(DisabledException ex, HttpServletResponse response) throws IOException {
//        response.sendError(HttpServletResponse.SC_CONFLICT);
//        String accept = request.getHeader(HttpHeaders.ACCEPT);
//
//        return new ModelAndView();
//    }


//public class CustomAuthenticationFailureHandler implements UserDetailsChecker {
//    @Override
//    public void check(UserDetails userDetails) {
//        if (!userDetails.isEnabled()) {
//            throw new UserIsDisableException();
//        }
//    }

//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request,
//                                        HttpServletResponse response, AuthenticationException exception)
//            throws IOException, ServletException {
//
//        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
//            throw new UserIsDisableException();
//        }
//    }

//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        if (accessDeniedException.getMessage().equalsIgnoreCase("User is disabled")) {
//            throw new UserIsDisableException();
//        }
//    }

//
////        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new UserIsDisableException());
//
////        AuthenticationException ex = ((AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
////        if (ex instanceof DisabledException) {
//
//        if (exception.getClass().isAssignableFrom(DisabledException.class)) {
//            throw new UserIsDisableException();
//        }


}
