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
package hu.icellmobilsoft.roaster.restassured.response.producer.spi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.roaster.restassured.response.ResponseProcessor;
import hu.icellmobilsoft.roaster.restassured.response.producer.ResponseProcessorConfig;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * Configurable {@link ResponseProcessor}
 *
 * @param <RESPONSE>
 *            response class (any type)
 * @author martin.nagy
 * @since 0.5.0
 */
public abstract class AbstractConfigurableResponseProcessor<RESPONSE> extends ResponseProcessor<RESPONSE> {
    private static final String HEADER_DELIMITER = ":";

    private String baseUriKey;
    private String path;
    private Headers headers;
    private int expectedStatusCode;
    private Map<String, String> queryParams;

    /**
     * Initializes the configurable values
     *
     * @param config
     *            Configuration class populated with microprofile config valuest
     * @throws BaseException
     *             exception
     */
    public void setConfig(ResponseProcessorConfig config) throws BaseException {
        if (Objects.isNull(config)) {
            throw new BaseException(CoffeeFaultType.INVALID_INPUT, "ERROR_MSG_CONVERTER_IS_NULL");
        }

        baseUriKey = config.getBaseUriKey();
        path = config.getPath();

        Optional<String[]> headersOpt = config.getHeaders();
        headers = headersOpt.map(this::parse).orElse(null);
    }

    private Headers parse(String[] headerStrings) {
        List<Header> headerList = new ArrayList<>();
        for (String headerString : headerStrings) {
            String[] split = headerString.split(HEADER_DELIMITER);
            headerList.add(new Header(split[0].trim(), split[1].trim()));
        }
        return new Headers(headerList);
    }

    @Override
    protected RequestSpecification createRequestSpecification(RequestSpecification initRequestSpecification) {
        RequestSpecification requestSpecification = super.createRequestSpecification(initRequestSpecification);
        if (headers != null) {
            requestSpecification.headers(headers);
        }
        if (queryParams != null) {
            requestSpecification.queryParams(queryParams);
        }
        return requestSpecification;
    }

    @Override
    protected RESPONSE toResponse(Response response, Class<RESPONSE> responseClass, ResponseSpecification iniResponseSpecification) {
        iniResponseSpecification.statusCode(expectedStatusCode);
        return super.toResponse(response, responseClass, iniResponseSpecification);
    }

    @Override
    public String baseUriKey() {
        return baseUriKey;
    }

    @Override
    public String path() {
        return path;
    }

    public void setExpectedStatusCode(int expectedStatusCode) {
        this.expectedStatusCode = expectedStatusCode;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }
}
