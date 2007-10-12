package com.apress.progwt.server.domain;

import java.util.Date;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.log4j.Logger;

import com.apress.progwt.client.domain.User;

/**
 * 
 * server side wrapper only. does NOT extend User
 * 
 * @author Jeff Dwyer
 * 
 */
public class ServerSideUser implements UserDetails {

	private static final Logger log = Logger.getLogger(ServerSideUser.class);

	private User user;

	public ServerSideUser(User u) {
		this.user = u;
	}

	public GrantedAuthority[] getAuthorities() {
		GrantedAuthority[] rtn = new GrantedAuthority[1];
		if (user.isSupervisor()) {
			log.debug("adding supervisor permissions");
			rtn[0] = new MyAuthority("ROLE_SUPERVISOR");
			return rtn;
		} else {
			rtn[0] = new MyAuthority("");
			return rtn;
		}

	}

	private class MyAuthority implements GrantedAuthority {
		private String auth;

		public MyAuthority(String auth) {
			this.auth = auth;
		}

		public String getAuthority() {
			return auth;
		}
	}

	public String getPassword() {
		return user.getPassword();
	}

	public String getUsername() {
		return user.getUsername();
	}

	public boolean isAccountNonExpired() {
		return user.isAccountNonExpired();
	}

	public boolean isAccountNonLocked() {
		return user.isAccountNonLocked();
	}

	public boolean isCredentialsNonExpired() {
		return user.isCredentialsNonExpired();
	}

	public boolean isEnabled() {
		return user.isEnabled();
	}

	public User getUser() {
		return user;
	}

	public long getId() {
		return user.getId();
	}

	public Date getDateCreated() {
		return user.getDateCreated();
	}

}
