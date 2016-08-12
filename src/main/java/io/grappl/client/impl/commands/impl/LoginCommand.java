package io.grappl.client.impl.commands.impl;

import io.grappl.client.api.commands.Command;
import io.grappl.client.impl.Application;
import io.grappl.client.impl.ApplicationState;
import io.grappl.client.impl.authentication.AuthenticationException;
import io.grappl.client.impl.authentication.Authentication;
import io.grappl.client.impl.authentication.Authenticator;

public class LoginCommand implements Command {

    @Override
    public void runCommand(ApplicationState state, String[] args) {

        ApplicationState applicationState = Application.getApplicationState();

        final String username = args[1];
        final String password = args[2];


        try {
            Authentication authentication = Authenticator.login(username, Authenticator.formatPassword(password));

            if(authentication.isLoggedIn()) {
                Application.getLog().log("Logged in as " + username);
                applicationState.useAuthentication(authentication);
            } else {
                Application.getLog().log("Use command 'register' to make an account (launches web browser).");
            }
        } catch (AuthenticationException e) {
            Application.getLog().log(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Allows a user to log in to their account";
    }
}
