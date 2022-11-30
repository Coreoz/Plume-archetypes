package ${package}.jee;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.slf4j.Marker;

import java.util.Arrays;

/**
 * Change the log level of Jersey logs in case of problem when writing HTTP responses.
 * Indeed, it is not an application error, these errors do not concern the application,
 * and no application solution can be provided.
 *
 * To avoid raising an alert, these error logs are rewritten as warning logs.
 *
 * This code should be removed once https://github.com/eclipse-ee4j/jersey/issues/4420#issuecomment-1332087576
 * is accepted.
 */
public class JerseyLogbackFilter extends TurboFilter {
    @Override
    public FilterReply decide(Marker marker, ch.qos.logback.classic.Logger logger, Level level, String format, Object[] params, Throwable t) {
        if (level == Level.ERROR
            && "org.glassfish.jersey.server.ServerRuntime$Responder".equals(logger.getName())
            && (LocalizationMessages.ERROR_WRITING_RESPONSE_ENTITY().equals(format)
            || LocalizationMessages.ERROR_COMMITTING_OUTPUT_STREAM().equals(format)
            || LocalizationMessages.ERROR_WRITING_RESPONSE_ENTITY_CHUNK().equals(format)
            || LocalizationMessages.ERROR_CLOSING_COMMIT_OUTPUT_STREAM().equals(format)
        )) {
            Object[] paramsWithThrowable = params;
            if (t != null) {
                if (params != null) {
                    paramsWithThrowable = Arrays.copyOf(params, params.length + 1);
                    paramsWithThrowable[params.length] = t;
                } else {
                    paramsWithThrowable = new Object[] { t };
                }
            }
            // rewrite error log as warning log
            logger.warn(marker, format, paramsWithThrowable);
            return FilterReply.DENY;
        }
        return FilterReply.NEUTRAL;
    }
}
