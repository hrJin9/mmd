package com.mmd.oauth.client.util;

import com.mmd.domain.OAuthProvider;
import org.springframework.core.convert.converter.Converter;

public class OAuthConverter implements Converter<String, OAuthProvider> {
    @Override
    public OAuthProvider convert(String name) {
        return OAuthProvider.ofName(name);
    }
}
