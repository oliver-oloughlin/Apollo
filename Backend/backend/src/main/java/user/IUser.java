package user;

public interface IUser {
	
	int getID();
	
	String getName();
	
	/*
	 * set the role of the user, an administrator can be downgraded to an ordinary user, and visa verca.
	 */
	void setRole(Role role);
	
	/*
	 * Gets the role of the user;
	 */
	Role getRole();
	
}
