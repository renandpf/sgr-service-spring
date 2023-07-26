package br.com.pupposoft.fiap.sgr.config.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.com.pupposoft.fiap.starter.exception.ExceptionJson;
import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandlerConfiguration {

	private static final String AUTH_CONSTRAINT_VIOLATION = "constraintViolation";
	private static final String AUTH_EXCEPTION_HANDLER_ERROR_TO_RESOLVE_EXCEPTION = "exceptionHandler.errorToResolveException";
	private static final String AUTH_ARGUMENT_NOT_VALID = "argumentNotValid";
	private static final String ERROR_TO_RESOLVE_EXCEPTION_HANDLER = "Error to resolve exception handler";
	private static final String MISSING_PARAM = "missingParam";
	private static final String BODY_INCORRECT_VALUE = "body.incorrectValue";


	@ExceptionHandler({ SystemBaseException.class})
	@ResponseBody
	public ResponseEntity<ExceptionJson> sasException(final SystemBaseException e, final HttpServletResponse response) {
		final ExceptionJson exceptionJson = new ExceptionJson(e);
		return new ResponseEntity<>(exceptionJson, new HttpHeaders(), e.getHttpStatus());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public ExceptionJson genericError(final Throwable e) {
		log.error(e.getMessage(), e);
		return new ExceptionJson(HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	}
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ExceptionJson handleHttpMessageNotReadableException(final HttpMessageNotReadableException ex) {
		log.error(ex.getMessage(), ex);

		if ( !StringUtils.hasLength(ex.getMessage()) && ";".contains(ex.getMessage())) {

			final String[] messageSplited = StringUtils.split(ex.getMessage(), ";");
			
			if(messageSplited != null) {
				final Matcher matcher = Pattern.compile("(JSON parse error: Cannot deserialize value of type (.*)) from ((.*))") //NOSONAR
						.matcher(messageSplited[0]);
				
				if (matcher.matches()) {
					return new ExceptionJson(BODY_INCORRECT_VALUE, matcher.group(3));
				}
			}
			
		}

		return new ExceptionJson(BODY_INCORRECT_VALUE, ex.getLocalizedMessage());
	}

	@ExceptionHandler(ServletRequestBindingException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ExceptionJson handleRequiredParameters(final ServletRequestBindingException ex) {
		log.error(ex.getMessage(), ex);
		return new ExceptionJson(MISSING_PARAM, ex.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseBody
	public ExceptionJson handlerMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
		log.error(e.getMessage(), e);
		return new ExceptionJson(e.getName(), "Failed to convert " + e.getValue());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ExceptionJson validationException(final MethodArgumentNotValidException methodArgumentNotValidException) {
		log.error(methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);
		try {
			final StringBuilder errors = new StringBuilder();
			
			methodArgumentNotValidException
				.getBindingResult()
				.getFieldErrors()
				.forEach(field -> errors.append(field.getField() + ":" + field.getDefaultMessage()+";"));
			
			return new ExceptionJson(AUTH_ARGUMENT_NOT_VALID, errors.toString());
			
		} catch (Exception exception) {
			log.error(ERROR_TO_RESOLVE_EXCEPTION_HANDLER, exception);
			return new ExceptionJson(AUTH_EXCEPTION_HANDLER_ERROR_TO_RESOLVE_EXCEPTION, exception.getMessage());
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ExceptionJson violationException(final ConstraintViolationException e) {
		log.error(e.getMessage(), e);
		final StringBuilder errors = new StringBuilder();

		e.getConstraintViolations().forEach(
				constraintViolation -> errors.append(constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage()+";"));

		return new ExceptionJson(AUTH_CONSTRAINT_VIOLATION, errors.toString());
	}

	
}
