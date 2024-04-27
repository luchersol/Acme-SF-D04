
package acme.entities.audits;

public enum Mark {

	A_PLUS, A, F, F_MINUS, B, C;


	@Override
	public String toString() {
		return this.equals(A_PLUS) ? "A+" : this.equals(F_MINUS) ? "F-" : super.toString();
	}
}
