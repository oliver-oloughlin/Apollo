package user;

public class User implements IUser {
	
	int ID;
	String NAME;
	Role role = Role.user;
	
	//NOTE: maybe use a builder here?
	public User(int id, String name) {
		this.ID = id;
		this.NAME = name;
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public Role getRole() {
		return role;
	}

}
