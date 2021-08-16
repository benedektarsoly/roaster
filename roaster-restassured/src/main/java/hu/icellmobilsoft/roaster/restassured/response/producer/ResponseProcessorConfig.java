/*-
 * #%L
 * Coffee
 * %%
 * Copyright (C) 2020 - 2021 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.roaster.restassured.response.producer;

import java.util.Optional;

import hu.icellmobilsoft.roaster.restassured.response.producer.spi.AbstractConfigurableResponseProcessor;

/**
 * {@link AbstractConfigurableResponseProcessor} configuration
 *
 * @author martin.nagy
 * @since 0.5.0
 */
public interface ResponseProcessorConfig {

    /**
     * Base URI config key. E.g.: {@literal project.service.base.uri}
     *
     * @return base URI config key
     */
    String getBaseUriKey();

    /**
     * HTTP path to call. E.g.: {@literal /foo/bar} if the URL is {@literal http://localhost/foo/bar}
     *
     * @return HTTP path to call
     */
    String getPath();

    /**
     * Optional HTTP request headers
     *
     * @return Optional HTTP request headers
     */
    Optional<String[]> getHeaders();

}
