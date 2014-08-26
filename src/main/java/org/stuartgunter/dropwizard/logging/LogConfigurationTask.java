/*
 * Copyright 2014 Stuart Gunter
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.stuartgunter.dropwizard.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.Task;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.List;

/**
 * Sets the logging {@link Level} for a number of {@link Logger}s
 */
public class LogConfigurationTask extends Task {

    private final LoggerContext loggerContext;

    /**
     * Creates a new LogConfigurationTask.
     */
    public LogConfigurationTask() {
        this((LoggerContext) LoggerFactory.getILoggerFactory());
    }

    /**
     * Creates a new LogConfigurationTask with the given {@link LoggerContext} instance.
     * <p/>
     * <b>Use {@link LogConfigurationTask#LogConfigurationTask()} instead.</b>
     *
     * @param loggerContext a {@link LoggerContext} instance
     */
    public LogConfigurationTask(LoggerContext loggerContext) {
        super("log-level");
        this.loggerContext = loggerContext;
    }

    @Override
    public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {
        List<String> loggerNames = getLoggerNames(parameters);
        Level loggerLevel = getLoggerLevel(parameters);

        for (String loggerName : loggerNames) {
            Logger logger = loggerContext.getLogger(loggerName);
            logger.setLevel(loggerLevel);
            output.println(String.format("Configured logging level for %s to %s", loggerName, loggerLevel));
            output.flush();
        }
    }

    private List<String> getLoggerNames(ImmutableMultimap<String, String> parameters) {
        return parameters.get("logger").asList();
    }

    private Level getLoggerLevel(ImmutableMultimap<String, String> parameters) {
        List<String> loggerLevels = parameters.get("level").asList();
        return loggerLevels.isEmpty() ? null : Level.valueOf(loggerLevels.get(0));
    }
}
