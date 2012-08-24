package org.mitre.rhex.oauth2;

public interface AuthorizationChecker {
	public boolean checkAuthorization(String clientId, String token);
}