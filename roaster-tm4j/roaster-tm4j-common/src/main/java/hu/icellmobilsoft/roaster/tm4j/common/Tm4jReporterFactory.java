/*-
 * #%L
 * Coffee
 * %%
 * Copyright (C) 2020 i-Cell Mobilsoft Zrt.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package hu.icellmobilsoft.roaster.tm4j.common;

import hu.icellmobilsoft.roaster.tm4j.common.config.Tm4jReporterConfig;
import hu.icellmobilsoft.roaster.tm4j.common.config.Tm4jReporterServerConfig;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class Tm4jReporterFactory {
    public Tm4jReporter createReporter() {
        Config roasterConfig = ConfigProvider.getConfig();

        Tm4jReporterConfig config = mapConfig(roasterConfig);

        return config.isEnabled() ?
                new DefaultTm4jReporter(config) :
                new NoopTm4jReporter();
    }

    private Tm4jReporterConfig mapConfig(Config roasterConfig) {
        Tm4jReporterConfig config = new Tm4jReporterConfig();

        config.setServer(createServerConfig(roasterConfig));

        config.setEnabled(roasterConfig.getOptionalValue("roaster.tm4j.enabled", Boolean.class).orElse(true));
        config.setProjectKey(roasterConfig.getOptionalValue("roaster.tm4j.projectKey", String.class).orElse(null));
        config.setTestCycleKey(roasterConfig.getOptionalValue("roaster.tm4j.testCycleKey", String.class).orElse(null));
        config.setEnvironment(roasterConfig.getOptionalValue("roaster.tm4j.environment", String.class).orElse("N/A"));

        return config;
    }

    private Tm4jReporterServerConfig createServerConfig(Config roasterConfig) {
        Tm4jReporterServerConfig serverConfig = new Tm4jReporterServerConfig();
        serverConfig.setBaseUrl(roasterConfig.getOptionalValue("roaster.tm4j.server.url", String.class).orElse(null));
        serverConfig.setBasicAuthToken(roasterConfig.getOptionalValue("roaster.tm4j.server.basicAuthToken", String.class).orElse(null));
        serverConfig.setUserName(roasterConfig.getOptionalValue("roaster.tm4j.server.userName", String.class).orElse(null));
        serverConfig.setPassword(roasterConfig.getOptionalValue("roaster.tm4j.server.password", String.class).orElse(null));
        return serverConfig;
    }
}
