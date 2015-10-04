package com.blogger;

import com.google.common.base.Preconditions;

public class ClientCredentials {

    public static final String KEY = "AIzaSyCJczKjCreeRowtgYHyohSVXlRDRFXJC8I";

    public static void errorIfNotSpecified() {
        Preconditions.checkNotNull(KEY,
                "Please enter your API key from https://code.google.com/apis/console/?api=tasks in "
                        + ClientCredentials.class);

    }
}
