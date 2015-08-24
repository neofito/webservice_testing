package com.neofito;

import org.apache.ws.security.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

public class PWCBHandler implements CallbackHandler 
{
    public void handle(Callback [] callbacks) throws IOException, UnsupportedCallbackException {
		
        for (int i = 0; i < callbacks.length; i++) {

            WSPasswordCallback pwcb = (WSPasswordCallback) callbacks[i];
		
            if (pwcb.getUsage() == WSPasswordCallback.USERNAME_TOKEN_UNKNOWN) {
		
                if (pwcb.getIdentifier().equals("neofito") && pwcb.getPassword().equals("neofito")) {
                    return;
                } else {
                    throw new UnsupportedCallbackException(callbacks[i], "fallo en la comprobacion");
                }
            }
        }
    }
}
