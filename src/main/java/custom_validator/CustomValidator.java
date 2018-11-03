package custom_validator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@AutoApplySession
@ApplicationScoped
public class CustomValidator implements HttpAuthenticationMechanism {
	
	@Inject
    private IdentityStoreHandler ish;
	
	@Override
	public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response,
			HttpMessageContext httpMessageContext) throws AuthenticationException {
		
		
		if (httpMessageContext.isAuthenticationRequest()) {
			
			Credential credential = httpMessageContext.getAuthParameters().getCredential();
			
			CredentialValidationResult validate = ish.validate(credential);
			if(validate.getStatus().equals(CredentialValidationResult.Status.VALID)) {
				return httpMessageContext.notifyContainerAboutLogin(validate);
			}
			return httpMessageContext.responseUnauthorized();
		}
		
		return httpMessageContext.doNothing();
		
	}
}
