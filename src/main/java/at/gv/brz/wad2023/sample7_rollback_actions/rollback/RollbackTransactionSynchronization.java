package at.gv.brz.wad2023.sample7_rollback_actions.rollback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronization;

import java.util.ArrayList;
import java.util.List;

// Sample 7
class RollbackTransactionSynchronization implements TransactionSynchronization {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(RollbackTransactionSynchronization.class);
 
    private final List<Runnable> rollbackActions = new ArrayList<>();
 
    @Override
    public void afterCompletion(int status) {
        if (status == TransactionSynchronization.STATUS_ROLLED_BACK) { // (1) execute tasks only if the tx was rolled back
            for (Runnable task : this.rollbackActions) {
                try {
                    task.run();
                } catch (Exception e) {
                    LOGGER.error("error during execution of rollback-action", e);
                }
            }
        }
    }
 
    public void addRollbackAction(Runnable task) {
        rollbackActions.add(0, task);
    } // (2) add rollback action to list
}