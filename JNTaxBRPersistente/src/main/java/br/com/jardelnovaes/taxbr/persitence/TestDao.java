package br.com.jardelnovaes.taxbr.persitence;

import java.util.List;

import br.com.jardelnovaes.taxbr.models.TestEntity;

public interface TestDao {
	List<TestEntity> getAll();
	TestEntity getById(int id);
	void save(TestEntity entity) throws Exception;
}
