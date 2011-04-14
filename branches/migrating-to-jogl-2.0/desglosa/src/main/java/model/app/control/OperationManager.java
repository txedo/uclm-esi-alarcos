package model.app.control;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import model.app.knowledge.Operation;

import persistence.dao.app.OperationDAO;

public class OperationManager {
	public static List<Operation> getAll () throws ConfigurationException {
		OperationDAO odao = new OperationDAO();
		return odao.getAll();
	}
}
