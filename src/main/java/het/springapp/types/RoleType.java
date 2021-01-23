package het.springapp.types;

public enum RoleType {
	 SUPER,
	 ADMIN,
	 USER;
	
	public static RoleType getRoleType(Integer roleId) {
		if (roleId == 0) {
			return RoleType.SUPER;
		} else if (roleId == 1) {
			return RoleType.ADMIN;
		}
		
		return RoleType.USER;
	}
}
