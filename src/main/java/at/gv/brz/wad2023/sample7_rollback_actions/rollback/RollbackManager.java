package at.gv.brz.wad2023.sample7_rollback_actions.rollback;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Optional;

@Component
public class RollbackManager {

    /**
     * Adds a rollback action to the current transaction within the actual {@link ThreadLocal}.
     * The rollback action is executed, whenever the current transaction is marked for rollback.
     *
     * @param rollbackAction that should be executed when the current transaction is marked for rollback
     * @see RollbackTransactionSynchronization
     */
    public void addRollbackAction(Runnable rollbackAction) {
        Optional<RollbackTransactionSynchronization> rollbackSynchronizationOptional = TransactionSynchronizationManager.getSynchronizations().stream()
                .filter(ts -> ts instanceof RollbackTransactionSynchronization)
                .map(ts -> (RollbackTransactionSynchronization) ts).findFirst();

        RollbackTransactionSynchronization rollbackSynchronization;
        if (!rollbackSynchronizationOptional.isPresent()) {
            rollbackSynchronization = new RollbackTransactionSynchronization();
            TransactionSynchronizationManager.registerSynchronization(rollbackSynchronization);
        } else {
            rollbackSynchronization = rollbackSynchronizationOptional.get();
        }

        rollbackSynchronization.addRollbackAction(rollbackAction);
    }
}