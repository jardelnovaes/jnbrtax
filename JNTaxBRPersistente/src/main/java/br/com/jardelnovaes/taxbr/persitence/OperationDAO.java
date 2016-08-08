package br.com.jardelnovaes.taxbr.persitence;

import java.util.List;

import br.com.jardelnovaes.taxbr.models.Operation;

public interface OperationDAO extends GenericDAO<Operation> {
	List<Operation> getByTransactionTypeId(long transTypeId);
}
