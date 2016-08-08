package br.com.jardelnovaes.taxbr.services;

import java.util.List;

import br.com.jardelnovaes.taxbr.models.Operation;

public interface OperationService extends GenericService<Operation> {
	List<Operation> getByTransactionTypeId(long transTypeId);
}
