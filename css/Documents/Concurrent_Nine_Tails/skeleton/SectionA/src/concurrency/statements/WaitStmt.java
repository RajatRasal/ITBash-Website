package concurrency.statements;

import concurrency.Store;
import concurrency.expressions.Expr;

public class WaitStmt implements Stmt {

	private Expr rhs;
	private Expr lhs;

	public WaitStmt(Expr lhs, Expr rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public boolean isEnabled(Store store) {
		return (rhs.eval(store) == lhs.eval(store));
	}

	@Override
	public void execute(Store store) {
	}
}
