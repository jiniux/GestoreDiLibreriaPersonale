package it.jiniux.gdlp.infrastructure.inmemory;

import lombok.Getter;

import java.util.Stack;

public class InMemoryTransactionState {
    private final Stack<RollbackAction> rollbackActions = new Stack<>();

    public InMemoryTransactionState(boolean readOnly) {
        this.isReadOnly = readOnly;
    }

    @Getter
    private boolean isReadOnly = false;

    public void addRollbackAction(RollbackAction action) {
        rollbackActions.add(action);
    }

    public void rollback() {
        while (!rollbackActions.isEmpty()) {
            RollbackAction action = rollbackActions.pop();
            action.execute();
        }
    }
}
