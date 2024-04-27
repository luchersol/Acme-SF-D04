
package acme.entities.project;

public enum Priority {

	MUST, SHOULD, COULD, WONT;


	@Override
	public String toString() {
		return switch (this) {
		case MUST -> "Must";
		case COULD -> "Could";
		case SHOULD -> "Should";
		case WONT -> "Won't";
		default -> throw new IllegalArgumentException("Unexpected value: " + this);
		};
	}
}
