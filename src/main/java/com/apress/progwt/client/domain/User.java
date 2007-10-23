package com.apress.progwt.client.domain;

//Generated Jul 18, 2006 12:44:47 PM by Hibernate Tools 3.1.0.beta4

import com.apress.progwt.client.domain.generated.AbstractUser;
import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * User generated by hbm2java
 */


/**
 * Extended by ServerSideUser on the Server to implement UserDetails, Since getting the
 * acegisecurity jar into client side land was a no go.
 * 
 */
public class User extends AbstractUser implements IsSerializable {


	public User() {
		setEnabled(true);
		setSupervisor(false);
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}


	// @Override
	public String toString() {
		return getId() + " " + getUsername();
	}

	/**
	 * TODO PEND HIGH
	 */
	public String getNickname() {
		if (isOpenID()) {
			String half = getUsername().replaceAll("http://", "");
			return half.replaceAll("/", "");
		}
		return getUsername();
	}

	private boolean isOpenID() {
		return getPassword() == null;
	}

}